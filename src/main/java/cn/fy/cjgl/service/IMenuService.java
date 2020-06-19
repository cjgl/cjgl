package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.Menu;

public interface IMenuService {
	public int addMenu(Menu menu);
	public int modMenu(Menu menu);
	public void delMenu(Menu menu);
	public Menu queryMenu(Menu menu);
	public List<Menu> queryMenuList(Menu menu);
	public Integer checkMenu(Menu menu);
}
