package com.sirzechs.ppdb.api;

import com.sirzechs.ppdb.model.ResponseModel;
import com.sirzechs.ppdb.model.data.Data;
import com.sirzechs.ppdb.model.jurusan.Jurusan;
import com.sirzechs.ppdb.model.login.Login;
import com.sirzechs.ppdb.model.pengumuman.Pengumuman;
import com.sirzechs.ppdb.model.register.Register;
import com.sirzechs.ppdb.model.sekolah.Sekolah;
import com.sirzechs.ppdb.model.status.Status;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("data.php")
    Call<Data> dataResponse(
            @Field("kode_pendaftar") String kode_pendaftar,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("nis") String nis,
            @Field("nisn") String nisn,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("agama") String agama,
            @Field("nik") String nik,
            @Field("no_telp") String no_telp,
            @Field("email") String email,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @POST("asal.php")
    Call<Sekolah> sekolahResponse(
            @Field("kode_pendaftar") String kode_pendaftar,
            @Field("nama_sekolah") String nama_sekolah,
            @Field("nama_kepala_sekolah") String nama_kepala_sekolah,
            @Field("status_sekolah") String status_sekolah,
            @Field("tahun_lulus") String tahun_lulus,
            @Field("nem") String nem,
            @Field("npsn_sekolah") String npsn_sekolah
    );

    @FormUrlEncoded
    @POST("jurusan.php")
    Call<Jurusan> jurusanResponse(
            @Field("kode_pendaftar") String kode_pendaftar,
            @Field("jurusan1") String jurusan1,
            @Field("jurusan2") String jurusan2
    );

    @FormUrlEncoded
    @POST("status.php")
    Call<Status> statusResponse(
            @Field("kode_pendaftar") String kode_pendaftar
    );

    @FormUrlEncoded
    @POST("cek_status.php")
    Call<Pengumuman> pengumumanResponse(
            @Field("kode_pendaftar") String kode_pendaftar
    );

    @GET("data_lolos.php")
    Call<ResponseModel> lihatResponse();

}
