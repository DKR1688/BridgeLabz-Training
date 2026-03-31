package com.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class DatabaseConnectTest {
	//it will run before every test case
	@BeforeEach
	public void setup() {
		DatabaseConnection.connect();
	}
	
	// it will run after every test case
	@AfterEach
	public void tearDown() {
		DatabaseConnection.disconnect();
	}
	
	//it will verify connection after test case it will diconnected
	@Test
	public void isConnectedTest() {
		assertTrue(DatabaseConnection.isConnected());
	}
}
