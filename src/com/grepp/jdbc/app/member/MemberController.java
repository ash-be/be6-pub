package com.grepp.jdbc.app.member;

// note 01 MVC 패턴
// Model(Service, Dao), Controller, View로 소프트웨어 구조를 구성하는 패턴
// Controller : 3-Tier Archi에서는 'Presentation Layer'
// 핵심로직인 Model이 외부에 종속되지 않도록 분리하기 위해
// 클라이언트와 직접 상호작용하는 Layer

// 1. 사용자의 입력값(요청 파라미터)을 app 내부에서 사용하기 적합한 형태로 파싱
// 2. 부적합한 사용자 입력값에 대한 검증 로직을 수행
//    사용자 요청에 대한 인가 처리를 하는 외벽 역할
// 3. Client에게 로직의 결과물을 '어떤 형태(text/html/json...)'로 보여줄 것인지 선택하는 작업 수행
//    > View 호출

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grepp.jdbc.app.member.dto.form.LeaveForm;
import com.grepp.jdbc.app.member.dto.form.ModifyForm;
import com.grepp.jdbc.app.member.dto.form.SignupForm;
import com.grepp.jdbc.app.member.validator.LeaveFormValidator;
import com.grepp.jdbc.app.member.validator.MemberValidator;
import com.grepp.jdbc.app.member.validator.ModifyFormValidator;
import com.grepp.jdbc.app.member.validator.SignupFormValidator;

import java.util.Map;

public class MemberController {

    private final SignupFormValidator signupValidator = new SignupFormValidator();
    private final ModifyFormValidator modifyValidator = new ModifyFormValidator();
    private final LeaveFormValidator leaveValidator = new LeaveFormValidator();

    private final MemberService memberService = new MemberService();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String signup(SignupForm form) {
        // 입력값에 대한 검증이 필요함 > validator 인터페이스를 구현한 MemberV 객체
        signupValidator.validate(form);
        return gson.toJson(memberService.signup(form.toDto()));

//        if(res.isPresent()) {
        // Validate에서 예외 처리를 다 해버려서, insert문제는 이슈가 생길 이유가 없어짐
//        return gson.toJson(res.get());
//        } else{
//            return "회원가입 실패";
    }

    public String get(String userId) {
        //vaildator 처리는 안 할 거임
        return gson.toJson(memberService.selectById(userId));
    }

    public String getAll() {
        return gson.toJson(memberService.selectAll());
    }

    public String modifyPassword(String userId, String password) {
        // 아이디 공백 여부?
        // 비밀번호 6자리 등 vildiator 필요

        ModifyForm form = new ModifyForm();
        form.setUserId(userId);
        form.setPassword(password);
        modifyValidator.validate(form);
        return gson.toJson(memberService.updatePassword(form.toDto()));
    }

    public String leave(String userId) {
        LeaveForm form = new LeaveForm();
        form.setUserId(userId);
        leaveValidator.validate(form);
        return gson.toJson(memberService.deleteById(userId));
    }

    public String login(String userId, String password) {
        memberService.authenticate(userId, password);
        return gson.toJson(Map.of("result", "success"));
    }
}