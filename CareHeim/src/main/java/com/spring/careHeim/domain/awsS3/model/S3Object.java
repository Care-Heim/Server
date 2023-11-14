package com.spring.careHeim.domain.awsS3.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class S3Object {
    String fileName;
    String fileUrl;
}
