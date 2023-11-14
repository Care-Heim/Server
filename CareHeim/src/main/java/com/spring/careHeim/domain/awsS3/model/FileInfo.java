package com.spring.careHeim.domain.awsS3.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class FileInfo {
    String fileName;
    String fileUrl;
}
