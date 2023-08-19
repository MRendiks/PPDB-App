<?php

include 'connection.php';

if($_POST){
    $kode_pendaftar = "SMK-".rand(10000, 100000);
    //POST DATA
    $username = filter_input(INPUT_POST, 'username', FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email', FILTER_SANITIZE_STRING);
    $password = filter_input(INPUT_POST, 'password', FILTER_SANITIZE_STRING);
    $role = 'peserta';

    $response = [];

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM users_admin where username = ?");
    $userQuery->execute(array($username));

    // Cek username apakah ada tau tidak
    if($userQuery->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Akun sudah digunakan';
    } else {
        $insertAccount = 'INSERT INTO users_admin (kode_pendaftar, username, email, password, role) values (:kode_pendaftar, :username, :email, :password, :role)';
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':kode_pendaftar' => $kode_pendaftar,
                ':username' => $username,
                ':email' => $email,
                ':password' => md5($password),
                ':role' => $role
            ]);

            //Beri response
            $response['status']= true;
            $response['message']= 'Akun berhasil didaftar]';
            $response['data'] = [
                'kode_pendaftar' => $kode_pendaftar,
                'email' => $email,
                'username' => $username,
                'role' => $role
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