package com.example.demo.util;

import com.example.demo.model.Barang;
import com.example.demo.payload.BarangResponse;

public class ModelMapper {
    public static BarangResponse mapBarangToBarangResponse(Barang barang){
        BarangResponse res = new BarangResponse();
        res.setReferenceId(barang.getReferenceId());
        res.setNama(barang.getNama());

        return res;
    }
}
