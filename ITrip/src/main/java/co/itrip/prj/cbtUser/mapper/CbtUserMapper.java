package co.itrip.prj.cbtUser.mapper;

import java.util.List;

import co.itrip.prj.cbtUser.service.CbtUserVO;

public interface CbtUserMapper {
	List<CbtUserVO> cbtUserList(CbtUserVO vo);
	int cbtUserInsert(CbtUserVO vo);
}
