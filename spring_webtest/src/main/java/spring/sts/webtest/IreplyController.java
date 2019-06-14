package spring.sts.webtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.model.ireply.IreplyDTO;
import spring.model.ireply.IreplyInter;
import spring.utility.webtest.Utility;

@RestController
public class IreplyController {
		
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IreplyInter ireplyInter;

	@PutMapping("/imgup/ireply/{rnum}")
	public ResponseEntity<String> modify( @RequestBody IreplyDTO vo, @PathVariable("rnum") int rnum) {
	 		
		log.info("rnum: " + rnum);
		log.info("modify: " + vo);
		 
		return ireplyInter.update(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	 
	@DeleteMapping("/imgup/ireply/{rnum}") 
	public ResponseEntity<String> remove(@PathVariable("rnum") int rnum) {
	 
		log.info("remove: " + rnum);
	 
		return ireplyInter.delete(rnum) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}	
		
	
	@GetMapping("/imgup/ireply/{rnum}")
	public ResponseEntity<IreplyDTO> get(@PathVariable("rnum") int rnum) {
	 
		log.info("get: " + rnum);
	 
		return new ResponseEntity<>(ireplyInter.read(rnum), HttpStatus.OK);
	}
	
	
	@GetMapping("/imgup/ireply/page")
	public ResponseEntity<String> getPage(@RequestParam("nPage") int nPage, @RequestParam("nowPage") int nowPage,
		@RequestParam("imgno") int imgno, @RequestParam("col") String col, @RequestParam("word") String word){
	 
		int total = ireplyInter.total(imgno);
		String url = "read";
	 
		int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수
		String fk = "imgno=";
	
		String paging = Utility.rpaging(total, nowPage, recordPerPage, col, word, url, nPage,fk,imgno);
	 
		return new ResponseEntity<>(paging, HttpStatus.OK);
	 
	}
	
	
	
	@GetMapping("/imgup/ireply/list/{imgno}/{sno}/{eno}")//요청uri
	public ResponseEntity<List<IreplyDTO>> getList (@PathVariable("imgno") int imgno,
			@PathVariable("sno") int sno, @PathVariable("eno") int eno) {	 
	 
		Map map = new HashMap();
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("imgno", imgno);
		 
	 
		return new ResponseEntity<List<IreplyDTO>>(ireplyInter.list(map), HttpStatus.OK);
	
	}	
			

	@PostMapping("/imgup/ireply/create")
	public ResponseEntity<String> create(@RequestBody IreplyDTO vo){
	//@RequestBody를 쓰면 json형식으로 온 데이터를 IreplyDTO타입 파라메터로 변환해서 받을 수 있음.
		
		//타입이 변경된 데이터가 제대로 들어왔는지 확인
		log.info("IreplyDTO1: " + vo.getContent());
		log.info("IreplyDTO1: " + vo.getId());
		log.info("IreplyDTO1: " + vo.getImgno());
		
		vo.setContent(vo.getContent().replaceAll("/n/r", "<br>"));
		
		int flag = ireplyInter.create(vo);
		
		log.info("Reply INSERT flag: "+flag);
		
		return flag == 1? new ResponseEntity<>("success",HttpStatus.OK)
							: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			
			
	}
	
}


