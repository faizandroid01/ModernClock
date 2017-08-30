package com.master.faiz.alarmclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class RepeatationDialogActivity extends Activity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeatation_dialog);

        //Setting ,,trying different thing
        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);
        int width = dp.widthPixels;
        int height = dp.heightPixels;
        getWindow().setLayout((int) (0.75 * width), (int) (0.30 * height));


        radioGroup = (RadioGroup) findViewById(R.id.add_alarm_repeat_radio_group);
        ImageView done = (ImageView) findViewById(R.id.add_alarm_repeat_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);


                Intent intent = new Intent();
                intent.putExtra("selectedOption", radioButton.getText().toString().trim());
                setResult(RESULT_OK, intent);
                finish();
            }


        });

    }
}
