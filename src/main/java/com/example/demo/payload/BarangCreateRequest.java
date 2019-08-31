package com.example.demo.payload;

public class BarangCreateRequest {

    private String nama;

    public BarangCreateRequest() {
    }

    public BarangCreateRequest(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
