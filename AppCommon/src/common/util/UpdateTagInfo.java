package common.util;

import java.util.LinkedList;

/*
 * 作成日： 2005/01/10
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class UpdateTagInfo {
    private String tagName ;
    private AttributeParam keyAttr ;
    private AttributeParam updateAttr ;

    public UpdateTagInfo(String tagName, AttributeParam keyAttr, AttributeParam updateAttr ){
        this.tagName = tagName ;
        this.keyAttr = keyAttr ;
        this.updateAttr = updateAttr ;
    }
    /**
     * @return keyAttr
     */
    public AttributeParam getKeyAttr() {
        return keyAttr;
    }
    /**
     * @param keyAttr keyAttr を設定します。
     */
    public void setKeyAttr(AttributeParam keyAttr) {
        this.keyAttr = keyAttr;
    }
    /**
     * @return tagName
     */
    public String getTagName() {
        return tagName;
    }
    /**
     * @param tagName tagName を設定します。
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    /**
     * @return updateAttr
     */
    public AttributeParam getUpdateAttr() {
        return updateAttr;
    }
    /**
     * @param updateAttr updateAttr を設定します。
     */
    public void setUpdateAttr(AttributeParam updateAttr) {
        this.updateAttr = updateAttr;
    }
}
