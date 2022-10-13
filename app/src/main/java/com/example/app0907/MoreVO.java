package com.example.app0907;

public class MoreVO {

    // 신고날짜
    private String uploaddate;
    // 주소
    private String spot;
    // 아이디
    private String id;
    // 추가사항
    private String detail;
    // 접수확인
    private String receipt;
    // 신고 이미지
    private String img;



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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public MoreVO(String uploaddate, String spot, String id, String detail, String receipt, String img) {
        this.uploaddate = uploaddate;
        this.spot = spot;
        this.id = id;
        this.detail = detail;
        this.receipt = receipt;
        this.img = img;
    }
}
