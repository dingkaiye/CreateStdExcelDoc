package ctrateStdExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

import org.apache.poi.ss.usermodel.Hyperlink;

/**
 * ��ȡODS����㼰ϵͳ��¼���ĵ�,����ģ�����ݽṹ
 * @author ding_kaiye
 *
 */
public class ToStdExcelUtil {
	
	private static Logger logger = LogManager.getLogger("Execl"); 
//	private static String configFile = "odsexecl.properties";
	/**
	 * ��ȡ Workbook 
	 * @param filepath
	 * @return
	 */
	public static Workbook getWorkbook(String filepath) {
		if (filepath == null) {
			return null;
		}
		InputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			if (filepath.endsWith(".xls")) {
				workbook = new HSSFWorkbook();
				writeWorkbookToFile(workbook, filepath);
			} else if (filepath.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook();
				writeWorkbookToFile(workbook, filepath);
			} else {
				workbook = new XSSFWorkbook();
				writeWorkbookToFile(workbook, filepath);
			}
			logger.error(filepath + " �ļ�������,�����ļ� ");
		}
		
		try {
			inputStream = new FileInputStream(filepath);
			if (filepath.endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else if (filepath.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new XSSFWorkbook(inputStream);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(filepath + " IOException ", e);
		}

		return (Workbook) workbook;

	}
	 
	/**
	 * �� workbook д���ļ���
	 * @param resultWorkbook
	 * @param outFile
	 */
	public static boolean writeWorkbookToFile(Workbook workbook, String outFile) {
		logger.info("�� workbook д���ļ� " + outFile  + "  ��ʼ ");
		FileOutputStream fOut =  null;
		try {
			File file = new File(outFile);
			
			fOut = new FileOutputStream(outFile);
			workbook.write(fOut);
			fOut.flush();  
			fOut.close();  // �����������ر��ļ�  
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
			return false;
		} catch (IOException e) {
			logger.error("IOException", e);
			return false;
		}
		logger.info("�� workbook д���ļ� " + outFile  + "  ���");
		return true;

	}
	
	/**
	 * ��ȡԴ��sheet, ���ɱ��嵥
	 * @param workbook
	 * @param sheetName
	 * @return List<TableOdsInfo> tableList
	 * @throws IOException 
	 */
	public static List<TableOdsInfo> readTableList (Workbook  workbook, String sheetName )  {
		logger.info("�����嵥  ��� ����Ϣ�б�  ��ʼ, Sheet Name: " + sheetName);
//		try {
//			Properties properties =  loadConfigPropertiesFile (configFile);
//		} catch (IOException e) {
//			logger.error(configFile + "�ļ�����ʧ��, �����ļ��Ƿ����", e);
//			throw e ;
//		}
		Sheet readsheet = workbook.getSheet(sheetName);
		List<TableOdsInfo> tableList = new ArrayList<TableOdsInfo>();
		tableList.clear();
		int baserow = -1 ;
		int baseCol = -1 ;
		int rowNum = readsheet.getLastRowNum();
		Map<String, Integer> colIndex = new HashMap<String, Integer>();
		for (int i=0; i<rowNum && baserow == -1; i++){
			logger.debug("��ǰ��ȡ��: " + i);
			Row row = readsheet.getRow(i);
			int colNum = row.getLastCellNum();
			for(int j=0; j<colNum && baserow == -1 ; j++ ){
//				Cell cell = row.getCell(j);
				String content = getCellString(row, j);
				if(content.contains("Դϵͳ����")){
					baserow = i;
					baseCol = j;
				}
				colIndex.clear();
				for(int cur=j; cur<colNum; cur++){
					content = getCellString(row, cur);
					if(content != null && !"".equals(content)){
						colIndex.put(content.trim(), cur);
					}
				}
			}
		}
		
		logger.debug("��ȡ���� baserow :" + baserow);
		logger.debug("��ȡ���� baseCol :" + baseCol);
		
		if(baserow == rowNum ){
			return null;
		}
		
		Set<String> tableSet = new HashSet<String>(); // ���ڶ� tableList����
		for (int i=baserow+1; i<rowNum; i++) {
			TableOdsInfo tbInfo = new TableOdsInfo();
			Row row = readsheet.getRow(i);
			if(row.getLastCellNum() == 0 ) {
				continue;
			}
			String tableName = getCellString(row, baseCol + colIndex.get("Դ������"));  // Դ������
			if (tableSet.add(tableName) == false || tableName == null || "".equals(tableName)) {
				logger.warn("����Ϊ�ջ��Ѵ���,����:[" + tableName + "]");
				continue;
			}
			logger.debug("��ǰ������Ϊ:" + i);
//			logger.debug("��ǰ������Ϊ:" + row.getCell(seqCol) );
			
			tbInfo.setSysCode    (getCellString(row, baseCol + colIndex.get("Դϵͳ����")));     // Դϵͳ����
			tbInfo.setModName    (getCellString(row, baseCol + colIndex.get("ģ��")));           // ģ��
			tbInfo.setSeqNo      (getCellString(row, baseCol + colIndex.get("���")));           // ���
			tbInfo.setTableEnName(tableName);
			tbInfo.setTableChName(getCellString(row, baseCol + colIndex.get("������ע��")));     // ������ע��
			tbInfo.setIsInOds    (getCellString(row, baseCol + colIndex.get("�Ƿ����")));       // �Ƿ����
			tbInfo.setIsNeedStd  (getCellString(row, baseCol + colIndex.get("�Ƿ���Ҫ��׼��"))); // �Ƿ���Ҫ��׼��
			tbInfo.setTtableName (getCellString(row, baseCol + colIndex.get("��������")));     // ��������
			tbInfo.setOtableName (getCellString(row, baseCol + colIndex.get("ϵͳ��¼�����"))); // ϵͳ��¼�����
			tbInfo.setTableType  (getCellString(row, baseCol + colIndex.get("������")));         // ������
			tbInfo.setVersion    (getCellString(row, baseCol + colIndex.get("�汾")));           // �汾
			tbInfo.setAlterTime  (getCellString(row, baseCol + colIndex.get("���ʱ��")));       // ���ʱ��
			
			tableList.add(tbInfo);
		}
		
		logger.info("�����嵥  ��� ����Ϣ�б�  ���, Sheet Name: " + sheetName);
		return tableList;
		
	}
	
	
	/**
	 * ��ȡ�� sheet �б��嵥��Ϣ 
	 * @param readsheet
	 * @return
	 */
	public static List<ColumnOdsInfo> readSheetAllColumnInfo(Sheet readsheet) {
		logger.info("��ȡ Sheet [" + readsheet.getSheetName() + "] �������ֶ���Ϣ ��ʼ");
		List<ColumnOdsInfo> allColumnList = new ArrayList<ColumnOdsInfo>();
		allColumnList.clear();
		int baserow = -1;
		int baseCol = -1;
		int rowNum = readsheet.getLastRowNum();
		Map<String, Integer> colIndex = new HashMap<String, Integer>();
		for (int i = 0; i < rowNum && baserow == -1; i++) {
			logger.debug("��ǰ��ȡ��: " + i);
			Row row = readsheet.getRow(i);
			int colNum = row.getLastCellNum();
			for (int j = 0; j < colNum && baserow == -1; j++) {
				String content = getCellString(row, j);
				if (content.contains("Դϵͳ")) {
					baserow = i;
					baseCol = j;
				}
				colIndex.clear();
				for(int cur=j; cur<colNum; cur++){
					content = getCellString(row, cur);
					if(content != null && !"".equals(content)){
						colIndex.put(content.trim(), cur);
					}
				}
			}
		}

		logger.debug("��ȡ���� baserow :" + baserow);
		logger.debug("��ȡ���� baseCol :" + baseCol);

		if (baserow == rowNum) {
			return null;
		}
		for (int i=1; i < rowNum - baserow; i++) {
			logger.debug("��ȡ Sheet [" + readsheet.getSheetName() + "] �� " + i + "��");
			Row row = readsheet.getRow(baserow + i);
			if(row.getLastCellNum() == 0 ){
				continue;
			}
			
			ColumnOdsInfo columnInfo = new ColumnOdsInfo();
			columnInfo.setSyscode(getCellString(row, baseCol + colIndex.get("Դϵͳ") )); // Դϵͳ
			columnInfo.setTableEnName(getCellString(row, baseCol + colIndex.get("Դ����"))); // Դ����
			columnInfo.setTableChName(getCellString(row, baseCol + colIndex.get("Դϵͳ"))); // Դ��������
			columnInfo.setColumnNo(getCellString(row, baseCol + colIndex.get("Դ��������"))); // �ֶ����
			columnInfo.setColumnEnName(getCellString(row, baseCol + colIndex.get("Դ�ֶ�"))); // Դ�ֶ�
			columnInfo.setColumnChName(getCellString(row, baseCol + colIndex.get("Դ�ֶ�������"))); // Դ�ֶ�������
			columnInfo.setTargetType(getCellString(row, baseCol + colIndex.get("Ŀ����������"))); // Ŀ����������
			columnInfo.setPristineType(getCellString(row, baseCol + colIndex.get("ԭ��������"))); // ԭ��������
			columnInfo.setIsInOds(getCellString(row, baseCol + colIndex.get("�Ƿ����"))); // �Ƿ����
			columnInfo.setIsStd(getCellString(row, baseCol + colIndex.get("�Ƿ���Ҫ��׼��"))); // �Ƿ���Ҫ��׼��
			columnInfo.setStdNo(getCellString(row, baseCol + colIndex.get("���ݱ�׼���"))); // ���ݱ�׼���
			columnInfo.setStdName(getCellString(row, baseCol + colIndex.get("���ݱ�׼��������"))); // ���ݱ�׼��������
			columnInfo.setIsNull(getCellString(row, baseCol + colIndex.get("��ֵ"))); // ��ֵ
			columnInfo.setDefaultValue(getCellString(row, baseCol + colIndex.get("Ĭ��ֵ"))); // Ĭ��ֵ
			columnInfo.setConstraint(getCellString(row, baseCol + colIndex.get("CONSTRAINT"))); // CONSTRAINT
			columnInfo.setIsUI(getCellString(row, baseCol + colIndex.get("UI"))); // UI
			columnInfo.setIsIDX(getCellString(row, baseCol + colIndex.get("IDX"))); // IDX
			columnInfo.setDescribe(getCellString(row, baseCol + colIndex.get("����˵��"))); // ����˵��

			allColumnList.add(columnInfo);
		}
		
		logger.info("��ȡ Sheet [" + readsheet.getSheetName() + "] �������ֶ���Ϣ ���");
		return allColumnList;
	}
	
	/**
	 * ��ȡ Դ�ֶ� sheet �� �ֶζ��� ���� 
	 * @param workbook
	 * @param sheetName
	 * @param tableList
	 * @return
	 */
	public static List<TableOdsInfo> readTableColumnInfo (Workbook workbook, String sheetName, List<TableOdsInfo> tableList) {
		if(logger.isInfoEnabled()){
			logger.info("�����嵥  ��� Դ�ֶ�����  ��ʼ, Workbook: " + workbook +  " Sheet Name: " + sheetName);
		}
		Sheet readsheet = workbook.getSheet(sheetName);
		List<TableOdsInfo>  tables = readTableColumnInfo(readsheet,  tableList);
		if (logger.isInfoEnabled()) {
			logger.info("�����嵥  ��� Դ�ֶ�����   ���, Workbook: " + workbook +  " Sheet Name: " + sheetName);
		}
		return tables;
	}
	
	public static List<TableOdsInfo> readTableColumnInfo(Sheet readsheet, List<TableOdsInfo> tableList) {
		if (logger.isInfoEnabled()) {
			logger.info("�����嵥  ��� Դ�ֶ�����  ��ʼ, Sheet Name: " + readsheet.getSheetName());
		}
		// ��ȡsheet�м�¼�����ֶ���Ϣ
		List<ColumnOdsInfo> allColumnList = readSheetAllColumnInfo(readsheet);
		
		Set<String> tableSet = new HashSet<String>(); // ���ڶ� tableList����
		int tabCnt = 0 ;
		for (TableOdsInfo tabInfo : tableList) {
			tabCnt ++ ;
			String tableName = tabInfo.getTableEnName();
			List<ColumnOdsInfo> columnList = new ArrayList<ColumnOdsInfo>();
			if (tableSet.add(tableName) == false || tableName == null || "".equals(tableName)) {
				logger.warn("����Ϊ�ջ��Ѵ���,����:[" + tableName + "]");
//				tableList.remove(tabInfo);
				continue;
			} else {
				// ��ѡ columnInfo
				int i = 0;
				for (ColumnOdsInfo columnInfo : allColumnList) {
					String colTableEnName = columnInfo.getTableEnName();
					if (tableName.equals(colTableEnName)) {
						i++;
						columnList.add(columnInfo);
						logger.debug("[" + tabCnt + "]" + tableName + " ����ֶ���Ϣ [" + i + "] " + columnInfo.getColumnEnName() + " " + columnInfo.getColumnChName() );
					}
				}
			}
			tabInfo.setColumns(columnList);
		}
		if (logger.isInfoEnabled()) {
			logger.info("�����嵥  ��� Դ�ֶ�����  ���, Sheet Name: " + readsheet.getSheetName());
		}
		return tableList;
	}
	
/**
 * 
 * @param stdExcelName  ����ļ���
 * @param tableList     ���嵥
 * @param DM  T�� O��
 */
public static void cetartDoc(String stdExcelName, List<TableOdsInfo> tableList, String DM){
		
		Workbook workbook = ToStdExcelUtil.getWorkbook(stdExcelName);
		String tableListSheetName = "����ͼ)�嵥" ;
		// ���� tableLisSheet  
		Sheet tableListSheet = null;
		int totRowNo = 0 ;
		tableListSheet = workbook.getSheet(tableListSheetName);
		if (tableListSheet != null){
			int index = workbook.getSheetIndex(tableListSheetName);
			workbook.removeSheetAt(index);
		}
		tableListSheet = workbook.createSheet(tableListSheetName);
		
		
		totRowNo = 0;  // tableList���α� 
		int curColumn = 0 ;
		int line = 0 ;
		CellStyle headStyle = workbook.createCellStyle(); //��ͷ��ʽ
		CellStyle contStyle = workbook.createCellStyle(); //������ʽ
		Font headFontStyle = workbook.createFont();;  // ��ͷ������ʽ����
		Font contFontStyle = workbook.createFont();;  // ����������ʽ����
		
		// ������ʽ 
		//contStyle.setAlignment(HorizontalAlignment.CENTER);   //����
		//�������ұ߿�
		contStyle.setBorderBottom(BorderStyle.THIN);   
		contStyle.setBorderLeft(BorderStyle.THIN);  
		contStyle.setBorderRight(BorderStyle.THIN);  
		contStyle.setBorderTop(BorderStyle.THIN); 
		
        //fontStyle.setBold(true); // �Ӵ�  
		contFontStyle.setFontName("����"); // ����  
		contFontStyle.setFontHeightInPoints((short) 11); // ��С  
        // ��������ʽ��ӵ���Ԫ����ʽ��   
        contStyle.setFont(contFontStyle);  
        
      //�������ұ߿�
        headStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setBorderBottom(BorderStyle.THIN);   
        headStyle.setBorderLeft(BorderStyle.THIN);  
        headStyle.setBorderRight(BorderStyle.THIN);  
        headStyle.setBorderTop(BorderStyle.THIN); 
        headStyle.setAlignment(HorizontalAlignment.CENTER); //����
        headFontStyle.setBold(true); // �Ӵ�  
        headFontStyle.setFontName("����"); // ����  
        headFontStyle.setFontHeightInPoints((short) 11); // ��С  
        headStyle.setFont(headFontStyle);  
        
		createTableSheetHead(tableListSheet, headStyle, 0 , 0) ;
		
		for(TableOdsInfo tableInfo : tableList) {
			String tableName = tableInfo.getTableEnName();
			String ODStableName = null;
			if("T".equals(DM)){
				ODStableName = tableInfo.getTtableName();
			}else if("O".equals(DM)){
				ODStableName = tableInfo.getOtableName();
			}
			
			String isInOds    = tableInfo.getIsInOds();     // �Ƿ���� N
			String tableType  = tableInfo.getTableType().trim();   // ������ 3-�Ǽǲ���
			List<ColumnOdsInfo> columns =  tableInfo.getColumns();
			if ("Y".equals(isInOds)) {  // ���
				// ��ӵ����嵥
				totRowNo ++ ;
				// ���	ģ�� 	��Ӣ������	����������	��������	��/��ͼ	�汾	 ���ʱ��
				
				String[] contents = {totRowNo+"", tableInfo.getModName(), ODStableName, tableInfo.getTableChName(), 
						"", "T", tableInfo.getVersion(), tableInfo.getAlterTime() };
				
				createOneTableSheetLine(tableListSheet, contents, contStyle, totRowNo, 0) ;
				
				// ���� tablesheet 
				Sheet  tablesheet = workbook.createSheet(ODStableName);
				curColumn = 0 ;
				
				// ���ɱ�ͷ  
				line = createTableListSheetHead(tablesheet, headStyle, curColumn, 0, tableListSheetName);
				curColumn = curColumn + line ;
				
				//���ɱ����� 
				line = createTableSheetConnect(tablesheet, columns, contStyle, curColumn , 0);
				curColumn = curColumn + line ;
				
				//����׷���ֶ�
				if("T".equals(DM)){
					line = tableSheetAddColumnT(tablesheet, tableInfo, contStyle, curColumn , 0);
				}else if("O".equals(DM)){
					line = tableSheetAddColumnO(tablesheet, tableInfo, contStyle, curColumn , 0);
				}
				curColumn = curColumn + line ;
				
				//���ɱ�β 
				createTableSheetFoot(tablesheet, headStyle, contStyle, curColumn, 0);
				//�Զ��п�
				for(int i=0; i<=tablesheet.getRow(0).getLastCellNum(); i++){
					tablesheet.autoSizeColumn(i);
				}
			}
			//�Զ��п�
			for(int i=0; i<=tableListSheet.getRow(0).getLastCellNum(); i++){
				tableListSheet.autoSizeColumn(i);
			}
			//��ӳ�����
			CreationHelper createHelper = workbook.getCreationHelper();
			for(int i=1; i<=tableListSheet.getLastRowNum(); i++){
				Row row = tableListSheet.getRow(i);
				Cell cell = row.getCell(2);
				if(cell != null){
					Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
					hyperlink.setAddress("#'" + cell.toString() + "'!A1");  
					cell.setHyperlink(hyperlink); 
					
					CellStyle linkStyle = workbook.createCellStyle();
					linkStyle.cloneStyleFrom(contStyle);
					Font cellFont= workbook.createFont();
					cellFont.setUnderline((byte) 1);
					cellFont.setColor(IndexedColors.BLUE.getIndex());
					linkStyle.setFont(cellFont);
					cell.setCellStyle(linkStyle);
				}
				tableListSheet.autoSizeColumn(i);
			}
		}
		
		writeWorkbookToFile(workbook, stdExcelName);
	}
	

	/**
	 * ���� T �� �ĵ� 
	 */
	public static void cetartDocT(String stdExcelName, List<TableOdsInfo> tableList){
		cetartDoc(stdExcelName, tableList, "T");
	}
	
	/**
	 * ���� O �� �ĵ�
	 */
	public static void cetartDocO(String stdExcelName, List<TableOdsInfo> tableList){
		cetartDoc(stdExcelName, tableList, "O");
	}
	
		
	/**
	 * ��ȡ��Ԫ������, �����Ԫ��Ϊnull, ���ؿմ�
	 * 
	 * @param row
	 * @param Col
	 * @return
	 */
	private static String getCellString(Row row, int Col) {
		String cellStringValue = null;
		Cell cell = row.getCell(Col);
		if (row != null && cell != null) {
			cell.setCellType(CellType.STRING);
			cellStringValue = cell.getStringCellValue();
			logger.debug(row.toString() + "[" + Col + "]:" + cellStringValue + " Type:" + CellType.STRING);
		} else {
			cellStringValue = "";
			logger.debug(row.toString() + "[" + Col + "]:" + cell + cellStringValue );
		}
		return cellStringValue;
	}
	
	/**
	 * ���� TableSheet ��ͷ 
	 * @param sheet
	 * @param totRowNo
	 * @param j
	 */
	public static int createTableSheetHead(Sheet sheet, CellStyle sourceStyle, int curRowNo, int curColNo) {
		if (curRowNo < 0 || curColNo < 0 ){
			curRowNo = 0;
			curColNo = 0;
		}
		int oldcurRowNo = curRowNo ;
		
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		
		String[] contents = {"���", "ģ��", "��Ӣ������", "����������", "��������", "��/��ͼ", "�汾", "���ʱ��"};
		curRowNo = createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
		curRowNo ++ ;
		for(int i=0; i<contents.length; i++){
			sheet.autoSizeColumn(i);
		}
		return curRowNo - oldcurRowNo;
	}
	 
	/**
	 * sheet ������ֶ����� 
	 * @param sheet
	 * @param curRowNo
	 * @param curColNo
	 * @return
	 */
	public static int createTableSheetConnect(Sheet sheet, List<ColumnOdsInfo> columns, CellStyle sourceStyle, int curRowNo , int curColNo){
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		int oldcurRowNo = curRowNo ;
		for (ColumnOdsInfo column : columns) {
			// �ֶ�Ӣ����	�ֶ�������	�ֶ����ͣ������ȣ�	�Ƿ�����Ϊ��	����	���/����	ȱʡֵ	�ֶ�˵��	���ݱ�׼���	���ݱ�׼��������	�������	�������	��ע

			List<String> contents = new ArrayList<String>();
			contents.add(column.getColumnEnName());   // �ֶ�Ӣ����	
			contents.add(column.getColumnChName());   // �ֶ�������	
			contents.add(column.getTargetType());     // �ֶ����ͣ������ȣ�	
			contents.add(column.getIsNull());         // �Ƿ�����Ϊ��	     
			contents.add(column.getConstraint());     // ����	
			contents.add("");                         //���/����	
			contents.add(column.getDefaultValue());   // ȱʡֵ	
			contents.add(column.getDescribe());   // �ֶ�˵��	  
			contents.add(column.getStdNo());      // ���ݱ�׼���	
			contents.add(column.getStdName());    // ���ݱ�׼��������	 
			contents.add("");   // �������	 
			contents.add("");   // �������	 
			contents.add("");   // ��ע

			createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
			curRowNo ++ ;
		}
		
		return curRowNo - oldcurRowNo;
		
	}
	
	/**
	 * ׷��T���ĸ����ֶ�
	 * @param sheet
	 * @param tableInfo
	 * @param curRowNo
	 */
	private static int tableSheetAddColumnT(Sheet sheet, TableOdsInfo tableInfo, CellStyle sourceStyle, int curRowNo, int curColNo){
		
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		String tableName  = tableInfo.getTableEnName();
		String isInOds    = tableInfo.getIsInOds();            // �Ƿ���� N
		String tableType  = tableInfo.getTableType().trim();   // ������ 3-�Ǽǲ���
		int oldcurRowNo   = curRowNo ;
		if ("Y".equals(isInOds)) {
			if("1-״̬��".equals(tableType) || "2-״̬��(��ɾ��)".equals(tableType)) {
				//DATE_ID, CYCLE_ID, HASH_VAL
				String[] contents1 = {"DATE_ID", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				
				String[] contents2 = {"CYCLE_ID", "���ڱ�־", "NUMBER", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
				
				String[] contents3 = {"HASH_VAL", "��ϣֵ", "RAW(2000)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents3, style, curRowNo, curColNo);
				curRowNo ++ ;
				
			}
			else if ("3-�Ǽǲ���".equals(tableType) || "4-�¼��� ".equals(tableType) || "5-�¼��޸�".equals(tableType)) {
				//DATE_ID, CyCLE_ID, HASH_VAL
				String[] contents1 = {"DATE_ID", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				
				String[] contents2 = {"CYCLE_ID", "���ڱ�־", "NUMBER", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
			} else {
				String[] contents = {"ODS�ĵ��� ������δ��д", "�貹��", "", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
				logger.error(tableName + " ODS�ĵ��� ������δ��д ");
			}
		}
		return curRowNo - oldcurRowNo ;
	}
	/**
	 * ׷��O���ĸ����ֶ�
	 * @param sheet
	 * @param tableInfo
	 * @param curRowNo
	 */
	private static int tableSheetAddColumnO(Sheet sheet, TableOdsInfo tableInfo, CellStyle sourceStyle, int curRowNo, int curColNo){
		
		String isInOds    = tableInfo.getIsInOds();     // �Ƿ���� N
		String tableType  = tableInfo.getTableType().trim();   // ������ 3-�Ǽǲ���
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		
		int oldcurRowNo = curRowNo ;
		if ("Y".equals(isInOds)) {
			if("1-״̬��".equals(tableType)) {
				//START_DT, END_DT, HASH_VAL
				String[] contents1 = {"START_DT", "��ʼ����", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents2 = {"END_DT", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents3 = {"HASH_VAL", "HASHֵ", "RAW(16)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents3, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("2-״̬��(��ɾ��)".equals(tableType)) {
				// DATE_ID, CYCLE_ID, DEL_FLG, HASH_VAL
				
				String[] contents1 = {"START_DT", "��ʼ����", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents2 = {"END_DT", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents3 = {"DEL_FLG", "ɾ����־", "VARCHAR2(1)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents3, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents4 = {"HASH_VAL", "HASHֵ", "RAW(16)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents4, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("3-�Ǽǲ���".equals(tableType)) {
				// DATE_ID
				String[] contents1 = {"DATE_ID", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("4-�¼���".equals(tableType)) {
				// DATE_ID
				String[] contents1 = {"DATE_ID", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("5-�¼��޸�".equals(tableType)) {
				// DATE_ID, UPDATE_DT
				String[] contents1 = {"DATE_ID", "��������", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents2 = {"UPDATE_DT", "�����޸�ʱ��", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
		}
		return curRowNo - oldcurRowNo ;
	}
	
	public static int createTableSheetFoot(Sheet sheet, CellStyle sourceHeadStyle, CellStyle sourceContStyle, int curRowNo, int curColNo) {
		int colLength = 0 ;
		CellRangeAddress cra = null ;
		
		Workbook workbook = sheet.getWorkbook();
		CellStyle headStyle = workbook.createCellStyle();
		CellStyle contStyle = workbook.createCellStyle();
		headStyle.cloneStyleFrom(sourceHeadStyle);
		contStyle.cloneStyleFrom(sourceContStyle);
		
		Row row = null ; 
		Cell cell = null;
		int oldcurRowNo = curRowNo;
		int oldcurColNo = curColNo;
		 
		row = sheet.createRow(curRowNo);
		cell = row.createCell(curColNo);
		cell.setCellValue("����");
		cell.setCellStyle(headStyle);
		colLength = 1;
		cra = new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength); // ��ʼ��, ��ֹ��, ��ʼ��, ��ֹ��
        sheet.addMergedRegion(cra); 
        curColNo = curColNo + colLength + 1;
        
        cell = row.createCell(curColNo);
		cell.setCellValue("�����ֶ�");
		cell.setCellStyle(headStyle);
		colLength = 4;
        cra =new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength); // ��ʼ��, ��ֹ��, ��ʼ��, ��ֹ��  
        sheet.addMergedRegion(cra); 
        curColNo = curColNo + colLength + 1;
        
		cell = row.createCell(curColNo);
		cell.setCellValue("����˵��");
		cell.setCellStyle(headStyle);
		colLength = 5;
		cra = new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength);
		sheet.addMergedRegion(cra);
		curColNo = curColNo + colLength + 1;
		
		row = sheet.getRow(curRowNo);
		for(int j=oldcurColNo; j<curColNo-oldcurColNo; j++){
			cell = row.getCell(j);
			if(cell == null){
				cell = row.createCell(j);
				logger.debug("��ǰ�ֶ�����:"  + j + ":" + cell.toString());
			}
			cell.setCellStyle(headStyle);
		}
		
        curRowNo++;
        
		for (int i = 1; i <= 2; i++, curRowNo++) {
			curColNo = oldcurColNo;
//			row = sheet.createRow(curRowNo);
//			cell = row.createCell(curColNo);
			colLength = 1;
			cra = new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength);
			sheet.addMergedRegion(cra);
			curColNo = curColNo + colLength + 1;

//			cell = row.createCell(curColNo);
			colLength = 4;
			cra = new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength);
			sheet.addMergedRegion(cra);
			curColNo = curColNo + colLength + 1;

//			cell = row.createCell(curColNo);
			colLength = 5;
			cra = new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength);
			sheet.addMergedRegion(cra);
			curColNo = curColNo + colLength + 1;
			
			row = sheet.createRow(curRowNo);
			for(int j=oldcurColNo; j < curColNo-oldcurColNo; j++){
				cell = row.createCell(j);
				if(cell != null){
					logger.debug("��ǰ�ֶ�����:"  + j + ":" + cell.toString());
					cell.setCellStyle(contStyle);
				}
			}
		}
       
        
		return curRowNo - oldcurRowNo;
	}
	 
	/**
	 * ���� TableListSheet ��ͷ 
	 * @param sheet
	 * @param curRowNo
	 * @param curColNo
	 * @param backSheetName
	 */
	public static int createTableListSheetHead(Sheet sheet, CellStyle sourceStyle, int curRowNo, int curColNo, String backSheetName) {
		if (curColNo < 0 || curRowNo < 0) {
			curColNo = 0;
			curRowNo = 0;
		}
			
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		
		String[] contents = {"�ֶ�Ӣ����", "�ֶ�������", "�ֶ�����(������)", "�Ƿ�����Ϊ��", "����", "���/����", 
				"ȱʡֵ", "�ֶ�˵��", "���ݱ�׼���", "���ݱ�׼��������", "�������", "�������", "��ע", "����"};
		createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
		Cell cell = sheet.getRow(curRowNo).getCell(contents.length - 1);
		if(null != cell){
			CreationHelper createHelper = workbook.getCreationHelper();
			Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
			hyperlink.setAddress("#'" + backSheetName + "'!A1");  
			logger.debug(sheet.getSheetName() + "��������: #" + backSheetName + "!A1");
			cell.setHyperlink(hyperlink); 
			CellStyle linkStyle = workbook.createCellStyle();
//			linkStyle.cloneStyleFrom(cell.getCellStyle());
			Font cellFont= workbook.createFont();
			cellFont.setUnderline((byte) 1);
			cellFont.setColor(IndexedColors.BLUE.getIndex());
			linkStyle.setFont(cellFont);
			cell.setCellStyle(linkStyle);
		}
		return  1 ;
	}

	
	
	
	/**
	 * ���� contents ����һ������ 
	 * @param sheet
	 * @param contents
	 * @param curRowNo
	 * @param curColNo
	 * @return
	 */
	
	private static int createOneTableSheetLine(Sheet sheet, List<String> contents, CellStyle sourceStyle, int curRowNo, int curColNo){
		if (sheet == null ){
			return -1;
		}
		
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		Cell cell = null;
		Row row = sheet.createRow(curRowNo);
		int j = curColNo;
		for(String con: contents){
			cell = row.createCell(j);
			cell.setCellStyle(style);
			cell.setCellValue(con);
			j ++ ;
		}
		return j - curColNo;
	}
	
	private static int createOneTableSheetLine(Sheet sheet, String[] contents, CellStyle sourceStyle, int curRowNo, int curColNo){
		if (sheet == null ){
			return -1;
		}
		
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		Cell cell = null;
		Row row = sheet.createRow(curRowNo);
		int j = curColNo;
		for(String con: contents){
			cell = row.createCell(j);
			cell.setCellStyle(style);
			cell.setCellValue(con);
			j ++ ;
		}
		return j - curColNo;
	}

//	private int createOneTableSheetLine(Sheet sheet, ColumnStdInfo columnInfo, int curRowNo, int curColNo){
//		if (sheet == null ){
//			return -1;
//		}
//		int oldcurRowNo  =  curRowNo ;
//		Workbook workbook = new XSSFWorkbook();
//		CellStyle style = workbook.createCellStyle();
//		Cell cell = null;
//		Row row = sheet.createRow(curRowNo);
//		int j = curColNo;
//		// ϵͳ  
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getSysCode());
//		j++;
//		// ��Ӣ����   
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getTableEnName());
//		j++;
//		// ��������
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getTableEnName());
//		j++;              
//		// �ֶ�Ӣ����	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getColumnEnName());
//		j++;
//		// �ֶ�������	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getColumnChName());
//		j++;
//		// �ֶ����ͣ������ȣ�	 
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getColumnType());
//		j++;
//		// �Ƿ�����Ϊ��	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getIsNull());
//		j++;
//		// ����	���/����	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getIsPk());
//		j++;
//		// ȱʡֵ	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getDefaultValue());
//		j++;
//		// �ֶ�˵��	 
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getSysCode());
//		j++;
//		// ���ݱ�׼���	 
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getDesc());
//		j++;
//		// ���ݱ�׼��������	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getStdNo());
//		j++;
//		// �������	  
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getStdChName());
//		j++;
//		// �������	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getAlterDesc());
//		j++;
//		// ��ע    
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getRemark());
//		j++;
//		// ������    
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getIndexName());
//		j++;
//		return curRowNo - oldcurRowNo + 1;
//	}
	
	/**
	 * ���� ����������·�� ��ȡ ָ���� properties �����ļ�, ������ ��Ӧ�� properties 
	 * @author ding_kaiye
	 * @param string    �����ļ�����(��·��)
	 * @return Properties ����
	 * @throws IOException 
	 */
	public static  Properties loadConfigPropertiesFile(String configFile) throws IOException {
		String webRootPath = "." + File.separator + "config" + File.separator ;  //����� ./config/ ��
		Properties properties = loadPropertiesFile(webRootPath , configFile);
		return properties;
	}
	
	public static  Properties loadPropertiesFile(String path, String configFile) throws IOException {
		if ( !path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		if (null == configFile || configFile.equals("")) {
			throw new IllegalArgumentException("Properties file path can not be null" + configFile);
		}
		Properties properties = loadPropertiesFile(path + configFile);
		return properties;
	}
	
	private static  Properties loadPropertiesFile(String configFile) throws IOException {
		if (null == configFile || configFile.equals("")) {
			//logger.error("Properties file path can not be null" + configFile);
			throw new IllegalArgumentException("Properties file path can not be null" + configFile);
		}
		
		InputStream inputStream = null;
		Properties properties = null;
		try {
			File file = new File(configFile);
			inputStream = new FileInputStream(file);
			properties = new Properties();
			properties.load(inputStream);
		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	
}
