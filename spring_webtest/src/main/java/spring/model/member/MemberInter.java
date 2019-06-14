package spring.model.member;

import java.util.List;
import java.util.Map;

public interface MemberInter {
	//< id를 메소드명. resultType을 return타입으로 , parameterType= 매개변수로-->
	//resulttype이 없는 경우는 int형으로 return타입 잡음.
	
	int create(MemberDTO dto);
	int duplicateEmail(String email);
	int duplicateId(String id);
	String getGrade(String id);
	int loginCheck(Map<String,String> map);
	MemberDTO read(String id);
	List<MemberDTO> list(Map map);
	int total(Map map);
	int update(MemberDTO dto);
	int updateFile(Map map);
	int updatePw(Map map);
	int delete(String id);
	String idFind(Map map);
	String passwdFind(Map map);
}
