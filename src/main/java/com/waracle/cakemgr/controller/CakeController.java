package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.model.Cake;
import com.waracle.cakemgr.payload.CakePayload;
import com.waracle.cakemgr.service.CakeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class CakeController {

    private final CakeService cakeService;

    public CakeController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCakesData() {

        List<CakePayload> cakePayloadList = this.prepareCakePayloadData();
        if (cakePayloadList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(cakePayloadList, HttpStatus.OK);
    }

    @GetMapping("cakes")
    public ResponseEntity<Resource> downloadCakes(String param) throws IOException {

        List<CakePayload> cakePayloadList = this.prepareCakePayloadData();
        if (cakePayloadList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // convert map to JSON file
        new ObjectMapper().writeValue(Paths.get("cakes.json").toFile(), cakePayloadList);

        InputStreamResource resource = new InputStreamResource(new FileInputStream("cakes.json"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=cakes.json");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(new File("cakes.json").length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("cakes")
    public ResponseEntity<?> saveNewCakeInformation(@Valid @RequestBody CakePayload cakePayloadRequest) {

        if (cakePayloadRequest == null || cakePayloadRequest.getTitle() == null
                || cakePayloadRequest.getDescription() == null || cakePayloadRequest.getImageUrl() == null
                || cakePayloadRequest.getDescription().isBlank() || cakePayloadRequest.getImageUrl().isBlank()
                || cakePayloadRequest.getTitle().isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Cake cake = modelMapper.map(cakePayloadRequest, Cake.class);

        cake = this.cakeService.saveNewCakeData(cake);
        if (cake != null && cake.getId() != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private List<CakePayload> prepareCakePayloadData() {

        List<Cake> cakeList = this.cakeService.fetchAllCakes();

        if (cakeList == null) {
            return null;
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Type listType = new TypeToken<List<CakePayload>>() {
        }.getType();

        return new ModelMapper().map(cakeList, listType);
    }
}
