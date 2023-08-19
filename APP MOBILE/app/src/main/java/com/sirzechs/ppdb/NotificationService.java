package com.sirzechs.ppdb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.sirzechs.ppdb.api.ApiClient;
import com.sirzechs.ppdb.api.ApiInterface;
import com.sirzechs.ppdb.model.pengumuman.Pengumuman;
import com.sirzechs.ppdb.model.pengumuman.PengumumanData;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    String KodePendaftar;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();


        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();

    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 1000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void notif_pengumuman(String pesan) {
        Intent notifyIntent = new Intent(this, PengumumanActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotificationService. this, "default" )
                .setSmallIcon(R.drawable.ic_baseline_email_24)
                .setContentTitle( "Pengumuman PPDB" )
                .setContentText( pesan )
                .setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( "channelku" , "contoh channel" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            mBuilder.setChannelId( "channelku" ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build()) ;
    }
    private void pengumuman(String kdDaf) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Pengumuman> call = apiInterface.pengumumanResponse(kdDaf);
        call.enqueue(new Callback<Pengumuman>() {
            @Override
            public void onResponse(Call<Pengumuman> call, Response<Pengumuman> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    if (response.body().getPengumumanData().getStatus().equals("lolos") ||
                            response.body().getPengumumanData().getStatus().equals("tidak_lolos")) {
                        sessionManager = new SessionManager(NotificationService.this);
                        PengumumanData pengumumanData = response.body().getPengumumanData();
                        sessionManager.createPengumumanSession(pengumumanData);
                        notif_pengumuman(response.body().getMessage());
                    }

                } else {
//                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pengumuman> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
//                Toast.makeText(NotificationService.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        sessionManager = new SessionManager(NotificationService.this);
                        KodePendaftar = sessionManager.getUserDetail().get(SessionManager.KODE_PENDAFTAR);
                        pengumuman(KodePendaftar.toString());
                        onDestroy();
                    }
                });
            }
        };
    }


}
