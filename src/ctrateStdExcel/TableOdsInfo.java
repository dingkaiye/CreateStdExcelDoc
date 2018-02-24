package ctrateStdExcel;

import java.util.List;

/**
 * 源表Sheet页, 表类型信息 
 * @author ding_kaiye
 *
 */
public class TableOdsInfo {
	private String sysCode; // 源系统名称 CBS
	private String modName; // 模块 贷款分册
	private String SeqNo;   // 编号 CBS0001
	private String tableEnName; // 源表名称 CDKDB
	private String tableChName; // 表中文注解 贷款产品担保信息表
	private String isInOds;     // 是否入库 N
	private String isNeedStd;   // 是否需要标准化 N
	private String TtableName;  // 缓冲层表名 T_CBS_CDKDB
	private String OtableName;  // 系统记录层表名 O_CBS_CDKDB
	private String tableType;   // 表类型 3-登记簿类
	private String version;     // 版本 V1.0
	private String alterTime;   // 变更时间 20171227
	
	private List<ColumnOdsInfo> columns ;
	
	public String getSysCode() {
		return sysCode;
	}
	public String getModName() {
		return modName;
	}
	public String getSeqNo() {
		return SeqNo;
	}
	public String getTableEnName() {
		return tableEnName;
	}
	public String getTableChName() {
		return tableChName;
	}
	public String getIsInOds() {
		return isInOds;
	}
	public String getIsNeedStd() {
		return isNeedStd;
	}
	public String getTtableName() {
		return TtableName;
	}
	public String getOtableName() {
		return OtableName;
	}
	public String getTableType() {
		return tableType;
	}
	public String getVersion() {
		return version;
	}
	public String getAlterTime() {
		return alterTime;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public void setModName(String modName) {
		this.modName = modName;
	}
	public void setSeqNo(String seqNo) {
		SeqNo = seqNo;
	}
	public void setTableEnName(String tableEnName) {
		this.tableEnName = tableEnName;
	}
	public void setTableChName(String tableChName) {
		this.tableChName = tableChName;
	}
	public void setIsInOds(String isInOds) {
		this.isInOds = isInOds;
	}
	public void setIsNeedStd(String isNeedStd) {
		this.isNeedStd = isNeedStd;
	}
	public void setTtableName(String ttableName) {
		TtableName = ttableName;
	}
	public void setOtableName(String otableName) {
		OtableName = otableName;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setAlterTime(String alterTime) {
		this.alterTime = alterTime;
	}
	public List<ColumnOdsInfo> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnOdsInfo> columns) {
		this.columns = columns;
	}

	// private String User ; // 用户名
	// private String ; // 重要性
	// private String is ; // 是否全表拉链
	// private String ; // 入库方式
	// private String ; // DBLINK名称
	// private String ; // 数据接口增全量标志
	// private String ; // 增量字段
	// private String ; // 排序字段
	// private String ; // 数据时间
	// private String ; // 初始化数据时间
	// private String ; // 初始化增量字段
	// private String ; // 注释、问题、说明
	// private String ; // 初始化类型
	// private String ; // 分区表(T)
	// private String ; // 分区表
	// private String ; // 物理属性
	// private String ; // 机构撤并
	// private String ; // 修改键字
	// private String ; // 修改键字说明
	
}
