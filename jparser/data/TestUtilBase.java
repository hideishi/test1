package app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

public class TestUtilBase {
    String sample = "methodName";
	//static ArrayList<Object> rsltList = new ArrayList<Object>();
    private static ArrayList<ClassTestData> classTestDataList = new ArrayList<ClassTestData>()  ;
    private static ArrayList<MethodTestData> methodTestDataList = null ;
//    private static ExceptionInfo exceptionInfo = null ;


    public static ArrayList<ClassTestData> getClassTestDataList() {
		return classTestDataList;
	}
	public static ArrayList<MethodTestData> getMethodTestDataList() {
		return methodTestDataList;
	}
	  private static String editParamValue(ParamInfo paramInfo){

		  String paramType = paramInfo.getParamType();
		  String paramValue = paramInfo.getParamValue();

		  return paramValue ;
	  }
}
