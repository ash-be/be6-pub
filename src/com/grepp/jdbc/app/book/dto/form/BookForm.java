package com.grepp.jdbc.app.book.dto.form;

import com.grepp.jdbc.app.book.dto.BookDto;

import java.time.LocalDateTime;

public class BookForm {
    private String bkIdx;
    private Integer isbn;
    private String category;
    private String title;
    private String author;
    private String info;
    private Integer bookAmt;
    private LocalDateTime regDate;
    private Integer rentCnt;

    public BookDto toDto() {
        BookDto bookDto = new BookDto();
        bookDto.setBkIdx(bkIdx);
        bookDto.setIsbn(isbn);
        bookDto.setCategory(category);
        bookDto.setTitle(title);
        bookDto.setAuthor(author);
        bookDto.setInfo(info);
        bookDto.setBookAmt(bookAmt);
        bookDto.setRegDate(regDate);
        bookDto.setRentCnt(rentCnt);
        return bookDto;
    }


    @Override
    public String toString() {
        return "bookForm{" +
                "bkIdx='" + bkIdx + '\'' +
                ", isbn=" + isbn +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", info='" + info + '\'' +
                ", bookAmt=" + bookAmt +
                ", regDate=" + regDate +
                ", rentCnt=" + rentCnt +
                '}';
    }

    public String getBkIdx() {
        return bkIdx;
    }

    public void setBkIdx(String bkIdx) {
        this.bkIdx = bkIdx;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getBookAmt() {
        return bookAmt;
    }

    public void setBookAmt(Integer bookAmt) {
        this.bookAmt = bookAmt;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public Integer getRentCnt() {
        return rentCnt;
    }

    public void setRentCnt(Integer rentCnt) {
        this.rentCnt = rentCnt;
    }
}
