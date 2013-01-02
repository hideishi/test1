package common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/*
 * 作成日: 2005/06/12
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */

/**
 * @author ken
 *
 * TODO この生成された型コメントのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
public class PoiUtil {
	private HSSFWorkbook workbook = null;
	private FileInputStream in = null;
	private FileOutputStream out = null ;
	private String filePath = null;
	private POIFSFileSystem pfs = null ;

	/**
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public HSSFWorkbook open(String path) throws IOException{
	    filePath = path ;

		try{
			in = new FileInputStream(path);
			pfs = new POIFSFileSystem(in);
			workbook = new HSSFWorkbook(pfs);
			return workbook ;

		} catch(IOException ioe) {
			throw new IOException(ioe.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException ioe) {
				throw new IOException(ioe.getMessage());
			}
		}

	}
	/**
	 *
	 *
	 */
	public void close() {
	    workbook = null;
	    pfs = null ;
	}
	public HSSFSheet getSheet(String sheetName)throws PoiException{
	    if (workbook == null){
	        throw new PoiException("PoiUtil#getSheetAt workbook is null.");
	    }
        return workbook.getSheet(sheetName);
	}
	public HSSFSheet getSheetAt(int idx)throws PoiException{
	    if (workbook == null){
	        throw new PoiException("PoiUtil#getSheetAt workbook is null.");
	    }
	    return workbook.getSheetAt(idx);
	}
	public String getSheetName(int idx)throws PoiException{
	    if (workbook == null){
	        throw new PoiException("PoiUtil#getSheetName workbook is null.");
	    }
	    return workbook.getSheetName(idx);
	}

	public HSSFRow getRow(HSSFSheet sheet, int idx)throws PoiException{
	    if (sheet.getLastRowNum() < idx){
	        return null ;
	    }
	    HSSFRow row = sheet.getRow(idx);
	    return row ;
	}
	public String getCellValue(HSSFCell cell)throws PoiException{
	    if (cell == null){
	        throw new IllegalArgumentException("cell is null.");
	    }
	    String value = null ;
	    int cellType = cell.getCellType();

	    switch(cellType){
	    case HSSFCell.CELL_TYPE_STRING:
		    value = cell.getStringCellValue() ;
	    	break;
	    case HSSFCell.CELL_TYPE_NUMERIC:
	    	value = String.valueOf(cell.getNumericCellValue());
	    	break;
	    case HSSFCell.CELL_TYPE_BOOLEAN:
	    	value = String.valueOf(cell.getBooleanCellValue());
	    	break;
	    case HSSFCell.CELL_TYPE_FORMULA:
	    	value = cell.getCellFormula();
	    	break;
	    case HSSFCell.CELL_TYPE_ERROR:
	    	value = "";
	    	break;
	    default:
	    	value = "";
	    	break;

	    }

	    return value ;
	}
	public String getCellValue(int sheetnum, int rownum, int cellnum)throws PoiException{
	    String value = null ;

	    HSSFSheet sheet = getSheetAt(sheetnum);
	    if (sheet == null){
	        throw new IllegalArgumentException("sheetnum is illegal.");
	    }
		HSSFRow row = sheet.getRow(rownum);
	    if (row == null){
	        throw new IllegalArgumentException("rownum is illegal.");
	    }
		short cellmax = row.getLastCellNum();
	    if (cellnum > cellmax){
	        throw new IllegalArgumentException("cellnum is illegal. rownum, cellnum = " + rownum + ", "+ cellnum);
	    }

		HSSFCell cell = getCell(row, cellnum);
	    if (cell == null){
	        value = "";
	        return value ;
	    }
	    int cellType = cell.getCellType();

	    switch(cellType){
	    case HSSFCell.CELL_TYPE_STRING:
		    value = cell.getStringCellValue() ;
	    	break;
	    case HSSFCell.CELL_TYPE_NUMERIC:
	    	value = String.valueOf(cell.getNumericCellValue());
	    	break;
	    case HSSFCell.CELL_TYPE_BOOLEAN:
	    	value = String.valueOf(cell.getBooleanCellValue());
	    	break;
	    case HSSFCell.CELL_TYPE_FORMULA:
	    	value = cell.getCellFormula();
	    	break;
	    case HSSFCell.CELL_TYPE_ERROR:
	    	//value = "CELL_TYPE_ERROR";
	    	value = "" ;
	    	break;
	    default:
	    	value = "";
	    	break;

	    }

	    return value ;
	}
	public HSSFCell getCell(HSSFRow row, int idx)throws PoiException{
	    if (row == null){
	        throw new IllegalArgumentException("PoiUtil#getCell: Input parameter \"row\" is null.");
	    }

	    HSSFCell cell = row.getCell((short)idx);
/*	    if (cell == null){
	        String msg = "PoiUtil#getCell: Cell that is the return value of HSSFRow#getCell(" + row.getRowNum() + "," + idx +  ") is null.";
	        throw new PoiException( msg );
	    }
*/
	    return cell ;
	}
	public double getCellValue(HSSFCell cell, double val)throws PoiException{
	    if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING){
	        throw new PoiException("CellType is not match. CellType is "+ cell.getCellType());
	    }
	    return cell.getNumericCellValue() ;
	}
//	public void setCellValue(String value){
//
//	}
    /**
     * @return workbook を戻します。
     */
    public HSSFWorkbook getWorkbook() {
        return workbook;
    }
    /**
     * @param workbook workbook を設定。
     */
    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }
}
