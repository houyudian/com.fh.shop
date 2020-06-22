package com.fh.shop.api.member.controller;

import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.biz.MemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.Check;
import com.fh.shop.api.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("member/add")
    public ServerResponse addMember(Member member) {
        return memberService.addMember(member);
    }

    @GetMapping("member/validate")
    public ServerResponse validateMember(String name) {
        return memberService.validateMember(name);
    }

    @GetMapping("member/validateMail")
    public ServerResponse validateMail(String mail) {
        return memberService.validateMail(mail);
    }

    @GetMapping("member/validatePhone")
    public ServerResponse validatePhone(String phone) {
        return memberService.validatePhone(phone);
    }

    @PostMapping("login")
    public ServerResponse login(String name, String pwd) {
        ServerResponse login = memberService.login(name, pwd);
        return login;
    }

    @GetMapping("member/findMember")
    @Check
    public ServerResponse findMember(HttpServletRequest request) {
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        return ServerResponse.success(member);
    }

    @RequestMapping("logout")
    @Check
    public ServerResponse logout(HttpServletRequest request) {
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);

        Long id = member.getId();
        String uuid = member.getUuid();
        RedisUtil.delete(KeyUtil.buildMemberKey(id, uuid));
        return ServerResponse.success();
    }

}
