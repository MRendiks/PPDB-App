<?php
session_start();
include 'connection.php';

if($_POST){
    
    $kode_pendaftar = filter_input(INPUT_POST, 'kode_pendaftar', FILTER_SANITIZE_STRING);
    $nama_sekolah = filter_input(INPUT_POST, 'nama_sekolah', FILTER_SANITIZE_STRING);
    $nama_kepala_sekolah = filter_input(INPUT_POST, 'nama_kepala_sekolah', FILTER_SANITIZE_STRING);
    $status_sekolah = filter_input(INPUT_POST, 'status_sekolah', FILTER_SANITIZE_STRING);
    $tahun_lulus = filter_input(INPUT_POST, 'tahun_lulus', FILTER_SANITIZE_STRING);
    $nem = filter_input(INPUT_POST, 'nem', FILTER_SANITIZE_STRING);
    $npsn_sekolah = filter_input(INPUT_POST, 'npsn_sekolah', FILTER_SANITIZE_STRING);

    $response = [];

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM data_asal_sekolah where kode_pendaftar = ?");
    $userQuery->execute(array($kode_pendaftar));

    // Cek username apakah ada tau tidak
    if($userQuery->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Sudah Terdaftar Lol';
    } else {
        $insertAccount = 'INSERT INTO data_asal_sekolah (kode_pendaftar, nama_sekolah, nama_kepala_sekolah, status_sekolah, tahun_lulus, nem, npsn_sekolah) values (:kode_pendaftar, :nama_sekolah, :nama_kepala_sekolah, :status_sekolah, :tahun_lulus, :nem, :npsn_sekolah)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':kode_pendaftar' => $kode_pendaftar,
                ':nama_sekolah' => $nama_sekolah,
                ':nama_kepala_sekolah' => $nama_kepala_sekolah,
                ':status_sekolah' => $status_sekolah,
                ':tahun_lulus' => $tahun_lulus,
                ':nem' => $nem,
                ':npsn_sekolah' => $npsn_sekolah
            ]);

            //Beri response
            $response['status']= true;
            $response['message']='Data Asal Sekolah berhasil dikirim';
            $response['data'] = [
                'kode_pendaftar' => $kode_pendaftar,
                'nama_sekolah' => $nama_sekolah,
                'nama_kepala_sekolah' => $nama_kepala_sekolah,
                'status_sekolah' => $status_sekolah,
                'tahun_lulus' => $tahun_lulus,
                'nem' => $nem,
                'npsn_sekolah' => $npsn_sekolah
            ];
        } catch (Exception $e){
            die($e->getMessage());
        }

    }
    
    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print JSON
    echo $json;
}