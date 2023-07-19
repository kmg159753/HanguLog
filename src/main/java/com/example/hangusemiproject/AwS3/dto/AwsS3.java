package com.example.hangusemiproject.AwS3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter

public class AwsS3 {

    private String key; // 파일명
    private String path; // 파일 경로 (객체 URL)

    public AwsS3() {

    }

    @Builder
    public AwsS3(String fileName, String path) {
        this.key = fileName;
        this.path = path;
    }
}