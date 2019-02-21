import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestComposite extends TestCase {
	
	public TestComposite(String method) {
		super(method);
	}
	
	static public Test suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTestSuite(TestDeleteData.class);
		suite.addTestSuite(TestGetData.class);
		suite.addTestSuite(TestGetMetaData.class);
		
		suite.addTest(TestServerFunctionClass.suite());
		
		return suite;
	}
}
