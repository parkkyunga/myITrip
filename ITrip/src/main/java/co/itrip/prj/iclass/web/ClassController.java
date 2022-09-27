package co.itrip.prj.iclass.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import co.itrip.prj.cmmncd.service.CmmnCdService;
import co.itrip.prj.iclass.service.ClassDtVO;
import co.itrip.prj.iclass.service.ClassService;
import co.itrip.prj.iclass.service.ClassVO;

@Controller
public class ClassController {
	
	@Autowired
	private ClassService cService; // 가이드 서비스
	
	@Autowired
	private CmmnCdService cmService; // 공통코드서비스
	
	
	@GetMapping("/iClassList.do") 
	public String iClass(ClassVO vo,Model model, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "6") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		model.addAttribute("pageInfo", PageInfo.of(cService.classList(vo)));
		//model.addAttribute("classList", cService.classList(vo));
		return "class/iclassList";
	}
	
	// Class insert & 파일처리
		@PostMapping("/classInsert.do")
		public String classInsert(ClassVO vo, ClassDtVO dtvo, MultipartFile file) throws IllegalStateException, IOException {
			
			if(!file.getOriginalFilename().isEmpty()) {
				String projectpath = System.getProperty("user.dir")+"/src/main/resources/static/files";  
				UUID uuid = UUID.randomUUID(); 
				String filename = uuid+"_"+file.getOriginalFilename(); 
				File saveFile = new File(projectpath,filename);
				file.transferTo(saveFile);
				vo.setAttach(filename);
				vo.setAttachDir("/files/"+filename);
			}
			// ClassVO
			System.out.println("1"+vo.getTitle());
			System.out.println("2"+vo.getClassNo()); // 0으로나옴
			System.out.println("3"+vo.getContent());
			System.out.println("4"+vo.getCrclm());
			System.out.println("5"+vo.getDt());  // null
			System.out.println("6"+vo.getPrice());
			System.out.println("7"+vo.getConfmCd()); // null
			System.out.println("8"+vo.getGuideId());
			System.out.println("9"+vo.getJobCd());
			System.out.println("10"+vo.getPsncpa());
			System.out.println("11"+vo.getClassCnt());
			System.out.println("12"+vo.getAttach());
			System.out.println("13"+vo.getAttachDir());
			System.out.println("19"+vo.getEnnc());
			
			// ClassDtVO
			System.out.println("14"+vo.getClassDt().get(0).getClassNo()); // 0으로나옴
			System.out.println("15"+vo.getClassDt().get(0).getTerm());
			System.out.println("16"+vo.getClassDt().get(0).getBeginTime());
			System.out.println("17"+vo.getClassDt().get(0).getEndTime());
			System.out.println("18"+vo.getClassDt().get(0).getCtimeNo()); // 0
			
			cService.classInsert(vo);
			
			return "guide/gclass";
		}
		
		@RequestMapping("/iClassSelectOne.do") 
		public String iClassSelectOne(ClassVO vo, Model model, ClassDtVO dvo) {
			model.addAttribute("classOne", cService.classSelectOne(vo));
			model.addAttribute("classDt", cService.classDtList(dvo));
			return "class/iclassSelectOne";
		}
		
		@GetMapping("/iClassInsert.do")
		public String iClassInsert() {
			return "class/iClassList";//말고 마이페이지로 넘어가야됨
		}

}
