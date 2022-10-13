package com.example.app0907;

import org.json.JSONArray;

import java.sql.Timestamp;

public class ReportVO {

    // 신고날짜
    private String uploaddate;

    // 적립포인트
    private String point_upload;

    // 신고 위치
    private String spot;

    // 신고 이미지
    private String img;

    public String getPoint_upload() {
        return point_upload;
    }

    public void setPoint_upload(String point_upload) {
        this.point_upload = point_upload;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ReportVO(String uploaddate, String point_upload, String spot, String img) {
        this.uploaddate = uploaddate;
        this.point_upload = point_upload;
        this.spot = spot;
        this.img = img;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }
}
