package com.example.app0907;

public class MoreVO {

    // 신고날짜
    private String uploaddate;

    // 접수확인
    private String receipt;

    // 주소
    private String spot;

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public MoreVO(String uploaddate, String receipt, String spot) {
        this.uploaddate = uploaddate;
        this.receipt = receipt;
        this.spot = spot;
    }
}
