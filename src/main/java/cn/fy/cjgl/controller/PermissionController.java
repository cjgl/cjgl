package cn.fy.cjgl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;

import cn.fy.cjgl.entity.Menu;
import cn.fy.cjgl.entity.Permission;
import cn.fy.cjgl.entity.Project;
import cn.fy.cjgl.entity.SubSystem;
import cn.fy.cjgl.service.IMenuService;
import cn.fy.cjgl.service.IPermissionService;
import cn.fy.cjgl.service.IProjectService;
import cn.fy.cjgl.service.ISubSystemService;

@Controller
@RequestMapping("permission")
public class PermissionController {
	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
    private IPermissionService permissionService;
	
	@Autowired
    private IMenuService menuService;
	
	@Autowired
    private IProjectService projectService;
	
	@Autowired
    private ISubSystemService subSystemService;
	
	@RequestMapping("/permissionPage")
	public ModelAndView permissionPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		log.debug("permissionPage");
		
		modelAndView.setViewName("permission/permissionPage");
		return modelAndView;
	}
	
	@RequestMapping("/queryAllPermission")
	@ResponseBody
	public List<Map<String, Object>> queryAllPermission(Permission permission,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		Project project = new Project();
		project.setDelflag("0");
		
		PageHelper.orderBy("t.createtime ASC");
		List<Project> projectList = this.projectService.queryProjectList(project);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Project p : projectList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectid", p.getProjectid());
			map.put("id", "p"+p.getProjectid());
			map.put("pId", "-1");
			map.put("text", p.getProjectname());
			map.put("state", "open");
			map.put("iconCls", "icon-application");
			
			addSubSystem(map);
			
			list.add(map);
		}

		return list;
	}
	
	private void addSubSystem(Map<String, Object> map) {
		SubSystem subSystem = new SubSystem();
		subSystem.setProjectid((int)map.get("projectid"));
		subSystem.setDelflag("0");
		map.remove("projectid");
		
		PageHelper.orderBy("t.createtime ASC");
		List<SubSystem> subSystemList = this.subSystemService.querySubSystemList(subSystem);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(SubSystem s : subSystemList) {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", "s"+s.getSubsystemid());
			hashMap.put("pId", map.get("id"));
			hashMap.put("text", s.getSubsystemname());
			hashMap.put("state", "open");
			hashMap.put("iconCls", "icon-mapadd");
			
			addMenu(hashMap, s.getSubsystemid(), 0);
			
			list.add(hashMap);
		}
		
		map.put("children", list);
	}
	
	private void addMenu(Map<String, Object> map, int subsystemid, int pmenuid) {
		Menu menu = new Menu();
		menu.setSubsystemid(subsystemid);
		menu.setPmenuid(pmenuid);
		
		PageHelper.orderBy("t.seqno ASC");
		List<Menu> menuList = this.menuService.queryMenuList(menu);
		
		if(menuList.size() == 0) {
			return;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(Menu m : menuList) {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", "m"+m.getMenuid());
			hashMap.put("pId", map.get("id"));
			hashMap.put("text", m.getMenuname());
			hashMap.put("state", "open");
			hashMap.put("iconCls", "icon-layout");
			
			addMenu(hashMap, subsystemid, m.getMenuid());
			
			List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
			Permission permission = new Permission();
			permission.setMenuid(m.getMenuid());
			
			PageHelper.orderBy("t.permissionid ASC");
			List<Permission> permissionList = this.permissionService.queryPermissionList(permission);
			for(Permission p : permissionList) {
				Map<String, Object> xHashMap = new HashMap<String, Object>();
				xHashMap.put("id", "x"+p.getPermissionid());
				xHashMap.put("pId", "m"+m.getMenuid());
				xHashMap.put("text", p.getPermissionname());
				xHashMap.put("state", "open");
				xHashMap.put("iconCls", "icon-lockkey");
				
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("permissiontag", p.getPermissiontag());
				attributes.put("menuid", m.getMenuid());
				xHashMap.put("attributes", attributes);
				
				arrayList.add(xHashMap);
			}
			
			List<Map<String, Object>> children = (List<Map<String, Object>>)hashMap.get("children");
			if(children != null && children.size() > 0) {
				children.addAll(arrayList);
				hashMap.put("children", children);
			} else {
				hashMap.put("children", arrayList);
			}
			
			list.add(hashMap);
		}
			
		map.put("children", list);
	}
	
	@RequestMapping(value= {"/addPermission"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addPermission(Permission permission, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int nResult = this.permissionService.addPermission(permission);
		String msg = "操作成功";
		if(nResult != 0) {
			msg = "标识重复";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/modPermission"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modPermission(Permission permission, HttpServletRequest request, HttpServletResponse response) throws IOException {

		int nResult = this.permissionService.modPermission(permission);
		String msg = "操作成功";
		if(nResult != 0) {
			msg = "名称重复";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/delPermission"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delPermission(Permission permission, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		this.permissionService.delPermission(permission);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", "0");
		map.put("msg", "操作成功");

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
}
