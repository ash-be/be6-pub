package com.grepp.jdbc.app.book;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grepp.jdbc.app.book.dto.form.BookForm;
import com.grepp.jdbc.app.book.dto.form.BookModifyForm;
import com.grepp.jdbc.app.book.validator.BookFormValidator;

public class BookController {

    private final BookService bookService = new BookService();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private BookFormValidator bookFormValidator = new BookFormValidator();

    // (1) findAllBooks
    public String findAllBooks() {
        return gson.toJson(bookService.selectAllBooks());
    }

    // (2) registBook
    public String registBook(BookForm form) {
        bookFormValidator.validate(form);

        return gson.toJson(bookService.registBook(form.toDto()));
    }


    // (3) modifyBookInfo
    public String modifyBookInfo(String bkIdx, String info) {
        BookModifyForm form = new BookModifyForm();
        form.setBkIdx(bkIdx);
        form.setInfo(info);
        return gson.toJson(bookService.updateBook(form.bookToDto()));
    }


    // (4) deleteBook  - boolean 타입으로 구현해야 함
    public Boolean deleteBook(String bkIdx) {
        return bookService.deleteBook(bkIdx);
    }
}
