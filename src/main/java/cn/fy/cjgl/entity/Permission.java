package cn.fy.cjgl.entity;

import org.apache.ibatis.type.Alias;

@Alias("Permission")
public class Permission {
	private Integer permissionid;
	private Integer menuid;
	private String permissionname;
	private String permissiontag;
	public Integer getPermissionid() {
		return permissionid;
	}
	public void setPermissionid(Integer permissionid) {
		this.permissionid = permissionid;
	}
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public String getPermissionname() {
		return permissionname;
	}
	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname;
	}
	public String getPermissiontag() {
		return permissiontag;
	}
	public void setPermissiontag(String permissiontag) {
		this.permissiontag = permissiontag;
	}
}
