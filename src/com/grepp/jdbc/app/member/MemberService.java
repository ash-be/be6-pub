package com.grepp.jdbc.app.member;

// note 02 Service
// 비즈니스 로직 구현
// DB의 트랜잭션 구현(commit / rollback)

import com.grepp.jdbc.app.member.auth.Principal;
import com.grepp.jdbc.app.member.auth.SecurityContext;
import com.grepp.jdbc.app.member.dao.MemberDao;
import com.grepp.jdbc.app.member.dao.MemberInfoDao;
import com.grepp.jdbc.app.member.dto.MemberDto;
import com.grepp.jdbc.app.member.dto.MemberInfoDto;
import com.grepp.jdbc.infra.db.JdbcTemplate;
import com.grepp.jdbc.infra.exception.DataAccessException;
import com.grepp.jdbc.infra.exception.ValidException;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MemberService {
    // 트랜잭션 관리
    // 비즈니스 로직 생성

    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
    private final MemberDao memberDao = new MemberDao();
    private final MemberInfoDao memberInfoDao = new MemberInfoDao();

    public MemberDto signup(MemberDto dto) {
        Connection conn = jdbcTemplate.getConnection();
        // try 안에서 일어난 각각의 모든 일이 문제가 생기면
        // 무조건 rollback
        try{
            memberDao.insert(conn, dto);
            MemberInfoDto info = new MemberInfoDto();

            info.setUserId(dto.getUserId());
            memberInfoDao.insert(conn, info);

            jdbcTemplate.commit(conn);
            return dto;
//            // 아이디 중복 여부는 이곳이 아니라, Controller인 Validator에서 진행해줘야 맞음
//            //
//            member.ifPresent(
//                    (e) -> {
//                        throw new ValidException("중복된 아이디입니다.");
//                    });
//            // 이거 뭐지? > dao에 있는 conn 새로 만들면 안 되고, 여기 있는 걸 dao가 써야 함
//            Optional<MemberDto> res = memberDao.insert(conn, dto);
//            jdbcTemplate.commit(conn);
//            return res;
        }catch (DataAccessException e){
            jdbcTemplate.rollback(conn);
            throw e;
        }finally {
            jdbcTemplate.close(conn);
        }
    }

    public MemberDto selectById(String userId) {
        Connection conn = jdbcTemplate.getConnection();
        // select문일 테니, catch는 필요없을 것
        try{
            return memberDao.selectById(conn, userId).orElse(null);
        } finally {
            jdbcTemplate.close(conn);
        }
    }

    public List<MemberDto> selectAll() {
        Connection conn = jdbcTemplate.getConnection();
        try{
            return memberDao.selectAll(conn);
        }finally {
            jdbcTemplate.close(conn);
        }
    }

    public MemberDto updatePassword(MemberDto dto) {
        Connection conn = jdbcTemplate.getConnection();
        try{
            memberDao.updatePassword(conn, dto);
            memberInfoDao.updateModifyDate(conn, dto.getUserId());
            jdbcTemplate.commit(conn);
            return dto;
        }catch (DataAccessException e){
            jdbcTemplate.rollback(conn);
            throw e;
        }finally {
            jdbcTemplate.close(conn);
        }
    }

    public MemberDto deleteById(String userId) {
        Connection conn = jdbcTemplate.getConnection();

        try{
            memberDao.delete(conn, userId);
            memberInfoDao.updateLeaveDate(conn, userId);
            jdbcTemplate.commit(conn);
            MemberDto dto = new MemberDto();
            dto.setUserId(userId);
            dto.setLeave(true);
            return dto;
        }catch (DataAccessException e){
            jdbcTemplate.rollback(conn);
            throw e;
        }finally {
            jdbcTemplate.close(conn);
        }
    }

    public void authenticate(String userId, String password) {
        Connection conn = jdbcTemplate.getConnection();

        SecurityContext securityContext = SecurityContext.getInstance();

        try{
            Optional<MemberDto> member = memberDao.selectByIdAndPassword(conn, userId, password);

            if(member.isPresent()){
                MemberDto dto = member.get();
                Principal principal = new Principal(dto.getUserId(), dto.getGrade(), LocalDateTime.now());
                securityContext.setPrincipal(principal);
                return;
            }
            securityContext.setPrincipal(Principal.ANONYMOUS);
        }finally {
            jdbcTemplate.close(conn);
        }
    }
}