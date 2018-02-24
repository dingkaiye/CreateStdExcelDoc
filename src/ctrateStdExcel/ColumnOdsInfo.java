package ctrateStdExcel;


public class ColumnOdsInfo {
    private String    syscode        ; 	// 源系统                         CBS
    private String    tableEnName    ; 	// 源表名                         CDKDB
    private String    tableChName    ; 	// 源表中文名                 贷款产品担保信息表
    private String    columnNo       ; 	// 字段序号                      1
    private String    columnEnName   ; 	// 源字段                          FRDM_U
    private String    columnChName   ; 	// 源字段中文名              法人代码
    private String    targetType     ; 	// 目标数据类型               VARCHAR2(3)
    private String    pristineType   ; 	// 原数据类型                   VARCHAR2(3)
    private String    isInOds        ; 	// 是否入库                       N
    private String    isStd          ; 	// 是否需要标准化            N
    private String    stdNo          ; 	// 数据标准编号
    private String    stdName        ; 	// 数据标准中文名称
    private String    isNull         ; 	// 空值
    private String    defaultValue   ; 	// 默认值
    private String    constraint     ; 	// CONSTRAINT
    private String    isUI           ; 	// UI
    private String    isIDX          ; 	// IDX
    private String    describe       ; 	// 特殊说明
//    private String    stdEnName        ; 	// 标准化字段                 FRDM_U
//    private String            ; 	// 标准化数据类型            VARCHAR2(3)
//    private String            ; 	// 函数
//    private String            ; 	// 拉链字段
    
    
	public String getSyscode() {
		return syscode;
	}
	public String getTableEnName() {
		return tableEnName;
	}
	public String getTableChName() {
		return tableChName;
	}
	public String getColumnNo() {
		return columnNo;
	}
	public String getColumnEnName() {
		return columnEnName;
	}
	public String getColumnChName() {
		return columnChName;
	}
	public String getTargetType() {
		return targetType;
	}
	public String getPristineType() {
		return pristineType;
	}
	public String getIsInOds() {
		return isInOds;
	}
	public String getIsStd() {
		return isStd;
	}
	public String getStdNo() {
		return stdNo;
	}
	public String getStdName() {
		return stdName;
	}
	public String getIsNull() {
		return isNull;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String getConstraint() {
		return constraint;
	}
	public String getIsUI() {
		return isUI;
	}
	public String getIsIDX() {
		return isIDX;
	}
	public String getDescribe() {
		return describe;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public void setTableEnName(String tableEnName) {
		this.tableEnName = tableEnName;
	}
	public void setTableChName(String tableChName) {
		this.tableChName = tableChName;
	}
	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}
	public void setColumnEnName(String columnEnName) {
		this.columnEnName = columnEnName;
	}
	public void setColumnChName(String columnChName) {
		this.columnChName = columnChName;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public void setPristineType(String pristineType) {
		this.pristineType = pristineType;
	}
	public void setIsInOds(String isInOds) {
		this.isInOds = isInOds;
	}
	public void setIsStd(String isStd) {
		this.isStd = isStd;
	}
	public void setStdNo(String stdNo) {
		this.stdNo = stdNo;
	}
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}
	public void setIsUI(String isUI) {
		this.isUI = isUI;
	}
	public void setIsIDX(String isIDX) {
		this.isIDX = isIDX;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
