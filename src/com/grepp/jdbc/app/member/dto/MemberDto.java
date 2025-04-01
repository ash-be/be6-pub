package com.grepp.jdbc.app.member.dto;

// note 03 DTO
// Data Trens Object
// 데이터베이스의 테이블을 따라감
// database로부터 받은 값을 object로 변환

import com.grepp.jdbc.app.member.code.Grade;

import java.util.Objects;

public class MemberDto {

    private String userId;
    private String password;
    private String email;
    private Grade grade;
    private String tell;
    private Boolean isLeave;

    // 생성자는 안 보이네? > 기본 생성자
    // 어차피 값 넣을 땐 new MemberDto()에서 set으로 값 넣을 것이기 때문에

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
            // 완전 동일한 주소값이니까 바로 투루~
            // this값이 Object와 같은 주소를 참조하면, 같은 인스턴스 바로 true
        }
        if (!(o instanceof MemberDto memberDto)) {
            return false;
            // 타입자체가 다르니까 무조건 다를 것 폴스~
            // o 가 MemberDto 가 아니면 웩
            // 우린 MemberDto만 취급해요.
        }
        // 진짜 논리적 비교가 필요할 때 실행
        return Objects.equals(userId, memberDto.userId);
        // return (a == b) || (a != null && a.equals(b));
        // null이 있어도 널포인트익셉션 안 나고 비교 가능
    }


    // hashCode() : 단일 객체에 대한 null-safe하게 hashCode 생성
    // ## hash()  : 여러 개 필드를 조합해 하나의 hashCode 생성
    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", grade=" + grade +
                ", tell='" + tell + '\'' +
                ", isLeave=" + isLeave +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public Boolean getLeave() {
        return isLeave;
    }

    public void setLeave(Boolean leave) {
        isLeave = leave;
    }
}

