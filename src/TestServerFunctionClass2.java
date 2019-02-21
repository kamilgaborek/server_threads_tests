import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestServerFunctionClass2 extends TestCase{
	
	private static ServerFunctionClass server;
	private static ServerSocket ss;
	
	public TestServerFunctionClass2(String method) {
		super(method);
	}
	
	public void testConnection() throws IOException {
		assertTrue("Nie aawiazano polaczenia!",ServerFunctionClass.connectToDB("klienci.db"));
	}
	
	public void testGetUserCount() throws IOException {
		assertEquals(1,ServerFunctionClass.getUserCount());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new TestServerFunctionClass2("testGetUserCount"));
		suite.addTest(new TestServerFunctionClass2("testConnection"));

		
		
		TestSetup wrapper = new TestSetup(suite) {
			protected void setUp() throws IOException {
				oneTimeSetUp();
			}
			protected void tearDown() throws IOException {
				oneTimeTearDown();
			}
		};
		
		return wrapper;
	}
	
	public static void  oneTimeSetUp() throws IOException {
		ss= new ServerSocket(8189);
		Socket s = ss.accept();
		server = new ServerFunctionClass(s);

	}
	
	public static void oneTimeTearDown() throws IOException {
		ss.close();
	}
	
	/*protected void setUp() throws IOException {
		ss= new ServerSocket(8189);
		Socket s = ss.accept();
		server = new ServerFunctionClass(s);

	}
	
	protected void tearDown() throws IOException {
		ss.close();
	}*/
	
}



