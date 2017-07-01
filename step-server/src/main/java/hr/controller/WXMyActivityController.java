package hr.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hr.pb.Util.PBHttpState;
import com.hr.pb.WX.PBWxUserAccessToken;
import com.hr.pb.WXMyActivity.PBWxMyActivityPage;
import com.hr.pb.WXMyActivity.PBWxPepleExecutePageable;

import hr.core.util.HttpsUtil;
import hr.core.util.MediaTypeExt;
import hr.core.wx.WxUrl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

@Api()
@RestController
@ResponseBody
@RequestMapping(value="/wx/my_activity",consumes={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_UTF8_VALUE},produces={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_UTF8_VALUE})
public class WXMyActivityController {
    @ApiOperation(value="我的挑战")
    @RequestMapping(path="index",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxMyActivityPage index() {
    	PBWxMyActivityPage.Builder ret=PBWxMyActivityPage.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="他们都行动了-刷新列表")
    @RequestMapping(path="peple_execute",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxPepleExecutePageable pepleExecute(@ApiParam @RequestParam(name="activityId",required=true) Integer activityId,@ApiParam @RequestParam(name="firstResult",required=true) Integer firstResult) {
    	PBWxPepleExecutePageable.Builder ret=PBWxPepleExecutePageable.newBuilder();
        return ret.build();
    }

    
    
}
