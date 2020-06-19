package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.RolemenuDao;
import cn.fy.cjgl.dao.RolepermissionDao;
import cn.fy.cjgl.entity.Rolemenu;
import cn.fy.cjgl.entity.Rolepermission;
import cn.fy.cjgl.service.IRolemenuService;

@Service
@Transactional
public class RolemenuService implements IRolemenuService {

	@Resource(name = "rolemenuDao")
	private RolemenuDao rolemenuDao;

	@Resource(name = "rolepermissionDao")
	private RolepermissionDao rolepermissionDao;

	public void addRolemenu(Rolemenu rolemenu) {
		this.rolemenuDao.addRolemenu(rolemenu);
	}

	public void modRolemenu(Rolemenu rolemenu) {
		this.rolemenuDao.modRolemenu(rolemenu);
	}

	public void delRolemenu(Rolemenu rolemenu) {
		this.rolemenuDao.delRolemenu(rolemenu);
	}

	public void delRolemenuByRoleid(Rolemenu rolemenu) {
		this.rolemenuDao.delRolemenuByRoleid(rolemenu);
	}

	public Rolemenu queryRolemenu(Rolemenu rolemenu) {
		return this.rolemenuDao.queryRolemenu(rolemenu);
	}

	public List<Rolemenu> queryRolemenuList(Rolemenu rolemenu) {
		return this.rolemenuDao.queryRolemenuList(rolemenu);
	}

	public void saveRolemenu(Rolemenu rolemenu, String ids) {
		this.rolemenuDao.delRolemenuByRoleid(rolemenu);
		Rolepermission rolepermission = new Rolepermission();
		rolepermission.setRoleid(rolemenu.getRoleid());
		this.rolepermissionDao.delRolepermissionByRoleid(rolepermission);

		if (ids != null && !"".equals(ids)) {
			String[] idArray = ids.split(",");

			for (String id : idArray) {
				if ("m".equals(id.substring(0, 1))) {
					Rolemenu tempRolemenu = new Rolemenu();
					tempRolemenu.setRoleid(rolemenu.getRoleid());
					tempRolemenu.setMenuid(Integer.parseInt(id.substring(1)));
					this.rolemenuDao.addRolemenu(tempRolemenu);
				} else if ("x".equals(id.substring(0, 1))) {
					Rolepermission tempRolepermission = new Rolepermission();
					tempRolepermission.setRoleid(rolemenu.getRoleid());
					tempRolepermission.setPermissionid(Integer.parseInt(id.substring(1)));
					this.rolepermissionDao.addRolepermission(tempRolepermission);
				}
			}
		}
	}

	@Override
	public List<Rolemenu> queryRolemenuListByRoleids(Rolemenu rolemenu) {
		return this.rolemenuDao.queryRolemenuListByRoleids(rolemenu);
	}

}
