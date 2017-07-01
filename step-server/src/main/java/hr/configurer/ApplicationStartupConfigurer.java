package hr.configurer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import hr.core.util.SpringContextUtil;
import hr.service.InitDataService;
@Configuration
public class ApplicationStartupConfigurer  implements ApplicationListener<ContextRefreshedEvent>{
	private static boolean started=false; 
	@Override
	public final void onApplicationEvent(ContextRefreshedEvent event) {
		if(!started){
			started=true;
			ApplicationContext applicationContext= event.getApplicationContext();
			SpringContextUtil.setApplicationContext(applicationContext);
		}
	}
	


}
