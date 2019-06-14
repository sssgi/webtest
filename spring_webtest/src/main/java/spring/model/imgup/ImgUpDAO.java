package spring.model.imgup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import spring.utility.webtest.DBclose;
import spring.utility.webtest.DBopen;

@Repository
public class ImgUpDAO {
	
	public boolean create(ImgUpDTO dto) {
		boolean flag = false;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into image (imgno,name, title, content, regdate, passwd, viewcnt, fname)");
		sql.append(" values ((select nvl(max(imgno),0)+1 from image),?,?,?,sysdate,?,0,?) ");		
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,dto.getName());
			pstmt.setString(2,dto.getTitle());
			pstmt.setString(3,dto.getContent());
			pstmt.setString(4,dto.getPasswd());
			pstmt.setString(5,dto.getFname());
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(rs, pstmt, con);
		}
		
		return flag;

	}

	
	public ImgUpDTO read(int imgno) {
		ImgUpDTO dto = null;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select imgno, name, title, content, regdate, viewcnt, fname ");
		sql.append(" from image where imgno = ?");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1,imgno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ImgUpDTO();
				dto.setImgno(rs.getInt("imgno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setViewcnt(rs.getInt("viewcnt"));
				dto.setFname(rs.getString("fname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(rs, pstmt, con);
		}
		
		return dto;
		
	}
	
	
	public boolean update(ImgUpDTO dto) {
		boolean flag = false;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update image ");
		sql.append(" set ");
		sql.append(" 	title=?, ");
		sql.append("    content=?, ");
		sql.append("    fname=? ");
		sql.append(" where imgno=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,dto.getTitle());
			pstmt.setString(2,dto.getContent());
			pstmt.setString(3,dto.getFname());
			pstmt.setInt(4,dto.getImgno());
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(pstmt, con);
		}
		
		return flag;
				
	}

	
	public boolean updateFile(Map map) {
		boolean flag = false;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		
		int imgno = (Integer)map.get("imgno");
		String fname = (String)map.get("fname");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update image ");
		sql.append(" set fname=? ");
		sql.append(" where imgno=? ");
		
		try {
			pstmt =con.prepareStatement(sql.toString());
			pstmt.setString(1,fname);
			pstmt.setInt(2,imgno);
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) flag = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(pstmt, con);
		}
		
		return flag;
		
	}

	
	public boolean delete(int imgno) {
		boolean flag = false;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from image");
		sql.append(" where imgno=? ");
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			pstmt.setInt(1,imgno);
			
			int cnt = pstmt.executeUpdate();
			if(cnt>0) flag = true;
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(pstmt, con);
		}
		
		return flag;
	}
		
	
	public boolean passwdCheck(Map map) {
		boolean flag = false;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int imgno = (Integer)map.get("imgno");
		String passwd = (String)map.get("passwd");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from image ");
		sql.append(" where imgno=? and passwd=? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1,imgno);
			pstmt.setString(2,passwd);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt(1);
				
				if(cnt>0) flag = true;
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(rs, pstmt, con);
		}
		
		return flag;
		
	}


	public List<ImgUpDTO> list(Map map){
		List<ImgUpDTO> list = new ArrayList();
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select imgno, name, title, regdate, viewcnt, fname, r ");
		sql.append("from( ");
		sql.append("	select imgno, name, title, regdate, viewcnt, fname, rownum r ");
		sql.append("    from( ");
		sql.append("        select imgno,name, title, regdate, viewcnt, fname ");
		sql.append("        from image ");
		
		if(word.trim().length()>0) {
			sql.append("    where "+col+" like '%'||?||'%' ");			
		}		
		sql.append("        order by imgno desc ");
		sql.append("    	) ");
		sql.append("    ) where r>=? and r <=? ");
		
		try {
			int i = 0;
			pstmt=con.prepareStatement(sql.toString());
			
			if(word.trim().length()>0) {
				pstmt.setString(++i,word);
			}
			
			pstmt.setInt(++i,sno);
			pstmt.setInt(++i,eno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ImgUpDTO dto = new ImgUpDTO();
				dto.setImgno(rs.getInt("imgno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setViewcnt(rs.getInt("viewcnt"));
				dto.setFname(rs.getString("fname"));
				
				list.add(dto);
								
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(rs, pstmt, con);
		}
		
		return list;		
		
	}

	
	public List imgRead(int imgno) {
		List list = new ArrayList();
		Connection con =DBopen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM(  ");
		sql.append("	 select    ");
		sql.append("           lag(imgno,2) over (order by imgno) pre_imgno2, ");
		sql.append("           lag(imgno,1) over (order by imgno ) pre_imgno1, ");
		sql.append("           imgno,  ");
		sql.append("           lead(imgno,1) over (order by imgno) nex_imgno1, ");
		sql.append("           lead(imgno,2) over (order by imgno) nex_imgno2, ");
		sql.append("           lag(fname,2) over (order by imgno) pre_file2, ");
		sql.append("           lag(fname,1) over (order by imgno ) pre_file1, ");
		sql.append("           fname,   ");
		sql.append("           lead(fname,1) over (order by imgno) nex_file1, ");
		sql.append("           lead(fname,2) over (order by imgno) nex_file2 ");
		sql.append("     from (  ");
		sql.append("           SELECT imgno, fname ");
		sql.append("           FROM image ");
		sql.append("           ORDER BY imgno DESC ");
		sql.append("          )  ");
		sql.append("     )  ");
		sql.append(" WHERE imgno = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int[] noArr = 
					   {
						rs.getInt("pre_imgno2"),
						rs.getInt("pre_imgno1"),
						rs.getInt("imgno"),
						rs.getInt("nex_imgno1"),
						rs.getInt("nex_imgno2")
					   };
				String[] files = 
					   {
						rs.getString("pre_file2"),
						rs.getString("pre_file1"),
						rs.getString("fname"),
						rs.getString("nex_file1"),
						rs.getString("nex_file2")
					   };
				
				list.add(files);
				list.add(noArr);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(rs, pstmt, con);
		}
		
		return list;
		
	}

	
	public void upViewcnt(int imgno) {
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update image set viewcnt = viewcnt+1 ");
		sql.append(" where imgno=? ");
				
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, imgno);
			
			pstmt.executeUpdate(); 
			// return값이 필요 없으므로 rs등으로 받을 필요가 없음.		
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(pstmt, con);
		}

	}
	

	public int total(Map map) {
		int total = 0;
		Connection con = DBopen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from image ");
		
		if(word.trim().length()>0) {
			sql.append(" where " +col+ " like '%'||?||'%' ");		
		}
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			
			if(word.trim().length()>0) {
				pstmt.setString(1, word);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBclose.close(rs, pstmt, con);
		}
		
		return total;
		
	}
}
