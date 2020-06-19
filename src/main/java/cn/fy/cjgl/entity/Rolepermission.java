package cn.fy.cjgl.entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("Rolepermission")
public class Rolepermission extends Permission {
	private Integer rolepermissionid;
	private Integer roleid;
	private List<Integer> roleidList;
	public Integer getRolepermissionid() {
		return rolepermissionid;
	}
	public void setRolepermissionid(Integer rolepermissionid) {
		this.rolepermissionid = rolepermissionid;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public List<Integer> getRoleidList() {
		return roleidList;
	}
	public void setRoleidList(List<Integer> roleidList) {
		this.roleidList = roleidList;
	}
}
