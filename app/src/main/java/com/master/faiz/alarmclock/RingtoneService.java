package com.master.faiz.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by faiz on 27/8/17.
 */

public class RingtoneService extends Service {

    MediaPlayer mp;
    int startId=0;
    boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //fetching the extra state value sent from AddAlarmActivty.java to Alarm Receiver and then finally to Ringtone Service
        String state = intent.getExtras().getString("extra");
        // Getting the hour and minute selected through the abve chain.
        String clock_hour = intent.getExtras().getString("hourSelected");
        String clock_min = intent.getExtras().getString("minuteSelected");



        // Notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, AddAlarmActivity.class);

        PendingIntent pendingIntentNotification = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification no = new Notification.Builder(this)
                .setContentTitle("Your Alarm at  " + clock_hour + ":" + clock_min)
                .setContentText("Click Me")
                .setSmallIcon(R.drawable.alarm)
                .setContentIntent(pendingIntentNotification)
                .setAutoCancel(true)
                .build();


        // This converts extra string from intent to startId valtiues 0 and  1

        assert state != null;

        switch (state) {
            case "alarm_on":
                startId = 1;
                break;
            case "alarm_off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        /*
           If else stmt to  control the behaviour of playing music n all
        1 - If there is no music playing and the user press alarm on , music should start to play
        2-  if there is music playing and the user press alarm off  ,  then it should stop playing.
        3 - If the user presses random button to bug proof the app , If there is no music playing and the user press alarm off, do nothing
        4- If there is the music playing and the user presses alarm on - do nothing .
        5 - Catch the odd events
         */


        if (!this.isRunning && startId == 1) {
            // create instance of the media player

            mp = MediaPlayer.create(this, R.raw.despacito_ringtone);
            mp.start();


            this.isRunning = true;
            this.startId = 0;

            nm.notify(0, no);


        } else if (this.isRunning && startId == 0) {
            // stop the music ringtone

            mp.stop();
            mp.reset();

            this.isRunning = false;
            this.startId = 0;

        } else if (!this.isRunning && startId == 0) {


            this.isRunning = false;
            this.startId = 0;
            Toast.makeText(this, "alarm is already in of state", Toast.LENGTH_SHORT).show();
        } else if (this.isRunning && startId == 1) {

            this.isRunning = true;
            this.startId = 1;
            Toast.makeText(this, "Alarmm is already started", Toast.LENGTH_SHORT).show();
        } else {

        }


        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "onDestroy called", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        this.isRunning = false;
    }
}
