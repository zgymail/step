package hr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hr.pb.WX.PBWxMenu;
import com.hr.pb.WX.PBWxMenuItem;

import hr.configurer.StepConfigurer;
@Service("InitDataService")
public class InitDataService {
	private static Logger logger = LoggerFactory.getLogger(InitDataService.class);
	@Value("classpath:database/schema.sql")
	private Resource schemaScript;
	
	@Value("classpath:database/Area.sql")
	private Resource areaScript;
	
	@Autowired
	private StepConfigurer stepConfigurer;
	
	@Autowired
	private WXService wXService;
	
	@Transactional
	public void init(){
		logger.info("创建微信菜单开始");
		PBWxMenu.Builder menu=PBWxMenu.newBuilder();
		String website=stepConfigurer.getWebsite();
		PBWxMenuItem.Builder menuButton1=PBWxMenuItem.newBuilder();
		menuButton1.setName("健康瘦");
		menuButton1.setKey("jian_kan_shou");
		menuButton1.setType("view");
		
		PBWxMenuItem.Builder menuButton1_c=PBWxMenuItem.newBuilder();
		menuButton1_c.setName("挑战瘦");
		menuButton1_c.setKey("tiao_zhan_shou");
		menuButton1_c.setType("view");
		menuButton1_c.setUrl(website+"/wx/activity/index?activityType=ActivityTypeWeight");
		menuButton1.addSubButton(menuButton1_c);
		
		PBWxMenuItem.Builder menuButton2_c=PBWxMenuItem.newBuilder();
		menuButton2_c.setName("测试授权");
		menuButton2_c.setKey("shouquan");
		menuButton2_c.setType("view");
		menuButton2_c.setUrl(wXService.getWxAuthorizeUrl());
		menuButton1.addSubButton(menuButton2_c);
		
		PBWxMenuItem.Builder menuButton3_c=PBWxMenuItem.newBuilder();
		menuButton3_c.setName("测试授权1");
		menuButton3_c.setKey("shouquan1");
		menuButton3_c.setType("view");
		menuButton3_c.setUrl("http://apptest.happyrun.cn/step/index.html");
		menuButton1.addSubButton(menuButton3_c);
		
		
		
		menu.addButton(menuButton1);
		
		
		
		PBWxMenuItem.Builder menuButton2=PBWxMenuItem.newBuilder();
		menuButton2.setName("健康挑战");
		menuButton2.setKey("jian_kan_tiao_zhan");
		menuButton2.setType("view");
		menuButton2.setUrl(website+"/wx/activity/index?activityType=ActivityTypeAll");
		menu.addButton(menuButton2);
		
		PBWxMenuItem.Builder menuButton3=PBWxMenuItem.newBuilder();
		menuButton3.setName("我的挑战");
		menuButton3.setKey("wo_de_tiao_zhan");
		menuButton3.setType("view");
		menuButton3.setUrl(website+"/wx/my_activity/index");
		menu.addButton(menuButton3);
		
		wXService.createMenu(menu.build());
		logger.info("创建微信菜单完成");
	}
}
