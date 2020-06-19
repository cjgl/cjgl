package cn.fy.cjgl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.fy.cjgl.entity.Menu;

@Mapper
public interface MenuDao {
	public void addMenu(Menu menu);
	public void modMenu(Menu menu);
	public void delMenu(Menu menu);
	public Menu queryMenu(Menu menu);
	public List<Menu> queryMenuList(Menu menu);
	public Integer checkMenu(Menu menu);
}
