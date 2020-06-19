package cn.fy.cjgl.entity;

import org.apache.ibatis.type.Alias;

@Alias("Userrole")
public class Userrole extends Role {
	private Integer userroleid;
	private Integer userid;
	private boolean checked;
	public Integer getUserroleid() {
		return userroleid;
	}
	public void setUserroleid(Integer userroleid) {
		this.userroleid = userroleid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
