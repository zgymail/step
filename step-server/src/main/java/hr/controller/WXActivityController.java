package hr.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.pb.Author.PBUser;
import com.hr.pb.Author.PBUser1;
import com.hr.pb.Util.PBErrorMessage;
import com.hr.pb.Util.PBHttpState;
import com.hr.pb.Util.PBSubmitResult;
import com.hr.pb.WXActivity.PBWxActivity;
import com.hr.pb.WXActivity.PBWxActivityDetailPage;
import com.hr.pb.WXActivity.PBWxActivityPage;
import com.hr.pb.WXActivity.PBWxActivityPublishExpendModify;
import com.hr.pb.WXActivity.PBWxActivityPublishIntroduceModify;
import com.hr.pb.WXActivity.PBWxActivityPublishLogoModify;
import com.hr.pb.WXActivity.PBWxActivityPublishPage;
import com.hr.pb.WXActivity.PBWxActivityPublishShareModify;
import com.hr.pb.WXActivity.PBWxActivityPublishStartStopTimeModify;
import com.hr.pb.WXActivity.PBWxActivityPublishSubmit;
import com.hr.pb.WXActivity.PBWxActivityPublishTaskModify;
import com.hr.pb.WXActivity.PBWxActivityPublishTitleModify;
import com.hr.pb.WXActivity.PBWxActivityType;

import hr.core.util.MediaTypeExt;
import hr.core.wx.WxUserAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api()
@RestController
@ResponseBody
@RequestMapping(value="/wx/activity",consumes={MediaTypeExt.APPLICATION_JSON_UTF8_VALUE,MediaTypeExt.APPLICATION_PROTOBUF_VALUE},produces={MediaTypeExt.APPLICATION_JSON_UTF8_VALUE,MediaTypeExt.APPLICATION_PROTOBUF_VALUE})
public class WXActivityController {
    @ApiOperation(value="挑战列表页")
    @RequestMapping(path="index",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPage index(@RequestParam(value="activityType",defaultValue="ActivityTypeAll") PBWxActivityType activityType) {
    	PBWxActivityPage.Builder ret=PBWxActivityPage.newBuilder();
    	ret.setTitle("挑战列表页");
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页")
    @RequestMapping(path="publish",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishPage publish() {
    	PBWxActivityPublishPage.Builder ret=PBWxActivityPublishPage.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改Logo")
    @RequestMapping(path="publish_prep_modify_logo",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishLogoModify publishPrepModifyLogo(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishLogoModify.Builder ret=PBWxActivityPublishLogoModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改Logo")
    @RequestMapping(path="publish_modify_logo",method=RequestMethod.POST)
    public PBSubmitResult publishModifyLogo(@RequestBody PBWxActivityPublishLogoModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改标题")
    @RequestMapping(path="publish_prep_modify_title",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishTitleModify publishPrepModifyTitle(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishTitleModify.Builder ret=PBWxActivityPublishTitleModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改标题")
    @RequestMapping(path="publish_modify_title",method=RequestMethod.POST)
    public PBSubmitResult publishModifyTitle(@RequestBody PBWxActivityPublishTitleModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改分享")
    @RequestMapping(path="publish_prep_modify_share",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishShareModify publishPrepModifyShare(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishShareModify.Builder ret=PBWxActivityPublishShareModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改分享")
    @RequestMapping(path="publish_modify_share",method=RequestMethod.POST)
    public PBSubmitResult publishModifyShare(@RequestBody PBWxActivityPublishShareModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改介绍")
    @RequestMapping(path="publish_prep_modify_introduce",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishIntroduceModify publishPrepModifyIntroduce(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishIntroduceModify.Builder ret=PBWxActivityPublishIntroduceModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改介绍")
    @RequestMapping(path="publish_modify_introduce",method=RequestMethod.POST)
    public PBSubmitResult publishModifyIntroduce(@RequestBody PBWxActivityPublishIntroduceModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改费用")
    @RequestMapping(path="publish_prep_modify_expend",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishExpendModify publishPrepModifyExpend(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishExpendModify.Builder ret=PBWxActivityPublishExpendModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改费用")
    @RequestMapping(path="publish_modify_expend",method=RequestMethod.POST)
    public PBSubmitResult publishModifyExpend(@RequestBody PBWxActivityPublishExpendModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改起止日期")
    @RequestMapping(path="publish_prep_modify_startstoptime",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishStartStopTimeModify publishPrepModifyStartStopTime(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishStartStopTimeModify.Builder ret=PBWxActivityPublishStartStopTimeModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改起止日期")
    @RequestMapping(path="publish_modify_startstoptime",method=RequestMethod.POST)
    public PBSubmitResult publishModifyStartStopTime(@RequestBody PBWxActivityPublishStartStopTimeModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-准备-修改任务")
    @RequestMapping(path="publish_prep_modify_task",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityPublishTaskModify publishPrepModifyTask(@RequestParam(name="activityPublishId",required=true) Integer activityPublishId) {
    	PBWxActivityPublishTaskModify.Builder ret=PBWxActivityPublishTaskModify.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战发布页-修改任务")
    @RequestMapping(path="publish_modify_task",method=RequestMethod.POST)
    public PBSubmitResult publishModifyTask(@RequestBody PBWxActivityPublishTaskModify bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    
    @ApiOperation(value="挑战发布页-发布挑战")
    @RequestMapping(path="publish",method=RequestMethod.POST)
    public PBSubmitResult publish(@RequestBody PBWxActivityPublishSubmit bodyData) {
    	PBSubmitResult.Builder ret=PBSubmitResult.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="挑战详情页")
    @RequestMapping(path="detail",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxActivityDetailPage detail(@RequestParam(name="activityId",required=true) Integer activityId) {
    	PBWxActivityDetailPage.Builder ret=PBWxActivityDetailPage.newBuilder();
        return ret.build();
    }

    
    
}
