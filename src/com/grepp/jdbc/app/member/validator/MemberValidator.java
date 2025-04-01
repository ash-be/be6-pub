package com.grepp.jdbc.app.member.validator;

import com.grepp.jdbc.app.member.dao.MemberDao;
import com.grepp.jdbc.app.member.dto.MemberDto;
import com.grepp.jdbc.infra.db.JdbcTemplate;
import com.grepp.jdbc.infra.exception.ValidException;
import com.grepp.jdbc.infra.validator.Validator;

import java.sql.Connection;
import java.util.Optional;

// validator라는 인터페이스를 구현하도록 할 것
// infra 패키지 참고
public class MemberValidator implements Validator<MemberDto> {

    private final MemberDao memberDao = new MemberDao();
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    // 검증 및 Exception 처리
    @Override
    public void validate(MemberDto dto) {
        // 여기서 입력값 검증
        // 1. 아이디는 공백일 수 없습니다.
        Connection conn = jdbcTemplate.getConnection();
        Optional<MemberDto> member = memberDao.selectById(conn, dto.getUserId());
        member.ifPresent(
                (e) -> {
                    throw new ValidException("중복된 아이디입니다.");
                });


        if(dto.getUserId() == null || dto.getUserId().isBlank()){
            throw new ValidException("아이디는 공백일 수 없습니다.");
        }

        // 2. 비밀번호는 6자리 이상입니다.
        if(dto.getPassword() == null || dto.getPassword().length() < 6){
            throw new ValidException("비밀번호는 6자리 이상이어야 합니다.");
        }
    }


}
