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
import com.sirzechs.ppdb.model.jurusan.Jurusan;
import com.sirzechs.ppdb.model.jurusan.JurusanData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JurusanActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemSelectedListener{
    Spinner etJur1, etJur2;
    Button btnKirim;
    String KodePendaftar, Jurusan1, Jurusan2, Kode;

    SessionManager sessionManager;
    ApiInterface apiInterface;
    EditText etKode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurusan);

        sessionManager = new SessionManager(JurusanActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etKode = findViewById(R.id.etKode);

        etJur1 = findViewById(R.id.etJur1);
        Spinner spinner = findViewById(R.id.etJur1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jurusan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        etJur2 = findViewById(R.id.etJur2);
        Spinner spinner2 = findViewById(R.id.etJur2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.jurusan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        KodePendaftar = sessionManager.getUserDetail().get(SessionManager.KODE_PENDAFTAR);
        etKode.setText(KodePendaftar);

        btnKirim = findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(this);
    }

    private void moveToLogin() {
        Intent intent = new Intent(JurusanActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                case R.id.btnKirim:
                    Kode = etKode.getText().toString();
                    Jurusan1 = etJur1.getSelectedItem().toString();
                    Jurusan2 = etJur2.getSelectedItem().toString();
                    jurusan(Kode, Jurusan1, Jurusan2);
                    break;
            }
    }

    private void jurusan(String kode, String jurusan1, String jurusan2) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Jurusan> call = apiInterface.jurusanResponse(kode, jurusan1, jurusan2);
        call.enqueue(new Callback<Jurusan>(){
            @Override
            public void onResponse(Call<Jurusan> call, Response<Jurusan> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    sessionManager = new SessionManager(JurusanActivity.this);
                    JurusanData jurusanData = response.body().getJurusanData();
                    sessionManager.createJurusanSession(jurusanData);

//                    Toast.makeText(JurusanActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JurusanActivity.this);

                    alertDialogBuilder.setTitle("Submit Data Jurusan sudah selesai");

                    alertDialogBuilder
                            .setMessage("Klik Ya untuk melanjutkan Data Asal Sekolah!")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent intent = new Intent(JurusanActivity.this, AsalActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                } else {
                    Toast.makeText(JurusanActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Jurusan> call, Throwable t) {
                Toast.makeText(JurusanActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

