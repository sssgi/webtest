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

import spring.model.reply.ReplyDTO;
import spring.model.reply.ReplyInter;
import spring.utility.webtest.Utility;

@RestController
public class ReplyController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ReplyInter replyInter;
	
	@PutMapping("/bbs/reply/{rnum}")
	public ResponseEntity<String> modify( @RequestBody ReplyDTO vo, @PathVariable("rnum") int rnum) {
	 		
		log.info("rnum: " + rnum);
		log.info("modify: " + vo);
		 
		return replyInter.update(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	 
	@DeleteMapping("/bbs/reply/{rnum}") 
	public ResponseEntity<String> remove(@PathVariable("rnum") int rnum) {
	 
		log.info("remove: " + rnum);
	 
		return replyInter.delete(rnum) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}	
		
	
	@GetMapping("/bbs/reply/{rnum}")
	public ResponseEntity<ReplyDTO> get(@PathVariable("rnum") int rnum) {
	 
		log.info("get: " + rnum);
	 
		return new ResponseEntity<>(replyInter.read(rnum), HttpStatus.OK);
	}
	
	
	@GetMapping("/bbs/reply/page")
	public ResponseEntity<String> getPage(@RequestParam("nPage") int nPage, @RequestParam("nowPage") int nowPage,
		@RequestParam("bbsno") int bbsno, @RequestParam("col") String col, @RequestParam("word") String word){
	 
		int total = replyInter.total(bbsno);
		String url = "read";
	 
		int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수
		
		String fk = "&bbsno=";
		
	
		String paging = Utility.rpaging(total, nowPage, recordPerPage, col, word, url, nPage, fk, bbsno);
	 
		return new ResponseEntity<>(paging, HttpStatus.OK);
	 
	}
	
	
	
	@GetMapping("/bbs/reply/list/{bbsno}/{sno}/{eno}")//요청uri
	public ResponseEntity<List<ReplyDTO>> getList (@PathVariable("bbsno") int bbsno,
			@PathVariable("sno") int sno, @PathVariable("eno") int eno) {	 
	 
		Map map = new HashMap();
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("bbsno", bbsno);
		 
	 
		return new ResponseEntity<List<ReplyDTO>>(replyInter.list(map), HttpStatus.OK);
	
	}	
			

	@PostMapping("/bbs/reply/create")
	public ResponseEntity<String> create(@RequestBody ReplyDTO vo){
	//@RequestBody를 쓰면 json형식으로 온 데이터를 replyDTO타입 파라메터로 변환해서 받을 수 있음.
		
		//타입이 변경된 데이터가 제대로 들어왔는지 확인
		log.info("ReplyDTO1: " + vo.getContent());
		log.info("ReplyDTO1: " + vo.getId());
		log.info("ReplyDTO1: " + vo.getBbsno());
		
		vo.setContent(vo.getContent().replaceAll("/n/r", "<br>"));
		
		int flag = replyInter.create(vo);
		
		log.info("Reply INSERT flag: "+flag);
		
		return flag == 1? new ResponseEntity<>("success",HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		
	}

}
