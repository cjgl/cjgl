package cn.fy.cjgl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.fy.cjgl.entity.SubSystem;

@Mapper
public interface SubSystemDao {
	public void addSubSystem(SubSystem subSystem);
	public void modSubSystem(SubSystem subSystem);
	public void delSubSystem(SubSystem subSystem);
	public SubSystem querySubSystem(SubSystem subSystem);
	public List<SubSystem> querySubSystemList(SubSystem subSystem);
	public Integer checkSubSystem(SubSystem subSystem);
}
