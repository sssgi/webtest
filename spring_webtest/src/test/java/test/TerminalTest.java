package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TerminalTest {
	private static Terminal term;
	
	//단위테스트를 시작하기 전에 해야할 내용
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		term = new Terminal();
		term.netConnect();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		term.netDisConnect();//연결해제
	}
	
	@Before
	public void setUp() throws Exception {
		term.logon("user1", "1234");
	}

	@After
	public void tearDown() throws Exception {
		term.logoff();
	}
	//각각 테스트 메소드가 독립적으로 실행되기 때문에 순서는 상관없음
	@Test
	public void TerminalConnected() throws Exception{
		assertTrue(term.isLogon());
		System.out.println("== logon test");
	}

	@Test
	public void getReturnMsg() throws Exception{
		term.sendMessage("hello");
		assertEquals("hello", term.getReturnMsg());
		System.out.println("== message test");
	}

}
