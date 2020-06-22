package admin.controller;

import admin.biz.member.MemberService;
import admin.common.DataTableResult;
import admin.param.MemberParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @RequestMapping("toListMember")
    public String toList() {
        return "/member/memberList";
    }

@RequestMapping("findMember")
@ResponseBody
    public DataTableResult findMember(MemberParam memberParam){
        return memberService.findMember(memberParam);
    }
}
