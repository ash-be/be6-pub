package com.grepp.jdbc.app.book;

import com.grepp.jdbc.app.book.dao.BookDao;
import com.grepp.jdbc.app.book.dto.BookDto;
import com.grepp.jdbc.infra.db.JdbcTemplate;
import com.grepp.jdbc.infra.exception.DataAccessException;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class BookService {


    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
    private final BookDao bookDao = new BookDao();

    // 모든 도서 조회 로직
    public List<BookDto> selectAllBooks() {
        Connection conn = jdbcTemplate.getConnection();
        try{
            return bookDao.selectAll(conn);
        }finally {
            jdbcTemplate.close(conn);
        }
    }

    // bk_idx값으로 도서 조회 로직
    public BookDto selectByBkIdx(String bk_Idx) {
        Connection conn = jdbcTemplate.getConnection();
        // select문일 테니, catch는 필요없을 것
        try{
            return bookDao.selectByBkIdx(conn, bk_Idx).orElse(null);
        } finally {
            jdbcTemplate.close(conn);
        }
    }


    // 도서 등록 로직
    public BookDto registBook(BookDto dto) {
        Connection conn = jdbcTemplate.getConnection();
        try{dto.setRegDate(LocalDateTime.now());
            bookDao.insert(conn, dto);

            jdbcTemplate.commit(conn);
            return dto;
        } catch (DataAccessException e){
            jdbcTemplate.rollback(conn);
            throw e;
        }finally {
            jdbcTemplate.close(conn);
        }
    }

    // 도서 수정 로직
    public BookDto updateBook(BookDto dto) {
        Connection conn = jdbcTemplate.getConnection();
        try{
            bookDao.updateBookInfo(conn, dto);
            jdbcTemplate.commit(conn);
            return dto;
        } catch (DataAccessException e){
            jdbcTemplate.rollback(conn);
            throw e;
        }finally {
            jdbcTemplate.close(conn);
        }
    }


    // 도서 삭제 로직
    public Boolean deleteBook(String bk_Idx) {
        Connection conn = jdbcTemplate.getConnection();
        try{boolean result = bookDao.delete(conn, bk_Idx);
            jdbcTemplate.commit(conn);
            return result;
        } catch (DataAccessException e){
            jdbcTemplate.rollback(conn);
            throw e;
        }finally {
            jdbcTemplate.close(conn);
        }
    }

}
