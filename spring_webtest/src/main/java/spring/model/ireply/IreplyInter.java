package spring.model.ireply;

import java.util.List;
import java.util.Map;

public interface IreplyInter {
	
	int create(IreplyDTO dto);
	IreplyDTO read(int rnum);
	int update(IreplyDTO dto);
	int delete(int rnum);
	List<IreplyDTO> list(Map map);
	int total(int imgno);
	int rcount(int imgno);

}
