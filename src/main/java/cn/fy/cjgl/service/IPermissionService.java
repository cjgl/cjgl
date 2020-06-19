package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Permission;

public interface IPermissionService {
	public int addPermission(Permission permission);
	public int modPermission(Permission permission);
	public void delPermission(Permission permission);
	public Permission queryPermission(Permission permission);
	public List<Permission> queryPermissionList(Permission permission);
	public Integer checkPermission(Permission permission);
}
