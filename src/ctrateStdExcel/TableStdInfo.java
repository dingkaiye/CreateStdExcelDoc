package ctrateStdExcel;

import java.util.List;

public class TableStdInfo {

	private String   SeqNo       ;    // ���
	private String   ModName     ;    // ģ��
	private String   TableEnName ;    // ��Ӣ������
	private String   TableChName ;    // ����������
	private String   TableDesc   ;    // ��������
	private String   Type        ;    // ��/��ͼ
	private String   Version     ;    // �汾
	private String   AlterTime   ;    // ���ʱ��

	private List<ColumnStdInfo> Columns ;  // 

	public String getSeqNo() {
		return SeqNo;
	}

	public String getModName() {
		return ModName;
	}

	public String getTableEnName() {
		return TableEnName;
	}

	public String getTableChName() {
		return TableChName;
	}

	public String getTableDesc() {
		return TableDesc;
	}

	public String getType() {
		return Type;
	}

	public String getVersion() {
		return Version;
	}

	public String getAlterTime() {
		return AlterTime;
	}

	public List<ColumnStdInfo> getColumns() {
		return Columns;
	}

	public void setSeqNo(String seqNo) {
		SeqNo = seqNo;
	}

	public void setModName(String modName) {
		ModName = modName;
	}

	public void setTableEnName(String tableEnName) {
		TableEnName = tableEnName;
	}

	public void setTableChName(String tableChName) {
		TableChName = tableChName;
	}

	public void setTableDesc(String tableDesc) {
		TableDesc = tableDesc;
	}

	public void setType(String type) {
		Type = type;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public void setAlterTime(String alterTime) {
		AlterTime = alterTime;
	}

	public void setColumns(List<ColumnStdInfo> columns) {
		Columns = columns;
	}
	
}
