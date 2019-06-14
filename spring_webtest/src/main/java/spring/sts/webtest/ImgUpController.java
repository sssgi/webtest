package spring.sts.webtest;

import java.math.BigDecimal;
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

import spring.model.imgup.ImgUpDAO;
import spring.model.imgup.ImgUpDTO;
import spring.model.imgup.ImgUpInter;
import spring.model.ireply.IreplyInter;
import spring.utility.webtest.Utility;

@Controller
public class ImgUpController {
	
	@Autowired
	private ImgUpInter inter;
	@Autowired
	private IreplyInter irinter;

	
	@PostMapping("/imgup/delete")
	public String delete(@RequestParam Map<String, String> map,
			HttpServletRequest request,
			RedirectAttributes redi) {
		
		String upDir = request.getRealPath("/imgup/storage");
				
		boolean pflag = false;
		int cnt = inter.passCheck(map);
		
		if(cnt>0)pflag = true;
		
		if(pflag == true){
			int flag = inter.delete(Integer.parseInt(map.get("imgno")));
			
			String oldfile = map.get("oldfile");
			if(flag==1){
				if(!oldfile.equals("default.jpg")) {
					Utility.deleteFile(upDir, oldfile);
				}
				
				redi.addFlashAttribute("msg", "삭제되었습니다");
				return "redirect:/imgup/list";
			}else {
				request.setAttribute("flag", flag);
				return "error/error";
			}
		}else {
			request.setAttribute("pflag", pflag);
			return "error/passwdError";
		}
	}
	
	@GetMapping("/imgup/delete")
	public String delete(String imgno, String oldfile, Model model) {
		model.addAttribute("imgno", imgno);
		model.addAttribute("oldfile", oldfile);
		
		return "/imgup/delete";
	}
	
	@PostMapping("/imgup/update")
	public String update(ImgUpDTO dto,
			String oldfile,
			HttpServletRequest request,
			RedirectAttributes redi) {
		
		String upDir = request.getRealPath("/imgup/storage");
		
		Map map = new HashMap();
		map.put("imgno", String.valueOf(dto.getImgno()));
		map.put("passwd", dto.getPasswd());
		 
		boolean pflag = false;
		int cnt = inter.passCheck(map);
		if(cnt>0)pflag = true;		
		if(pflag == true){
			
			int size = (int)dto.getFnameMF().getSize();
			String fname = "";
			if(size > 0) {
				fname = Utility.saveFileSpring(dto.getFnameMF(), upDir);
			}else {
				fname = oldfile;
			}
			dto.setFname(fname);
			
			int flag = inter.update(dto);
			
			if(flag == 1){
				if(!oldfile.equals("default.jpg") && size > 0) {
					Utility.deleteFile(upDir, oldfile);
				}
				redi.addFlashAttribute("msg", "수정되었습니다");
				return "redirect:/imgup/list";
			}else {
				request.setAttribute("flag", flag);
				return "error/error";
			}
		}else {
			request.setAttribute("pflag", pflag);
			return "error/passwdError";
		}
	}
	
	@GetMapping("/imgup/update")
	public String update(int imgno, String oldfile,
			Model model) {

		ImgUpDTO dto = inter.read(imgno);
		
		model.addAttribute("imgno", imgno);
		model.addAttribute("oldfile", oldfile);
		model.addAttribute("dto", dto);
		
		return "/imgup/update";
	}
	
	@GetMapping("/imgup/read")
	public String read(int imgno, Model model, int nowPage, String col, String word, HttpServletRequest request) {
		inter.upViewcnt(imgno);
		ImgUpDTO dto = inter.read(imgno);
		
		String content = dto.getContent();
		content = content.replaceAll("\r\n", "<br>");
	   
		//5개 이미지, 이미지목록-----------------------------------------------
		Map map = inter.imgRead(imgno);
		String files[] = {(String)map.get("PRE_FILE2"),
						  (String)map.get("PRE_FILE1"),
						  (String)map.get("FNAME"),
						  (String)map.get("NEX_FILE1"),
						  (String)map.get("NEX_FILE2")};
		java.math.BigDecimal noArr[] = {(java.math.BigDecimal)map.get("PRE_IMGNO2"),
										(java.math.BigDecimal)map.get("PRE_IMGNO1"),
										(java.math.BigDecimal)map.get("IMGNO"),
										(java.math.BigDecimal)map.get("NEX_IMGNO1"),
										(java.math.BigDecimal)map.get("NEX_IMGNO2")};
		
		BigDecimal currentLeftId = noArr[1];
		BigDecimal currentRightId = noArr[3];
		
		model.addAttribute("imgno", imgno);
		model.addAttribute("dto", dto);
		model.addAttribute("content", content);
		
		model.addAttribute("files", files);
		model.addAttribute("noArr", noArr);
		
		model.addAttribute("currentLeftId", currentLeftId);
		model.addAttribute("currentRightId", currentRightId);
		
		/* 댓글 관련  시작 */
		int nPage= 1; //시작 페이지 번호는 1부터 
		 
		if (request.getParameter("nPage") != null) { 
			nPage= Integer.parseInt(request.getParameter("nPage"));  
		}
		int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수
		 
		int sno = ((nPage-1) * recordPerPage) + 1;
		int eno = nPage * recordPerPage;
		 
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("imgno", imgno);
		map.put("nPage", nPage);
		map.put("nowPage", nowPage);
		map.put("col", col);
		map.put("word", word);
		 
		model.addAllAttributes(map);
		/* 댓글 관련 끝 */  

		
		return "/imgup/read";
		
	}
	
	

	
	@PostMapping("/imgup/create")
	public String create(ImgUpDTO dto,
			HttpServletRequest request,
			RedirectAttributes redi) {
		
		
		String upDir = request.getRealPath("/imgup/storage");

		int size = (int)dto.getFnameMF().getSize();
		String fname = "";
		if(size > 0) {
			fname = Utility.saveFileSpring(dto.getFnameMF(), upDir);
		}else {
			fname = "default.jpg";
		}
		dto.setFname(fname);
		
		int flag = inter.create(dto);
		if(flag==1) {
			redi.addFlashAttribute("msg", "등록되었습니다");
			return "redirect:/imgup/list";
		}else {
			request.setAttribute("flag", flag);
			return "error/error";
		}
		
	}
	
	@GetMapping("/imgup/create")
	public String create() {
		return "/imgup/create";
	}
	
	@RequestMapping("/imgup/list")
	public String list(HttpServletRequest request) {
		
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		if(col.equals("total")){
			word = "";
		}
		
		int nowPage = 1;
		if(request.getParameter("nowPage") != null){
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		int recordPerPage = 3;
		int sno = ((nowPage-1)*recordPerPage)+1;
		int eno = recordPerPage * nowPage;
		
		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);
		
		
		List<ImgUpDTO> list = inter.list(map);
		
		int total = inter.total(map);
		String paging = Utility.paging(total, nowPage, recordPerPage, col, word, "list");
		
		request.setAttribute("list", list);
		request.setAttribute("paging", paging);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("irinter",irinter);
		
		return "/imgup/list";
	}
	
}
