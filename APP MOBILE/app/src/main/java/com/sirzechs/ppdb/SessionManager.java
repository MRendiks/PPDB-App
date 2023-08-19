package com.sirzechs.ppdb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sirzechs.ppdb.model.data.DataData;
import com.sirzechs.ppdb.model.jurusan.JurusanData;
import com.sirzechs.ppdb.model.login.LoginData;
import com.sirzechs.ppdb.model.pengumuman.PengumumanData;
import com.sirzechs.ppdb.model.sekolah.SekolahData;
import com.sirzechs.ppdb.model.status.StatusData;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Response Login

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID = "id_admin";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String KODE_PENDAFTAR = "kode_pendaftar";
    public static final String ROLE = "role";


    // Response Data
    public static final String DATA_KODE_PENDAFTAR = "kode_pendaftar";
    public static final String DATA_FULLNAME = "nama_lengkap";
    public static final String DATA_NIS = "nis";
    public static final String DATA_NISN = "nisn";
    public static final String DATA_JENIS_KELAMIN = "jenis_kelamin";
    public static final String DATA_TEMPAT_LAHIR = "tempat_lahir";
    public static final String DATA_TANGGAL_LAHIR = "tanggal_lahir";
    public static final String DATA_AGAMA = "agama";
    public static final String DATA_NIK = "nik";
    public static final String DATA_EMAIL = "email";
    public static final String DATA_NO_TELP = "no_telp";
    public static final String DATA_ALAMAT = "alamat";


    // Reponse Asal Sekolah

    public static final String ASAL_KODE_PENDAFTAR = "kode_pendaftar";
    public static final String ASAL_NAMA_SEKOLAH = "nama_sekolah";
    public static final String ASAL_NAMA_KEPALA_SEKOLAH = "nama_kepala_sekolah";
    public static final String ASAL_STATUS_SEKOLAH = "status_sekolah";
    public static final String ASAL_TAHUN_LULUS = "tahun_lulus";
    public static final String ASAL_NEM = "nem";
    public static final String ASAL_NPSN_SEKOLAH = "npsn_sekolah";

    // Response Jurusan

    public static final String JUR_KODE_PENDAFTAR = "kode_pendaftar";
    public static final String JUR_JURUSAN1 = "jurusan1";
    public static final String JUR_JURUSAN2 = "jurusan2";

    // Response Status

    public static final String STA_KODE_PENDAFTAR = "kode_pendaftar";


    // Response Pengumuman

    public static final String PENG_KODE = "kode_pendaftar";
    public static final String PENG_NAMA = "nama_lengkap";
    public static final String PENG_JUR = "jurusan";
    public static final String PENG_STATUS = "status";

    public SessionManager (Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getId_admin());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(ROLE, user.getRole());
        editor.putString(KODE_PENDAFTAR, user.getKode_pendaftar());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID,null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME,null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        user.put(KODE_PENDAFTAR, sharedPreferences.getString(KODE_PENDAFTAR,null));
        user.put(ROLE, sharedPreferences.getString(ROLE,null));
        return user;
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void createDataSession(DataData data) {
        editor.putString(DATA_KODE_PENDAFTAR, data.getKode_pendaftar());
        editor.putString(DATA_FULLNAME, data.getNama_lengkap());
        editor.putString(DATA_NIS, data.getNis());
        editor.putString(DATA_NISN, data.getNisn());
        editor.putString(DATA_JENIS_KELAMIN, data.getJenis_kelamin());
        editor.putString(DATA_TEMPAT_LAHIR, data.getTempat_lahir());
        editor.putString(DATA_TANGGAL_LAHIR, data.getTanggal_lahir());
        editor.putString(DATA_AGAMA, data.getAgama());
        editor.putString(DATA_NIK, data.getNik());
        editor.putString(DATA_NO_TELP, data.getNo_telp());
        editor.putString(DATA_EMAIL, data.getEmail());
        editor.putString(DATA_ALAMAT, data.getAlamat());

        editor.commit();
    }

    public HashMap<String, String> getDataDetail(){
        HashMap<String,String> data = new HashMap<>();
        data.put(DATA_KODE_PENDAFTAR, sharedPreferences.getString(DATA_KODE_PENDAFTAR, null));
        data.put(DATA_FULLNAME, sharedPreferences.getString(DATA_FULLNAME, null));
        data.put(DATA_NIS, sharedPreferences.getString(DATA_NIS, null));
        data.put(DATA_NISN, sharedPreferences.getString(DATA_NISN, null));
        data.put(DATA_JENIS_KELAMIN, sharedPreferences.getString(DATA_JENIS_KELAMIN, null));
        data.put(DATA_TEMPAT_LAHIR, sharedPreferences.getString(DATA_TEMPAT_LAHIR, null));
        data.put(DATA_TANGGAL_LAHIR, sharedPreferences.getString(DATA_TANGGAL_LAHIR, null));
        data.put(DATA_AGAMA, sharedPreferences.getString(DATA_AGAMA, null));
        data.put(DATA_NIK, sharedPreferences.getString(DATA_NIK, null));
        data.put(DATA_NO_TELP, sharedPreferences.getString(DATA_NO_TELP, null));
        data.put(DATA_EMAIL, sharedPreferences.getString(DATA_EMAIL, null));
        data.put(DATA_ALAMAT, sharedPreferences.getString(DATA_ALAMAT, null));
        return data;
    }

    public void createSekolahSession(SekolahData sekolah) {
        editor.putString(ASAL_KODE_PENDAFTAR, sekolah.getKode_pendaftar());
        editor.putString(ASAL_NAMA_SEKOLAH, sekolah.getNama_sekolah());
        editor.putString(ASAL_NAMA_KEPALA_SEKOLAH, sekolah.getNama_kepala_sekolah());
        editor.putString(ASAL_STATUS_SEKOLAH, sekolah.getStatus_sekolah());
        editor.putString(ASAL_TAHUN_LULUS, sekolah.getTahun_lulus());
        editor.putString(ASAL_NEM, sekolah.getNem());
        editor.putString(ASAL_NPSN_SEKOLAH, sekolah.getNpsn_sekolah());

        editor.commit();
    }

    public HashMap<String, String> getSekolahDetail(){
        HashMap<String,String> data = new HashMap<>();
        data.put(ASAL_KODE_PENDAFTAR, sharedPreferences.getString(ASAL_KODE_PENDAFTAR, null));
        data.put(ASAL_NAMA_SEKOLAH, sharedPreferences.getString(ASAL_NAMA_SEKOLAH, null));
        data.put(ASAL_NAMA_KEPALA_SEKOLAH, sharedPreferences.getString(ASAL_NAMA_KEPALA_SEKOLAH, null));
        data.put(ASAL_STATUS_SEKOLAH, sharedPreferences.getString(ASAL_STATUS_SEKOLAH, null));
        data.put(ASAL_TAHUN_LULUS, sharedPreferences.getString(ASAL_TAHUN_LULUS, null));
        data.put(ASAL_NEM, sharedPreferences.getString(ASAL_NEM, null));
        data.put(ASAL_NPSN_SEKOLAH, sharedPreferences.getString(ASAL_NPSN_SEKOLAH, null));
        return data;
    }

    public void createJurusanSession(JurusanData jurusan) {
        editor.putString(JUR_KODE_PENDAFTAR, jurusan.getKode_pendaftar());
        editor.putString(JUR_JURUSAN1, jurusan.getJurusan1());
        editor.putString(JUR_JURUSAN2, jurusan.getJurusan2());
        editor.commit();
    }

    public HashMap<String, String> getJurusanDetail(){
        HashMap<String,String> data = new HashMap<>();
        data.put(JUR_KODE_PENDAFTAR, sharedPreferences.getString(JUR_KODE_PENDAFTAR, null));
        data.put(JUR_JURUSAN1, sharedPreferences.getString(JUR_JURUSAN1, null));
        data.put(JUR_JURUSAN2, sharedPreferences.getString(JUR_JURUSAN2, null));
        return data;
    }

    public void createStatusSession(StatusData status) {
        editor.putString(STA_KODE_PENDAFTAR, status.getKode_pendaftar());
        editor.commit();
    }

    public HashMap<String, String> getStatusDetail(){
        HashMap<String,String> data = new HashMap<>();
        data.put(STA_KODE_PENDAFTAR, sharedPreferences.getString(STA_KODE_PENDAFTAR, null));
        return data;
    }

    public void createPengumumanSession(PengumumanData pengumumanData) {
        editor.putString(PENG_KODE, pengumumanData.getKode_pendaftar());
        editor.putString(PENG_NAMA, pengumumanData.getNama_lengkap());
        editor.putString(PENG_JUR, pengumumanData.getJurusan());
        editor.putString(PENG_STATUS, pengumumanData.getStatus());
        editor.commit();
    }

    public HashMap<String, String> getPengumumanDetail(){
        HashMap<String, String> pengumuman = new HashMap<>();
        pengumuman.put(PENG_KODE, sharedPreferences.getString(PENG_KODE, null));
        pengumuman.put(PENG_NAMA, sharedPreferences.getString(PENG_NAMA, null));
        pengumuman.put(PENG_JUR, sharedPreferences.getString(PENG_JUR, null));
        pengumuman.put(PENG_STATUS, sharedPreferences.getString(PENG_STATUS, null));
        return pengumuman;
    }
}
