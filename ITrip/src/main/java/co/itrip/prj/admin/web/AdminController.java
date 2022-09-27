package co.itrip.prj.admin.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.itrip.prj.consult.service.ConsultService;
import co.itrip.prj.consult.service.ConsultVO;

import org.springframework.web.bind.annotation.PostMapping;

import co.itrip.prj.admin.service.AdminService;
import co.itrip.prj.guide.service.GuideVO;
import co.itrip.prj.iclass.service.ClassService;
import co.itrip.prj.iclass.service.ClassVO;
import co.itrip.prj.member.service.MemberVO;


@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService; // 1:1 상담서비스
	
	@Autowired
	private AdminService dao;

	
	@GetMapping("/appClass.do") // admin-Class 승인
	public String appClass(ClassVO vo, Model model, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "5") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		model.addAttribute("pageInfo", PageInfo.of(adminService.classList(vo)));
		return "admin/appclass";
	}
	

	@GetMapping("/appConsult.do") // admin-Consult 승인
	public String appConsult(ConsultVO vo, Model model, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "5") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		// vo.setStateCd("1");
		model.addAttribute("pageInfo", PageInfo.of(adminService.ConsultList(vo)));
		return "admin/appconsult";
	}
	
	
	@GetMapping("/memberAuthList.do")
	public String memberAuthList(GuideVO vo,Model model) {
		model.addAttribute("memberList", dao.memberAuthList(vo));
		return "admin/memberAuthList";
	}


	@GetMapping("/memberList.do")
	public String memberList(MemberVO vo,Model model) {
		model.addAttribute("memberList", dao.memberList(vo));
		return "admin/memberList";
	}
	
	@PostMapping("/memberAuthUpdate.do")
	public String memberAuthUpdate(GuideVO vo) {
		dao.memberAuthUpdate(vo);
		return "admin/memberAuthList";
	}
}
