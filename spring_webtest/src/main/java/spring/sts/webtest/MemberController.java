package spring.sts.webtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spring.model.member.MemberDAO;
import spring.model.member.MemberDTO;
import spring.model.member.MemberInter;
import spring.utility.webtest.Utility;

/**
  request - forward

  Model - forward, (send)redirect(url에 parameter노출)
  RedirectAttributes - (send)redirect(Model의 약점 보완)
  redirect는 재요청!!!! 기존의 데이터를 가지고 있지 않음
  
  !!RedirectAttributes extend Model!!
   - addFlashAttribute() - url에 parameter 노출x
   - addAttribute()      - url에 parameter 노출

   요청의 결과를 보여줄때는 forward : viewname
   요청의 결과가 아니라 새로운 페이지를 보여줄때는 redirect : url
**/

@Controller //controller이므로 스캔필요 ->annotation써야함. 
public class MemberController {

	@Autowired
	private MemberInter inter;
	
	//agree
	@GetMapping("/member/agreement")
	public String agree() {
		
		return "/agree";
		
	}
	
	
	
	//create
	@PostMapping("/member/create")
	public String create() {
		
		return "/create";
		
	}
	
	
	@ResponseBody //data를 리턴할 수 있는 annotation
	@GetMapping(value="/member/idCheck", produces="text/plain; charset=UTF-8")//produces=responsebody의 형태를 지정, 이때는 경로를 쓸때 value로 표시 
	public String idCheck(String id) {
		
		int flag = inter.duplicateId(id);
		String str = "";
		
		if(flag==1){
			str= "동일한 아이디가 존재하므로 사용할 수 없습니다.";			
		}else{
			str= id+"은/는 사용 가능한 아이디 입니다.";				
		}
		
		return str;
		
	}
	
	
	@ResponseBody
	@GetMapping(value="/member/emailCheck", produces="text/plain; charset=UTF-8")
	public String emailCheck(String email) {
		
		int flag = inter.duplicateEmail(email);
		String str ="";
		
		if(flag==1) {
			str= "동일한 이메일이 존재하므로 사용할 수 없습니다.";
		}else {
			str= email+"은/는 사용 가능한 이메일 입니다.";
		}	
			
		return str;	
		
	}
	
	
	@PostMapping("/member/createProc")
	public String createProc(MemberDTO dto, HttpServletRequest request, RedirectAttributes redi) {
		
		String str = null;
		String url = "/member/prcreate";
		
		if(inter.duplicateId(dto.getId())==1) { 
			
			str="이미 사용 중인 아이디입니다. 아이디 중복확인을 하세요";
			request.setAttribute("msg", str);
			
		}else if(inter.duplicateEmail(dto.getEmail())==1) {
			
			str="이미 사용중인 이메일입니다. 이메일 중복확인을 하세요.";
			request.setAttribute("msg",str);
			
		}else {
			
			String basePath = request.getRealPath("/member/storage");
			//----------------업로드를 하지 않을때 default로 처리--------------------	
			String fname=null;
			int size = (int)dto.getFnameMF().getSize();
			
			if(size>0) {
				fname= Utility.saveFileSpring(dto.getFnameMF(), basePath); 
			}else {
				fname="member.jpg";
			}
			//-------------------------------------------------------------
			dto.setFname(fname);
			
			int flag = inter.create(dto);
			
			if(flag==1) {
				
				redi.addFlashAttribute("msg","회원등록이 되었습니다.");
				url="redirect:/";	
				
			}else {
				
				request.setAttribute("flag",false);
				url = "error/error";
				
			}
								
		}
		
		return url;
	}
	
	
	
	
	//login
	@GetMapping("/member/login")
	public String login(HttpServletRequest request) {
				
		// --------------------쿠키 설정 내용시작-------------------
		String c_id=""; //id 저장 여부를 저장하는 변수, Y
		String c_id_val=""; //id 값
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				cookie = cookies[i];
				if(cookie.getName().equals("c_id")){
					c_id = cookie.getValue();
				}else if(cookie.getName().equals("c_id_val")){
					c_id_val = cookie.getValue();
				}
			}
		}
		//----------------------쿠키 설정 내용 끝--------------------	
		
		request.setAttribute("c_id",c_id);
		request.setAttribute("c_id_val", c_id_val);
		
				
		return "/login";
		
	}
	
	
	@PostMapping("/member/login")
	public String login(@RequestParam Map<String,String> map, HttpServletResponse response,
						HttpSession session, RedirectAttributes redi, String c_id, Model model) {//map은 꼭 requestParam을 annotation을 써야함.		
		int flag = inter.loginCheck(map);
		String grade = null; //회원등급
		
		if(flag==1){
			
			grade = inter.getGrade(map.get("id"));
			session.setAttribute("id", map.get("id")); //로그인을 유지하기 위해
			session.setAttribute("grade", grade);
			
			//cookie저장, Checkbox는 선택하지 않으면 null
			Cookie cookie = null;
		
			
			if(c_id != null){ //처음에는 값이 없으므로 null체크로 처리
				cookie = new Cookie("c_id","Y");//아이디 저장여부 쿠키
				cookie.setMaxAge(120);//2분유지
				response.addCookie(cookie);//쿠키기록
				
				cookie = new Cookie("c_id_val",map.get("id"));//id값 저장 쿠키
				cookie.setMaxAge(120);
				response.addCookie(cookie);			
			}else{
				cookie = new Cookie("c_id","");//쿠키삭제
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				
				cookie = new Cookie("c_id_val","");//쿠키삭제
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
					
		}	
		
		//댓글추가에 필요. flag==1 : 로그인 처리가 됨.
		if (flag==1) {
			
			if(map.get("rurl")!=null && !map.get("rurl").equals("")) {
			 
			redi.addAttribute("imgno",map.get("imgno"));	
			redi.addAttribute("bbsno", map.get("bbsno"));
			redi.addAttribute("nowPage", map.get("nowPage"));
			redi.addAttribute("nPage", map.get("nPage"));
			redi.addAttribute("col", map.get("col"));
			redi.addAttribute("word", map.get("word"));
			return "redirect:"+map.get("rurl");
			 
			 
			}else {
				redi.addFlashAttribute("msg","로그인이 되었습니다."); //parameter를 보이기 싫어서 redi를 씀.
				return "redirect:/"; // '/'를 url로 갖는 곳으로 찾아감. -> memberController에 없기 때문에 homeController에 의해서 /home을 찾아감.
			}	
		
		}else {
			
			model.addAttribute("msg","아이디 또는 비밀번호를 잘못 입력하셨거나 회원이 아닙니다."); //model 대신 request 쓸 수 있음.
			return "member/prcreate";
			
		}
	}
	
	
	
	//logout
	@GetMapping("/member/logOut")
	public String logOut(HttpSession session){
		session.invalidate(); //session에 저장된 내용 삭제
		
		return "redirect:/";
		
	}
	
	
	
	
	//read
	@GetMapping("/member/read")
	public String read(String id, HttpSession session, Model model) {
		
		String grade =(String)session.getAttribute("grade");
		
		if(id==null){
			id = (String)session.getAttribute("id");
		}
		
		MemberDTO dto = inter.read(id);
		
		//request대신 model을 써서 저장함.
		model.addAttribute("dto",dto); 
		model.addAttribute("grade",grade);
		
		return "/read"; // viewResolver로 찾을 페이지이름.
	}
	
	
	
	
	//update
	@GetMapping("/member/update")
	public String update(String id, HttpSession session, Model model) {
		
		if(id==null) {
			id = (String)session.getAttribute("id");
		}
		
		MemberDTO dto = inter.read(id); // 수정할 내용을 읽어오는 작업
		model.addAttribute("dto",dto);
				
		return "/update";
	}
	
	
	@PostMapping("/member/update")
	public String update(MemberDTO dto, String xemail, Model model, RedirectAttributes redi) {

		String str = "";
		String url = "/member/prcreate";
		
		if(!dto.getEmail().equals(xemail) && inter.duplicateEmail(dto.getEmail())==1) {// A && B

			str="중복된 이메일입니다. 이메일을 확인을 하세요";
			model.addAttribute("msg",str);
			
		}else{ // !A || !B
																
			int flag = inter.update(dto);

			if(flag==1) {
				redi.addFlashAttribute("msg","회원정보가 수정되었습니다.");
				url = "redirect:/";
			}else {
				model.addAttribute("flag",flag);
				url = "error/error";
					
			}

		}
		
		return url;	
		
	}
	
	
	@GetMapping("/member/updateFile")
	public String updateFile() {

		return "/updateFile";
	}
	
	
	@PostMapping("/member/updateFile")
	public String updateFile(String id, String oldfile, MultipartFile fnameMF, 
			HttpServletRequest request, RedirectAttributes redi ) {
		
		
		String upDir = request.getRealPath("/member/storage");
		String fname = null;
		
		if(fnameMF.getSize()>0) {
			if(oldfile!=null && !oldfile.equals("member.jpg")) {
				Utility.deleteFile(upDir, oldfile);
			}
			fname = Utility.saveFileSpring(fnameMF, upDir);
		}
		
		Map map = new HashMap();
		map.put("id",id);
		map.put("fname",fname);
		
		int flag = inter.updateFile(map);

		if(flag==1) {
			redi.addFlashAttribute("id",id);
			redi.addFlashAttribute("msg","사진이 변경되었습니다.");
			return "redirect:/member/read";
		}else {
			request.setAttribute("flag",flag);
			return "error/error";
		}	

	}
	
	
	@GetMapping("/member/updatePw")
	public String updatePw(){
		
		return "/updatePw";
		
	}
	
	
	@PostMapping("member/updatePw")
	public String updatePw(@RequestParam Map<String,String> map, RedirectAttributes redi, Model model) {

		int flag = inter.updatePw(map);
		
		if(flag==1) {
			
			redi.addFlashAttribute("id", map.get("id"));
			redi.addFlashAttribute("msg","비밀번호가 변경되었습니다.");
			return "redirect:/member/read";		
			
		} else {
			
			model.addAttribute("flag",flag); //url상으로 파라메터를 감추려면 request를 쓰는게 좋음.
			return "error/error";
			
		}
		
	}
	
	
	
	//delete
	@GetMapping("/member/delete")
	public String delete() {
		
		return "/delete";
		
	}
	
	
	@PostMapping("/member/delete")
	public String delete(HttpServletRequest request, RedirectAttributes redi ) {
		
		String id = request.getParameter("id");
		String oldfile = request.getParameter("oldfile");
		String upDir = request.getRealPath("/member/storage");
		String grade = (String)request.getSession().getAttribute("grade");
		
		int flag = inter.delete(id);
		
		if(flag==1 && !oldfile.equals("member.jpg")) {
			Utility.deleteFile(upDir, oldfile);
		}
		
		if(flag==1 && !grade.equals("A")){
			request.getSession().invalidate(); //로그아웃처리
		}

		if(flag==1) {
			redi.addFlashAttribute("msg","탈퇴되었습니다. 자동 로그아웃됩니다.");
			return "redirect:/";
		}else {
			request.setAttribute("flag",flag);
			return "error/error";
			
		}
	}
	
	
		
	
	//Find
	
	@GetMapping("/member/idFind")
	public String idFind() {
		
		return "/member/idFind";
	}
	
	
	@ResponseBody
	@GetMapping(value="/member/idFindCheck", produces = "text/plain;charset=UTF-8")
	public String idFind(@RequestParam Map<String,String> map) {
					
		String str = "";
		String id = inter.idFind(map);
		
		if(id!=null) {
			str = "귀하의 ID는"+id+"입니다";
		}else {
			str = "이름 또는 이메일을 잘못 입력하셨거나 회원이 아닙니다.";
		}

		return str;
	}
	
	
	@GetMapping("/member/passwdFind")
	public String passwdFind() {
		
		return "/member/passwdFind";
		
	}
	
	
	@ResponseBody
	@GetMapping(value="/member/passwdFindCheck", produces = "text/plain; charset=UTF-8")
	public String passwdFind(@RequestParam Map<String,String> map) {
		
		String str = "";
		String passwd = inter.passwdFind(map);
		
		if(passwd!=null) {
			str = " 귀하의 비밀번호는" +passwd+"입니다";
		}else {
			str = "이름 또는 아이디를 잘못 입력하셨거나 회원이 아닙니다. 회원가입하세요";			
		}
		
		return str;
	
	}
	
	
	//list
	@RequestMapping("/admin/list") // get과 post를 둘다 받을 수 있음.
  	public String list(HttpServletRequest request) {
		
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		
		if(col.equals("total")) word="";
		
		//페이징 관련
		int nowPage = 1;
		int recordPerPage =3;
		
		if(request.getParameter("nowPage")!=null){
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		
		int sno = ((nowPage-1)*recordPerPage)+1;
		int eno = nowPage * recordPerPage;
		
		Map map = new HashMap();
		map.put("col",col);
		map.put("word",word);
		map.put("sno",sno);
		map.put("eno",eno);
		
		int total = inter.total(map);
		
		List<MemberDTO> list = inter.list(map); 
			
		String url = "list";
		String paging = Utility.paging(total, nowPage, recordPerPage, col, word, url);
	
		request.setAttribute("list",list);
		request.setAttribute("paging", paging);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		
	
		return "/list";
	}
	
	


}
