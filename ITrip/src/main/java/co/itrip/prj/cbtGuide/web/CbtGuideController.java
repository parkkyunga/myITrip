package co.itrip.prj.cbtGuide.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.itrip.prj.cbtGuide.mapper.CbtGuideMapper;
import co.itrip.prj.cbtGuide.service.CbtGuideService;
import co.itrip.prj.cbtGuide.service.CbtGuideVO;
import co.itrip.prj.cbtGuide.service.CbtKeywordVO;
import co.itrip.prj.gtpcd.service.GtpCdService;
import co.itrip.prj.langcd.service.LangCdService;

@Controller
public class CbtGuideController {
	@Autowired
	private CbtGuideMapper cgDao;
	@Autowired
	private GtpCdService gtpDao;
	@Autowired
	private LangCdService langDao;
	
	//메인화면
	@RequestMapping("/cbtGuideMain.do")
	public String cbtGuideMain(Model model) {
		model.addAttribute("cbtList", cgDao.cbtGuideList());
	    model.addAttribute("gtpCdList", gtpDao.gtpCdList());
		model.addAttribute("langCdList", langDao.langCdList());
	    return "cbtGuide/cbtGuideMain";
	}
	
	//유형, 언어별로 리스트 문제 출력
	@PostMapping("/cbtGuideListTab.do")
	public String cbtGuideListTab(CbtGuideVO vo, Model model) {
		model.addAttribute("cbtList", cgDao.cbtGuideListTab(vo));
		return "cbtGuide/cbtGuideListTab";
	}
	//문제 등록 폼
	@GetMapping("/cbtGuideInsertForm.do")
	public String cbtGuideInsertForm(Model model) {
	    model.addAttribute("gtpCdList", gtpDao.gtpCdList());
		model.addAttribute("langCdList", langDao.langCdList());
		return "cbtGuide/cbtGuideInsertForm";
	}
	//등록
	@PostMapping("/cbtGuideInsert.do")
	public String cbtGuideInsert(CbtGuideVO vo, CbtKeywordVO kvo, Model model,
			@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		//파일 처리
		String projectPath = System.getProperty("user.dir")+"/src/main/resources/static/files"; //프로젝트 경로
		UUID uuid = UUID.randomUUID();
		
		if(!file.isEmpty()) {
			//파일명 충돌방지를 위한 파일별명만듦
			String filename= uuid +"_"+file.getOriginalFilename();
			File saveFile = new File(projectPath, filename);
			//파일 물리적 위치에 저장
			file.transferTo(saveFile);
			vo.setAttach(filename);
			String path ="/files/"+filename;
			vo.setAttachDir(path);
		}
		cgDao.cbtGuideInsert(vo);

		return "redirect:/cbtGuideMain.do";
	}

}
