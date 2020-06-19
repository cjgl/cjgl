package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.RolepermissionDao;
import cn.fy.cjgl.entity.Rolepermission;
import cn.fy.cjgl.service.IRolepermissionService;

@Service
@Transactional
public class RolepermissionService implements IRolepermissionService {

	@Resource(name = "rolepermissionDao")
	private RolepermissionDao rolepermissionDao;

	@Override
	public void addRolepermission(Rolepermission rolepermission) {
		this.rolepermissionDao.addRolepermission(rolepermission);
	}

	@Override
	public void modRolepermission(Rolepermission rolepermission) {
		this.rolepermissionDao.modRolepermission(rolepermission);
	}

	@Override
	public void delRolepermission(Rolepermission rolepermission) {
		this.rolepermissionDao.delRolepermission(rolepermission);
	}

	@Override
	public void delRolepermissionByRoleid(Rolepermission rolepermission) {
		this.rolepermissionDao.delRolepermissionByRoleid(rolepermission);
	}

	@Override
	public Rolepermission queryRolepermission(Rolepermission rolepermission) {
		return this.rolepermissionDao.queryRolepermission(rolepermission);
	}

	@Override
	public List<Rolepermission> queryRolepermissionList(Rolepermission rolepermission) {
		return this.rolepermissionDao.queryRolepermissionList(rolepermission);
	}

	@Override
	public List<Rolepermission> queryRolepermissionListByRoleidList(Rolepermission rolepermission) {
		return this.rolepermissionDao.queryRolepermissionListByRoleidList(rolepermission);
	}

}
