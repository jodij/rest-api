package com.example.demo.controller;

import com.example.demo.model.Barang;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.BarangCreateRequest;
import com.example.demo.payload.BarangResponse;
import com.example.demo.payload.PagedResponse;
import com.example.demo.service.BarangService;
import com.example.demo.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class DataController {

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private BarangService barangService;


    @RequestMapping(value = "/data",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedResponse<BarangResponse> getData(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return barangService.findBarang(page, size);
    }

    @RequestMapping(value = "/data",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createData(@Valid @RequestBody BarangCreateRequest barangCreateRequest){

        logger.info(barangCreateRequest.getNama());
        Barang result = barangService.createBarang(barangCreateRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/data/{refId}")
                .buildAndExpand(result.getReferenceId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Barang created successfully"));
    }
}
