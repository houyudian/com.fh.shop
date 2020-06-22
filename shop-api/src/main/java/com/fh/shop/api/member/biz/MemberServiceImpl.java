package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.mapper.MemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.MD5Util;
import com.fh.shop.api.util.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public ServerResponse addMember(Member m) {
        String name = m.getName();
        String phone = m.getPhone();
        String pwd = m.getPwd();
        String mail = m.getMail();
        String realName = m.getRealName();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(mail) || StringUtils.isEmpty(realName)) {
            return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_ENTRY);
        }

        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        Member member = memberMapper.selectOne(memberQueryWrapper.eq("name", name));
        if (member != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_NAME_EXIST);
        }
        QueryWrapper<Member> phone1 = memberQueryWrapper.eq("phone", phone);
        Member member1 = memberMapper.selectOne(phone1);
        if (member1 != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_EXIST);
        }
        QueryWrapper<Member> mail1 = memberQueryWrapper.eq("mail", mail);

        Member member2 = memberMapper.selectOne(mail1);
        if (member2 != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_EXIST);
        }
        memberMapper.insert(m);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse validateMember(String name) {
        if (StringUtils.isEmpty(name)) {
            return ServerResponse.error(ResponseEnum.MEMBER_NAME_IS_ENTRY);
        }
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("name", name);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if (member != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_NAME_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse validateMail(String mail) {
        if (StringUtils.isEmpty(mail)) {
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_IS_ENTRY);
        }
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mail", mail);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if (member != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_MAIL_EXIST);
        }
        return ServerResponse.success(mail);
    }

    @Override
    public ServerResponse validatePhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_ENTRY);
        }
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("phone", phone);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if (member != null) {
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(String name, String pwd) {

        if (!StringUtils.isNotEmpty(name) || !StringUtils.isNotEmpty(pwd)) {
            return ServerResponse.error(ResponseEnum.LOGIN_MEMBER_NULL_IS_EMPTY);
        }
        name = RSAUtil.decrypt(name);
        pwd = RSAUtil.decrypt(pwd);
        QueryWrapper<Member> m = new QueryWrapper<>();

        m.eq("name", name);
        Member member = memberMapper.selectOne(m);
        if (member == null) {
            return ServerResponse.error(ResponseEnum.LOGIN_MEMBER_NAME_ERROR);
        }
        if (!pwd.equals(member.getPwd())) {
            return ServerResponse.error(ResponseEnum.LOGIN_MEMBER_PWD_ERROR);
        }

        MemberVo vo = new MemberVo();
        Long id = member.getId();
        vo.setId(id);
        vo.setName(member.getName());
        vo.setRealName(member.getRealName());
        String uuid = UUID.randomUUID().toString();
        vo.setUuid(uuid);

        String s1 = JSONObject.toJSONString(vo);

        String s2 = null;
        try {
            s2 = Base64.getEncoder().encodeToString(s1.getBytes("utf-8"));

            String sign = MD5Util.sign(s2, SystemConstant.APPSECRET);
            String baseSign = Base64.getEncoder().encodeToString(sign.getBytes());
            String result = s2 + "." + baseSign;
            RedisUtil.setEx(KeyUtil.buildMemberKey(id, uuid), "1", KeyUtil.MEMBER_EXPIRE);
            return ServerResponse.success(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
