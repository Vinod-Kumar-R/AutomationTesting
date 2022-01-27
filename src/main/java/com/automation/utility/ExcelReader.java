package com.automation.utility;

import com.aventstack.extentreports.Status;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

/**
 * This Class the generic method for Read an excel file. 
 *
 */
public class ExcelReader {
  private File  filename;
  private Workbook workbook;
  private Sheet sheet;
  private Row row;
  private String excelfilename;
  private int excelsheetindex;
  private Cell cell;

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
  
  public void createExcelFile(boolean excelfile) throws IOException {
    this.workbook = WorkbookFactory.create(excelfile);
  }
  
  public int getExcelsheetindex() {
    return excelsheetindex;
  }

  public void getExcelsheetindex(int excelsheetindex) {
    this.excelsheetindex = excelsheetindex;
    this.sheet = this.workbook.getSheetAt(this.excelsheetindex);
  }
  
  public void setExcelSheet(String sheetName) {
    this.sheet = this.workbook.createSheet(sheetName);
  }
  

  /*
   * In the constructor we are reading the excel file.
   * @param file it contain the location of the file which need to read
   * @param sheetIndex in excel it will specific which sheet row has to read
   * @throws IOException if excel is not able to read then IOException are thrown
   * @throws EncryptedDocumentException if excel file is encrypted then 
   *     EncryptedDocumentException are throw
   * 
   */
  /*
  public ExcelReader(String file, int sheetIndex) throws EncryptedDocumentException, IOException  {

    this.filename = new File(file);
    this.workbook = WorkbookFactory.create(this.filename);
    this.sheet = this.workbook.getSheetAt(sheetIndex);
    System.out.println("paramter constructor vinod");
    logger.info("filename " + this.filename);
    logger.info("workbook " + this.workbook);
    logger.info("sheet " + this.sheet);
  }
*/
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
   * method used to create cell and update the value in it. 
   * @param column is column number in which value as to set
   * @param cellData is data to set in row and cell
   */
  public void setCellData(int column, String cellData) {
    this.cell = this.row.createCell(column, CellType.STRING);
    this.cell.setCellValue(cellData);
  }
  
  /**
   * Method is used to set the cell data in String format.
   * @param column number in which data need to enter.
   * @param cellData Actual data for cell
   * @param style of the cell
   */
  public void setCellData(int column, String cellData, CellStyle style) {
    this.cell = this.row.createCell(column, CellType.STRING);
    this.cell.setCellValue(cellData);
    this.cell.setCellStyle(style);
  }
  
  /**
   * Method is used to set the Date Format for particular cell.
   * @param column number of data.
   * @param cellData Cell Data to enter it
   * @param style Cell style
   */
  public void setCellData(int column, Date cellData, CellStyle style) {
    this.cell = this.row.createCell(column, CellType.STRING);
    this.cell.setCellValue(cellData);
    this.cell.setCellStyle(style);
  }
  
  public void setCreateRow(int row) {
    this.row = this.sheet.createRow(row);
  }
  
  /**
   * Set the Header Cell style.
   * @return CellStyle of header.
   */
  public CellStyle getHeaderCellStyle() {
    CellStyle style = this.workbook.createCellStyle();
    Font font = this.workbook.createFont();
    font.setBold(true);
    font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
    //style.setFillBackgroundColor(IndexedColors.BLUE.index);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
    return style;
  }
  
  /**
   * Method is used to set the style of Cell based on the status of execution.
   * @param status Extent report Status.
   * @return CellStyle
   */
  public CellStyle getStatusCell(Status status) {
    CellStyle style = this.workbook.createCellStyle();
    Font font = this.workbook.createFont();
    font.setColor(IndexedColors.WHITE.getIndex());
    switch (status) {
      case PASS: 
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());        
        break;
      case FAIL: 
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        break;
      case SKIP: 
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        break;
      default : 
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        font.setColor(IndexedColors.BLACK.getIndex());        
        break;
    }
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFont(font);
    style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    return style;
  }
  
  /**
   * Method is used to set the date format style.
   * @return CellStyle
   */
  public CellStyle getdatetimeCell() {
    CellStyle style = this.workbook.createCellStyle();
    CreationHelper creationHelper = workbook.getCreationHelper();
    style.setDataFormat(creationHelper.createDataFormat().getFormat(
                    "dd/mm/yyyy hh:mm:ss"));
    style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    return style;
  }
  
  /**
   * Method is used to set the style for Cell of generic.
   * 
   */
  public CellStyle generalStyle() {
    CellStyle style = this.workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setWrapText(true);
    return style;   
  }
  
  /**
   * Method is used to set the text style of generic.
   * @return CellStyle
   */
  public CellStyle generalTextStyle() {
    CellStyle style = this.workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.LEFT);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setWrapText(true);
    return style;   
  }
  
  public void freezePane(int colSplit, int rowSplit) {
    this.sheet.createFreezePane(colSplit, rowSplit);
  }
  
  /**
   * Method is used to set the Border for cell Range.
   * @param firstRow - start of first row cell.
   * @param lastRow - End of the Last row cell.
   * @param firstCol - start of First Column cell.
   * @param lastCol - End of last column cell.
   */
  public void setBorder(int firstRow, int lastRow, int firstCol, int lastCol) {
    CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
    PropertyTemplate propertyTemplate = new PropertyTemplate();
    propertyTemplate.drawBorders(region, BorderStyle.THIN, BorderExtent.ALL);
    propertyTemplate.applyBorders(this.sheet);
    
  }

  /**
   * This method is used to close the workbook.
   * @throws IOException ioException
   */
  public void closeWorkbook() throws IOException {
    this.workbook.close();
  }
  
  /**
   * Method is used to write to excel sheet.
   * @param fileName is the new file which will create if not exit.
   * @throws IOException if file not able to write
   */
  public void writeWorkbook(String fileName) throws IOException {
    FileOutputStream fileOut  = new FileOutputStream(fileName);
    this.workbook.write(fileOut);
    fileOut.close();
  }
  
  /**
   * Method is used to write to excel sheet.
   * @param fileName is the new file which will create if not exit.
   * @throws IOException if file not able to write
   */
  public void writeWorkbook(File fileName) throws IOException {
    FileOutputStream fileOut  = new FileOutputStream(fileName);
    this.workbook.write(fileOut);
    fileOut.close();
  }
  
  /**
   * Method is used to set the ColumnWidth for Result Excel sheet.
   */
  public void setWidthHeight() {
    this.sheet.setColumnWidth(0, 13 * 256);
    this.sheet.setColumnWidth(1, 80 * 256);
    this.sheet.setColumnWidth(2, 13 * 256);
    this.sheet.setColumnWidth(3, 10 * 256);
    this.sheet.setColumnWidth(4, 20 * 256);
    this.sheet.setColumnWidth(5, 20 * 256);
    this.sheet.setColumnWidth(6, 20 * 256); 
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
