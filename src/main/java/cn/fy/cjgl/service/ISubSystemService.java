package cn.fy.cjgl.service;

import java.util.List;

import cn.fy.cjgl.entity.SubSystem;

public interface ISubSystemService {
	public int addSubSystem(SubSystem subSystem);
	public int modSubSystem(SubSystem subSystem);
	public void delSubSystem(SubSystem subSystem);
	public SubSystem querySubSystem(SubSystem subSystem);
	public List<SubSystem> querySubSystemList(SubSystem subSystem);
	public Integer checkSubSystem(SubSystem subSystem);
}
