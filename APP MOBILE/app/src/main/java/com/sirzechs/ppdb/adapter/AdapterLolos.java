package com.sirzechs.ppdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sirzechs.ppdb.R;
import com.sirzechs.ppdb.model.LolosModel;

import java.util.List;

public class AdapterLolos extends RecyclerView.Adapter<AdapterLolos.HolderData> {
    private Context ctx;
    private List<LolosModel> listLolos;

    public AdapterLolos(Context ctx, List<LolosModel> listLolos){
        this.ctx = ctx;
        this.listLolos = listLolos;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lolos, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        LolosModel lolos = listLolos.get(position);

        holder.nama_lengkap.setText(lolos.getNama_lengkap());
        holder.jurusan.setText(lolos.getJurusan());
        holder.nem.setText(lolos.getNem());
//        holder.status.setText(lolos.getStatus());
    }

    @Override
    public int getItemCount() {
        return listLolos.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView nama_lengkap, jurusan, nem, status;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            nama_lengkap = itemView.findViewById(R.id.data_namalengkap);
            jurusan = itemView.findViewById(R.id.data_jurusan);
            nem = itemView.findViewById(R.id.data_nem);
//            status = itemView.findViewById(R.id.data_status);
        }
    }
}
