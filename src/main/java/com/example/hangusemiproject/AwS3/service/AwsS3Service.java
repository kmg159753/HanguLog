package com.example.hangusemiproject.AwS3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.hangusemiproject.AwS3.dto.AwsS3;
import com.example.hangusemiproject.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;
    private final JwtUtil jwtUtil;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public AwsS3 upload(MultipartFile multipartFile, HttpServletRequest httpServletRequest) throws IOException {

        String tokenFromRequest = jwtUtil.getJwtFromHeader(httpServletRequest);
        String userId = jwtUtil.getUserInfoFromToken(tokenFromRequest).getSubject();

        String key = randomFileName(multipartFile.getOriginalFilename(), userId);
        String path = putS3(multipartFile.getInputStream(), key, multipartFile.getSize());

        return AwsS3
                .builder()
                .key(key)
                .path(path)
                .build();
    }

    private String randomFileName(String originalFilename, String userId) {
        return userId + "/" + UUID.randomUUID() + originalFilename;
    }

    private String putS3(InputStream uploadFileStream, String fileName, long contentLength) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFileStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getS3(bucket, fileName);
    }

    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void remove(AwsS3 awsS3) {
        if (!amazonS3.doesObjectExist(bucket, awsS3.getKey())) {
            throw new AmazonS3Exception("객체 " +awsS3.getKey()+ " 이/가 존재하지 않습니다.");
        }
        amazonS3.deleteObject(bucket, awsS3.getKey());
    }
}