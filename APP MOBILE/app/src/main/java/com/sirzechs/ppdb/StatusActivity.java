package com.sirzechs.ppdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sirzechs.ppdb.api.ApiClient;
import com.sirzechs.ppdb.api.ApiInterface;
import com.sirzechs.ppdb.model.status.Status;
import com.sirzechs.ppdb.model.status.StatusData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnKirim;
    EditText etKode;

    String KodePendaftaran, Kode;

    SessionManager sessionManager;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        sessionManager = new SessionManager(StatusActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        etKode = findViewById(R.id.etKode);
        KodePendaftaran = sessionManager.getUserDetail().get(SessionManager.KODE_PENDAFTAR);
        etKode.setText(KodePendaftaran);

        btnKirim = findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(this);
    }

    private void moveToLogin() {
        Intent intent = new Intent(StatusActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnKirim:
                Kode = etKode.getText().toString();
                status(Kode);
                break;
        }
    }

    private void status(String kode) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Status> call = apiInterface.statusResponse(kode);
        call.enqueue(new Callback<Status>(){
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    sessionManager = new SessionManager(StatusActivity.this);
                    StatusData statusData = response.body().getStatusData();
                    sessionManager.createStatusSession(statusData);

                    Toast.makeText(StatusActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StatusActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(StatusActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(StatusActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}