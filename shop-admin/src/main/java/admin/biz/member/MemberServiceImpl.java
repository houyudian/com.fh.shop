package admin.biz.member;

import admin.common.DataTableResult;
import admin.mapper.MemberMapper;
import admin.param.MemberParam;
import admin.po.Member;
import admin.util.DateUtil;
import admin.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public DataTableResult findMember(MemberParam memberParam) {
        String name = memberParam.getName();
        String realName = memberParam.getRealName();
        if (StringUtils.isNotEmpty(name)&&StringUtils.isNotEmpty(realName)){
            try {
                String s = new String(name.getBytes("iso-8859-1"), "utf-8");
                String realName1 = new String(realName.getBytes("iso-8859-1"), "utf-8");
                memberParam.setRealName(s);
                memberParam.setRealName(realName1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Long count = memberMapper.memberCount(memberParam);
        List<Member> memberList = memberMapper.findMember(memberParam);
        List<MemberVo> memberVoList = memberList.stream().map(x -> {
            MemberVo m = new MemberVo();
            m.setId(x.getId());
            m.setPhone(x.getPhone());
            m.setName(x.getName());
            m.setRealName(x.getRealName());
            m.setBirthday(DateUtil.date(x.getBirthday(), DateUtil.Y_M_D));
            m.setAreaName(x.getAreaName());
            m.setMail(x.getMail());
            return m;
        }).collect(Collectors.toList());
        return new DataTableResult(memberParam.getDraw(), count, count, memberVoList);
    }
}
