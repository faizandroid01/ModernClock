package com.master.faiz.alarmclock;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    TextView repeat, ringtone;
    ImageView imText1, imText2, voiceRecog,stopAlarm,deleteAlarm;
    Switch vibrate;
    Button ok, cancel;

    protected static final int RES_SPEECH = 1;
    protected static final int REPEAT_RESULT = 2;
    EditText speech;

    AlarmManager alarm_Manager;
    TextView setTimeTextView;
    static final int DIALOG_ID = 0;
    int hour_x;
    int minute_x;
    PendingIntent pendingIntent;
    Intent my_Intent;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        vibrate = (Switch) findViewById(R.id.alarm_vibrate_switch);
        repeat = (TextView) findViewById(R.id.set_activity_repeat);
        ringtone = (TextView) findViewById(R.id.set_activity_ringtone);
        imText1 = (ImageView) findViewById(R.id.set_activity_imgView1);
        imText2 = (ImageView) findViewById(R.id.set_activity_imgView2);
        voiceRecog = (ImageView) findViewById(R.id.set_activity_voiceRecog);
        deleteAlarm = (ImageView) findViewById(R.id.set_activity_alarm_delete_alarm);
        stopAlarm = (ImageView) findViewById(R.id.set_activity_alarm_stop_alarm);

        ok = (Button) findViewById(R.id.set_activity_OK);
        cancel = (Button) findViewById(R.id.set_activity_Cancel);
        speech = (EditText) findViewById(R.id.set_activity_alarm_speech_text);
        setTimeTextView = (TextView) findViewById(R.id.set_activity_time);

        alarm_Manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Setting Alarm
        calendar=Calendar.getInstance();



        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        imText1.setOnClickListener(this);
        imText2.setOnClickListener(this);
        vibrate.setOnClickListener(this);
        voiceRecog.setOnClickListener(this);
        deleteAlarm.setOnClickListener(this);
        stopAlarm.setOnClickListener(this);
        showTimePickerDialog();
 //        intent to call alarmm reciver
         my_Intent=new Intent(AddAlarmActivity.this,AlarmReceiver.class);

   }

    public void showTimePickerDialog()
    {

        setTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              showDialog(DIALOG_ID);

 }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == ok) {

         /*   calendar.set(Calendar.HOUR_OF_DAY,alarm_TimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE,alarm_TimePicker.getCurrentMinute());

            int hour=alarm_TimePicker.getCurrentHour();
            int min=alarm_TimePicker.getCurrentMinute();

            String hour_string =String.valueOf(hour);
            String min_string =String.valueOf(min);*/
      // Creating a pending Intent that delays the intent untill the specific time  and

            /* put in extra string into myIntent
            tell the clock that alarm on is pressssedm*/
            my_Intent.putExtra("extra","alarm_on");
            my_Intent.putExtra("hour_selected",""+hour_x);
            my_Intent.putExtra("minute_selected",""+minute_x);

         pendingIntent = PendingIntent.getBroadcast(AddAlarmActivity.this,0,my_Intent,PendingIntent.FLAG_UPDATE_CURRENT);


            // set the Alarm Manager
            alarm_Manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            setAlarmText("Alarm is set to  "+hour_x+":"+minute_x);


        }


        if (v == cancel) {

          AlertDialog ad= new AlertDialog.Builder(this).setMessage("Discard all changes")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(AddAlarmActivity.this, MainActivity.class));

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(R.drawable.warning)
                    .setTitle("Warning")
                  .create();
            ad.show();


        }
        if (v == imText1) {
            //Open a dialog having repeatation checked
         /*  RepeatationDialogActivity repeatationDialogActivity=new RepeatationDialogActivity();
            repeatationDialogActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/


            // invoking the start Activity for result to get the input of repeat
            startActivityForResult(new Intent(this, RepeatationDialogActivity.class), REPEAT_RESULT);


        }
        if (v == imText2) {    // open an activity to select ringtone from inside or set the default


        }
        if (v == vibrate) {
            vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {


                    }else
                        {

                        }
                }
            });
        }
        if (v == voiceRecog) {

            // Animation part
            final Animation anim = AnimationUtils.loadAnimation(AddAlarmActivity.this, R.anim.bounce);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 30);
            anim.setInterpolator(interpolator);
            voiceRecog.startAnimation(anim);
            //Speech part
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                /*
                 intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                 intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                 intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
                */

            try {
                startActivityForResult(intent, RES_SPEECH);
                speech.setText("");


            } catch (ActivityNotFoundException e) {
                Toast.makeText(AddAlarmActivity.this, "Oops! Your device doesn't support Voice Recognition .", Toast.LENGTH_SHORT).show();
            }

        }
        if(v==stopAlarm)
        {


             setAlarmText("Alarm Off");
            //cancels alarm
            alarm_Manager.cancel(pendingIntent);

            //put extra string in intent and tell the clock to be off
            my_Intent.putExtra("extra","alarm_off");
            //stops the ringtone


            sendBroadcast(my_Intent);


        }if(v==deleteAlarm)
        {     setAlarmText("Alarm Deleted");



        }
    }

    // need to show when ok button is clicked
    private void setAlarmText(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

    }

    // onCreate Dialog to get the id of the some picker dialog
    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == DIALOG_ID) {
            return new TimePickerDialog(AddAlarmActivity.this, onTimePickerListener, hour_x, minute_x, false);

        } else

            return null;
    }

    // Listener used in onCreateDialog method to set the time
    protected TimePickerDialog.OnTimeSetListener onTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

           hour_x = hourOfDay;
            minute_x = minute;

            calendar.set(Calendar.HOUR_OF_DAY, hour_x);
            calendar.set(Calendar.MINUTE, minute_x);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);


            setTimeTextView.setText(""+hour_x+" : "+""+minute_x);
            //  Toast.makeText(AddAlarmActivity.this, "" + hour_x + " : " + "" + minute_x, Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RES_SPEECH: {
                if (resultCode == RESULT_OK && data != null) {
                    final ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    AlertDialog ad = new AlertDialog.Builder(AddAlarmActivity.this)
                            .setTitle("if ?")
                            .setMessage(text.get(0))
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    Toast.makeText(AddAlarmActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    speech.setText(text.get(0));
                                }
                            })
                            .setCancelable(false)
                            .create();
                    ad.show();

                    //textView.setText(text.get(0));
                }
            }
            case REPEAT_RESULT: {

                if (data != null) {
                    String repeatText = data.getStringExtra("selectedOption");
                    repeat.setText(repeatText);


                }

            }


        }
    }






}
