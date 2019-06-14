package spring.model.bbs;

import java.util.List;
import java.util.Map;

public interface BbsInter {
	int create(BbsDTO dto);
	List<BbsDTO> list (Map map);
	BbsDTO read(int bbsno);
	int update(BbsDTO dto);
	int delete(int bbsno);
	int total(Map map);
	BbsDTO readReply (int bbsno);
	void upAnsnum (Map map); //return이 없는 메소드
	int createReply(BbsDTO dto);
	int passCheck(Map map);
	int refnumCheck(int bbsno);
	int upViewcnt(int bbsno);
	
}
