package cn.fy.cjgl.entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("Rolemenu")
public class Rolemenu extends Menu {
	private Integer rolemenuid;
	private Integer roleid;
	private List<Integer> roleidList;
	private List<Rolemenu> rolemenuList;
	public Integer getRolemenuid() {
		return rolemenuid;
	}
	public void setRolemenuid(Integer rolemenuid) {
		this.rolemenuid = rolemenuid;
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
	public List<Rolemenu> getRolemenuList() {
		return rolemenuList;
	}
	public void setRolemenuList(List<Rolemenu> rolemenuList) {
		this.rolemenuList = rolemenuList;
	}
}
