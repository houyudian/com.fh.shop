package admin.mapper;

import admin.param.MemberParam;
import admin.po.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface MemberMapper extends BaseMapper<Member> {

    Long memberCount(MemberParam memberParam);

    List<Member> findMember(MemberParam memberParam);

}
