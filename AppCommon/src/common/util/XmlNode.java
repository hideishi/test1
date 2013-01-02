package common.util;

import java.util.ArrayList;

public class XmlNode {
	private short nodeTpe ;
	private String nodeName ;
	private String nodeValue ;
	private ArrayList<XmlNode> childList = new ArrayList<XmlNode>();

	public short getNodeTpe() {
		return nodeTpe;
	}
	public void setNodeTpe(short nodeTpe) {
		this.nodeTpe = nodeTpe;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	public ArrayList<XmlNode> getChildList() {
		return childList;
	}
	public void setChildList(ArrayList<XmlNode> childList) {
		this.childList = childList;
	}


}
