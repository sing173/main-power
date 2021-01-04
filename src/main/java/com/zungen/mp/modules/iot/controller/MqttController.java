package com.zungen.mp.modules.iot.controller;


import com.zungen.mp.common.api.CommonResult;
import com.zungen.mp.modules.iot.service.MqttGateway;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "MqttController", description = "电力智能管理终端交互接口")
@RequestMapping("/mqtt")
public class MqttController {
    @Autowired
    private MqttGateway mqttGateway;

    @ApiOperation(value = "发送主题测试")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult sendMqtt(@RequestParam(value = "data") String data,
                                 @RequestParam(value = "topic") String topic){
        mqttGateway.sendToMqtt(data, topic);
        return CommonResult.success(null);
    }


}
