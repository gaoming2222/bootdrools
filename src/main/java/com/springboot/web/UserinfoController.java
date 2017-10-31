package com.springboot.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.entity.Userinfo;
import com.springboot.filter.*;
import com.springboot.mapper.UserinfoMapper;
import com.springboot.tools.JsonUtility;

@Controller
@EnableAutoConfiguration
//MyBatis 支持
@MapperScan("com.springboot.mapper")
@SpringBootApplication
public class UserinfoController {
	
	@Autowired
    private  UserinfoMapper userinfoMapper;
	private  UserinfoFilter userinfoFilter;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
        return "indextest";
    }
	@RequestMapping(value = "/getUserinfo",method = RequestMethod.POST)
    @ResponseBody
      	List<Userinfo> getAllUsers() throws IOException {
		List<Userinfo> userinfo = userinfoMapper.getAllUserinfo();
		userinfoFilter = new UserinfoFilter();
		List<Userinfo> result = userinfoFilter.getFilterUserinfo(userinfo);
		return result;
    }
}
