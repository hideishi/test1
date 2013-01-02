package common.dbutil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * 作成日： 2005/01/09
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。 ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞
 * コード・テンプレート
 */
public class XmlUtil {
    private String xmlFilePath = null;

    private DocumentBuilderFactory factory = null;

    private DocumentBuilder builder = null;

    private Document document = null;

    private String encoding = "UTF-8";

    /**
     * XML Dcumentを取得
     *
     * @return
     */
    public Document getDocument() {
        return document;
    }

    public XmlUtil() throws ParserConfigurationException,
    							SAXException, IOException {
//        this.xmlFilePath = xmlFilePath;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
//        document = builder.parse(new File(xmlFilePath));

    }
    /**
     * コンストラクタ
     *
     * @param xmlFilePath
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public XmlUtil(String xmlFilePath) throws ParserConfigurationException,
            SAXException, IOException {
        this.xmlFilePath = xmlFilePath;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(new File(xmlFilePath));

    }
    public Element createElement(String elname){
        return document.createElement(elname);
    }
    /**
     * アトリビュート追加
     *
     * @param parentTagName
     *            親タグ名称
     * @param tagInfo
     *            更新するタグ情報
     */
    public boolean appendAttribute(String parentTagName, UpdateTagInfo tagInfo) {
        boolean rslt = false ;

        //同一アトリビュート存在判定
        if (isAttributeExist(parentTagName, tagInfo)) {
            return rslt ;
        }

        Element configTag = document.getDocumentElement();
        NodeList parentTags = configTag.getElementsByTagName(parentTagName);

        Element parentTag = (Element) parentTags.item(0);

        Element elem = document.createElement("property");

        AttributeParam keyAttr = tagInfo.getKeyAttr();
        AttributeParam updateAttr = tagInfo.getUpdateAttr();
        elem.setAttribute(keyAttr.getName(), keyAttr.getValue());
        elem.setAttribute(updateAttr.getName(), updateAttr.getValue());

        parentTag.appendChild(elem);

        return true ;
    }

    /**
     * アトリビュート更新
     *
     * @param parentTagName
     *            親タグ名称
     * @param tagInfo
     *            更新するタグ情報
     */
    public boolean updateAttribute(String parentTagName, UpdateTagInfo tagInfo) {
        boolean rslt = false ;

        Element configTag = document.getDocumentElement();
        NodeList parentTags = configTag.getElementsByTagName(parentTagName);

        Element parentTag = (Element) parentTags.item(0);

        //<mail>タグ配下の<property>タグのリストを取得
        NodeList propertyTags = parentTag.getElementsByTagName(tagInfo
                .getTagName());

        Element smtpServerPropertyTag = null;
        for (int i = 0; i < propertyTags.getLength(); i++) {
            Element propertyTag = (Element) propertyTags.item(i);

            String tagInfoKeyAttrValue = tagInfo.getKeyAttr().getValue();
            String propertyAttribute = propertyTag.getAttribute(tagInfo
                    .getKeyAttr().getName());

            if (tagInfoKeyAttrValue.equals(propertyAttribute)) {
                smtpServerPropertyTag = propertyTag;
                break;
            }
        }
        if (smtpServerPropertyTag != null) {
            AttributeParam updateAttr = tagInfo.getUpdateAttr();
            smtpServerPropertyTag.setAttribute(updateAttr.getName(), updateAttr.getValue());
            rslt = true ;
        }
        return rslt ;
    }
    /**
     * 同一アトリビュート存在判定
     *
     * @param parentTagName
     *            親タグ名称
     * @param tagInfo
     *            更新するタグ情報
     */
    public boolean isAttributeExist(String parentTagName, UpdateTagInfo tagInfo){
        boolean rslt = false;

        Element configTag = document.getDocumentElement();
        NodeList parentTags = configTag.getElementsByTagName(parentTagName);

        Element parentTag = (Element) parentTags.item(0);

        //<mail>タグ配下の<property>タグのリストを取得
        NodeList propertyTags = parentTag.getElementsByTagName(tagInfo.getTagName());

//        Element smtpServerPropertyTag = null;
        for (int i = 0; i < propertyTags.getLength(); i++) {
            Element propertyTag = (Element) propertyTags.item(i);

            String tagInfoKeyAttrValue = tagInfo.getKeyAttr().getValue();
            String propertyAttribute = propertyTag.getAttribute(tagInfo.getKeyAttr().getName());

            if (tagInfoKeyAttrValue.equals(propertyAttribute)) {
//                smtpServerPropertyTag = propertyTag;
                rslt = true ;
                break;
            }
        }

        return rslt;
    }
    /**
     *
     * @param xmlpath
     * @param indent
     */
    public void saveXml(String xmlpath, boolean indent) {
        if (document == null) {
            throw new IllegalArgumentException("documentが、存在しない");
        }
        if (xmlFilePath == null) {
            throw new IllegalArgumentException("");
        }
        //  XML 文書の出力
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlpath);

        TransformerFactory transFactory = TransformerFactory.newInstance();

        Transformer transformer = null;
        try {
            transformer = transFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            System.err.println("Document cannnot be configured:");
            System.err.println(e.toString());
            return;
        }

        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        if (indent){
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }else{
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
        }

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            System.err.println("Document cannnot be tranformed:");
            System.err.println(e.toString());
            return;
        }
    }
    /**
     * 作成された DOM を XML 文書として出力する。
     *
     */
    public void saveXml(boolean indent) {
        saveXml(xmlFilePath, indent);
    }
    /**
     *
     * @param nodeList
     * @param attrName
     * @param value
     * @return
     */
    public String getElementValue(NodeList nodeList, String attrName,
            String value) {
        Element element = null;

        for (int i = 0; i < nodeList.getLength(); i++) {
            // 要素を取得
            element = (Element) nodeList.item(i);

            // name属性が"CS_ITEM"のelementを収集
            if (element.getAttribute(attrName).equals(value)) {
                break;
            }
        }
        return element.getAttribute("value");
    }

    /**
     * Nodeリストから特定属性値のelementを抽出する
     */
    public LinkedList<Element> getElementList(NodeList nodeList, String attrName,String value) {
        LinkedList<Element> elementList = new LinkedList<Element>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            // formGroup要素を取得
            Element element = (Element) nodeList.item(i);

            // name属性が"CS_ITEM"のelementを収集
            if (element.getAttribute(attrName).equals(value)) {
                elementList.add(element);
            }
        }
        return elementList;
    }
    /**
     *
     * @param recordNodes
     * @return
     */
//    private LinkedList<Element> getItemList(NodeList recordNodes) {
//        LinkedList<Element> itemList = new LinkedList<Element>();
//
//        for (int i = 0; i < recordNodes.getLength(); i++) {
//            // formGroup要素を取得
//            Element element = (Element) recordNodes.item(i);
//
//            // name属性が"CS_ITEM"のelementを収集
//            if (element.getAttribute("name").equals("CS_ITEM")) {
//                itemList.add(element);
//            }
//        }
//        return itemList;
//    }
    //    public void getValue(){
    //        getCsItemInfo();
    //    }

}