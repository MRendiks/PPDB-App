<?php 

$host = "localhost";
$username = "root";
$password = "";
$dbname = "ppdb";

$koneksi = mysqli_connect($host, $username, $password, $dbname);

$query = mysqli_query($koneksi, "SELECT data_pendaftar.`kode_pendaftar`, data_pendaftar.nama_lengkap, status_pendaftaran.`jurusan`, data_asal_sekolah.nem, status_pendaftaran.`status` FROM status_pendaftaran JOIN data_pendaftar ON status_pendaftaran.`kode_pendaftar` = data_pendaftar.`kode_pendaftar` JOIN data_asal_sekolah ON status_pendaftaran.kode_pendaftar = data_asal_sekolah.kode_pendaftar WHERE status_pendaftaran.status = 'lolos' ORDER BY data_asal_sekolah.nem DESC");
// $data = mysqli_fetch_array($query);
$cek = mysqli_affected_rows($koneksi);


if($cek > 0){
    $response['kode'] = '1';
    $response['pesan'] = 'Data Tersedia';
    $response['data'] = array();

    while($ambil = mysqli_fetch_object($query)){
        $a['kode_pendaftar'] = $ambil->kode_pendaftar;
        $a['nama_lengkap'] = $ambil->nama_lengkap;
        $a['jurusan'] = $ambil->jurusan;
        $a['nem'] = $ambil->nem;
        $a['status'] = $ambil->status;

        array_push($response['data'], $a);
    }


}else{
    $response['kode'] = '0';
    $response['pesan'] = 'Data Tidak Tersedia';
}

$json = json_encode($response);
echo $json;

mysqli_close($koneksi);
?>
