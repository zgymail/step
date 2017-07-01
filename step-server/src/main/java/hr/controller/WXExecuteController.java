package hr.controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.pb.WXExecute.PBWxExecuteDietPrepare;
import com.hr.pb.WXExecute.PBWxExecuteDietSubmit;
import com.hr.pb.WXExecute.PBWxExecuteEarlyPrepare;
import com.hr.pb.WXExecute.PBWxExecuteEarlySubmit;
import com.hr.pb.WXExecute.PBWxExecuteStepPrepare;
import com.hr.pb.WXExecute.PBWxExecuteStepSubmit;
import com.hr.pb.WXExecute.PBWxExecuteWeightPrepare;
import com.hr.pb.WXExecute.PBWxExecuteWeightSubmit;
import com.hr.pb.WXMyActivity.PBWxMyActivityPage;
import com.hr.pb.WXMyActivity.PBWxPepleExecutePageable;

import hr.core.util.MediaTypeExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api()
@RestController
@ResponseBody
@RequestMapping(value="/wx/execute",consumes={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_UTF8_VALUE},produces={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_UTF8_VALUE})
public class WXExecuteController {
    @ApiOperation(value="早起打卡-准备")
    @RequestMapping(path="early_prep",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxExecuteEarlyPrepare earlyPrep() {
    	PBWxExecuteEarlyPrepare.Builder ret=PBWxExecuteEarlyPrepare.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="早起打卡")
    @RequestMapping(path="early",method=RequestMethod.POST)
    public PBWxExecuteEarlySubmit early(@RequestBody PBWxExecuteEarlySubmit bodyData) {
    	PBWxExecuteEarlySubmit.Builder ret=PBWxExecuteEarlySubmit.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="计步打卡-准备")
    @RequestMapping(path="step_prep",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxExecuteStepPrepare stepPrep() {
    	PBWxExecuteStepPrepare.Builder ret=PBWxExecuteStepPrepare.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="计步打卡")
    @RequestMapping(path="step",method=RequestMethod.POST)
    public PBWxExecuteStepSubmit step(@RequestBody PBWxExecuteStepSubmit bodyData) {
    	PBWxExecuteStepSubmit.Builder ret=PBWxExecuteStepSubmit.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="体重打卡-准备")
    @RequestMapping(path="weight_prep",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxExecuteWeightPrepare weightPrep() {
    	PBWxExecuteWeightPrepare.Builder ret=PBWxExecuteWeightPrepare.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="体重打卡")
    @RequestMapping(path="weight",method=RequestMethod.POST)
    public PBWxExecuteWeightSubmit step(@RequestBody PBWxExecuteWeightSubmit bodyData) {
    	PBWxExecuteWeightSubmit.Builder ret=PBWxExecuteWeightSubmit.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="饮食打卡-准备")
    @RequestMapping(path="diet_prep",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    public PBWxExecuteDietPrepare dietPrep() {
    	PBWxExecuteDietPrepare.Builder ret=PBWxExecuteDietPrepare.newBuilder();
        return ret.build();
    }
    
    @ApiOperation(value="饮食打卡")
    @RequestMapping(path="diet",method=RequestMethod.POST)
    public PBWxExecuteDietSubmit step(@RequestBody PBWxExecuteDietSubmit bodyData) {
    	PBWxExecuteDietSubmit.Builder ret=PBWxExecuteDietSubmit.newBuilder();
        return ret.build();
    }
    
    
}
