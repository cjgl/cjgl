package cn.fy.cjgl.controller;

import java.io.IOException;
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
import com.github.pagehelper.PageInfo;

import cn.fy.cjgl.entity.Role;
import cn.fy.cjgl.service.IRoleService;
import cn.fy.cjgl.util.DateTimeUtil;

@Controller
@RequestMapping("role")
public class RoleController {

	private static final Logger log = LoggerFactory.getLogger(SubSystemController.class);
	
	@Autowired
    private IRoleService roleService;
	
	@RequestMapping("/rolePage")
	public ModelAndView subSystemPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		log.debug("subSystemPage");
		
		modelAndView.setViewName("role/rolePage");
		return modelAndView;
	}
	
	@RequestMapping("/queryRoleList")
	@ResponseBody
	public Map<String, Object> queryRoleList(Role role,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		PageHelper.offsetPage((page-1)*rows, rows, true);
		//PageHelper.orderBy(sortName + " " + sortOrder);
		PageHelper.orderBy("t.createtime DESC");
		
		role.setDelflag("0");
		List<Role> roleList = this.roleService.queryRoleList(role);
		PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", roleList);
		map.put("total", pageInfo.getTotal());
		return map;
	}
	
	@RequestMapping(value= {"/addRole"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String addSubSystem(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		role.setCreatetime(now);
		role.setUpdatetime(now);
		role.setDelflag("0");
		
		int nResult = this.roleService.addRole(role);
		String msg = "操作成功";
		if(nResult != 0) {
			msg = "名称或类型重复";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/modRole"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String modRole(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String now = DateTimeUtil.getDateTime19();
		role.setUpdatetime(now);
		role.setDelflag("0");
		int nResult = 0;
		String msg = "";
		if(role.getRoleid() != 0) {
			nResult = this.roleService.modRole(role);
			
			if(nResult != 0) {
				msg = "名称或类型重复";
			} else {
				msg = "操作成功";
			}
		} else {
			nResult = 1;
			msg = "内置角色无法修改";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", nResult+"");
		map.put("msg", msg);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping(value= {"/delRole"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String delRole(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(role.getRoleid() != 0) {
			this.roleService.delRole(role);

			map.put("nResult", "0");
			map.put("msg", "操作成功");
		} else {
			map.put("nResult", "1");
			map.put("msg", "内置角色无法删除");
		}

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
}
