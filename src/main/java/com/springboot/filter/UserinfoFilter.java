package com.springboot.filter;

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

import com.springboot.entity.*;;

public class UserinfoFilter {
	
	public List<Userinfo> getFilterUserinfo(List<Userinfo> input) throws IOException{
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
		for(int i = 0;i<input.size();i++){
			Userinfo userinfo = input.get(i);
			kieSession.insert(userinfo);
		}
		QueryResults queryResults = kieSession.getQueryResults("person age is 30");
		for (QueryResultsRow q : queryResults) {
			Userinfo u = (Userinfo) q.get("userinfo");
			System.out.println(u.getUsername());
			result.add(u);
		}
		return result;
	}

}
