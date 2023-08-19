<?php
session_start();
include 'connection.php';

if($_POST){
    
    $kode_pendaftar = filter_input(INPUT_POST, 'kode_pendaftar', FILTER_SANITIZE_STRING);
    $longitude = filter_input(INPUT_POST, 'longitude', FILTER_SANITIZE_STRING);
    $latitude = filter_input(INPUT_POST, 'latitude', FILTER_SANITIZE_STRING);
    $alamat = filter_input(INPUT_POST, 'alamat', FILTER_SANITIZE_STRING);
    $jarak = filter_input(INPUT_POST, 'jarak', FILTER_SANITIZE_STRING);
    $url = filter_input(INPUT_POST, 'url', FILTER_SANITIZE_STRING);

    $response = [];

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM data_alamat where kode_pendaftar = ?");
    $userQuery->execute(array($kode_pendaftar));

    // Cek username apakah ada tau tidak
    if($userQuery->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Sudah Terdaftar  Lol';
    } else {
        $insertAccount = 'INSERT INTO data_alamat(kode_pendaftar, longitude, latitude, alamat, jarak, url ) values (:kode_pendaftar, :longitude, :latitude, :alamat, :jarak, :url)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':kode_pendaftar' => $kode_pendaftar,
                ':longitude' => $longitude,
                ':latitude' => $latitude,
                ':alamat' => $alamat,
                ':jarak' => $jarak,
                ':url' => $url
            ]);

            //Beri response
            $response['status']= true;
            $response['message']='Data Pendaftar berhasil dikirim';
            $response['data'] = [
                'kode_pendaftar' => $kode_pendaftar,
                'longitude' => $longitude,
                'latitude' => $latitude,
                'alamat' => $alamat,
                'jarak' => $jarak,
                'url' => $url
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