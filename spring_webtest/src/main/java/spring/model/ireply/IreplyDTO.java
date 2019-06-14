package spring.model.ireply;

public class IreplyDTO {
	private int rnum;
	private String content;
	private String regdate;
	private String id;
	private int imgno;
	
	public IreplyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public IreplyDTO(int rnum, String content, String regdate, String id, int imgno) {
		super();
		this.rnum = rnum;
		this.content = content;
		this.regdate = regdate;
		this.id = id;
		this.imgno = imgno;
	}

	@Override
	public String toString() {
		return "IreplyDTO [rnum=" + rnum + ", content=" + content + ", regdate=" + regdate + ", id=" + id + ", imgno="
				+ imgno + "]";
	}

	public int getRnum() {
		return rnum;
	}
	
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getImgno() {
		return imgno;
	}
	public void setImgno(int imgno) {
		this.imgno = imgno;
	}
	
	
	
	
	
	
}
