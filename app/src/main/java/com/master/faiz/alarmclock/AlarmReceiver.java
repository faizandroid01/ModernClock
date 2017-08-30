package com.master.faiz.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by faiz on 26/8/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Fetch extras details of intent
        String get_string=intent.getExtras().getString("extra");
        String get_hourSelected=intent.getExtras().getString("hour_selected");
        String get_minuteSelected=intent.getExtras().getString("minute_selected");


        // Create an intent to ringtone service

 Intent ringtone_service = new Intent(context, RingtoneService.class);


        // pass the extra string from AddAlarm Activity to Ringtone Service
        ringtone_service.putExtra("extra",get_string);
        ringtone_service.putExtra("hourSelected",get_hourSelected);
        ringtone_service.putExtra("minuteSelected",get_minuteSelected);


        // start the service
           context.startService(ringtone_service);

    }}
