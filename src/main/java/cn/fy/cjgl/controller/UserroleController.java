package cn.fy.cjgl.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;

import cn.fy.cjgl.entity.Userrole;
import cn.fy.cjgl.service.IUserroleService;

@Controller
@RequestMapping("userrole")
public class UserroleController {
	
	@Autowired
    private IUserroleService userroleService;
	
	@RequestMapping("/queryUserroleList")
	@ResponseBody
	public List<Userrole> queryUserroleList(Userrole userrole,  Integer page, Integer rows, String sortName, String sortOrder, HttpServletRequest request, HttpServletResponse response) {
		//PageHelper.offsetPage((page-1)*rows, rows, true);
		//PageHelper.orderBy(sortName + " " + sortOrder);
		PageHelper.orderBy("t.createtime DESC");
		
		userrole.setDelflag("0");
		List<Userrole> userroleList = this.userroleService.queryUserroleList(userrole);

		return userroleList;
	}
	
	@RequestMapping(value= {"/saveUserrole"}, produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String saveUserrole(Userrole userrole, String roleids, HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.userroleService.saveUserrole(userrole, roleids);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nResult", "0");
		map.put("msg", "操作成功");

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
}
