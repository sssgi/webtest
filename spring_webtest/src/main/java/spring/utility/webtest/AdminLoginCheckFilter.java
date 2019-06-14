package spring.utility.webtest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdminLoginCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest  httpRequest = (HttpServletRequest)request;
		// Filter가 요청 중간에서 request 객체를 추출합니다.
		// ServletRequest가 상위이므로 다운캐스팅 필요
		
		HttpSession session = httpRequest.getSession(false);
		// 새로운 세션을 생성하지않고 request 객체에서 기존의 세션 반환
		
		boolean login = false; // 로그인 하지 않았다고 가정
		
		if(session != null) {  // session 객체가 생성되어 있는지 확인
			
			if(session.getAttribute("id")!=null && session.getAttribute("grade").equals("A")) {
			// 로그인을 했으면서 관리자인지 확인합니다.
				login = true; //관리자라면
			}
		}
		
		if(login) {// 정상적으로 로그인이 되었다면 요청 페이지로 이동합니다.
			chain.doFilter(request, response); //요청 페이지로 이동
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/member/login");
			dispatcher.forward(request, response); // 로그인이 안되었다면 로그인 페이지로 이동
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}


//filter : 요청과 응답사이에서 원하는대로 여과시키기도 하고 채집하기도 함.
