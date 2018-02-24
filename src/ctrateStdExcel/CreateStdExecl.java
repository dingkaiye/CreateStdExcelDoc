package ctrateStdExcel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateStdExecl {
	private static Logger logger = LogManager.getLogger("Execl"); 
	
	public static void main (String[] argv) throws IOException{

		if(argv.length != 3){
			System.out.println("参数数量不对");
			System.out.println("参数: 数据字典文件   T层文件名称   O层文件名称 ");
			System.exit(-1);
		}
		String fileName = argv[0];  
		String outFileT = argv[1];  
		String outFileO = argv[2];   
	
		boolean errorFlg = false;
		if(fileName == null || "".equals(fileName)){
			logger.error("数据字典文件不能为空\n");
			errorFlg = true;
		}
		if(outFileT == null || "".equals(outFileT)){
			logger.error("T层文件 不能为空\n");
			errorFlg = true;
		}
		if(outFileO == null || "".equals(outFileO)){
			logger.error("O层文件 不能为空\n");
			errorFlg = true;
		}
		
		File file=new File(fileName);
        if(!file.exists() || !file.isFile()){
        	logger.error("数据字典文件: " + fileName + " 不存在");
        	errorFlg = true;
		}

		if(errorFlg){
			logger.error("   请检查!!!");
			System.exit(-1);
		}
		
		logger.debug("数据字典文件:" + fileName);
		logger.debug("T层文件        :" + outFileT);
		logger.debug("O层文件        :" + outFileO);
		
		Workbook workbook = ToStdExcelUtil.getWorkbook(fileName);
		//读取 表清单 
		String sheetName = "源表";
		List<TableOdsInfo> tableList;
//		try {
			tableList = ToStdExcelUtil.readTableList(workbook, sheetName);
//		} catch (IOException e) {
//			logger.info("读表清单  获得 表信息列表  失败, Sheet Name: " + sheetName, e);
//			throw e;
//		}
		//读取 字段定义信息 
		sheetName = "源字段";
		tableList = ToStdExcelUtil.readTableColumnInfo(workbook, sheetName, tableList);
		
		file=new File(outFileT);
		if (file.exists() && file.isFile()) {
			logger.info(outFileT + "已存在, 开始删除文件 ");
			if(file.delete()) {
				logger.info(outFileT + "已存在, 删除文件完成 ");
			}else{
				logger.info(outFileT + "已存在, 删除文件失败 ");
				System.exit(-1);
			}
		}
        ToStdExcelUtil.cetartDocT(outFileT, tableList);
        
		file=new File(outFileO);
		if (file.exists() && file.isFile()) {
			logger.info(outFileO + "已存在, 开始删除文件 ");
			if(file.delete()) {
				logger.info(outFileO + "已存在, 删除文件完成 ");
			}else{
				logger.info(outFileO + "已存在, 删除文件失败 ");
				System.exit(-1);
			}
		}
		ToStdExcelUtil.cetartDocO(outFileO, tableList);
	}
	
	
}
