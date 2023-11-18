package com.spring.careHeim.domain.image;

import com.spring.careHeim.config.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    public byte[] separateObject(byte[] image, List<int[]> coordinates) throws BaseException {
        // int[] 타입의 좌표들을 Point로 변형
        List<Point> points = new ArrayList<Point>(coordinates.size());
        coordinates.forEach(coordinate -> {
            points.add(new Point(coordinate[0], coordinate[1]));
        });

        // byteArray image to mat type
        MatOfByte matOfByte = new MatOfByte(image);
        Mat img = Imgcodecs.imdecode(matOfByte, Imgcodecs.IMREAD_COLOR);

        // 마스크 생성
        Mat mask = new Mat(img.size(), CvType.CV_8UC1, Scalar.all(0));
        MatOfPoint polygon = new MatOfPoint();
        polygon.fromList(points);

        ArrayList<MatOfPoint> list = new ArrayList<>();
        list.add(polygon);

        Imgproc.fillPoly(mask, list, Scalar.all(255));

        // mask 합성
        List<Mat> channels = new ArrayList<>();
        Core.split(img, channels);
        channels.add(mask);
        Mat resultMat = new Mat();
        Core.merge(channels, resultMat);

        // 합성 결과 저장 - format : png
        MatOfByte resultMatOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", resultMat, resultMatOfByte);

        return resultMatOfByte.toArray();
    }
}
