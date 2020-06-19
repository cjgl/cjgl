package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.MenuDao;
import cn.fy.cjgl.entity.Menu;
import cn.fy.cjgl.service.IMenuService;

@Service
@Transactional
public class MenuService implements IMenuService {
	
	@Resource(name = "menuDao")
	private MenuDao menuDao;
	
	public int addMenu(Menu menu) {
		int nResult = 0;
		if((nResult=this.menuDao.checkMenu(menu)) == 0) {
			this.menuDao.addMenu(menu);
		}
		return nResult;
	}
	
	public int modMenu(Menu menu) {
		int nResult = 0;
		if((nResult=this.menuDao.checkMenu(menu)) == 0) {
			this.menuDao.modMenu(menu);
		}
		return nResult;
	}
	
	public void delMenu(Menu menu) {
		this.menuDao.delMenu(menu);
	}
	
	public Menu queryMenu(Menu menu) {
		return this.menuDao.queryMenu(menu);
	}
	
	public List<Menu> queryMenuList(Menu menu){
		return this.menuDao.queryMenuList(menu);
	}
	
	public Integer checkMenu(Menu menu) {
		return this.menuDao.checkMenu(menu);
	}
	
}
