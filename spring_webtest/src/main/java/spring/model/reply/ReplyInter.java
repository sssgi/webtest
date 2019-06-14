package spring.model.reply;

import java.util.List;
import java.util.Map;

public interface ReplyInter {
	int create(ReplyDTO dto);
	ReplyDTO read(int rnum);
	int update(ReplyDTO dto);
	int delete(int rnum);
	List<ReplyDTO> list(Map map);
	int total(int bbsno);
	int rcount(int bbsno);
	int bdelete(int bbsno);
}
