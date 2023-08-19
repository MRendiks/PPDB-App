package com.sirzechs.ppdb.model.jurusan;

import com.google.gson.annotations.SerializedName;

public class JurusanData {


    @SerializedName("kode_pendaftar")
    private String kode_pendaftar;

    @SerializedName("jurusan1")
    private String jurusan1;

    @SerializedName("jurusan2")
    private String jurusan2;

    public String getKode_pendaftar() {
        return kode_pendaftar;
    }

    public void setKode_pendaftar(String kode_pendaftar) {
        this.kode_pendaftar = kode_pendaftar;
    }

    public String getJurusan1() {
        return jurusan1;
    }

    public void setJurusan1(String jurusan1) {
        this.jurusan1 = jurusan1;
    }

    public String getJurusan2() {
        return jurusan2;
    }

    public void setJurusan2(String jurusan2) {
        this.jurusan2 = jurusan2;
    }
}
