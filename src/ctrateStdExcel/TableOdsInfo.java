package ctrateStdExcel;

import java.util.List;

/**
 * Դ��Sheetҳ, ��������Ϣ 
 * @author ding_kaiye
 *
 */
public class TableOdsInfo {
	private String sysCode; // Դϵͳ���� CBS
	private String modName; // ģ�� ����ֲ�
	private String SeqNo;   // ��� CBS0001
	private String tableEnName; // Դ������ CDKDB
	private String tableChName; // ������ע�� �����Ʒ������Ϣ��
	private String isInOds;     // �Ƿ���� N
	private String isNeedStd;   // �Ƿ���Ҫ��׼�� N
	private String TtableName;  // �������� T_CBS_CDKDB
	private String OtableName;  // ϵͳ��¼����� O_CBS_CDKDB
	private String tableType;   // ������ 3-�Ǽǲ���
	private String version;     // �汾 V1.0
	private String alterTime;   // ���ʱ�� 20171227
	
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

	// private String User ; // �û���
	// private String ; // ��Ҫ��
	// private String is ; // �Ƿ�ȫ������
	// private String ; // ��ⷽʽ
	// private String ; // DBLINK����
	// private String ; // ���ݽӿ���ȫ����־
	// private String ; // �����ֶ�
	// private String ; // �����ֶ�
	// private String ; // ����ʱ��
	// private String ; // ��ʼ������ʱ��
	// private String ; // ��ʼ�������ֶ�
	// private String ; // ע�͡����⡢˵��
	// private String ; // ��ʼ������
	// private String ; // ������(T)
	// private String ; // ������
	// private String ; // ��������
	// private String ; // ��������
	// private String ; // �޸ļ���
	// private String ; // �޸ļ���˵��
	
}
