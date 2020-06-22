package com.fh.shop.api.member.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.po.Member;

public interface MemberService {
    ServerResponse addMember(Member member);

    ServerResponse validateMember(String name);

    ServerResponse validateMail(String mail);

    ServerResponse validatePhone(String phone);

    ServerResponse login(String name, String pwd);

}
