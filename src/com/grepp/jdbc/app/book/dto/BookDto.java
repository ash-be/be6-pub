package com.grepp.jdbc.app.book.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookDto {
    private String BkIdx;
    private Integer Isbn;
    private String Category;
    private String Title;
    private String Author;
    private String Info;
    private Integer BookAmt;
    private transient LocalDateTime RegDate;
    private Integer RentCnt;

// equals / hashCode 오버라이딩 필요 여부



    @Override
    public String toString() {
        return "BookDto{" +
                "BkIdx=" + BkIdx +
                ", Isbn=" + Isbn +
                ", Category='" + Category + '\'' +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", Info='" + Info + '\'' +
                ", BookAmt=" + BookAmt +
                ", RegDate=" + RegDate +
                ", RentCnt=" + RentCnt +
                '}';
    }

    public String getBkIdx() {
        return BkIdx;
    }

    public void setBkIdx(String bkIdx) {
        BkIdx = bkIdx;
    }

    public Integer getIsbn() {
        return Isbn;
    }

    public void setIsbn(Integer isbn) {
        Isbn = isbn;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public Integer getBookAmt() {
        return BookAmt;
    }

    public void setBookAmt(Integer bookAmt) {
        BookAmt = bookAmt;
    }

    public LocalDateTime getRegDate() {
        return RegDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        RegDate = regDate;
    }

    public Integer getRentCnt() {
        return RentCnt;
    }

    public void setRentCnt(Integer rentCnt) {
        RentCnt = rentCnt;
    }
}
