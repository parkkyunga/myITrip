package co.itrip.prj.member.web;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.itrip.prj.cbtGuide.service.CbtGuideVO;
import co.itrip.prj.cbtGuide.service.MyCbtHderVO;
import co.itrip.prj.cmmncd.service.CmmnCdService;
import co.itrip.prj.community.service.CommunityService;
import co.itrip.prj.community.service.CommunityVO;
import co.itrip.prj.consult.service.ConsultService;
import co.itrip.prj.consult.service.ConsultVO;
import co.itrip.prj.cs.service.CSBoardService;
import co.itrip.prj.cs.service.CSBoardVO;
import co.itrip.prj.guide.service.GuideService;
import co.itrip.prj.guide.service.GuideVO;
import co.itrip.prj.iclass.service.ClassAttendVO;
import co.itrip.prj.iclass.service.ClassService;
import co.itrip.prj.iclass.service.ClassVO;
import co.itrip.prj.member.service.MemberService;
import co.itrip.prj.member.service.MemberVO;
import co.itrip.prj.review.service.ReviewVO;


@Controller
public class MemberController { //Principal
	
	@Autowired
	private CommunityService comService;
	
	@Autowired
	private MemberService mService;
	
	@Autowired
	private CmmnCdService cdService;
	
	@Autowired
	private ClassService cService;
	
	@Autowired
	private CSBoardService csService;
	
	@Autowired
	private ConsultService conService;
	
	@Autowired
	private GuideService guiService;

	
	
	// 마이페이지
	@GetMapping("/myPage")
	public String myPage() {
		return "member/mypage";
	}
	
		
	// 마이페이지-클래스
	@GetMapping("/mClass")
	public String mClass(ClassVO vo, ClassAttendVO avo, Model model, Principal principal) {
		vo.setMemberId(principal.getName());
		avo.setMemberId(principal.getName());
		model.addAttribute("classList", cService.myClassList(vo));
		model.addAttribute("attend", cService.myClassAttendList(avo));
		return "member/mclass";
	}
	
	// 마이페이지-1:1상담
	@GetMapping("/mConsult")
	public String mConsult(ConsultVO vo, Model model, Principal principal) {
		vo.setMemberId(principal.getName());
		model.addAttribute("consultList", conService.myConsultList(vo));
		return "member/mconsult";
	}
	
	// 가이드 신청 요청
	@GetMapping("/gApply")
	public String gApply(Model model, MemberVO vo, GuideVO gvo, Principal principal) {
		// 가이드 신청폼에 member테이블 id,name 가져옴
		vo.setMemberId(principal.getName());
		gvo.setGuideId(principal.getName());
		
		if(guiService.guideSelect(gvo) == null) {
			model.addAttribute("guides", mService.memberSelect(vo));
			// 가이드 신청폼 select 태그
			model.addAttribute("careerCdList", cdService.careerCdList());
			model.addAttribute("dutyCdList", cdService.dutyCdList());
			return "member/gapply";
		} else {
			model.addAttribute("members", mService.memberSelect(vo));
			model.addAttribute("guides", guiService.guideSelect(gvo));
			return "member/error";
		}
		
	}
	
	// 클래스 리뷰폼
	@PostMapping("/mcReview")
	public String mcReview(ReviewVO vo, Model model, Principal principal) {
		// 클래스 리뷰폼에 가이드 아이디, 클래스 번호 가져감
		return "member/classReview";
	}

	// 마이페이지 내가 쓴 글(스터디게시판)
	@GetMapping("/myWriter")
	public String myWriter(CommunityVO vo,CSBoardVO csvo, Model model, Principal principal,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "5") int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		vo.setCtgry("''");
		vo.setMemberId(principal.getName());
		
		csvo.setMemberId(principal.getName());
		csvo.setCtgry("QNA");
		
		model.addAttribute("pageOutfo", PageInfo.of(csService.findAll(csvo)));
		model.addAttribute("pageInfo", PageInfo.of(comService.findAll(vo)));
		
		return "member/mywriter";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// 상담 리뷰
	@GetMapping("/conReview")
	public String conReview() {
		return "member/consultReview";
	}
	
	

	// 유저 회원 정보 수정페이지
	@GetMapping("/mrecive.do")
	public String mrecive(Principal principal, MemberVO vo, Model model) {
		vo.setMemberId(principal.getName());
		model.addAttribute("member", mService.memberSelect(vo)); 
		return "member/mrecive";
	}
	
	// 회원 정보 수정페이지에서 수정 후  form action -> DB수정 -> 수정된 정보 바로 적용
	@PostMapping("/mreviceUpdate.do")
	public String mreviceUpdate(MemberVO vo, Principal principal) {
		vo.setMemberId(principal.getName());
		mService.memberUpdate(vo);
		return "redirect:myPage";
	}
	
//	// 가이드 회원 정보 수정페이지
//	@GetMapping("/gmrecive.do")
//	public String mrecive1(Principal principal, MemberVO vo, Model model) {
//		vo.setMemberId(principal.getName());
//		model.addAttribute("member", mService.memberSelect(vo)); 
//		return "member/gmrecive";
//	}
	
//	// 회원 정보 수정페이지에서 수정 후  form action -> DB수정 -> 수정된 정보 바로 적용
//	@PostMapping("/gmreviceUpdate.do")
//	public String mreviceUpdate1(MemberVO vo, Principal principal) {
//		vo.setMemberId(principal.getName());
//		mService.memberUpdate(vo);
//		// principal 로 아이디값 받아올때 변경한값 담을 위치
//		return "redirect:gmyPage.do";
//	}

	
	/* 김하은 : 회원가입*/
	@PostMapping("/memberInsert.do")
	public String memberInsert(MemberVO vo) {
		mService.memberInsert(vo);
		return "main/main";
	}

}

