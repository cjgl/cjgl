package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Rolemenu;

public interface IRolemenuService {
	public void addRolemenu(Rolemenu rolemenu);
	public void modRolemenu(Rolemenu rolemenu);
	public void delRolemenu(Rolemenu rolemenu);
	public void delRolemenuByRoleid(Rolemenu rolemenu);
	public Rolemenu queryRolemenu(Rolemenu rolemenu);
	public List<Rolemenu> queryRolemenuList(Rolemenu rolemenu);
	public void saveRolemenu(Rolemenu rolemenu, String menuids);
	public List<Rolemenu> queryRolemenuListByRoleids(Rolemenu rolemenu);
}
