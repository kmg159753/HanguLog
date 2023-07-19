package com.example.hangusemiproject.AwS3.controller;


import com.example.hangusemiproject.AwS3.dto.AwsS3;
import com.example.hangusemiproject.AwS3.service.AwsS3Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping()
    public AwsS3 upload(@RequestPart("file") MultipartFile multipartFile , HttpServletRequest httpServletRequest) throws IOException {
        return awsS3Service.upload(multipartFile, httpServletRequest);
    }

    @DeleteMapping()
    public void remove(AwsS3 awsS3) {
        awsS3Service.remove(awsS3);
    }
}