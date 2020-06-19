package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Role;

public interface IRoleService {
	public int addRole(Role role);
	public int modRole(Role role);
	public void delRole(Role role);
	public Role queryRole(Role role);
	public List<Role> queryRoleList(Role role);
	public Integer checkRole(Role role);
}
