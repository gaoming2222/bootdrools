package com.springboot.web;

import java.io.IOException;
import java.io.InputStream;
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
    public  UserinfoMapper userinfoMapper;
	public  UserinfoFilter userinfoFilter;
	
	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
	
	@RequestMapping("/1")
    @ResponseBody
    String home2() {
        return "Hello World!";
    }

//	@RequestMapping("/getUserinfo")
//    @ResponseBody
//    String getAllUsers() {
//		List<Userinfo> userinfo = userinfoMapper.getAllUserinfo();
//		return JsonUtility.convertBean2Json(userinfo);
//    }
	@RequestMapping("/getUserinfo")
    @ResponseBody
      List<Userinfo> getAllUsers() throws IOException {
		List<Userinfo> userinfo = userinfoMapper.getAllUserinfo();
		//List<Userinfo> result = userinfoFilter.getFilterUserinfo(userinfo);
		List<Userinfo> result = null;
		String url = "http://127.0.0.1:8080/kie-drools-wb/maven2/com/myteam/gm4/1.0.0/gm4-1.0.0.jar";
		ReleaseIdImpl releaseId = null;
		releaseId = new ReleaseIdImpl("com.myteam", "gm4", "LATEST");
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(url);
		urlResource.setUsername("admin");
		urlResource.setPassword("admin");
		urlResource.setBasicAuthentication("enabled");
		InputStream is = urlResource.getInputStream();
		KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
		KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
		KieSession kieSession = kContainer.newKieSession();
		for(int i = 0;i<userinfo.size();i++){
			Userinfo userinfo1 = userinfo.get(i);
			kieSession.insert(userinfo1);
		}
		QueryResults queryResults = kieSession.getQueryResults("person age is 30");
		for (QueryResultsRow q : queryResults) {
			Userinfo u = (Userinfo) q.get("userinfo");
			System.out.println(u.getUsername());
			result.add(u);
		}
		return result;
		//return result;
    }

}
