package com.example.waitlist.business;

import java.util.Date;

public class BookListVO {
    private String bookNum;// 예약번호
    private String bookTime; //예약시간
    private int peopleNum; //입장인원
    private String cancelTime; //취소시간
    private int enterState; //입장상태

    public BookListVO(){

    }
    public BookListVO(String bookNum, String bookTime, int peopleNum, String cancelTime, int enterState) {
        this.bookNum = bookNum;
        this.bookTime = bookTime;
        this.peopleNum = peopleNum;
        this.cancelTime = cancelTime;
        this.enterState = enterState;
    }

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getEnterState() {
        return enterState;
    }

    public void setEnterState(int enterState) {
        this.enterState = enterState;
    }

    @Override
    public String toString() {
        return "BookListVO{" +
                "bookNum='" + bookNum + '\'' +
                ", bookTime='" + bookTime + '\'' +
                ", peopleNum=" + peopleNum +
                ", cancelTime='" + cancelTime + '\'' +
                ", enterState=" + enterState +
                '}';
    }
}
