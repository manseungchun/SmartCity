package com.example.app0907;

import org.json.JSONArray;

import java.sql.Timestamp;

public class ReportVO {

    // 신고날짜
    private String uploaddate;

    // 적립포인트
    private String point_upload;

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

    public ReportVO(String uploaddate, String point_upload) {
        this.uploaddate = uploaddate;
        this.point_upload = point_upload;
    }
}
