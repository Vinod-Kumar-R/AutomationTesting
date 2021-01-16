package com.encash.offers.utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * This Class the generic method for Read an excel file. 
 *
 */
public class ExcelReader {
  private static Logger logger = LogManager.getLogger(ExcelReader.class);
  private File  filename;
  private Workbook workbook;
  private Sheet sheet;
  private Row row;
  private String excelfilename;
  private int excelsheetindex;

  public String getExcelfilename() {
    return excelfilename;
  }

  /**
   * This method is used to set the excel file name for an object.
   * @param excelfilename , excel file name which need to read
   * @throws EncryptedDocumentException exception if excel file is encrypted
   * @throws IOException , Exception if excel file is not able to read
   */
  public void setExcelfilename(String excelfilename) 
                  throws EncryptedDocumentException, IOException {
    this.excelfilename = excelfilename;
    this.filename = new File(this.excelfilename);
    this.workbook = WorkbookFactory.create(this.filename);
  }

  public int getExcelsheetindex() {
    return excelsheetindex;
  }

  public void setExcelsheetindex(int excelsheetindex) {
    this.excelsheetindex = excelsheetindex;
    this.sheet = this.workbook.getSheetAt(this.excelsheetindex);
  }

   /**
   * In the constructor we are reading the excel file.
   * @param file it contain the location of the file which need to read
   * @param sheetIndex in excel it will specific which sheet row has to read
   * @throws IOException if excel is not able to read then IOException are thrown
   * @throws EncryptedDocumentException if excel file is encrypted then 
   *     EncryptedDocumentException are throw
   * 
   */
  /**
  public ExcelReader(String file, int sheetIndex) throws EncryptedDocumentException, IOException  {

    this.filename = new File(file);
    this.workbook = WorkbookFactory.create(this.filename);
    this.sheet = this.workbook.getSheetAt(sheetIndex);
    System.out.println("paramter constructor vinod");
    logger.info("filename " + this.filename);
    logger.info("workbook " + this.workbook);
    logger.info("sheet " + this.sheet);
  }
**/
  /**
   * This method is used to get the number of row count in an sheet.
   * @param sheetindex read the integer value of sheet
   * @return the total number of row are used 
   */
  public int rowCout(int sheetindex) {

    return sheet.getPhysicalNumberOfRows();
  }

  /**
   * This method is used to get the number of column are used in particular row.
   * @param rownum is integer number 
   * @return total number of column used in particular row
   */
  public int columnCout(int rownum) {
    row = this.sheet.getRow(rownum);
    return row.getPhysicalNumberOfCells();
  }

  private int  headerColumncount() {
    return this.sheet.getRow(0).getPhysicalNumberOfCells();


  }

  /**
   * This method is used to fetch the data from particular row and column.
   * @param row Row contain the integer number from which row data need to fetch 
   * @param column Column contain the integer number from which column data need to fetch
   * @return the cell contain data as String type
   * 
   */
  public String getCellData(int row, int column) {


    String cellvalue = null;
    this.row = sheet.getRow(row);
    cellvalue = formatCell(this.row.getCell(column));
    return cellvalue;
  }

  /**
   * This method is used to close the workbook.
   * @throws IOException ioException
   */
  public void closeWorkbook() throws IOException {
    this.workbook.close();
  }

  /**
   * This function return particular Row Data in format of Map&lt;Integer,String&gt;.
   * @param row this contain the row number in which data has to read
   * @return Map&lt;Integer,String&gt; where Integer = column index and String the cell value
   */
  public Map<Integer, String> getRowData(int row) {

    Map<Integer, String> rowData = new HashMap<Integer, String>();
    this.row = sheet.getRow(row);

    /*for(Cell cell : this.row) {
            RowData.put(cell.getColumnIndex(), FormatCell(cell));
       }*/

    for (int c = 1; c < headerColumncount(); c++) {
      Cell cell = this.row.getCell(c - 1);
      rowData.put(c - 1, formatCell(cell));

    }
    return rowData;
  }

  /**
   * This is method is used to get data based on the cell type i.e. 
   * cell has string value, boolean value or formula.
   * @param cell cell 
   * @return cell value is return
   */
  
  private String formatCell(Cell cell) {
    String cellvalue = null;

    if (cell == null) {
      return null;
    }

    switch (cell.getCellType()) {

      case BLANK : cellvalue = null;
      break;
      case _NONE : cellvalue = null;
      break;
      case BOOLEAN : cellvalue = Boolean.toString(cell.getBooleanCellValue());
      break;
      case ERROR :   break;
      case FORMULA : cellvalue =  cell.getStringCellValue();
      break;
      case NUMERIC : 
        if (DateUtil.isCellDateFormatted(cell)) {
          cellvalue = cell.getDateCellValue().toString();
        } else {
          DataFormatter fmt = new DataFormatter();
          cellvalue = fmt.formatCellValue(cell);
        }
        break;
      case STRING : cellvalue =  cell.getStringCellValue();
      break;

      default: break;
    }

    return cellvalue;

  }

}
