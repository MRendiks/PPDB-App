package com.sirzechs.ppdb;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sirzechs.ppdb.adapter.AdapterLolos;
import com.sirzechs.ppdb.api.ApiClient;
import com.sirzechs.ppdb.api.ApiInterface;
import com.sirzechs.ppdb.model.LolosModel;
import com.sirzechs.ppdb.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class lolosActivity extends AppCompatActivity {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;

    private List<LolosModel> listBarang = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lolos);

        rvData = findViewById(R.id.rv_Loloslist);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        retrieveData();
    }

    public void retrieveData(){
        ApiInterface ardData = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> tampilData = ardData.lihatResponse();
        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                listBarang = response.body().getData();

                adData = new AdapterLolos(lolosActivity.this, listBarang);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();

                Toast.makeText(lolosActivity.this, "body"+adData, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

    }
}
