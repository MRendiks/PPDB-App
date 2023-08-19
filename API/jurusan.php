<?php
session_start();
include 'connection.php';

if($_POST){
    $kode_pendaftar = filter_input(INPUT_POST, 'kode_pendaftar', FILTER_SANITIZE_STRING);
    $jurusan1 = filter_input(INPUT_POST, 'jurusan1', FILTER_SANITIZE_STRING);
    $jurusan2 = filter_input(INPUT_POST, 'jurusan2', FILTER_SANITIZE_STRING);

    $response = [];

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM data_jurusan where kode_pendaftar = ?");
    $userQuery->execute(array($kode_pendaftar));

    // Cek username apakah ada tau tidak
    if($userQuery->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Sudah Terdaftar Lol';
    } else {
        $insertAccount = 'INSERT INTO data_jurusan (kode_pendaftar, jurusan1, jurusan2) values (:kode_pendaftar, :jurusan1, :jurusan2)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':kode_pendaftar' => $kode_pendaftar,
                ':jurusan1' => $jurusan1,
                ':jurusan2' => $jurusan2
            ]);

            //Beri response
            $response['status']= true;
            $response['message']='Data Jurusan berhasil dikirim';
            $response['data'] = [
                'kode_pendaftar' => $kode_pendaftar,
                'jurusan1' => $jurusan1,
                'jurusan2' => $jurusan2
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