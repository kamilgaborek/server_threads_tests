import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import junit.framework.TestCase;

public class TestGetMetaData extends TestCase{
	private Connection con;
	private Statement st;
	
	public TestGetMetaData(String method) {
		super(method);
	}

	
	public void testExecuteUserQuery() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + "klienci.db");
		Statement st=con.createStatement();
		GetMetaData date=new GetMetaData(con);
		
		assertNotNull(date.getMetaDate());
	}
}
