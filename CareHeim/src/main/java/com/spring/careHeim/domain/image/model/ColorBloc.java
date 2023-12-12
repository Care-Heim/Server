package com.spring.careHeim.domain.image.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class ColorBloc {
    private double avgH;
    private double avgS;
    private double avgV;
    private String name;
    private double pixelFraction;

    public ColorBloc(Color color) {
        avgH = color.getH();
        avgS = color.getS();
        avgV = color.getV();
        pixelFraction = color.getPixelFraction();
    }

    public void add(double H, double S, double V, double new_pixel) {
        this.avgH = calculateAverage(this.avgH, H, new_pixel);
        this.avgS = calculateAverage(this.avgS, S, new_pixel);
        this.avgV = calculateAverage(this.avgV, V, new_pixel);
        this.pixelFraction += new_pixel;
    }

    public boolean isSameColor(double H, double S, double V) {
        if(Math.abs(H - this.avgH) <= 15) {
            if(Math.abs(S - this.avgS) <= 25) {
                if(Math.abs(V - this.avgV) <= 30) {
                    return true;
                }
            }
        }
        return false;
    }

    public double calculateAverage(double avg, double newValue, double new_pixel) {
        double newAvg = (avg * this.pixelFraction + newValue * new_pixel) / (this.pixelFraction + new_pixel);
        return newAvg;
    }

    public void decideColorName() {
        String name = "";

        if(this.avgV < 20) {
            name = "검정색";
        } else if(this.avgS < 10 && this.avgV > 95) {
            name = "흰색";
        } else if(this.avgS < 5) {
            if(this.avgV < 40) {
                name = "어두운 ";
            } else if(this.avgV > 85) {
                name = "밝은 ";
            }
            name += "회색";
        } else {
            if(this.avgV > 60) {
                if(this.avgS < 60){
                    name = "연한 ";
                }
            } else {
                name = "짙은 ";
            }


            if(avgH < 15 || avgH > 345) { //red
                if(this.avgS < 70)
                    name += "분홍색";
                else
                    name += "빨강색";
            } else if (avgH < 45) { // orange
                name += "주황색";
            } else if (avgH < 70) { // yellow
                name += "노란색";
            } else if (avgH < 160) { // green
                name += "초록색";
            } else if (avgH < 205) { // sky blue
                name += "하늘색";
            } else if (avgH < 240) { // blue
                if(this.avgS < 70) {
                    if(avgH > 230)
                        name = "연한 보라색";
                    else
                        name = "하늘색";
                } else {
                    name += "파란색";
                }
            } else if (avgH < 285) { // purple
                name += "보라색";
            } else { // rose
                name += "자주색";
            }
        }

        this.name = name;
    }
}
