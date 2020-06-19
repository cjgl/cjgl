package cn.fy.cjgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fy.cjgl.dao.PermissionDao;
import cn.fy.cjgl.entity.Permission;
import cn.fy.cjgl.service.IPermissionService;

@Service
@Transactional
public class PermissionService implements IPermissionService {

	@Resource(name = "permissionDao")
	private PermissionDao permissionDao;

	@Override
	public int addPermission(Permission permission) {
		int nResult = 0;
		if ((nResult = this.permissionDao.checkPermission(permission)) == 0) {
			this.permissionDao.addPermission(permission);
		}
		return nResult;
	}

	@Override
	public int modPermission(Permission permission) {
		int nResult = 0;
		if ((nResult = this.permissionDao.checkPermission(permission)) == 0) {
			this.permissionDao.modPermission(permission);
		}
		return nResult;
	}

	@Override
	public void delPermission(Permission permission) {
		this.permissionDao.delPermission(permission);
	}

	@Override
	public Permission queryPermission(Permission permission) {
		return this.permissionDao.queryPermission(permission);
	}

	@Override
	public List<Permission> queryPermissionList(Permission permission) {
		return this.permissionDao.queryPermissionList(permission);
	}

	@Override
	public Integer checkPermission(Permission permission) {
		return this.permissionDao.checkPermission(permission);
	}

}
