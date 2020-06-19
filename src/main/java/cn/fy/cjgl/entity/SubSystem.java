package cn.fy.cjgl.entity;

public class SubSystem {
	private Integer subsystemid;
	private Integer projectid;
	private String subsystemname;
	private Integer type;
	private String remark;
	private String createtime;
	private String updatetime;
	private String delflag;
	
	private String projectname;
	public Integer getSubsystemid() {
		return subsystemid;
	}
	public void setSubsystemid(Integer subsystemid) {
		this.subsystemid = subsystemid;
	}
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public String getSubsystemname() {
		return subsystemname;
	}
	public void setSubsystemname(String subsystemname) {
		this.subsystemname = subsystemname;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
}
