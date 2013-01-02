package app;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.util.FileUtil;
import common.util.Range;
import common.util.XmlUtil;

public class TestUtil {
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
	/**
	   * MethodTestDataタグのテストデータ情報を解析
	   * @param methodDataList
	   */
	  private static ArrayList<MethodTestData> parseMethodTestData(NodeList methodDataList){
		  ArrayList<MethodTestData> methodTestDataList = new ArrayList<MethodTestData>();

	        for (int i = 0; i < methodDataList.getLength(); i++) {
	            // 要素を取得
	        	Element element = (Element) methodDataList.item(i);

	            String attrName = "methodName";
	            String methodName = element.getAttribute(attrName);

		        //InputParam解析
	    	    NodeList inputParamList = element.getElementsByTagName("InputParam");
	    	    ArrayList<ParamInfo> paramInfoList = TestXmlUtil.parseInputParam(inputParamList);

		  	  	//ExpectedValue解析
	    	    NodeList expectedValueList = element.getElementsByTagName("ExpectedValue");
	    	    ParamInfo expectedValueInfo = TestXmlUtil.parseExpectedValue(expectedValueList);

	    	    NodeList usedExceptionList = element.getElementsByTagName("UsedException");
	    	    ExceptionInfo exceptionInfo = TestXmlUtil.parseUsedException(usedExceptionList);

	    	    MethodTestData mtd = new MethodTestData(methodName, paramInfoList, expectedValueInfo, exceptionInfo);
	    	    methodTestDataList.add(mtd);
	        }
	        return methodTestDataList ;

	  }
	  /**
	   * テストデータの読み込み
	   */
	  public static ArrayList<ClassTestData> loadTestData(){

	    String xmlFilePath = "C:\\eclipse\\pleiades-e3.5-1\\workspace\\parse1\\input\\testdata.xml";

		XmlUtil util = null ;
	    NodeList nodeList = null ;
	    try {
			util = new XmlUtil(xmlFilePath);

			String tagname = "ClassTestData";
			nodeList = util.getNodeListByRoot(tagname);

		    String attrName = "";
		    String value = "";
		    //LinkedList<Element> elemlist = util.getElementList(nodeList, attrName, value) ;

		    Element element = null;

	        int len = nodeList.getLength();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	            // 要素を取得
	            element = (Element) nodeList.item(i);

	            // clsname属性の値を取得
	            attrName = "clsname";
	            String clsname = element.getAttribute(attrName);

	            //@@@@@@
	            tagname = "MethodTestData";
	    	    NodeList methodDataList = element.getElementsByTagName(tagname);
		        len = methodDataList.getLength();

		        methodTestDataList = parseMethodTestData(methodDataList);

		        ClassTestData ctd = new ClassTestData(clsname, methodTestDataList);

		        classTestDataList.add(ctd);
	        }

		} catch (ParserConfigurationException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
        return classTestDataList ;
	}
	  private static String editParamValue(ParamInfo paramInfo){

		  String paramType = paramInfo.getParamType();
		  String paramValue = paramInfo.getParamValue();

		  return paramValue ;
	  }
	  /*
	*/
	public static MethodTestData getInputValues(String className, String testName){

		boolean exitflg = false ;
		ArrayList<ParamInfo> paramInfoList = null ;
//		ArrayList<String> valueList = new ArrayList<String>();

		MethodTestData mtd = null ;

		Iterator<ClassTestData> itrctd = classTestDataList.iterator();
		while(itrctd.hasNext()){
			ClassTestData ctd = (ClassTestData)itrctd.next();
			if (ctd.getClassName().compareTo(className)==0){
				//対象クラスのメソッド情報取得
				ArrayList<MethodTestData> mtdlist = ctd.getMethodTestDataList();
				Iterator itrmtd = mtdlist.iterator();
				while(itrmtd.hasNext()){
					mtd = (MethodTestData)itrmtd.next() ;
					if (mtd.getMethodName().compareTo(testName)==0){
						//対象メソッドの場合
						paramInfoList = mtd.getParamInfoList();

						Iterator itrparam = paramInfoList.iterator();
						while(itrparam.hasNext()){
							ParamInfo paramInfo = (ParamInfo)itrparam.next();
							mtd.getValueList().add(editParamValue(paramInfo));
						}

						exitflg = true ;
						break;

					}
				}
				if (exitflg){
					break;
				}
			}
		}
		return mtd ;
	}
/*
	public static String getExpectedValue(String className, String testName){
		String rtn = "";

		if (testName.compareTo("testService2")==0){
			rtn = "exit service2" ;
		}
		else
		if (testName.compareTo("testService")==0){
			rtn = "5" ;
		}
		else
		if (testName.compareTo("testIsInclude")==0){
			rtn = "true" ;
		}
		return rtn ;
	}
*/
}
