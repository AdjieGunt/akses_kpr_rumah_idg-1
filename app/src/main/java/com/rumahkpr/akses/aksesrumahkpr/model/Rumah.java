package com.rumahkpr.akses.aksesrumahkpr.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by JEMMY CALAK on 3/18/2018.
 */

public class Rumah implements Serializable{
    String alamat, blok, daya_listrik, deskripsi, garasi, img1, img2, img3, img4, img5, harga, id_booking, id_desa_keluraha,
            id_kab_kota, id_kecamatan, id_provinsi, id_stk_dev, id_stk_kavling, id_stk_proyek, id_tipe_rumah, jalur_pdam, jalur_pdam_lama,
            jalur_telepon_lama, jalur_telp,  jenis, jenis_lama, jenis_lelang, jml_dilihat, kamar_mandi, kamar_tidur, kategori_agunan,
            keyword, klaster, kodepos, lantai, latitude, longitude, luas_bangunan, luas_tanah, nama, nomor, respons_eloan, sertifikat, status_eloan,
            status_jual, status_pengajuan, status_pengajuan_lama, status_unit, status_unit_lama, subsidi, subsidi_lama, tahun_bangun, video;

    public String getId_stk_proyek() {
        return id_stk_proyek;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setId_stk_proyek(String id_stk_proyek) {
        this.id_stk_proyek = id_stk_proyek;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBlok() {
        return blok;
    }

    public void setBlok(String blok) {
        this.blok = blok;
    }

    public String getDaya_listrik() {
        return daya_listrik;
    }

    public void setDaya_listrik(String daya_listrik) {
        this.daya_listrik = daya_listrik;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGarasi() {
        return garasi;
    }

    public void setGarasi(String garasi) {
        this.garasi = garasi;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getId_booking() {
        return id_booking;
    }

    public void setId_booking(String id_booking) {
        this.id_booking = id_booking;
    }

    public String getId_desa_keluraha() {
        return id_desa_keluraha;
    }

    public void setId_desa_keluraha(String id_desa_keluraha) {
        this.id_desa_keluraha = id_desa_keluraha;
    }

    public String getId_kab_kota() {
        return id_kab_kota;
    }

    public void setId_kab_kota(String id_kab_kota) {
        this.id_kab_kota = id_kab_kota;
    }

    public String getId_kecamatan() {
        return id_kecamatan;
    }

    public void setId_kecamatan(String id_kecamatan) {
        this.id_kecamatan = id_kecamatan;
    }

    public String getId_provinsi() {
        return id_provinsi;
    }

    public void setId_provinsi(String id_provinsi) {
        this.id_provinsi = id_provinsi;
    }

    public String getId_stk_dev() {
        return id_stk_dev;
    }

    public void setId_stk_dev(String id_stk_dev) {
        this.id_stk_dev = id_stk_dev;
    }

    public String getId_stk_kavling() {
        return id_stk_kavling;
    }

    public void setId_stk_kavling(String id_stk_kavling) {
        this.id_stk_kavling = id_stk_kavling;
    }

    public String getId_tipe_rumah() {
        return id_tipe_rumah;
    }

    public void setId_tipe_rumah(String id_tipe_rumah) {
        this.id_tipe_rumah = id_tipe_rumah;
    }

    public String getJalur_pdam() {
        return jalur_pdam;
    }

    public void setJalur_pdam(String jalur_pdam) {
        this.jalur_pdam = jalur_pdam;
    }

    public String getJalur_pdam_lama() {
        return jalur_pdam_lama;
    }

    public void setJalur_pdam_lama(String jalur_pdam_lama) {
        this.jalur_pdam_lama = jalur_pdam_lama;
    }

    public String getJalur_telepon_lama() {
        return jalur_telepon_lama;
    }

    public void setJalur_telepon_lama(String jalur_telepon_lama) {
        this.jalur_telepon_lama = jalur_telepon_lama;
    }

    public String getJalur_telp() {
        return jalur_telp;
    }

    public void setJalur_telp(String jalur_telp) {
        this.jalur_telp = jalur_telp;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJenis_lama() {
        return jenis_lama;
    }

    public void setJenis_lama(String jenis_lama) {
        this.jenis_lama = jenis_lama;
    }

    public String getJenis_lelang() {
        return jenis_lelang;
    }

    public void setJenis_lelang(String jenis_lelang) {
        this.jenis_lelang = jenis_lelang;
    }

    public String getJml_dilihat() {
        return jml_dilihat;
    }

    public void setJml_dilihat(String jml_dilihat) {
        this.jml_dilihat = jml_dilihat;
    }

    public String getKamar_mandi() {
        return kamar_mandi;
    }

    public void setKamar_mandi(String kamar_mandi) {
        this.kamar_mandi = kamar_mandi;
    }

    public String getKamar_tidur() {
        return kamar_tidur;
    }

    public void setKamar_tidur(String kamar_tidur) {
        this.kamar_tidur = kamar_tidur;
    }

    public String getKategori_agunan() {
        return kategori_agunan;
    }

    public void setKategori_agunan(String kategori_agunan) {
        this.kategori_agunan = kategori_agunan;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKlaster() {
        return klaster;
    }

    public void setKlaster(String klaster) {
        this.klaster = klaster;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public String getLantai() {
        return lantai;
    }

    public void setLantai(String lantai) {
        this.lantai = lantai;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLuas_bangunan() {
        return luas_bangunan;
    }

    public void setLuas_bangunan(String luas_bangunan) {
        this.luas_bangunan = luas_bangunan;
    }

    public String getLuas_tanah() {
        return luas_tanah;
    }

    public void setLuas_tanah(String luas_tanah) {
        this.luas_tanah = luas_tanah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getRespons_eloan() {
        return respons_eloan;
    }

    public void setRespons_eloan(String respons_eloan) {
        this.respons_eloan = respons_eloan;
    }

    public String getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(String sertifikat) {
        this.sertifikat = sertifikat;
    }

    public String getStatus_eloan() {
        return status_eloan;
    }

    public void setStatus_eloan(String status_eloan) {
        this.status_eloan = status_eloan;
    }

    public String getStatus_jual() {
        return status_jual;
    }

    public void setStatus_jual(String status_jual) {
        this.status_jual = status_jual;
    }

    public String getStatus_pengajuan() {
        return status_pengajuan;
    }

    public void setStatus_pengajuan(String status_pengajuan) {
        this.status_pengajuan = status_pengajuan;
    }

    public String getStatus_pengajuan_lama() {
        return status_pengajuan_lama;
    }

    public void setStatus_pengajuan_lama(String status_pengajuan_lama) {
        this.status_pengajuan_lama = status_pengajuan_lama;
    }

    public String getStatus_unit() {
        return status_unit;
    }

    public void setStatus_unit(String status_unit) {
        this.status_unit = status_unit;
    }

    public String getStatus_unit_lama() {
        return status_unit_lama;
    }

    public void setStatus_unit_lama(String status_unit_lama) {
        this.status_unit_lama = status_unit_lama;
    }

    public String getSubsidi() {
        return subsidi;
    }

    public void setSubsidi(String subsidi) {
        this.subsidi = subsidi;
    }

    public String getSubsidi_lama() {
        return subsidi_lama;
    }

    public void setSubsidi_lama(String subsidi_lama) {
        this.subsidi_lama = subsidi_lama;
    }

    public String getTahun_bangun() {
        return tahun_bangun;
    }

    public void setTahun_bangun(String tahun_bangun) {
        this.tahun_bangun = tahun_bangun;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
