<?php
session_start();
include 'connection.php';

if($_POST){
    //data pendaftar
    // $id_pendaftar = filter_input(INPUT_POST, 'id_pendaftar', FILTER_SANITIZE_STRING);
    $kode_pendaftar = filter_input(INPUT_POST, 'kode_pendaftar', FILTER_SANITIZE_STRING);
    $nama_lengkap = filter_input(INPUT_POST, 'nama_lengkap', FILTER_SANITIZE_STRING);
    $nis = filter_input(INPUT_POST, 'nis', FILTER_SANITIZE_STRING);
    $nisn = filter_input(INPUT_POST, 'nisn', FILTER_SANITIZE_STRING);
    $jenis_kelamin = filter_input(INPUT_POST, 'jenis_kelamin', FILTER_SANITIZE_STRING);
    $tempat_lahir = filter_input(INPUT_POST, 'tempat_lahir', FILTER_SANITIZE_STRING);
    $tanggal_lahir = filter_input(INPUT_POST, 'tanggal_lahir', FILTER_SANITIZE_STRING);
    $agama = filter_input(INPUT_POST, 'agama', FILTER_SANITIZE_STRING);
    $nik = filter_input(INPUT_POST, 'nik', FILTER_SANITIZE_STRING);
    $no_telp = filter_input(INPUT_POST, 'no_telp', FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email', FILTER_SANITIZE_STRING);
    $alamat = filter_input(INPUT_POST, 'alamat', FILTER_SANITIZE_STRING);

    $response = [];

    //Cek username didalam databse
    $userQuery = $connection->prepare("SELECT * FROM data_pendaftar where kode_pendaftar = ?");
    $userQuery->execute(array($kode_pendaftar));

    // Cek username apakah ada tau tidak
    if($userQuery->rowCount() != 0){
        // Beri Response
        $response['status']= false;
        $response['message']='Sudah Terdaftar Lol';
    } else {
        $insertAccount = 'INSERT INTO data_pendaftar (kode_pendaftar, nama_lengkap, nis, nisn, jenis_kelamin, tempat_lahir, tanggal_lahir, agama, nik, no_telp, email, alamat ) values ( :kode_pendaftar, :nama_lengkap, :nis, :nisn , :jenis_kelamin, :tempat_lahir, :tanggal_lahir, :agama, :nik, :no_telp, :email, :alamat)';
        
        $statement = $connection->prepare($insertAccount);

        try{
            //Eksekusi statement db
            $statement->execute([
                ':kode_pendaftar' => $kode_pendaftar,
                ':nama_lengkap' => $nama_lengkap,
                ':nis' => $nis,
                ':nisn' => $nisn,
                ':jenis_kelamin' => $jenis_kelamin,
                ':tempat_lahir' => $tempat_lahir,
                ':tanggal_lahir' => $tanggal_lahir,
                ':agama' => $agama,
                ':nik' => $nik,
                ':no_telp' => $no_telp,
                ':email' => $email,
                ':alamat' => $alamat
            ]);

            $userQuery1 = $connection->prepare("SELECT * FROM data_pendaftar where kode_pendaftar = ?");
            $userQuery1->execute(array($kode_pendaftar));
            $query = $userQuery1->fetch();
            //Beri response
            $response['status']= true;
            $response['message']='Data Pendaftar berhasil dikirim';
            $response['data'] = [
                'kode_pendaftar' => $kode_pendaftar,
                'nama_lengkap' => $nama_lengkap,
                'nis' => $nis,
                'nisn' => $nisn,
                'jenis_kelamin' => $jenis_kelamin,
                'tempat_lahir' => $tempat_lahir,
                'tanggal_lahir' => $tanggal_lahir,
                'agama' => $agama,
                'nik' => $nik,
                'no_telp' => $no_telp,
                'email' => $email,
                'alamat' => $alamat
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