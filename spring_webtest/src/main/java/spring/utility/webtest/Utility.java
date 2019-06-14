package spring.utility.webtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import spring.model.ireply.IreplyInter;
import spring.model.reply.ReplyInter;

public class Utility {

	// FileItem타입을 사용하지 않고 MultipartFile타입을 사용
	public static String saveFileSpring(MultipartFile multipartFile, String basePath) {
		// input form's parameter name
		String fileName = "";

		// original file name
		String originalFileName = multipartFile.getOriginalFilename();// 경로빼고 순수 파일명만

		// file content type
		String contentType = multipartFile.getContentType();

		// file size
		long fileSize = multipartFile.getSize();

		System.out.println("fileSize: " + fileSize);
		System.out.println("originalFileName: " + originalFileName);

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			if (fileSize > 0) { // 파일이 존재한다면
				// 인풋 스트림을 얻는다.
				// 순수파일명이 내가 저장할 경로에 있을 경우
				inputStream = multipartFile.getInputStream();

				File oldfile = new File(basePath, originalFileName);

				if (oldfile.exists()) {
					for (int k = 0; true; k++) {
						// 파일명 중복을 피하기 위한 일련 번호를 생성하여
						// 파일명으로 조합 => txt라는 파일이 있을경우 txt0, txt1이런식으로 생성
						oldfile = new File(basePath, "(" + k + ")" + originalFileName);

						// 조합된 파일명이 존재하지 않는다면, 일련번호가
						// 붙은 파일명 다시 생성
						if (!oldfile.exists()) { // 존재하지 않는 경우
							fileName = "(" + k + ")" + originalFileName;
							break;
						}
					}
				} else {
					fileName = originalFileName;
				}

				// UploadSave에서 write를 해서 자동으로 저장되는 메소드가 없기 때문에 로직을 직접 생성해줌
				// =>fileItem.write(uploadedFile);에서 write의 역할과 동일
				// make server full path to save
				String serverFullPath = basePath + "\\" + fileName;
				System.out.println("fileName: " + fileName);
				System.out.println("serverFullPath: " + serverFullPath);

				outputStream = new FileOutputStream(serverFullPath);

				// 버퍼를 만든다.
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return fileName;
	}

	// 문자체크
	public static String checkNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * 
	 * @param totalRecord   전체 레코드수
	 * @param nowPage       현재 페이지
	 * @param recordPerPage 페이지당 레코드 수
	 * @param col           검색 컬럼
	 * @param word          검색어
	 * @return 페이징 생성 문자열
	 */

	public static String paging(int totalRecord, int nowPage, int recordPerPage, String col, String word, String url) {

		int pagePerBlock = 5; // 블럭당 페이지 수 , rownum을 한번에 몇개까지 보여줄건지(1~5가 나오고 다음을 눌러야 6~10이 나옴)
		int totalPage = (int) (Math.ceil((double) totalRecord / recordPerPage)); // 전체 페이지 = 전체레코드/페이지 당 보여줄 레코드,
																					// 안나누어떨어지면 올림으로 함(ceil)
		int totalGrp = (int) (Math.ceil((double) totalPage / pagePerBlock));// 전체 그룹 전체페이지/한페이지당 보여줄 rownum ,안나누어 떨어져도
																			// 올림 123 456 78 (3블럭)
		int nowGrp = (int) (Math.ceil((double) nowPage / pagePerBlock)); // 현재 그룹 현재페이지/한페이당보여줄 rounum, 2페이지/3블럭 = 1번째
																			// 블럭에 있음(올림) 6페이지/2블럭 = 3
		int startPage = ((nowGrp - 1) * pagePerBlock) + 1; // 특정 그룹의 페이지 목록 시작
		int endPage = (nowGrp * pagePerBlock); // 특정 그룹의 페이지 목록 종료

		StringBuffer str = new StringBuffer();
		str.append("<div style='text-align:center'>");
		str.append("<ul class='pagination'> ");// 페이지하단 번호의 모양을 사각형으로 지정

		int _nowPage = (nowGrp - 1) * pagePerBlock; // 10개 이전 페이지로 이동 pagePerBlock=5일때 1그룹은 1~5의 rownum을 가짐,
		// 2그룹(6~10)-1 * 5 = 5 = > 2그룹에서 이전을 눌렀을때 이동하는곳(현재그룹에서 이전그룹의 맨 마지막 페이지를 가리킴)
		if (nowGrp >= 2) { // 내가 보고 있는 그룹이 2그룹이면(6~10)
			str.append(
					"<li><a href='" + url + "?col=" + col + "&word=" + word + "&nowPage=" + _nowPage + "'>이전</A></li>");
		}

		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) { // endPage가 전체페이지보다 커지면 반복을 끝냄
				break;
			}
			if (nowPage == i) {
				str.append("<li class='active'><a href=#>" + i + "</a></li>");
			} else {
				// 현재페이지가 아닌곳에서 col과 word를 가지고 감
				str.append("<li><a href='" + url + "?col=" + col + "&word=" + word + "&nowPage=" + i + "'>" + i
						+ "</A></li>");
			}
		}
		// 2그룹 (6~10)* 5 = 10+1 =>11페이지로 이동(2그룹에서 다음을 눌렀을때 이동하는 곳, 현재그룹에서 다음그룹의 첫번째 페이지를
		// 가리킴)
		_nowPage = (nowGrp * pagePerBlock) + 1; // 10개 다음 페이지로 이동
		if (nowGrp < totalGrp) {
			str.append(
					"<li><A href='" + url + "?col=" + col + "&word=" + word + "&nowPage=" + _nowPage + "'>다음</A></li>");
		}
		str.append("</ul>");
		str.append("</div>");
		return str.toString();
	}

	/**
	 * 등록날짜와 오늘,어제,그제날짜와 비교
	 * 
	 * @param wdate - 등록날짜
	 * @return - true:오늘,어제,그제중 등록날짜와 같음 false:오늘,어제,그제 날짜가 등록날짜와 다 다름
	 */
	public static boolean compareDay(String wdate) {
		boolean flag = false;
		List<String> list = getDay();
		// 오늘 0 ,어제 1, 그제 2
		if (wdate.equals(list.get(0)) || wdate.equals(list.get(1)) || wdate.endsWith(list.get(2))) {
			flag = true;
		}
		return flag;
	}

	private static List<String> getDay() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		// getInstance() : 현재위치에 맞는 날짜정보를 제공
		Calendar cal = Calendar.getInstance();
		// 반목문을 돌면서 오늘 - 어제 - 그제 순으로 저장이 됨
		for (int j = 0; j < 3; j++) {
			list.add(sd.format(cal.getTime()));
			cal.add(Calendar.DATE, -1);// Calendar.DATE 어떤형식을 변경할지를 알려주는것
		}
		return list;
	}

	public static String getCodeValue(String code) {
		String value = null;

		Hashtable codes = new Hashtable();

		codes.put("A01", "회사원");
		codes.put("A02", "전산관련직");
		codes.put("A03", "연구전문직");
		codes.put("A04", "각종학교학생");
		codes.put("A05", "일반자영업");
		codes.put("A06", "공무원");
		codes.put("A07", "의료인");
		codes.put("A08", "법조인");
		codes.put("A09", "종교/언론/예술인");
		codes.put("A10", "기타");

		value = (String) codes.get(code);
		return value;

	}

	public static void deleteFile(String upDir, String oldfile) {

		File file = new File(upDir, oldfile);
		if (file.exists())
			file.delete();// 파일이 존재한다면 지움

	}

	public static String rpaging(int total, int nowPage, int recordPerPage, String col, String word, String url,
			int nPage, String fk, int fno) {
		int pagePerBlock = 5; // 블럭당 페이지 수
		int totalPage = (int) (Math.ceil((double) total / recordPerPage)); // 전체 페이지
		int totalGrp = (int) (Math.ceil((double) totalPage / pagePerBlock));// 전체 그룹
		int nowGrp = (int) (Math.ceil((double) nPage / pagePerBlock)); // 현재 그룹
		int startPage = ((nowGrp - 1) * pagePerBlock) + 1; // 특정 그룹의 페이지 목록 시작
		int endPage = (nowGrp * pagePerBlock); // 특정 그룹의 페이지 목록 종료

		StringBuffer str = new StringBuffer();
		str.append("<div style='text-align:center'>");
		str.append("<ul class='pagination'> ");
		int _nowPage = (nowGrp - 1) * pagePerBlock; // 10개 이전 페이지로 이동

		if (nowGrp >= 2) {
			str.append("<li><a href='" + url + "?col=" + col + "&word=" + word + "&nowPage=" + nowPage + fk
					+ fno + "&nPage=" + _nowPage + "'>이전</A></li>");
		}

		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}

			if (nPage == i) {
				str.append("<li class='active'><a href=#>" + i + "</a></li>");
			} else {
				str.append("<li><a href='" + url + "?col=" + col + "&word=" + word + "&nowPage=" + nowPage + fk
						+ fno + "&nPage=" + i + "'>" + i + "</A></li>");
			}
		}

		_nowPage = (nowGrp * pagePerBlock) + 1; // 10개 다음 페이지로 이동

		if (nowGrp < totalGrp) {
			str.append("<li><A href='" + url + "?col=" + col + "&word=" + word + "&nowPage=" + nowPage + fk
					+ fno + "&nPage=" + _nowPage + "'>다음</A></li>");
		}
		str.append("</ul>");
		str.append("</div>");

		return str.toString();

	}

	// tld에서 선언하는 함수는 전부 static이어야함/
	public static int rcount(int bbsno, ReplyInter rinter) {
		return rinter.rcount(bbsno);
	}

	
	//tld에서 선언하는 함수는 전부 static이어야함/
	public static int rcount2(int imgno, IreplyInter irinter) {
		return irinter.rcount(imgno);
	}

}