package spring.model.imgup;

import java.util.List;
import java.util.Map;

public interface ImgUpInter { 
	//resultType       parameterType
	List<ImgUpDTO> list(Map map);
	int total(Map map);
	//resultType이 없는것은 int가 resultType
	int create(ImgUpDTO dto);
	ImgUpDTO read(int imgno);
	Map imgRead(int imgno);
	int update(ImgUpDTO dto);
	int upViewcnt(int imgno);
	int delete (int imgno);
	int passCheck(Map map);
}
