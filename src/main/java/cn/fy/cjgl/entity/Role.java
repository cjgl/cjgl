package cn.fy.cjgl.entity;

public class Role {
	private Integer roleid;
	private Integer projectid;
	private Integer subsystemid;
	private String rolename;
	private String remark;
	private String createtime;
	private String updatetime;
	private String delflag;
	
	private String projectname;
	private String subsystemname;
	
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public Integer getSubsystemid() {
		return subsystemid;
	}
	public void setSubsystemid(Integer subsystemid) {
		this.subsystemid = subsystemid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
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
	public String getSubsystemname() {
		return subsystemname;
	}
	public void setSubsystemname(String subsystemname) {
		this.subsystemname = subsystemname;
	}
}
