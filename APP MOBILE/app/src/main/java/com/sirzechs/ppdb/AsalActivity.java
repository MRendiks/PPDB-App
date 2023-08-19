package com.sirzechs.ppdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sirzechs.ppdb.api.ApiClient;
import com.sirzechs.ppdb.api.ApiInterface;
import com.sirzechs.ppdb.model.sekolah.Sekolah;
import com.sirzechs.ppdb.model.sekolah.SekolahData;
import com.sirzechs.ppdb.model.status.Status;
import com.sirzechs.ppdb.model.status.StatusData;

import java.security.KeyPair;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsalActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemSelectedListener {
    String KodePendaftar, Nama, NamaKepsek, Kode, Tahun, Status, Nem, Npsn;
    EditText etKode, etAsNama, etAsNamaKepsek, etAsTahun, etAsNem, etAsNpsn;

    Button btnKirim;
    Spinner etAsStatus;
    SessionManager sessionManager;
    ApiInterface apiInterface;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asal);

        sessionManager = new SessionManager(AsalActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }
        
        etKode = findViewById(R.id.etKode);
        etAsNama = findViewById(R.id.etAsNama);
        etAsNamaKepsek = findViewById(R.id.etAsNamaKepsek);
        etAsTahun = findViewById(R.id.etAsTahun);
        etAsNem = findViewById(R.id.etAsNem);
        etAsNpsn = findViewById(R.id.etAsNpsn);

        KodePendaftar = sessionManager.getUserDetail().get(SessionManager.KODE_PENDAFTAR);
        etKode.setText(KodePendaftar);

        etAsStatus = findViewById(R.id.etAsStatus);
        Spinner spinner = findViewById(R.id.etAsStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnKirim = findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(this);

    }

    private void moveToLogin() {
        Intent intent = new Intent(AsalActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnKirim:
                Kode = etKode.getText().toString();
                Nama = etAsNama.getText().toString();
                NamaKepsek = etAsNamaKepsek.getText().toString();
                Status = etAsStatus.getSelectedItem().toString();
                Tahun = etAsTahun.getText().toString();
                Nem = etAsNem.getText().toString();
                Npsn = etAsNpsn.getText().toString();
                sekolah(Kode, Nama, NamaKepsek, Status, Tahun, Nem, Npsn);
                break;
            case R.id.tvLoginHere:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void status(String kode) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<com.sirzechs.ppdb.model.status.Status> call = apiInterface.statusResponse(kode);
        call.enqueue(new Callback<Status>(){
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    sessionManager = new SessionManager(AsalActivity.this);
                    StatusData statusData = response.body().getStatusData();
                    sessionManager.createStatusSession(statusData);

                    Toast.makeText(AsalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AsalActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    Toast.makeText(AsalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(AsalActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sekolah(String kode, String nama, String namaKepsek, String status, String tahun, String nem, String npsn) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Sekolah> call = apiInterface.sekolahResponse(kode, nama, namaKepsek, status, tahun, nem, npsn);
        call.enqueue(new Callback<Sekolah>(){
            @Override
            public void onResponse(Call<Sekolah> call, Response<Sekolah> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    sessionManager = new SessionManager(AsalActivity.this);
                    SekolahData sekolahData = response.body().getSekolahData();
                    sessionManager.createSekolahSession(sekolahData);

//                    Toast.makeText(AsalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AsalActivity.this);

                    alertDialogBuilder.setTitle("Submit Data Asal Sekolah sudah selesai");

                    alertDialogBuilder
                            .setMessage("Klik Ya untuk menunggu hasil Pengumuman!")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    status(KodePendaftar);
                                    Intent intent = new Intent(AsalActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                } else {
                    Toast.makeText(AsalActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sekolah> call, Throwable t) {
                Toast.makeText(AsalActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}