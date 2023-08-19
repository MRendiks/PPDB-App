package com.sirzechs.ppdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sirzechs.ppdb.api.ApiClient;
import com.sirzechs.ppdb.api.ApiInterface;
import com.sirzechs.ppdb.model.data.Data;
import com.sirzechs.ppdb.model.data.DataData;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    SessionManager sessionManager;
    Spinner etJk, etAgama;
    String KodePendaftar, EmailPendaftar, kode, nama, nis, nisn, jk, tempat, tanggal, agama, nik, notel, email, alamat;
    TextView tvDate;
    Button btnKirim;
    EditText etKode, etNama, etJenis, etTempat, etTanggal, etNIK, etNotelp, etEmail, etTvid, etId, etAlamat, etNis, etNisn;
    ApiInterface apiInterface;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        sessionManager = new SessionManager(DataActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        //deklarasi komponen
        btnKirim = findViewById(R.id.btnKirim);
        btnKirim.setOnClickListener(this);

        etKode = findViewById(R.id.etKode);
        etEmail = findViewById(R.id.etEmail);
        etNama = findViewById(R.id.etNama);
        etTempat = findViewById(R.id.etTempat);
        etAgama = findViewById(R.id.etAgama);
        etNIK = findViewById(R.id.etNIK);
        etNotelp = findViewById(R.id.etNoTelp);
        etAlamat = findViewById(R.id.etAlamat);
        etNis = findViewById(R.id.etNis);
        etNisn = findViewById(R.id.etNisn);

        //set text
        KodePendaftar = sessionManager.getUserDetail().get(SessionManager.KODE_PENDAFTAR);
        etKode.setText(KodePendaftar);

        EmailPendaftar = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        etEmail.setText(EmailPendaftar);

        //spinner
        etJk = findViewById(R.id.etJk);
        Spinner spinner = findViewById(R.id.etJk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        etAgama = findViewById(R.id.etAgama);
        Spinner spinneragama = findViewById(R.id.etAgama);
        ArrayAdapter<CharSequence> adapteragama = ArrayAdapter.createFromResource(this, R.array.agama, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneragama.setAdapter(adapteragama);
        spinneragama.setOnItemSelectedListener(this);

        //Date Picker
        Calendar calendar = Calendar.getInstance();
        final int defaultYear = calendar.get(Calendar.YEAR);
        final int defaultMonth = calendar.get(Calendar.MONTH);
        final int defaultDay = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate = findViewById(R.id.tv_date);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DataActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener, defaultYear, defaultMonth, defaultDay
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                int month = selectedMonth + 1;
                String date = selectedDayOfMonth + "/" + month + "/" + selectedYear;
                tvDate.setText(date);
            }
        };


        //
    }

    private void moveToLogin() {
        Intent intent = new Intent(DataActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnKirim:
                kode = etKode.getText().toString();
                nama = etNama.getText().toString();
                nis = etNis.getText().toString();
                nisn = etNisn.getText().toString();
                jk = etJk.getSelectedItem().toString();
                tempat = etTempat.getText().toString();
                tanggal = tvDate.getText().toString();
                agama = etAgama.getSelectedItem().toString();
                nik = etNIK.getText().toString();
                notel = etNotelp.getText().toString();
                email = etEmail.getText().toString();
                alamat = etAlamat.getText().toString();
                data(kode, nama, nis, nisn, jk, tempat, tanggal, agama, nik, notel, email, alamat);
                break;
        }
    }

    private void data(String kode, String nama, String nis, String nisn, String jk, String tempat, String tanggal, String agama, String nik, String notel, String email, String alamat) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Data> call = apiInterface.dataResponse(kode, nama, nis, nisn, jk, tempat, tanggal, agama, nik, notel, email, alamat);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    sessionManager = new SessionManager(DataActivity.this);
                    DataData dataData = response.body().getDataData();
                    sessionManager.createDataSession(dataData);

//                    Toast.makeText(DataActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DataActivity.this);

                    alertDialogBuilder.setTitle("Submit Data pendaftaran sudah selesai");

                    alertDialogBuilder
                            .setMessage("Klik Ya untuk melanjutkan pemilihan jurusan!")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent intent = new Intent(DataActivity.this, JurusanActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();


                } else {
                    Toast.makeText(DataActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("Error ", t.getLocalizedMessage());
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