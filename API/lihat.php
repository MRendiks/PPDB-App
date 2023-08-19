<?php

include 'connection.php';

if($_POST){

    //Data
    $kode_pendaftar = $_POST['kode_pendaftar'] ?? '';

    $response = []; //Data Response

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT data_pendaftar.`kode_pendaftar`, data_pendaftar.nama_lengkap, status_pendaftaran.`jurusan`, status_pendaftaran.`status`FROM status_pendaftaran 
    JOIN data_pendaftar ON status_pendaftaran.`kode_pendaftar` = data_pendaftar.`kode_pendaftar`
    WHERE data_pendaftar.`kode_pendaftar` = ? ");
    $userQuery->execute(array($kode_pendaftar));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['message'] = "Data Anda Sedang Verifikasi Oleh Panitia";
    } else {
            $response['status'] = true;
            $response['message'] = "Pengumuman Berhasil Ditampilkan";
            $response['data'] = [
                'kode_pendaftar' => $query['kode_pendaftar'],
                'nama_lengkap' => $query['nama_lengkap'],
                'jurusan' => $query['jurusan'],
                'status' => $query['status']
            ];
        } 

    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print
    echo $json;

}