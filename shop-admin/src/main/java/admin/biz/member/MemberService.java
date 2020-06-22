package admin.biz.member;

import admin.common.DataTableResult;
import admin.param.MemberParam;

public interface MemberService {
    DataTableResult findMember(MemberParam memberParam);

}
