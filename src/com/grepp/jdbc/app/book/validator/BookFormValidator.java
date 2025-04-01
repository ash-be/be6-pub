package com.grepp.jdbc.app.book.validator;

import com.grepp.jdbc.app.book.dao.BookDao;
import com.grepp.jdbc.app.book.dto.BookDto;
import com.grepp.jdbc.app.book.dto.form.BookForm;
import com.grepp.jdbc.app.member.dto.MemberDto;
import com.grepp.jdbc.infra.db.JdbcTemplate;
import com.grepp.jdbc.infra.exception.ValidException;
import com.grepp.jdbc.infra.validator.Validator;

import java.sql.Connection;
import java.util.Optional;

public class BookFormValidator implements Validator<BookForm> {

    private final BookDao bookDao = new BookDao();
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    // 도서명, isbn, 작가, 카테고리는 공백일 수 없습니다.
    // 도서재고는 0보다 작을 수 없습니다.
    // 카테고리는 NOVEL, POEM, HUMANITY 가 존재합니다.

    @Override
    public void validate(BookForm form) {

        Connection conn = jdbcTemplate.getConnection();
        Optional<BookDto> book = bookDao.selectByBkIdx(conn, form.getBkIdx());

        if(form.getTitle() == null || form.getTitle().isBlank()){
            throw new ValidException("도서명은 공백일 수 없습니다.");
        }

        if(form.getIsbn() == null || form.getIsbn() == 0){
            throw new ValidException("ISBN은 공백일 수 없습니다.");
        }

        if(form.getAuthor() == null || form.getAuthor().isBlank()){
            throw new ValidException("작가는 공백일 수 없습니다.");
        }

        if(form.getCategory() == null || form.getCategory().isBlank()){
            throw new ValidException("카테고리는 공백일 수 없습니다.");
        }

        // 카테고리 구분
        try {
            Category.valueOf(form.getCategory()); // 대소문자 민감함 (대문자로 입력 받아야 함)
        } catch (IllegalArgumentException e) {
            throw new ValidException("카테고리는 NOVEL, POEM, HUMANITY 중 하나여야 합니다.");
        }

//        if(!((form.getCategory()=="NOVEL")
//                ||(form.getCategory()=="NOVEL")
//                ||(form.getCategory()=="NOVEL"))){
//            throw new ValidException("카테고리는 NOVEL, POEM, HUMANITY로 구분됩니다.");
//        }
//        if(Category.valueOf(form.getCategory())){
//            throw new ValidException("카테고리는 NOVEL, POEM, HUMANITY 중 하나여야 합니다.");
//        }

        if(form.getRentCnt() == null || form.getRentCnt() < 0){
            throw new ValidException("도서 재고는 0보다 작을 수 없습니다.");
        }


    }
}
