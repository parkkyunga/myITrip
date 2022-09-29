package co.itrip.prj.member.service;

import java.util.List;

import co.itrip.prj.cbtGuide.service.CbtGuideVO;
import co.itrip.prj.cbtGuide.service.MyCbtHderVO;

public interface MemberService {
	
	List<MemberVO> memberSelectList(MemberVO vo);
	MemberVO memberSelect(MemberVO vo); // 유저 단건조회
	int memberInsert(MemberVO vo); // 회원가입
	int memberUpdate(MemberVO vo); // 유저 정보수정
	int memberDelete(MemberVO vo); // 회원 삭제
	
	public int ajaxIdChk(String mId);    // id 중복 검사
	public int ajaxNickChk(String mNik); // 닉네임 중복검사
	public int ajaxpwChk(String mPw);    // 패스워드 확인

	List<MyCbtHderVO> myWrongAnswerLangCd(MyCbtHderVO vo);
	List<CbtGuideVO> myWrongAnswerNote(CbtGuideVO vo);
}
