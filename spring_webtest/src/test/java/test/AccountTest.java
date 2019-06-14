package test;

import junit.framework.TestCase;

public class AccountTest extends TestCase {

	Account account;
	
	//테스트 메소드 실행전 호출
	//setUp할 때마다 Account객체를 생성하기 때문에 테스트 메소드가 4개여서 4개의 객체가 생성됨 => 메소드시작전에 먼저 실행됨을 알 수 있음
	protected void setUp() throws Exception {
		account = new Account(10000);
	}
	
	//테스트 메소드 실행후 호출
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//4개의 TEST메소드 실행전에 다 setup과 tearDown을 실행
	public void testAccount() {
		//fail("Not yet implemented");
	}
	//두값이 같은지 비교
	public void testGetBalance() {
		//fail("Not yet implemented");
		assertEquals(10000, account.getBalance());
	}
	//1000원을 빼고 남은 값이 같은지 비교
	public void testWithdraw() {
		//fail("Not yet implemented");
		account.withdraw(1000);
		assertEquals(9000, account.getBalance());
	}

	public void testDeposit() {
		//fail("Not yet implemented");
		account.deposit(1000);
		assertEquals(11000, account.getBalance());
	}

}
