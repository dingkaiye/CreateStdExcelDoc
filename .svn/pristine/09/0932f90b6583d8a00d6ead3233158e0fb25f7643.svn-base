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
 * 读取ODS缓冲层及系统记录层文档,生成模板数据结构
 * @author ding_kaiye
 *
 */
public class ToStdExcelUtil {
	
	private static Logger logger = LogManager.getLogger("Execl"); 
//	private static String configFile = "odsexecl.properties";
	/**
	 * 获取 Workbook 
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
			logger.error(filepath + " 文件不存在,创建文件 ");
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
	 * 将 workbook 写入文件中
	 * @param resultWorkbook
	 * @param outFile
	 */
	public static boolean writeWorkbookToFile(Workbook workbook, String outFile) {
		logger.info("将 workbook 写入文件 " + outFile  + "  开始 ");
		FileOutputStream fOut =  null;
		try {
			File file = new File(outFile);
			
			fOut = new FileOutputStream(outFile);
			workbook.write(fOut);
			fOut.flush();  
			fOut.close();  // 操作结束，关闭文件  
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
			return false;
		} catch (IOException e) {
			logger.error("IOException", e);
			return false;
		}
		logger.info("将 workbook 写入文件 " + outFile  + "  完成");
		return true;

	}
	
	/**
	 * 读取源表sheet, 生成表清单
	 * @param workbook
	 * @param sheetName
	 * @return List<TableOdsInfo> tableList
	 * @throws IOException 
	 */
	public static List<TableOdsInfo> readTableList (Workbook  workbook, String sheetName )  {
		logger.info("读表清单  获得 表信息列表  开始, Sheet Name: " + sheetName);
//		try {
//			Properties properties =  loadConfigPropertiesFile (configFile);
//		} catch (IOException e) {
//			logger.error(configFile + "文件加载失败, 请检查文件是否存在", e);
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
			logger.debug("当前读取行: " + i);
			Row row = readsheet.getRow(i);
			int colNum = row.getLastCellNum();
			for(int j=0; j<colNum && baserow == -1 ; j++ ){
//				Cell cell = row.getCell(j);
				String content = getCellString(row, j);
				if(content.contains("源系统名称")){
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
		
		logger.debug("获取到的 baserow :" + baserow);
		logger.debug("获取到的 baseCol :" + baseCol);
		
		if(baserow == rowNum ){
			return null;
		}
		
		Set<String> tableSet = new HashSet<String>(); // 用于对 tableList判重
		for (int i=baserow+1; i<rowNum; i++) {
			TableOdsInfo tbInfo = new TableOdsInfo();
			Row row = readsheet.getRow(i);
			if(row.getLastCellNum() == 0 ) {
				continue;
			}
			String tableName = getCellString(row, baseCol + colIndex.get("源表名称"));  // 源表名称
			if (tableSet.add(tableName) == false || tableName == null || "".equals(tableName)) {
				logger.warn("表名为空或已存在,跳过:[" + tableName + "]");
				continue;
			}
			logger.debug("当前处理行为:" + i);
//			logger.debug("当前处理行为:" + row.getCell(seqCol) );
			
			tbInfo.setSysCode    (getCellString(row, baseCol + colIndex.get("源系统名称")));     // 源系统名称
			tbInfo.setModName    (getCellString(row, baseCol + colIndex.get("模块")));           // 模块
			tbInfo.setSeqNo      (getCellString(row, baseCol + colIndex.get("编号")));           // 编号
			tbInfo.setTableEnName(tableName);
			tbInfo.setTableChName(getCellString(row, baseCol + colIndex.get("表中文注解")));     // 表中文注解
			tbInfo.setIsInOds    (getCellString(row, baseCol + colIndex.get("是否入库")));       // 是否入库
			tbInfo.setIsNeedStd  (getCellString(row, baseCol + colIndex.get("是否需要标准化"))); // 是否需要标准化
			tbInfo.setTtableName (getCellString(row, baseCol + colIndex.get("缓冲层表名")));     // 缓冲层表名
			tbInfo.setOtableName (getCellString(row, baseCol + colIndex.get("系统记录层表名"))); // 系统记录层表名
			tbInfo.setTableType  (getCellString(row, baseCol + colIndex.get("表类型")));         // 表类型
			tbInfo.setVersion    (getCellString(row, baseCol + colIndex.get("版本")));           // 版本
			tbInfo.setAlterTime  (getCellString(row, baseCol + colIndex.get("变更时间")));       // 变更时间
			
			tableList.add(tbInfo);
		}
		
		logger.info("读表清单  获得 表信息列表  完成, Sheet Name: " + sheetName);
		return tableList;
		
	}
	
	
	/**
	 * 读取将 sheet 中表清单信息 
	 * @param readsheet
	 * @return
	 */
	public static List<ColumnOdsInfo> readSheetAllColumnInfo(Sheet readsheet) {
		logger.info("读取 Sheet [" + readsheet.getSheetName() + "] 中所有字段信息 开始");
		List<ColumnOdsInfo> allColumnList = new ArrayList<ColumnOdsInfo>();
		allColumnList.clear();
		int baserow = -1;
		int baseCol = -1;
		int rowNum = readsheet.getLastRowNum();
		Map<String, Integer> colIndex = new HashMap<String, Integer>();
		for (int i = 0; i < rowNum && baserow == -1; i++) {
			logger.debug("当前读取行: " + i);
			Row row = readsheet.getRow(i);
			int colNum = row.getLastCellNum();
			for (int j = 0; j < colNum && baserow == -1; j++) {
				String content = getCellString(row, j);
				if (content.contains("源系统")) {
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

		logger.debug("获取到的 baserow :" + baserow);
		logger.debug("获取到的 baseCol :" + baseCol);

		if (baserow == rowNum) {
			return null;
		}
		for (int i=1; i < rowNum - baserow; i++) {
			logger.debug("读取 Sheet [" + readsheet.getSheetName() + "] 第 " + i + "行");
			Row row = readsheet.getRow(baserow + i);
			if(row.getLastCellNum() == 0 ){
				continue;
			}
			
			ColumnOdsInfo columnInfo = new ColumnOdsInfo();
			columnInfo.setSyscode(getCellString(row, baseCol + colIndex.get("源系统") )); // 源系统
			columnInfo.setTableEnName(getCellString(row, baseCol + colIndex.get("源表名"))); // 源表名
			columnInfo.setTableChName(getCellString(row, baseCol + colIndex.get("源系统"))); // 源表中文名
			columnInfo.setColumnNo(getCellString(row, baseCol + colIndex.get("源表中文名"))); // 字段序号
			columnInfo.setColumnEnName(getCellString(row, baseCol + colIndex.get("源字段"))); // 源字段
			columnInfo.setColumnChName(getCellString(row, baseCol + colIndex.get("源字段中文名"))); // 源字段中文名
			columnInfo.setTargetType(getCellString(row, baseCol + colIndex.get("目标数据类型"))); // 目标数据类型
			columnInfo.setPristineType(getCellString(row, baseCol + colIndex.get("原数据类型"))); // 原数据类型
			columnInfo.setIsInOds(getCellString(row, baseCol + colIndex.get("是否入库"))); // 是否入库
			columnInfo.setIsStd(getCellString(row, baseCol + colIndex.get("是否需要标准化"))); // 是否需要标准化
			columnInfo.setStdNo(getCellString(row, baseCol + colIndex.get("数据标准编号"))); // 数据标准编号
			columnInfo.setStdName(getCellString(row, baseCol + colIndex.get("数据标准中文名称"))); // 数据标准中文名称
			columnInfo.setIsNull(getCellString(row, baseCol + colIndex.get("空值"))); // 空值
			columnInfo.setDefaultValue(getCellString(row, baseCol + colIndex.get("默认值"))); // 默认值
			columnInfo.setConstraint(getCellString(row, baseCol + colIndex.get("CONSTRAINT"))); // CONSTRAINT
			columnInfo.setIsUI(getCellString(row, baseCol + colIndex.get("UI"))); // UI
			columnInfo.setIsIDX(getCellString(row, baseCol + colIndex.get("IDX"))); // IDX
			columnInfo.setDescribe(getCellString(row, baseCol + colIndex.get("特殊说明"))); // 特殊说明

			allColumnList.add(columnInfo);
		}
		
		logger.info("读取 Sheet [" + readsheet.getSheetName() + "] 中所有字段信息 完成");
		return allColumnList;
	}
	
	/**
	 * 读取 源字段 sheet 中 字段定义 数据 
	 * @param workbook
	 * @param sheetName
	 * @param tableList
	 * @return
	 */
	public static List<TableOdsInfo> readTableColumnInfo (Workbook workbook, String sheetName, List<TableOdsInfo> tableList) {
		if(logger.isInfoEnabled()){
			logger.info("读表清单  获得 源字段数据  开始, Workbook: " + workbook +  " Sheet Name: " + sheetName);
		}
		Sheet readsheet = workbook.getSheet(sheetName);
		List<TableOdsInfo>  tables = readTableColumnInfo(readsheet,  tableList);
		if (logger.isInfoEnabled()) {
			logger.info("读表清单  获得 源字段数据   完成, Workbook: " + workbook +  " Sheet Name: " + sheetName);
		}
		return tables;
	}
	
	public static List<TableOdsInfo> readTableColumnInfo(Sheet readsheet, List<TableOdsInfo> tableList) {
		if (logger.isInfoEnabled()) {
			logger.info("读表清单  获得 源字段数据  开始, Sheet Name: " + readsheet.getSheetName());
		}
		// 获取sheet中记录所有字段信息
		List<ColumnOdsInfo> allColumnList = readSheetAllColumnInfo(readsheet);
		
		Set<String> tableSet = new HashSet<String>(); // 用于对 tableList判重
		int tabCnt = 0 ;
		for (TableOdsInfo tabInfo : tableList) {
			tabCnt ++ ;
			String tableName = tabInfo.getTableEnName();
			List<ColumnOdsInfo> columnList = new ArrayList<ColumnOdsInfo>();
			if (tableSet.add(tableName) == false || tableName == null || "".equals(tableName)) {
				logger.warn("表名为空或已存在,跳过:[" + tableName + "]");
//				tableList.remove(tabInfo);
				continue;
			} else {
				// 挑选 columnInfo
				int i = 0;
				for (ColumnOdsInfo columnInfo : allColumnList) {
					String colTableEnName = columnInfo.getTableEnName();
					if (tableName.equals(colTableEnName)) {
						i++;
						columnList.add(columnInfo);
						logger.debug("[" + tabCnt + "]" + tableName + " 添加字段信息 [" + i + "] " + columnInfo.getColumnEnName() + " " + columnInfo.getColumnChName() );
					}
				}
			}
			tabInfo.setColumns(columnList);
		}
		if (logger.isInfoEnabled()) {
			logger.info("读表清单  获得 源字段数据  完成, Sheet Name: " + readsheet.getSheetName());
		}
		return tableList;
	}
	
/**
 * 
 * @param stdExcelName  输出文件名
 * @param tableList     表清单
 * @param DM  T层 O层
 */
public static void cetartDoc(String stdExcelName, List<TableOdsInfo> tableList, String DM){
		
		Workbook workbook = ToStdExcelUtil.getWorkbook(stdExcelName);
		String tableListSheetName = "表（视图)清单" ;
		// 生成 tableLisSheet  
		Sheet tableListSheet = null;
		int totRowNo = 0 ;
		tableListSheet = workbook.getSheet(tableListSheetName);
		if (tableListSheet != null){
			int index = workbook.getSheetIndex(tableListSheetName);
			workbook.removeSheetAt(index);
		}
		tableListSheet = workbook.createSheet(tableListSheetName);
		
		
		totRowNo = 0;  // tableList的游标 
		int curColumn = 0 ;
		int line = 0 ;
		CellStyle headStyle = workbook.createCellStyle(); //表头样式
		CellStyle contStyle = workbook.createCellStyle(); //内容样式
		Font headFontStyle = workbook.createFont();;  // 表头字体样式定义
		Font contFontStyle = workbook.createFont();;  // 内容字体样式定义
		
		// 定义样式 
		//contStyle.setAlignment(HorizontalAlignment.CENTER);   //居中
		//上下左右边框
		contStyle.setBorderBottom(BorderStyle.THIN);   
		contStyle.setBorderLeft(BorderStyle.THIN);  
		contStyle.setBorderRight(BorderStyle.THIN);  
		contStyle.setBorderTop(BorderStyle.THIN); 
		
        //fontStyle.setBold(true); // 加粗  
		contFontStyle.setFontName("宋体"); // 字体  
		contFontStyle.setFontHeightInPoints((short) 11); // 大小  
        // 将字体样式添加到单元格样式中   
        contStyle.setFont(contFontStyle);  
        
      //上下左右边框
        headStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setBorderBottom(BorderStyle.THIN);   
        headStyle.setBorderLeft(BorderStyle.THIN);  
        headStyle.setBorderRight(BorderStyle.THIN);  
        headStyle.setBorderTop(BorderStyle.THIN); 
        headStyle.setAlignment(HorizontalAlignment.CENTER); //居中
        headFontStyle.setBold(true); // 加粗  
        headFontStyle.setFontName("宋体"); // 字体  
        headFontStyle.setFontHeightInPoints((short) 11); // 大小  
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
			
			String isInOds    = tableInfo.getIsInOds();     // 是否入库 N
			String tableType  = tableInfo.getTableType().trim();   // 表类型 3-登记簿类
			List<ColumnOdsInfo> columns =  tableInfo.getColumns();
			if ("Y".equals(isInOds)) {  // 入库
				// 添加到表清单
				totRowNo ++ ;
				// 序号	模块 	表英文名称	表中文名称	表功能描述	表/视图	版本	 变更时间
				
				String[] contents = {totRowNo+"", tableInfo.getModName(), ODStableName, tableInfo.getTableChName(), 
						"", "T", tableInfo.getVersion(), tableInfo.getAlterTime() };
				
				createOneTableSheetLine(tableListSheet, contents, contStyle, totRowNo, 0) ;
				
				// 创建 tablesheet 
				Sheet  tablesheet = workbook.createSheet(ODStableName);
				curColumn = 0 ;
				
				// 生成表头  
				line = createTableListSheetHead(tablesheet, headStyle, curColumn, 0, tableListSheetName);
				curColumn = curColumn + line ;
				
				//生成表数据 
				line = createTableSheetConnect(tablesheet, columns, contStyle, curColumn , 0);
				curColumn = curColumn + line ;
				
				//生成追加字段
				if("T".equals(DM)){
					line = tableSheetAddColumnT(tablesheet, tableInfo, contStyle, curColumn , 0);
				}else if("O".equals(DM)){
					line = tableSheetAddColumnO(tablesheet, tableInfo, contStyle, curColumn , 0);
				}
				curColumn = curColumn + line ;
				
				//生成表尾 
				createTableSheetFoot(tablesheet, headStyle, contStyle, curColumn, 0);
				//自动列宽
				for(int i=0; i<=tablesheet.getRow(0).getLastCellNum(); i++){
					tablesheet.autoSizeColumn(i);
				}
			}
			//自动列宽
			for(int i=0; i<=tableListSheet.getRow(0).getLastCellNum(); i++){
				tableListSheet.autoSizeColumn(i);
			}
			//添加超链接
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
	 * 生成 T 层 文档 
	 */
	public static void cetartDocT(String stdExcelName, List<TableOdsInfo> tableList){
		cetartDoc(stdExcelName, tableList, "T");
	}
	
	/**
	 * 生成 O 层 文档
	 */
	public static void cetartDocO(String stdExcelName, List<TableOdsInfo> tableList){
		cetartDoc(stdExcelName, tableList, "O");
	}
	
		
	/**
	 * 获取单元格内容, 如果单元格为null, 返回空串
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
	 * 创建 TableSheet 表头 
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
		
		String[] contents = {"序号", "模块", "表英文名称", "表中文名称", "表功能描述", "表/视图", "版本", "变更时间"};
		curRowNo = createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
		curRowNo ++ ;
		for(int i=0; i<contents.length; i++){
			sheet.autoSizeColumn(i);
		}
		return curRowNo - oldcurRowNo;
	}
	 
	/**
	 * sheet 中添加字段数据 
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
			// 字段英文名	字段中文名	字段类型（含长度）	是否允许为空	主键	外键/索引	缺省值	字段说明	数据标准编号	数据标准中文名称	变更日期	变更内容	备注

			List<String> contents = new ArrayList<String>();
			contents.add(column.getColumnEnName());   // 字段英文名	
			contents.add(column.getColumnChName());   // 字段中文名	
			contents.add(column.getTargetType());     // 字段类型（含长度）	
			contents.add(column.getIsNull());         // 是否允许为空	     
			contents.add(column.getConstraint());     // 主键	
			contents.add("");                         //外键/索引	
			contents.add(column.getDefaultValue());   // 缺省值	
			contents.add(column.getDescribe());   // 字段说明	  
			contents.add(column.getStdNo());      // 数据标准编号	
			contents.add(column.getStdName());    // 数据标准中文名称	 
			contents.add("");   // 变更日期	 
			contents.add("");   // 变更内容	 
			contents.add("");   // 备注

			createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
			curRowNo ++ ;
		}
		
		return curRowNo - oldcurRowNo;
		
	}
	
	/**
	 * 追加T层表的附加字段
	 * @param sheet
	 * @param tableInfo
	 * @param curRowNo
	 */
	private static int tableSheetAddColumnT(Sheet sheet, TableOdsInfo tableInfo, CellStyle sourceStyle, int curRowNo, int curColNo){
		
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		String tableName  = tableInfo.getTableEnName();
		String isInOds    = tableInfo.getIsInOds();            // 是否入库 N
		String tableType  = tableInfo.getTableType().trim();   // 表类型 3-登记簿类
		int oldcurRowNo   = curRowNo ;
		if ("Y".equals(isInOds)) {
			if("1-状态类".equals(tableType) || "2-状态类(带删除)".equals(tableType)) {
				//DATE_ID, CYCLE_ID, HASH_VAL
				String[] contents1 = {"DATE_ID", "数据日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				
				String[] contents2 = {"CYCLE_ID", "周期标志", "NUMBER", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
				
				String[] contents3 = {"HASH_VAL", "哈希值", "RAW(2000)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents3, style, curRowNo, curColNo);
				curRowNo ++ ;
				
			}
			else if ("3-登记簿类".equals(tableType) || "4-事件类 ".equals(tableType) || "5-事件修改".equals(tableType)) {
				//DATE_ID, CyCLE_ID, HASH_VAL
				String[] contents1 = {"DATE_ID", "数据日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				
				String[] contents2 = {"CYCLE_ID", "周期标志", "NUMBER", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
			} else {
				String[] contents = {"ODS文档中 表类型未填写", "需补充", "", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
				logger.error(tableName + " ODS文档中 表类型未填写 ");
			}
		}
		return curRowNo - oldcurRowNo ;
	}
	/**
	 * 追加O层表的附加字段
	 * @param sheet
	 * @param tableInfo
	 * @param curRowNo
	 */
	private static int tableSheetAddColumnO(Sheet sheet, TableOdsInfo tableInfo, CellStyle sourceStyle, int curRowNo, int curColNo){
		
		String isInOds    = tableInfo.getIsInOds();     // 是否入库 N
		String tableType  = tableInfo.getTableType().trim();   // 表类型 3-登记簿类
		Workbook workbook = sheet.getWorkbook();
		CellStyle style = workbook.createCellStyle();
		style.cloneStyleFrom(sourceStyle);
		
		int oldcurRowNo = curRowNo ;
		if ("Y".equals(isInOds)) {
			if("1-状态类".equals(tableType)) {
				//START_DT, END_DT, HASH_VAL
				String[] contents1 = {"START_DT", "开始日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents2 = {"END_DT", "结束日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents3 = {"HASH_VAL", "HASH值", "RAW(16)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents3, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("2-状态类(带删除)".equals(tableType)) {
				// DATE_ID, CYCLE_ID, DEL_FLG, HASH_VAL
				
				String[] contents1 = {"START_DT", "开始日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents2 = {"END_DT", "结束日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents2, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents3 = {"DEL_FLG", "删除标志", "VARCHAR2(1)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents3, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents4 = {"HASH_VAL", "HASH值", "RAW(16)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents4, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("3-登记簿类".equals(tableType)) {
				// DATE_ID
				String[] contents1 = {"DATE_ID", "数据日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("4-事件类".equals(tableType)) {
				// DATE_ID
				String[] contents1 = {"DATE_ID", "数据日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
			}
			else if ("5-事件修改".equals(tableType)) {
				// DATE_ID, UPDATE_DT
				String[] contents1 = {"DATE_ID", "数据日期", "NUMBER(8)", "","","","","","","","","","" };
				createOneTableSheetLine(sheet, contents1, style, curRowNo, curColNo);
				curRowNo ++ ;
				String[] contents2 = {"UPDATE_DT", "数据修改时间", "NUMBER(8)", "","","","","","","","","","" };
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
		cell.setCellValue("索引");
		cell.setCellStyle(headStyle);
		colLength = 1;
		cra = new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra); 
        curColNo = curColNo + colLength + 1;
        
        cell = row.createCell(curColNo);
		cell.setCellValue("索引字段");
		cell.setCellStyle(headStyle);
		colLength = 4;
        cra =new CellRangeAddress(curRowNo, curRowNo, curColNo, curColNo + colLength); // 起始行, 终止行, 起始列, 终止列  
        sheet.addMergedRegion(cra); 
        curColNo = curColNo + colLength + 1;
        
		cell = row.createCell(curColNo);
		cell.setCellValue("索引说明");
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
				logger.debug("当前字段内容:"  + j + ":" + cell.toString());
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
					logger.debug("当前字段内容:"  + j + ":" + cell.toString());
					cell.setCellStyle(contStyle);
				}
			}
		}
       
        
		return curRowNo - oldcurRowNo;
	}
	 
	/**
	 * 创建 TableListSheet 表头 
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
		
		String[] contents = {"字段英文名", "字段中文名", "字段类型(含长度)", "是否允许为空", "主键", "外键/索引", 
				"缺省值", "字段说明", "数据标准编号", "数据标准中文名称", "变更日期", "变更内容", "备注", "返回"};
		createOneTableSheetLine(sheet, contents, style, curRowNo, curColNo);
		Cell cell = sheet.getRow(curRowNo).getCell(contents.length - 1);
		if(null != cell){
			CreationHelper createHelper = workbook.getCreationHelper();
			Hyperlink hyperlink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
			hyperlink.setAddress("#'" + backSheetName + "'!A1");  
			logger.debug(sheet.getSheetName() + "返回链接: #" + backSheetName + "!A1");
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
	 * 根据 contents 生成一行数据 
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
//		// 系统  
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getSysCode());
//		j++;
//		// 表英文名   
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getTableEnName());
//		j++;
//		// 表中文名
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getTableEnName());
//		j++;              
//		// 字段英文名	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getColumnEnName());
//		j++;
//		// 字段中文名	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getColumnChName());
//		j++;
//		// 字段类型（含长度）	 
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getColumnType());
//		j++;
//		// 是否允许为空	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getIsNull());
//		j++;
//		// 主键	外键/索引	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getIsPk());
//		j++;
//		// 缺省值	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getDefaultValue());
//		j++;
//		// 字段说明	 
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getSysCode());
//		j++;
//		// 数据标准编号	 
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getDesc());
//		j++;
//		// 数据标准中文名称	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getStdNo());
//		j++;
//		// 变更日期	  
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getStdChName());
//		j++;
//		// 变更内容	
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getAlterDesc());
//		j++;
//		// 备注    
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getRemark());
//		j++;
//		// 索引名    
//		cell = row.createCell(j);
//		cell.setCellStyle(style);
//		cell.setCellValue(columnInfo.getIndexName());
//		j++;
//		return curRowNo - oldcurRowNo + 1;
//	}
	
	/**
	 * 按照 参数给出的路径 读取 指定的 properties 配置文件, 并返回 对应的 properties 
	 * @author ding_kaiye
	 * @param string    配置文件名称(含路径)
	 * @return Properties 对象
	 * @throws IOException 
	 */
	public static  Properties loadConfigPropertiesFile(String configFile) throws IOException {
		String webRootPath = "." + File.separator + "config" + File.separator ;  //存放于 ./config/ 中
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
