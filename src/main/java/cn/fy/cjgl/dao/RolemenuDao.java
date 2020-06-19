package cn.fy.cjgl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.fy.cjgl.entity.Rolemenu;

@Mapper
public interface RolemenuDao {
	public void addRolemenu(Rolemenu rolemenu);
	public void modRolemenu(Rolemenu rolemenu);
	public void delRolemenu(Rolemenu rolemenu);
	public void delRolemenuByRoleid(Rolemenu rolemenu);
	public Rolemenu queryRolemenu(Rolemenu rolemenu);
	public List<Rolemenu> queryRolemenuList(Rolemenu rolemenu);
	public List<Rolemenu> queryRolemenuListByRoleids(Rolemenu rolemenu);
}
