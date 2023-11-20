package com.spring.careHeim.domain.image;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.domain.image.model.Color;
import com.spring.careHeim.domain.image.model.ColorBloc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.spring.careHeim.config.BaseResponseStatus.FAILED_TO_COLOR;

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

    public List<Color> detectColors(byte[] byte_img) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.copyFrom(byte_img);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).setMaxResults(3).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List colors = new ArrayList<Color>();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return null;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                DominantColorsAnnotation colorsInfo = res.getImagePropertiesAnnotation().getDominantColors();

                for (ColorInfo color : colorsInfo.getColorsList()) {
                    int[] RGB = {(int) color.getColor().getRed(), (int) color.getColor().getGreen(), (int) color.getColor().getBlue()};

                    Color temp = new Color(RGB);
                    temp.setPixelFraction(color.getPixelFraction());

                    colors.add(temp);
                }
            }
            return colors;
        }
    }

}
