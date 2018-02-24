package ctrateStdExcel;

public class ColumnStdInfo {
	private String  sysCode  ;       // 系统
	private String  tableEnName  ;   // 表英文名
	private String  tableChName  ;   // 表中文名
	private String  columnEnName  ;  // 字段英文名	
	private String  columnChName  ;  // 字段中文名	
	private String  columnType  ;    // 字段类型（含长度）	
	private String  isNull  ;        // 是否允许为空	
	private String  isPk  ;          // 主键	外键/索引	
	private String  defaultValue  ;  // 缺省值	
	private String  desc  ;          // 字段说明	
	private String  stdNo  ;         // 数据标准编号	
	private String  stdChName  ;     // 数据标准中文名称	
	private String  alterTime  ;     // 变更日期	
	private String  alterDesc  ;     // 变更内容	
	private String  Remark  ;        // 备注
	private String[]  IndexName  ;     // 索引名
	
	public String getSysCode() {
		return sysCode;
	}
	public String getTableEnName() {
		return tableEnName;
	}
	public String getTableChName() {
		return tableChName;
	}
	public String getColumnEnName() {
		return columnEnName;
	}
	public String getColumnChName() {
		return columnChName;
	}
	public String getColumnType() {
		return columnType;
	}
	public String getIsNull() {
		return isNull;
	}
	public String getIsPk() {
		return isPk;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String getDesc() {
		return desc;
	}
	public String getStdNo() {
		return stdNo;
	}
	public String getStdChName() {
		return stdChName;
	}
	public String getAlterTime() {
		return alterTime;
	}
	public String getAlterDesc() {
		return alterDesc;
	}
	public String getRemark() {
		return Remark;
	}
	public String[] getIndexName() {
		return IndexName;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public void setTableEnName(String tableEnName) {
		this.tableEnName = tableEnName;
	}
	public void setTableChName(String tableChName) {
		this.tableChName = tableChName;
	}
	public void setColumnEnName(String columnEnName) {
		this.columnEnName = columnEnName;
	}
	public void setColumnChName(String columnChName) {
		this.columnChName = columnChName;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setStdNo(String stdNo) {
		this.stdNo = stdNo;
	}
	public void setStdChName(String stdChName) {
		this.stdChName = stdChName;
	}
	public void setAlterTime(String alterTime) {
		this.alterTime = alterTime;
	}
	public void setAlterDesc(String alterDesc) {
		this.alterDesc = alterDesc;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public void setIndexName(String[] indexName) {
		IndexName = indexName;
	}

}
