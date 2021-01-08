package com.zungen.mp.modules.iot.model.vo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * mqtt自定义消息
 * @author luomingxing
 * @param <T> 对应各接口模型
 */
@Data
public class MqttMessage<T> {
    /**
     * 消息标识，相同源发出的相同类型消息的 token 应该各不相同，
     * 可用 UUID、随机数或自增长数值等手段。
     */
    private String token;
    /**
     * 消息产生的时间戳
     */
    private String timestamp;
    /**
     * 标记“body是否被压缩及压缩算法，后期扩展使用
     * 0：表示body未压缩
     */
    private int compress = 0;
    /**
     * 标记“body是否被加密及加密算法，后期扩展使用
     * 0：表示body未加密
     */
    private int encryption = 0;
    /**
     * 消息体，JSON 格式字符串，可选。可被压缩及加密。
     * 加密和压缩顺序为：先加密，后压缩
     */
    private T body;

    public static MqttMessage<Map<String, Object>> getDefaultMessage(Map<String, Object> data) {
        MqttMessage<Map<String, Object>> message = new MqttMessage<>();
        message.setToken(IdUtil.fastSimpleUUID());
        message.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        message.setBody(data);
        return message;
    }
}
