package com.sirzechs.ppdb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.sirzechs.ppdb.api.ApiClient;
import com.sirzechs.ppdb.api.ApiInterface;
import com.sirzechs.ppdb.model.pengumuman.Pengumuman;
import com.sirzechs.ppdb.model.pengumuman.PengumumanData;
import com.sirzechs.ppdb.model.status.Status;
import com.sirzechs.ppdb.model.status.StatusData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    Button btnPengumuman, btnPetunjuk;
    ImageButton btnKirim, btnSekolah, btnJurusan, btnStatus;
    TextView etKode_Pendaftar, etUsername, etEmail, etCardUser, etTvid, etLihatId;
    SessionManager sessionManager;
    String KodePendaftar, Username, Email, Id, KDDaf;
    ApiInterface apiInterface;
    String KodePendaftaran, Kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        btnPengumuman.setOnClickListener(this);


        sessionManager = new SessionManager(MainActivity.this);

        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etTvid = findViewById(R.id.tvId);
        etUsername = findViewById(R.id.tvUsername);
        etKode_Pendaftar = findViewById(R.id.tvKodePendaftaran);
        etEmail = findViewById(R.id.tvEmail);

        Id = sessionManager.getUserDetail().get(SessionManager.USER_ID);
        Username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        Email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        KodePendaftar = sessionManager.getUserDetail().get(SessionManager.KODE_PENDAFTAR);



        etTvid.setText(Id);
        etUsername.setText(Username);
        etEmail.setText(Email);
        etKode_Pendaftar.setText(KodePendaftar);

//        pengumuman(etKode_Pendaftar.getText().toString());
//        notif_pengumuman();

//        startService(new Intent(this, NotificationService.class));

        btnPetunjuk = findViewById(R.id.btnPetunjuk);
        btnPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PedomanActivity.class);
                startActivity(intent);
            }
        });

        btnKirim = findViewById(R.id.btnDaftar);
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                startActivity(intent);
            }
        });

        btnSekolah = findViewById(R.id.btnSekolah);
        btnSekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AsalActivity.class);
                startActivity(intent);
            }
        });

        btnJurusan = findViewById(R.id.btnJurusan);
        btnJurusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JurusanActivity.class);
                startActivity(intent);
            }
        });

        btnStatus = findViewById(R.id.btnStatus);
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, lolosActivity.class);
                startActivity(intent);
            }
        });

    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                sessionManager.logoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    private void notif_pengumuman(String pesan) {
        Intent notifyIntent = new Intent(this, PengumumanActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, "default" )
                .setSmallIcon(R.drawable.ic_baseline_email_24)
                .setContentTitle( "Pengumuman PPDB" )
                .setContentText( pesan)
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
                        sessionManager = new SessionManager(MainActivity.this);
                        PengumumanData pengumumanData = response.body().getPengumumanData();
                        sessionManager.createPengumumanSession(pengumumanData);
                        notif_pengumuman(response.body().getMessage());
                    }

                } else {
//                    notif_pengumuman(response.body().getMessage());
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pengumuman> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onBackPressed() {
        startService(new Intent(this, NotificationService.class));
        super.onBackPressed();
    }
}