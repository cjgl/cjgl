package cn.fy.cjgl.entity;

import org.apache.ibatis.type.Alias;

@Alias("Menu")
public class Menu {
	private Integer menuid;
	private Integer subsystemid;
	private Integer pmenuid;
	private String menuname;
	private String menuurl;
	private String iconcls;
	private Integer seqno;
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public Integer getSubsystemid() {
		return subsystemid;
	}
	public void setSubsystemid(Integer subsystemid) {
		this.subsystemid = subsystemid;
	}
	public Integer getPmenuid() {
		return pmenuid;
	}
	public void setPmenuid(Integer pmenuid) {
		this.pmenuid = pmenuid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public String getIconcls() {
		return iconcls;
	}
	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
	public Integer getSeqno() {
		return seqno;
	}
	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
}
