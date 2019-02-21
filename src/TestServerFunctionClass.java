import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestServerFunctionClass extends TestCase{
	
	public TestServerFunctionClass(String method) {
		super(method);
	}
	
	public void testConnection() throws IOException {
		assertTrue("Nie aawiazano polaczenia!",ServerFunctionClass.connectToDB("klienci.db"));
	}
	
	public void testGetUserCount() throws IOException {
		assertEquals(0,ServerFunctionClass.getUserCount());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new TestServerFunctionClass("testGetUserCount"));
		
		return suite;
	}
}


