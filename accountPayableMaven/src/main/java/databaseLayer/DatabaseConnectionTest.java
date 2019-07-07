package databaseLayer;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseConnectionTest {

	@Test
	public void test() {
		DatabaseConnection conn = new DatabaseConnection();
		assertEquals(true, conn instanceof DatabaseConnection);
		
		DatabaseConnection connFalse = new DatabaseConnection();
		assertNotEquals(false, connFalse instanceof DatabaseConnection);
	}

}
