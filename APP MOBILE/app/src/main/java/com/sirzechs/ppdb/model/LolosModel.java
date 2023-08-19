package com.sirzechs.ppdb.model;

import com.google.gson.annotations.SerializedName;

public class LolosModel {
    @SerializedName("kode_pendaftar")
    private String kode_pendaftar;

    @SerializedName("nama_lengkap")
    private String nama_lengkap;

    @SerializedName("jurusan")
    private String jurusan;

    @SerializedName("nem")
    private String nem;

    @SerializedName("status")
    private String status;

    public String getKode_pendaftar() {
        return kode_pendaftar;
    }

    public void setKode_pendaftar(String kode_pendaftar) {
        this.kode_pendaftar = kode_pendaftar;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getNem() {
        return nem;
    }

    public void setNem(String nem) {
        this.nem = nem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
