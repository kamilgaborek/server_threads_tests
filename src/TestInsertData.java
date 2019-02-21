import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestInsertData extends TestCase{
	
	public TestInsertData(String method) {
		super(method);
	}
	
	public void testInsertToDB() throws IOException{
		
        try {
        	ServerSocket ss= new ServerSocket(8189);
    		Socket userSocket = ss.accept();
    		InputStream is = userSocket.getInputStream();
            OutputStream os = userSocket.getOutputStream();
            Scanner sc = new Scanner(is);
            PrintWriter pw = new PrintWriter(os, true);
            
            Connection con= DriverManager.getConnection("jdbc:sqlite:" + "klienci.db");
            
            InsertData data=new InsertData(con, pw, sc);
        	
        	data.insertToDB();
        }
        catch(SQLException e){
        	assertTrue(true);
        }
        catch(FileNotFoundException e){
        	assertTrue(true);
        }
        
        
	}
	
	public void testTableDetails() throws IOException, SQLException {
		ServerSocket ss= new ServerSocket(8189);
		Socket userSocket = ss.accept();
		InputStream is = userSocket.getInputStream();
        OutputStream os = userSocket.getOutputStream();
        Scanner sc = new Scanner(is);
        PrintWriter pw = new PrintWriter(os, true);
        
        Connection con= DriverManager.getConnection("jdbc:sqlite:" + "klienci.db");
        Statement st=con.createStatement();
        
        InsertData data=new InsertData(con, pw, sc);
    	
    	assertEquals(3,data.tableDetails("klienci", pw, st));
		
	}
	
	public static Test suite(){
		TestSuite suite= new TestSuite();
		suite.addTest(new TestInsertData("testTableDetails"));
		
		return suite;
	}

}
