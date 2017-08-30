package com.master.faiz.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    Context context;
    public RecyclerView recyclerView;
    public AlarmAdapter alarmAdapter;
    ArrayList<String> alarmTime;
    ArrayList<String> alarmRepeat;
    ArrayList<String> alarmLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Array list to add data from AddAlarmActivity's components to recycler view
        alarmTime=new ArrayList<>();
        alarmRepeat=new ArrayList<>();
        alarmLabel=new ArrayList<>();

        addData();

        //Action Bar related stuffs ******************************************************
        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(R.dimen.alarm_appbar_elevation);

        context = this;
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle("Alarm");
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.collapsedTitleTextColorAlarm));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.collapsedTitleTextColorAlarm));
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary));

        //*******************************Recycler View Setup **************************
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecyclerView);
        alarmAdapter=new AlarmAdapter(getApplicationContext(),getData());

        //recycler view
        recyclerView.setAdapter(alarmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(context, "onClick : "+position, Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(context, "onLongClick : "+position, Toast.LENGTH_SHORT).show();

            }
        }));

    }
    public  void addData()
    {
        String addAlarmGetTime =getIntent().getExtras().getString("add_alarm_get_time");
        String addAlarmGetLabel =getIntent().getExtras().getString("add_alarm_get_label");
        String addAlarmGetOccurences =getIntent().getExtras().getString("add_alarm_get_occurences");
        alarmTime.add(addAlarmGetTime);
        alarmRepeat.add(addAlarmGetOccurences);
        alarmLabel.add(addAlarmGetLabel);
    }


    public  List<Information> getData()  // changed static to non static
    {

      // ArrayList<String> occurrences =new ArrayList<>();







   /*  String  occurrences[]={"Wake Up","Sleep Now","Call Her","Love Her","Send a msg"};
        String  times[]={"12:08","23:09","11:56","03:00","04:00"};
        String  titles[]={"Everyday","Once","Mon","Mon","Sun"};*/

        // create three array list to which recycler view data will be stored
        List<Information> data= new ArrayList<>();
        for(int i=0; i<alarmTime.size() && i<alarmLabel.size() && i<alarmRepeat.size() ;i++){
  Information current=new Information();
        /*    current.timeId=alarmTime[i];
            current.titleId=alarmTime[i];
            current. occurenceId=alarmLabel[i];*/

            data.add(current);
        }
        return data;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_alarm,menu);
        return true;
    }
    public void onFab(View v)
    {
       startActivity(new Intent(this,AddAlarmActivity.class));
    }



     //touch events for recycler view

   static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener (Context context, final RecyclerView recyclerView, final ClickListener clickListener)
        {

            this.clickListener=clickListener;
                gestureDetector =new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){


            // SImpleOnGestureListener is used so that we could have flexibility to override on onclick and onLongClick methodfrom the list


                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {


                         return true;

                    }

                    @Override
                    public void onLongPress(MotionEvent e) {

                   View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(child!=null && clickListener!=null)
                        {
                            clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                        }


                    }
                });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child=rv.findChildViewUnder(e.getX(),e.getY());

                 if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e))
                 {
                     clickListener.onClick(child,rv.getChildPosition(child));
                 }



            return false;
        }





        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public static interface ClickListener
    {
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }






}
