package com.betelgeuse.corp.vidgetjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.betelgeuse.corp.vidgetjava.widgets.ViewTest;

//class myJobTest extends JobService{
//
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
//        Log.i("TEST", "TEST WIDGET");
//        jobFinished(params, false);
//        return true;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
//        Log.i("TEST", "TEST WIDGET");
//        return true;
//    }
//}

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("TEST", "TEST TEST TICK");
                handler.postDelayed(this,5000);
            }
        };
        handler.post(runnable);

//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, ViewTest.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast
//                (this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//        alarmManager.setRepeating
//                (AlarmManager.RTC,System.currentTimeMillis(), 5000, pendingIntent);
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        JobInfo jobInfo = new JobInfo.Builder
//                (1, new ComponentName(this, myJobTest.class))
//                .setRequiresCharging(false)
//                .setPeriodic(1 * 1000)
//                .build();
//        jobScheduler.schedule(jobInfo);
    }
}