import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

public class TestDeleteData extends TestCase{
	
	public TestDeleteData(String method) {
		super(method);
	}
	
	
	public void testExceptionExecuteUserQuery() {
		try {
			Connection con = DriverManager.getConnection("jdbc:sqlite:" + "klienci.db");
			Statement st=con.createStatement();
			DeleteData.executeUserQuery("", st);
			fail("Nie powinno dojsc do fail!");
		}
		catch(SQLException e) {
			assertTrue(true);
		}
	}
}
