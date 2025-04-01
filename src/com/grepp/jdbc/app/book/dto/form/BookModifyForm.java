package com.grepp.jdbc.app.book.dto.form;

import com.grepp.jdbc.app.book.dto.BookDto;

public class BookModifyForm {

    private String bkIdx;
    private String info;

    @Override
    public String toString() {
        return "ModifyForm{" +
                "bk_Idx='" + bkIdx + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public String getBkIdx() {
        return bkIdx;
    }

    public void setBkIdx(String bkIdx) {
        this.bkIdx = bkIdx;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BookDto bookToDto() {
        BookDto dto = new BookDto();
        dto.setBkIdx(bkIdx);
        dto.setInfo(info);
        return dto;
    }
}