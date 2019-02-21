import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import junit.framework.TestCase;

public class TestGetData extends TestCase{
	private Connection con;
	private Statement st;
	
	public TestGetData(String method) {
		super(method);
	}
	
	protected void setUp() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + "klienci.db");
		Statement st=con.createStatement();
	}

	
	public void testExecuteUserQuery() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + "klienci.db");
		Statement st=con.createStatement();
		assertNotNull(GetData.executeUserQuery("select * from klienci", st));
	}
	
	public void testExceptionExecuteUserQuery() {
		try {
			
			GetData.executeUserQuery("select *", st);
			fail("Nie powinno dojsc do fail!");
		}
		catch(SQLException e) {
			assertTrue(true);
		}
		catch(NullPointerException e) {
			assertTrue(true);
		}
	}
}
