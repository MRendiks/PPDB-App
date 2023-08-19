<?php
session_start();
include 'connection.php';

if($_POST){
    $kode_pendaftar = filter_input(INPUT_POST, 'kode_pendaftar', FILTER_SANITIZE_STRING);
    $status = 'pengecekan';

    $response = [];

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM data_jurusan where kode_pendaftar = ?");
    $userQuery->execute(array($kode_pendaftar));

    // Cek username apakah ada tau tidak
    if($userQuery->rowCount() != 1){
        // Beri Response
        $response['status']= false;
        $response['message']='Sudah Terdaftar Lol';
    } else {
        $insertAccount = 'INSERT INTO status_pendaftaran (kode_pendaftar, status) values (:kode_pendaftar, :status)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':kode_pendaftar' => $kode_pendaftar,
                ':status' => $status
            ]);

            //Beri response
            $response['status']= true;
            $response['message']='Status berhasil dikirim';
            $response['data'] = [
                'kode_pendaftar' => $kode_pendaftar,
                'status' => $status
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