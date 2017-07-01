package hr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hr.configurer.StepConfigurer;
import hr.core.util.HttpsUtil;
import hr.core.util.MediaTypeExt;
import hr.core.wx.WxSign;
import hr.core.wx.WxUrl;
import hr.service.InitDataService;
import io.swagger.annotations.Api;
import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Api()
@RestController
@ResponseBody
@RequestMapping(value="/sys")
public class SystemController {
	@Autowired
	private InitDataService initDataService;
	private static Logger logger = LoggerFactory.getLogger(SystemController.class);
	@RequestMapping(path = "init",method = RequestMethod.GET)
    public String init(){
		initDataService.init();
		return "success";
    }

}
