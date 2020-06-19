package cn.fy.cjgl.entity;

import org.apache.ibatis.type.Alias;

@Alias("User")
public class User {
	public static String[] EXPORT_HEADERS = {"projectname:项目名称","subsystemname:子系统名称","loginname:登录名称","username:用户名称","createtime:创建时间","updatetime:修改时间"};
	private Integer userid;
	private Integer projectid;
	private Integer subsystemid;
	private String loginname;
	private String username;
	private String pwd;
	private String sso;
	private String createtime;
	private String updatetime;
	private String delflag;
	
	private String projectname;
	private String subsystemname;
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSso() {
		return sso;
	}
	public void setSso(String sso) {
		this.sso = sso;
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
