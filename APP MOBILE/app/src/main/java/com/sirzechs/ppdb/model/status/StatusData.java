package com.sirzechs.ppdb.model.status;

import com.google.gson.annotations.SerializedName;

public class StatusData {
    public String getKode_pendaftar() {
        return kode_pendaftar;
    }

    public void setKode_pendaftar(String kode_pendaftar) {
        this.kode_pendaftar = kode_pendaftar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("kode_pendaftar")
    private String kode_pendaftar;

    @SerializedName("status")
    private String status;
}
