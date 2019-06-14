package spring.sts.webtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spring.model.bbs.BbsDTO;
import spring.model.bbs.BbsInter;
import spring.model.bbs.BbsService;
import spring.model.reply.ReplyInter;
import spring.utility.webtest.Utility;


@Controller
public class BbsController {

	@Autowired
	private BbsInter binter;
	@Autowired
	ReplyInter rinter;
	@Autowired
	BbsService service;
	
	
	//list
	@RequestMapping("/bbs/list")
	public String list(HttpServletRequest request) {
		// 검색관련--------------------------------
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		
		if (col.equals("total")) {word = "";}

		// 페이지관련--------------------------------
		int nowPage = 1;
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		
		int recordPerPage = 5;

		// DB페이징관련------------------------------
		int sno = ((nowPage - 1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;
		
		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);

		int total = binter.total(map);

		String url = "list";
		String paging = Utility.paging(total, nowPage, recordPerPage, col, word, url);
		
		List<BbsDTO> list = binter.list(map);

		request.setAttribute("list", list);
		request.setAttribute("paging", paging);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("rinter", rinter);

		return "/bbs/list";
	}
	
	
	
	//create
	@GetMapping("/bbs/create")
	public String create() {
		
		return "/bbs/create";
	
	}
	
	
	@PostMapping("/bbs/create")
	public String create(BbsDTO dto, Model model, RedirectAttributes redi) {
		
		boolean flag = binter.create(dto)>0;

		if (flag) {
			redi.addFlashAttribute("msg", "게시글이 등록되엇습니다");
			return "redirect:/bbs/list";
		} else {
			model.addAttribute("flag", flag);
			return "error/error";
		}
		
	}
	
	
	
	
	//read
	@GetMapping("/bbs/read")
	public String read(int bbsno, Model model, int nowPage, String col, String word, HttpServletRequest request ) {
		
		binter.upViewcnt(bbsno);
		
		BbsDTO dto = binter.read(bbsno);

		model.addAttribute("dto", dto);

		/* 댓글 관련 시작 */
		int nPage = 1; // 시작 페이지 번호는 1부터

		if (request.getParameter("nPage") != null) {
			nPage = Integer.parseInt(request.getParameter("nPage"));
		}
		
		int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수
		int sno = ((nPage - 1) * recordPerPage) + 1; //
		int eno = nPage * recordPerPage;

		Map map = new HashMap();
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("bbsno", bbsno);
		map.put("nPage", nPage);
		map.put("nowPage", nowPage);
		map.put("col", col);
		map.put("word", word);

		model.addAllAttributes(map);
		/* 댓글 관련 끝 */

		return "/bbs/read";
	}
	
	
		
	//update
	@GetMapping("/bbs/update")
	public String update(int bbsno, Model model) {
			
		BbsDTO dto = binter.read(bbsno);
		model.addAttribute("dto", dto);
		
		return "/bbs/update";
		
	}
		
	@PostMapping("/bbs/update")
	public String update(BbsDTO dto, Model model, RedirectAttributes redi) {

		Map map = new HashMap();
		map.put("bbsno", String.valueOf(dto.getBbsno()));
		map.put("passwd", dto.getPasswd());

		boolean pflag = binter.passCheck(map)>0;
		boolean flag = false;

		if (pflag == true) {
			flag = binter.update(dto)>0;
		
			if (flag) {
				redi.addFlashAttribute("msg", "게시글을 수정했습니다");
				return "redirect:/bbs/list";
			} else {
				model.addAttribute("flag", flag);
				return "error/error";
			}
			
		} else {
			model.addAttribute("pflag", pflag);
			return "error/passwdError";
		}
				
	}	
	
	
	
	//delete
	@GetMapping("/bbs/delete")
	public String delete(int bbsno, Model model) {
		
		boolean flag = binter.refnumCheck(bbsno)>0;
		model.addAttribute("flag", flag);

		return "/bbs/delete";
	}
		
	@PostMapping("/bbs/delete")
	public String delete(@RequestParam Map<String, String>map, int bbsno, Model model, RedirectAttributes redi) {
		
		boolean pflag = binter.passCheck(map)>0;

		try {
			if (pflag) {
				service.delete(bbsno);
				redi.addFlashAttribute("msg", "게시글이 삭제되었습니다");
				return "redirect:/bbs/list";
			} else {
				model.addAttribute("pflag", pflag);
				return "error/error";
			}
			
		} catch(Exception e){
			model.addAttribute("flag", false);
			return "error/error";
		}
		
	}
	
	
	
	
	//reply
	@GetMapping("/bbs/reply")
	public String reply(int bbsno, Model model){
		BbsDTO dto = binter.readReply(bbsno);
		model.addAttribute("dto",dto);

		return "/bbs/reply";
	}

	
	@PostMapping("/bbs/reply")
	public String reply(BbsDTO dto, RedirectAttributes redi, Model model) {
		
		dto.setRefnum(dto.getBbsno());// 부모글의 bbsno의 값을 넣어줌
		
		try {
			service.reply(dto);
			redi.addFlashAttribute("msg", "답변을 등록하였습니다");
			return "redirect:/bbs/list";
		}catch (Exception e) {
			model.addAttribute("flag", false);
			return "error/error";
		}
				
	}
	
	
	
}
