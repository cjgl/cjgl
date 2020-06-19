package cn.fy.cjgl.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateModelException;

@Component
public class ShiroTagsFreeMarkerCfg {
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@PostConstruct
	public void setSharedVariable() throws TemplateModelException {  
		//println "设置freeMarker 的shiro 标签"
		freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());  
	}  
}
