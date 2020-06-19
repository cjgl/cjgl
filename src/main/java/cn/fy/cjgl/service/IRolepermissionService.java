package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Rolepermission;

public interface IRolepermissionService {
	void addRolepermission(Rolepermission rolepermission);
	void modRolepermission(Rolepermission rolepermission);
	void delRolepermission(Rolepermission rolepermission);
	void delRolepermissionByRoleid(Rolepermission rolepermission);
	Rolepermission queryRolepermission(Rolepermission rolepermission);
	List<Rolepermission> queryRolepermissionList(Rolepermission rolepermission);
	List<Rolepermission> queryRolepermissionListByRoleidList(Rolepermission rolepermission);
}
