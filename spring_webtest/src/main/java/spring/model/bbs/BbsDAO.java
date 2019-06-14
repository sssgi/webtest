package spring.model.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.utility.webtest.DBclose;
import spring.utility.webtest.DBopen;

@Repository
public class BbsDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int total(String col, String word) {
	
		Map map = new HashMap();
		map.put("col",col);
		map.put("word",word);
		
		return mybatis.selectOne("bbs.total",map);

	}
	
	
	public void upAnsnum(Map map) throws Exception {
		
		mybatis.update("bbs.upAnsnum",map);
		
	}
	
	
	public BbsDTO readReply(int bbsno) {
		 
		return mybatis.selectOne("bbs.readReply",bbsno);
			
	}
	
	
	public void upViewcnt(int bbsno) {
		
		mybatis.update("bbs.upViewcnt",bbsno);
	}
		
	
	public List<BbsDTO> list(Map map){
		
		return mybatis.selectList("bbs.list",map);
	}
	
	
	public boolean passCheck(Map<String, String> map) {
		boolean flag = false;
	
		int cnt = mybatis.selectOne("bbs.passCheck",map);
		if(cnt>0) { flag = true;}
	
		return flag;
		
	}
	
	
	public boolean create(BbsDTO dto) {
		boolean flag = false;
		
		int cnt = mybatis.insert("bbs.create",dto);
		
		if(cnt >0) {flag = true;}
		
		return flag;
		
	}
	
	
	public boolean createReply(BbsDTO dto) throws Exception {
		boolean flag = false;
		
		int cnt = mybatis.insert("bbs.createReply", dto);
		if(cnt>0) {flag = true;}
		
		return flag;
		
	}
		
	
	public BbsDTO read(int bbsno) {
					 
		return mybatis.selectOne("bbs.read",bbsno);			
	}
	
	
	public boolean update(BbsDTO dto) {
		boolean flag = false;
		
		int cnt = mybatis.update("bbs.update",dto);
		if(cnt>0) {flag=true;}
		
		return flag;
		
	}
	
	
	public boolean delete(int bbsno) {
		boolean flag = false;
		int cnt = mybatis.delete("bbs.delete", bbsno);
		if(cnt>0) {flag = true;	}	
		
		return flag;

	}


	public boolean refnumCheck(int bbsno) {
		
		boolean flag = false;
		int cnt = mybatis.selectOne("bbs.refnumCheck",bbsno);
				
		if(cnt>0) {flag=true;}
		
		return flag;
	}	
		
}		
		
		