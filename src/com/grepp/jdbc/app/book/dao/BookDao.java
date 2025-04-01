package com.grepp.jdbc.app.book.dao;


import com.grepp.jdbc.app.book.dto.BookDto;
import com.grepp.jdbc.infra.db.JdbcTemplate;
import com.grepp.jdbc.infra.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {

    // jdbcTemp을 구현하는 방법 온전히 이해한 건 아니지만...
    // url, user, password를 필드로 하고 있는 jdbcTemp 갖고 와서, 초기화
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();


    // (1) 도서 조회
    public List<BookDto> selectAll(Connection conn) {
        String sql = "select * from book";
        List<BookDto> books = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            // stmt.executeQ 메서드 자체가 resultSet을 반환함(결과 테이블일 것)
            try(ResultSet rset = stmt.executeQuery()) {
                // 1개 row마다 읽어내서, 결과 테이블에 add
                while(rset.next()){
                    books.add(generateBookDto(rset));
                }

                return books;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    // rset을 반환 받아서, 한 줄씩 읽어들이는 메서드
    // 모든 "조회"에는 다 사용될 것
    private BookDto generateBookDto(ResultSet rset) throws SQLException {
        BookDto res = new BookDto();
        res.setBkIdx(rset.getString("bk_idx"));
        res.setIsbn(rset.getInt("isbn"));
        res.setCategory(rset.getString("category"));
        res.setTitle(rset.getString("title"));
        res.setAuthor(rset.getString("author"));
        res.setInfo(rset.getString("info"));
        res.setBookAmt(rset.getInt("book_Amt"));
        // localDateTime 타입 맞추는 법 getObject(columnLabel, + class.Type)
        res.setRegDate(rset.getObject("reg_date", LocalDateTime.class));
        res.setRentCnt(rset.getInt("rent_cnt"));

        return res;
    }

    // (2,3) 등록 및 수정한 도서 출력
    public Optional<BookDto> selectByBkIdx(Connection conn, String bkIdx){
        String sql = "select * from book where bk_idx = ?";
        BookDto res = null;
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, bkIdx);
            try (ResultSet rset = stmt.executeQuery()){
                // 값이 하나일 거란 확신에 if를 씀
                if(rset.next()){
                    res = generateBookDto(rset);
                }
                return Optional.ofNullable(res);
            }
        }catch (SQLException ex){
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }


    // (2) 도서 등록
    public Optional<BookDto> insert(Connection conn, BookDto dto){

        String sql = "insert into book(" +
                "bk_Idx, isbn, category, title, author, " +
                "info, book_Amt, reg_Date, rent_Cnt) "
                + "values(?,?,?,?,?,?,?,?,?)";

        // 실제 DB 연결 + SQL 실행
        try (
                // 커넥션 템플릿에서 가져옴
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, dto.getBkIdx());
            stmt.setInt(2, dto.getIsbn());
            stmt.setString(3, dto.getCategory());
            stmt.setString(4, dto.getTitle());
            stmt.setString(5, dto.getAuthor());
            stmt.setString(6, dto.getInfo());
            stmt.setInt(7, dto.getBookAmt());
            stmt.setObject(8, dto.getRegDate());    //timestemp여서 LocalDateTime으로 처리함 -> statement는 set object로 받아줌
            stmt.setInt(9, dto.getRentCnt());

            int res = stmt.executeUpdate();
            return res > 0 ? Optional.of(dto) : Optional.empty();

        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }


    // (3) 도서 소개 수정 - bk_idx로 찾음
    public Optional<BookDto> updateBookInfo(Connection conn, BookDto dto){
        String sql = "update book set info = ? where bk_idx = ?";

        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, dto.getInfo());
            stmt.setString(2, dto.getBkIdx());
            System.out.println(stmt);
            int res = stmt.executeUpdate();
            return res > 0 ? Optional.of(dto) : Optional.empty();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }


    // (4) 도서 삭제
    // del은 optional이 아니고 boolean으로 해도 무방하니, 함 해보자!
    public boolean delete(Connection conn, String bkIdx){
        String sql = "delete from book where bk_idx = ?";
        try(
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, bkIdx);
            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }


}
