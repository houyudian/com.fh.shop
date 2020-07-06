package com.fh.shop.api.member.controller;

import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.biz.MemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.Check;
import com.fh.shop.api.util.KeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Api(tags = "会员接口")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("member/add")
    @ApiOperation("会员注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "名称",required = true,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="pwd",value = "密码",required = true,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="realName",value = "真实姓名",required = true,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="mail",value = "邮箱",required = true,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="phone",value = "电话",required = true,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="birthday",value = "生日",required = true,dataType = "string",paramType = "query"),
            @ApiImplicitParam(name="proId",value = "省",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name="cityId",value = "市",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name="areaId",value = "区/县",dataType = "long",paramType = "query"),
            @ApiImplicitParam(name="areaName",value = "地区",dataType = "string",paramType = "query"),

    })
    public ServerResponse addMember(Member member) {
        return memberService.addMember(member);
    }

    @GetMapping("member/validate")
    @ApiOperation("验证名称")
    public ServerResponse validateMember(String name) {
        return memberService.validateMember(name);
    }

    @GetMapping("member/validateMail")
    @ApiOperation("邮箱验证")
    public ServerResponse validateMail(String mail) {
        return memberService.validateMail(mail);
    }

    @GetMapping("member/validatePhone")
    @ApiOperation("手机号验证")
    public ServerResponse validatePhone(String phone) {
        return memberService.validatePhone(phone);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "string", paramType = "query")
    })
    public ServerResponse login(String name, String pwd) {
        return memberService.login(name, pwd);
    }

    @GetMapping("member/findMember")
    @Check
    @ApiOperation("会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header"),
    })
    public ServerResponse findMember(HttpServletRequest request) {
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);
        return ServerResponse.success(member);
    }

    @RequestMapping("logout")
    @Check
    @ApiOperation("退出")
    @ApiImplicitParams({
            @ApiImplicitParam(name="x-auth",value="登录后的头信息",required = true,dataType = "String",paramType = "header"),
    })
    public ServerResponse logout(HttpServletRequest request) {
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURRENT_MEMBER);

        Long id = member.getId();
        String uuid = member.getUuid();
        RedisUtil.delete(KeyUtil.buildMemberKey(id, uuid));
        return ServerResponse.success();
    }

}
