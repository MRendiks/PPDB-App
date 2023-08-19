<?php
session_start();
include 'connection.php';

if($_POST){
    //Data
    $email = $_POST['email'] ?? '';
    $password = $_POST['password'] ?? '';

    $response = []; //Data Response

    //Cek kode_pendaftar didalam databse
    $userQuery = $connection->prepare("SELECT * FROM users_admin where email = ?");
    $userQuery->execute(array($email));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['message'] = "Email Tidak Terdaftar";
    } else {
        // Ambil password di db
        $passwordDB = $query['password'];

        if(strcmp(md5($password), $passwordDB) === 0){
            $response['status'] = true;
            $response['message'] = "Login Berhasil";
            $response['data'] = [
                'id_admin' => $query['id_admin'],
                'kode_pendaftar' => $query['kode_pendaftar'],
                'username' => $query['username'],
                'email' => $query['email'],
                'role' => $query['role']
            ];
        } else {
            $response['status'] = false;
            $response['message'] = "Password anda salah";
        }
    }

    //Jadikan data JSON
    $json = json_encode($response, JSON_PRETTY_PRINT);

    //Print
    echo $json;

}