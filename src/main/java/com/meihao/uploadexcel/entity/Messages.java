package com.meihao.uploadexcel.entity;

import java.io.Serializable;

public class Messages implements Serializable {
    private String college;//学院
    private String classes;//班级
    private String bookId;//书号
    private String bookName;//教材名称
    private String reservationNumber;//预定数量


    public Messages() {
    }

    public Messages(String bookId, String bookName, String reservationNumber, String college, String classes) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.reservationNumber = reservationNumber;
        this.college = college;
        this.classes = classes;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "college='" + college + '\'' +
                ", classes='" + classes + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", reservationNumber='" + reservationNumber + '\'' +
                '}';
    }
}