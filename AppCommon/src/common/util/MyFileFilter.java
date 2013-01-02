package common.util;

import java.io.File;
import java.io.FilenameFilter;


public class MyFileFilter implements FilenameFilter{


    // フィルタ対象文字列
    private final String FILTER_KEYWORD = ".java";

    // FilenameFilterインタフェースで宣言されているacceptメソッドを記述
    // 第一引数はFile型、第二引数はString型で戻り値はboolean型
    // dirはFilenameFilterTestクラスにあるOBJECT_DIRと同一の値
    // nameはOBJECT_DIRのディレクトリ内に存在している、ある1ファイルの名前
    public boolean accept(File dir, String name){

        // Fileクラスのオブジェクト生成
        File file = new File(name);

        // ディレクトリならばfalseを返却(リストに追加しない)
        if(file.isDirectory()){
            return false;
        }

        // ファイル名の末尾が".exe"ならばtrueを返却(リストに追加)
        // そうでなければfalseを返却(リストに追加しない)
        return (name.endsWith(FILTER_KEYWORD));
    }
}

