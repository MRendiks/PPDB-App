package com.sirzechs.ppdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PengumumanActivity extends AppCompatActivity {
    TextView tvKode, tvNama, tvStatus, tvJurusan;
    String kode, nama, status, jurusan;
    SessionManager sessionManager;

    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman);
        sessionManager = new SessionManager(PengumumanActivity.this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengumumanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvKode = findViewById(R.id.tvKodeDaf);
        tvNama = findViewById(R.id.tvNama);
        tvStatus = findViewById(R.id.tvStatus);
        tvJurusan = findViewById(R.id.tvJurusan);

        kode = sessionManager.getPengumumanDetail().get(SessionManager.PENG_KODE);
        nama = sessionManager.getPengumumanDetail().get(SessionManager.PENG_NAMA);
        status = sessionManager.getPengumumanDetail().get(SessionManager.PENG_STATUS);
        jurusan = sessionManager.getPengumumanDetail().get(SessionManager.PENG_JUR);

        tvKode.setText(kode);
        tvNama.setText(nama);
        if (status.equals("lolos")){
            tvStatus.setText("LOLOS");
            tvStatus.setTextColor(this.getResources().getColor(R.color.green));
            tvJurusan.setText(jurusan);

        }else{
            tvStatus.setText("TIDAK LOLOS");
            tvStatus.setTextColor(this.getResources().getColor(R.color.red));
            tvJurusan.setText(" - ");
        }


    }

    private void moveToLogin() {
        Intent intent = new Intent(PengumumanActivity.this, LoginActivity.class);
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
}