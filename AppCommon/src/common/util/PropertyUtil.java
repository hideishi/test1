package common.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	static private String confPath ;		//= "C:\\eclipse\\wsp\\ec_work\\JavaProj1\\conf";
	static private String confFilename ;	// = "LoadTestData.xml";

    static Properties prop = null ;

	/** プロパティファイル配置パスを設定 */
	static public void setConfPath(String fpath){
		confPath = fpath ;
	}
	/** プロパティファイル配置パスを取得 */
	static public String getConfPath(){
		return confPath ;
	}
	/** プロパティファイル名を設定 */
	static public void setConfFilename(String fname){
		confFilename = fname ;
	}
	/**
	 * 定義情報ファイルを読み込む.<BR>
	 * 事前に　setConfPath、setConfFilename　を行うこと.
	 */
    static public void load() {
        prop = new Properties();

        try {
            InputStream stream = new FileInputStream(confPath + File.separator + confFilename);
            prop.loadFromXML(stream);
            stream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	/**
	 * 定義情報ファイルを読み込む.
	 * @param confFilename 定義情報ファイル名 例：Sample.xml
	 */
    static public void load(String _confPath, String _confFilename) {
        prop = new Properties();

        try {
            InputStream stream = new FileInputStream(_confPath + File.separator + _confFilename);

            prop.loadFromXML(stream);
            stream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
	 * 定義情報ファイルを読み込む.<BR>
     * 定義ファイルが格納されるフォルダがクラスパスとして定義されていること
     * @param _confFilename 定義情報ファイル名 例：Sample.xml
     */
     static public void load(String _confFilename) {
        prop = new Properties();

        try {

            InputStream stream = ClassLoader.getSystemResourceAsStream(_confFilename);

            prop.loadFromXML(stream);
            stream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 指定されたキーにより定義情報を取得する.
     * @param key キー
     * @return 定義情報
     */
    static public String getProperty(String key) {
    	return prop.getProperty(key);
    }

}
