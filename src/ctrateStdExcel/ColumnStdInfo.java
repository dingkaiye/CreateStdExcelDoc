package ctrateStdExcel;

public class ColumnStdInfo {
	private String  sysCode  ;       // ϵͳ
	private String  tableEnName  ;   // ��Ӣ����
	private String  tableChName  ;   // ��������
	private String  columnEnName  ;  // �ֶ�Ӣ����	
	private String  columnChName  ;  // �ֶ�������	
	private String  columnType  ;    // �ֶ����ͣ������ȣ�	
	private String  isNull  ;        // �Ƿ�����Ϊ��	
	private String  isPk  ;          // ����	���/����	
	private String  defaultValue  ;  // ȱʡֵ	
	private String  desc  ;          // �ֶ�˵��	
	private String  stdNo  ;         // ���ݱ�׼���	
	private String  stdChName  ;     // ���ݱ�׼��������	
	private String  alterTime  ;     // �������	
	private String  alterDesc  ;     // �������	
	private String  Remark  ;        // ��ע
	private String[]  IndexName  ;     // ������
	
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
