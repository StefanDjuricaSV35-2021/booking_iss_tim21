package com.ISS.Booking_iss_tim21.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RestController
@RequestMapping("/api/v1/auth/images")
public class UploadController {

    public static String UPLOAD_DIRECTORY = "./src/main/resources/images/";

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("images") List<MultipartFile> files) throws IOException {
        List<String> filenames = new ArrayList<>();

        for(MultipartFile file: files){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(UPLOAD_DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }

        return ResponseEntity.ok().body(filenames);
    }
}
