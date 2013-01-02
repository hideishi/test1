package common.dbutil;
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
public class AttributeParam {
    private String name ;
    private String value ;

    public AttributeParam(String name, String value){
        this.name = name ;
        this.value = value ;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name name を設定します。
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value value を設定します。
     */
    public void setValue(String value) {
        this.value = value;
    }
}
