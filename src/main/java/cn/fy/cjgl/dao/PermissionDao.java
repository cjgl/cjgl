package cn.fy.cjgl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.fy.cjgl.entity.Permission;

@Mapper
public interface PermissionDao {
	public void addPermission(Permission permission);
	public void modPermission(Permission permission);
	public void delPermission(Permission permission);
	public Permission queryPermission(Permission permission);
	public List<Permission> queryPermissionList(Permission permission);
	public Integer checkPermission(Permission permission);
}
