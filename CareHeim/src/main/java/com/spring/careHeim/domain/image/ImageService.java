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
        Mat mask = new Mat(img.size(), CvType.CV_8UC3, new Scalar(0, 255, 0));
        MatOfPoint polygon = new MatOfPoint();
        polygon.fromList(points);

        ArrayList<MatOfPoint> list = new ArrayList<>();
        list.add(polygon);

        Imgproc.fillPoly(mask, list, Scalar.all(255));

        Mat resultMat = img.clone();

        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] pixel = mask.get(i, j);
                if (pixel[0] == 0 && pixel[1] == 255 && pixel[2] == 0) {  // 형광 부분 (마스크 값이 0인 부분)
                    resultMat.put(i, j, new double[]{0, 255, 0});  // 형광으로 설정
                }
            }
        }

        // 합성 결과 저장 - format : jpg
        MatOfByte resultMatOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", resultMat, resultMatOfByte);

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

                    if(RGB[1] >= 240 && RGB[0] < 10 && RGB[2] < 10) {
                         continue;
                    }

                    Color temp = new Color(RGB);
                    temp.setPixelFraction(color.getPixelFraction());

                    colors.add(temp);
                }
            }
            return colors;
        }
    }

    public List<ColorBloc> clustringColor(List<Color> colors) {
        List<ColorBloc> blocs = new ArrayList<>();

        for(Color color : colors) {
            if(blocs.isEmpty()) {
                ColorBloc newBloc = new ColorBloc(color);
                blocs.add(newBloc);
            } else {
                boolean flag = true;
                for(ColorBloc bloc : blocs) {
                    if(bloc.isSameColor(color.getH(), color.getS(), color.getV())) {
                        bloc.add(color.getH(), color.getS(), color.getV(),color.getPixelFraction());
                        flag = false;
                        break;
                    }
                }

                if(flag) {
                    blocs.add(new ColorBloc(color));
                }
            }
        }

        for(ColorBloc bloc : blocs) {
            bloc.decideColorName();
        }

        return blocs;
    }

    public List<String> getMainColors(byte[] img) throws BaseException {
        try {
            List<Color> colors = detectColors(img);
            List<ColorBloc> colorBlocs = clustringColor(colors);

            double entPixelFraction = 0;

            for(ColorBloc bloc : colorBlocs) {
                entPixelFraction += bloc.getPixelFraction();
            }

            ArrayList<String> mainColors = new ArrayList<>();

            for(ColorBloc bloc : colorBlocs) {
                if(bloc.getPixelFraction() / entPixelFraction < 0.2) {
                    continue;
                }

                String name = bloc.getName();
                mainColors.add(name);
            }
            System.out.println("색상 okay");
            return mainColors;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(FAILED_TO_COLOR);
        }
    }
}
