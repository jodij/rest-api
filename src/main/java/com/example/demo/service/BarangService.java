package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Barang;
import com.example.demo.payload.BarangCreateRequest;
import com.example.demo.payload.BarangResponse;
import com.example.demo.payload.PagedResponse;
import com.example.demo.repository.BarangRepository;
import com.example.demo.util.AppConstants;
import com.example.demo.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BarangService {

    @Autowired
    private BarangRepository barangRepository;

    public PagedResponse<BarangResponse> findBarang(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Barang> barangs = barangRepository.findAll(pageable);

        if (barangs.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), barangs.getNumber(),
                    barangs.getSize(), barangs.getTotalElements(), barangs.getTotalPages(), barangs.isLast());
        }

        List<BarangResponse> barangResponses = barangs.map(barang -> {
            return ModelMapper.mapBarangToBarangResponse(barang);
        }).getContent();

        return new PagedResponse<>(barangResponses, barangs.getNumber(),
                barangs.getSize(), barangs.getTotalElements(), barangs.getTotalPages(), barangs.isLast());
    }

    public Barang createBarang(BarangCreateRequest barangCreateRequest) {
        Barang barang = new Barang(barangCreateRequest.getNama());
        return barangRepository.save(barang);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
