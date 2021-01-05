package com.zungen.mp.modules.iot.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 通过接口把数据写到mqttOutboundChannel通道，不需要实现
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    /**
     * 订阅所有台区终端的信息（被动）
     */
    String MQTT_TOPIC_SUB_TERMINAL_NOTIFY = "device/+/topo/notify/report/terminalInfo";
    /**
     * 订阅所有台区终端的信息（主动）
     */
    String MQTT_TOPIC_SUB_TERMINAL = "device/+/topo/get/response/terminalInfo";
    /**
     * 订阅所有台区终端的拓扑数据
     */
    String MQTT_TOPIC_SUB_TOPOLOGY = "device/+/topo/action/response/topology";
    /**
     * 订阅所有台区终端的分析报告
     */
    String MQTT_TOPIC_SUB_REPORT = "device/+/topo/get/response/analysisReport";

    /**
     * 请求获取台区终端的信息
     */
    String MQTT_TOPIC_PUB_TERMINAL = "server/{}/zungen/get/request/terminalInfo";
    /**
     * 请求获取某个台区终端的拓扑数据
     */
    String MQTT_TOPIC_PUB_TOPOLOGY = "server/{}/zungen/action/request/topology";
    /**
     * 请求获取某个台区终端的分析报告
     */
    String MQTT_TOPIC_PUB_REPORT = "server/{}/zungen/get/request/analysisReport";


    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);

}
