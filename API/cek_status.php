<?php
session_start();
include 'connection.php';

if($_POST){
    $kode_pendaftar = filter_input(INPUT_POST, 'kode_pendaftar', FILTER_SANITIZE_STRING);

    $response = [];

    $userQuery = $connection->prepare("SELECT * FROM status_pendaftaran WHERE `kode_pendaftar` = ? LIMIT 1");
    $userQuery->execute(array($kode_pendaftar));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['message'] = "Data Anda Sedang Verifikasi Oleh Panitia";
    } else {
        $userQuery = $connection->prepare("SELECT data_pendaftar.`kode_pendaftar`, data_pendaftar.nama_lengkap, status_pendaftaran.`jurusan`, data_asal_sekolah.nem, status_pendaftaran.`status` FROM status_pendaftaran JOIN data_pendaftar ON status_pendaftaran.`kode_pendaftar` = data_pendaftar.`kode_pendaftar` JOIN data_asal_sekolah ON status_pendaftaran.kode_pendaftar = data_asal_sekolah.kode_pendaftar WHERE status_pendaftaran.status = 'lolos' ORDER BY data_asal_sekolah.nem DESC");

        $userQuery->execute();
        $query2 = $userQuery->fetchAll();
        $urutan = 95;
        foreach ($query2 as $data) {
            // echo $data['kode_pendaftar'];
            if ($data['kode_pendaftar'] == $kode_pendaftar || $urutan >=95) {
                $userQuery = $connection->prepare("SELECT data_pendaftar.`kode_pendaftar`, data_pendaftar.nama_lengkap, status_pendaftaran.`jurusan`, status_pendaftaran.`status`FROM status_pendaftaran JOIN data_pendaftar ON status_pendaftaran.`kode_pendaftar` = data_pendaftar.`kode_pendaftar` WHERE data_pendaftar.`kode_pendaftar` = ?");
                $userQuery->execute(array($kode_pendaftar));
                $query = $userQuery->fetch();
                if($userQuery->rowCount() != 0){
                    $response['status'] = true;
                    $response['message'] = "Posisi anda berada di 95-100 orang yang dinyatakan lolos. Akan tetapi, harap hati-hati jika tergeser dan dinyatakan tidak lolos.";
                    $response['data'] = [
                        'kode_pendaftar' => $query['kode_pendaftar'],
                        'nama_lengkap' => $query['nama_lengkap'],
                        'jurusan' => $query['jurusan'],
                        'status' => $query['status']
                    ];
                } 
            }
            else{
                $userQuery = $connection->prepare("SELECT data_pendaftar.`kode_pendaftar`, data_pendaftar.nama_lengkap, status_pendaftaran.`jurusan`, status_pendaftaran.`status`FROM status_pendaftaran JOIN data_pendaftar ON status_pendaftaran.`kode_pendaftar` = data_pendaftar.`kode_pendaftar` WHERE data_pendaftar.`kode_pendaftar` = ?");
                $userQuery->execute(array($kode_pendaftar));
                $query = $userQuery->fetch();
                if($userQuery->rowCount() != 0){
                    $response['status'] = false;
                    $response['message'] = "Klik untuk melihat pengumuman Hasil Seleksi";
                    $response['data'] = [
                        'kode_pendaftar' => $query['kode_pendaftar'],
                        'nama_lengkap' => $query['nama_lengkap'],
                        'jurusan' => $query['jurusan'],
                        'status' => $query['status']
                    ];
                } else {
                    $response['status'] = true;
                    $response['message'] = "Klik untuk melihat pengumuman Hasil Seleksi";
                    $response['data'] = [
                        'kode_pendaftar' => $query['kode_pendaftar'],
                        'nama_lengkap' => $query['nama_lengkap'],
                        'jurusan' => $query['jurusan'],
                        'status' => $query['status']
                    ];
                } 
            }
            $urutan++;
        }
        

                
    } 
    
    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print JSON
    echo $json;
}