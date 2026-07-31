package BankAccount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
public class UnitTest {

	@Test
	public void Test_Deposit_ValidAmount() {
		Program account=new Program(100);
		account.deposit(50);
		assertEquals(150, account.getBalance());
	}

	@Test
	public void Test_Deposit_NegativeAmount() {
		Program account =new Program(100);

		Exception ex=assertThrows(IllegalArgumentException.class, () -> {account.deposit(-20);});
		assertEquals("Deposit amount cannot be negative.", ex.getMessage());
	}

	@Test
	public void Test_Withdraw_ValidAmount() {
		Program account =new Program(200);
		account.withdraw(50);
		assertEquals(150, account.getBalance());
	}

	@Test
	public void Test_Withdraw_InsufficientFunds() {
		Program account = new Program(100);
		Exception ex =assertThrows(IllegalStateException.class, () -> {account.withdraw(200);});
		assertEquals("Insufficient funds.", ex.getMessage());
	}
}
