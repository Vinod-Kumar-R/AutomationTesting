package com.encash.offers.Utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Hello world!
 *
 */
public class ExcelReader 
{
	private File  filename ;
	private Workbook workbook;
	private Sheet sheet;
	private Row row;



	public ExcelReader(String file,int Sheetindex) throws Exception {

		this.filename = new File(file);
		this.workbook = WorkbookFactory.create(this.filename);
		this.sheet = this.workbook.getSheetAt(Sheetindex);
	}

	public int Rowcout(int sheetindex) {
		//sheet = this.workbook.getSheetAt(sheetindex);
		return sheet.getPhysicalNumberOfRows();
	}

	public int ColumnCout(int rownum) {
		row = this.sheet.getRow(rownum);
		return row.getPhysicalNumberOfCells();
	}

	private int  headerColumncount() {
		return this.sheet.getRow(0).getPhysicalNumberOfCells();
		
		
	}
	public String GetCellData(int Row, int Column) {


		String cellvalue = null;
		this.row = sheet.getRow(Row);
		cellvalue = FormatCell(this.row.getCell(Column));
		return cellvalue;
	}
	
	public void CloseWorkbook() throws IOException {
		this.workbook.close();
	}
	


	/**
	 * This function return particular Row Data in format of Map<Integer,String>
	 * @param Row
	 * @return Map<Integer,String> where Integer = column index and String the cell value
	 */
	public Map<Integer,String> GetRowData(int Row){

		Map<Integer,String> RowData = new HashMap();
		this.row = sheet.getRow(Row);

		/*for(Cell cell : this.row) {
			RowData.put(cell.getColumnIndex(), FormatCell(cell));
		}*/
		
		for(int c = 1;c<headerColumncount();c++) {
			Cell cell = this.row.getCell(c-1);
			     RowData.put(c-1, FormatCell(cell));
			
		}
		return RowData;
	}


	private String FormatCell(Cell cell) {
		String cellvalue = null;

		if(cell == null) {
			return null;
		}

		switch(cell.getCellType()) {

		case BLANK : cellvalue = null;
		break;
		case _NONE : cellvalue = null;
		break;
		case BOOLEAN : cellvalue = Boolean.toString(cell.getBooleanCellValue());
		break;
		case ERROR :   break;
		case FORMULA : break;
		case NUMERIC : 
			if(DateUtil.isCellDateFormatted(cell)) {
				cellvalue = cell.getDateCellValue().toString();
			}
			else
			{
				DataFormatter fmt = new DataFormatter();
				cellvalue = fmt.formatCellValue(cell);
			}
			break;
		case STRING : cellvalue =  cell.getStringCellValue();
		break; 
		}

		return cellvalue;

	}

}
