package cn.fy.cjgl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.fy.cjgl.entity.Rolepermission;

@Mapper
public interface RolepermissionDao {
	void addRolepermission(Rolepermission rolepermission);
	void modRolepermission(Rolepermission rolepermission);
	void delRolepermission(Rolepermission rolepermission);
	void delRolepermissionByRoleid(Rolepermission rolepermission);
	Rolepermission queryRolepermission(Rolepermission rolepermission);
	List<Rolepermission> queryRolepermissionList(Rolepermission rolepermission);
	List<Rolepermission> queryRolepermissionListByRoleidList(Rolepermission rolepermission);
}