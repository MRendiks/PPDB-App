<?php

include 'connection.php';

if($_POST){
    //Data
    $kode_pendaftar = $_POST['kode_pendaftar'] ?? '';
    $response = []; //Data Response
    
    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT data_pendaftar.*, data_alamat.longitude, data_alamat.latitude, data_alamat.alamat, data_alamat.url, data_asal_sekolah.nama_sekolah, data_asal_sekolah.nama_kepala_sekolah, data_asal_sekolah.nis, 
    data_asal_sekolah.nisn, data_asal_sekolah.alamat_sekolah, data_asal_sekolah.status_sekolah, data_asal_sekolah.tahun_lulus, data_asal_sekolah.nem, data_jurusan.jurusan1, data_jurusan.jurusan2 FROM data_pendaftar 
    JOIN data_alamat ON data_alamat.kode_pendaftar = data_pendaftar.kode_pendaftar JOIN data_asal_sekolah 
    ON data_asal_sekolah.kode_pendaftar = data_alamat.kode_pendaftar JOIN data_jurusan ON data_jurusan.kode_pendaftar = data_asal_sekolah.kode_pendaftar 
    WHERE data_pendaftar.kode_pendaftar = ? ");
    $userQuery->execute(array($kode_pendaftar));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['message'] = "Data Tidak Ditemukan";
    } else {
            $response['status'] = true;
            $response['message'] = "Data Berhasil Ditampilkan";
            $response['data'] = [
                'kode_pendaftar' => $query['kode_pendaftar'],
                'nama_lengkap' => $query['nama_lengkap'],
                'jenis_kelamin' => $query['jenis_kelamin'],
                'tempat_lahir' => $query['tempat_lahir'],
                'tanggal_lahir' => $query['tanggal_lahir'],
                'agama' => $query['agama'],
                'nik' => $query['nik'],
                'no_telp' => $query['no_telp'],
                'email' => $query['email'],
                'longitude' => $query['longitude'],
                'latitude' => $query['latitude'],
                'alamat' => $query['alamat'],
                'url' => $query['url'],
                'nama_sekolah' => $query['nama_sekolah'],
                'nama_kepala_sekolah' => $query['nama_kepala_sekolah'],
                'nis' => $query['nis'],
                'nisn' => $query['nisn'],
                'status_sekolah' => $query['status_sekolah'],
                'tahun_lulus' => $query['tahun_lulus'],
                'nem' => $query['nem'],
                'alamat_sekolah' => $query['alamat_sekolah'],
                'jurusan1' => $query['jurusan1'],
                'jurusan2' => $query['jurusan2']
            ];
        } 

    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print
    echo $json;

}