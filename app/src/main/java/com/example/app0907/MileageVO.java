package com.example.app0907;

public class MileageVO {

    // 사용 날짜
    private String pointdate;

    // 포인트
    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPointdate() {
        return pointdate;
    }

    public void setPointdate(String pointdate) {
        this.pointdate = pointdate;
    }

    public MileageVO(String pointdate, String point) {
        this.pointdate = pointdate;
        this.point = point;
    }
}
