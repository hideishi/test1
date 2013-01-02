package common;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JParserUtilTest {
	static private ArrayList<String> importList = new ArrayList<String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		importList.add("java.util.List");
		importList.add("java.util.*");

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsJavaClass() {
		//fail("まだ実装されていません");

		{
			String clsname = "ArrayList";

			boolean rslt = JParserUtil.isJavaClass(importList, clsname);
			if (rslt){
				System.out.println("clsname = "+ clsname + "は、Javaのクラスです。");
			}else{
				System.out.println("clsname = "+ clsname + "は、Javaのクラスではありません。");
			}
		}
		{
			String clsname = "String";
			boolean rslt = JParserUtil.isJavaClass(importList, clsname);
			if (rslt){
				System.out.println("clsname = "+ clsname + "は、Javaのクラスです。");
			}else{
				System.out.println("clsname = "+ clsname + "は、Javaのクラスではありません。");

			}
		}

		{
			String clsname = "Strings";
			boolean rslt = JParserUtil.isJavaClass(importList, clsname);
			if (rslt){
				System.out.println("clsname = "+ clsname + "は、Javaのクラスです。");
			}else{
				System.out.println("clsname = "+ clsname + "は、Javaのクラスではありません。");

			}
		}
	}

}
