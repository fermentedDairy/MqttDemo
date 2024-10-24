package org.fermented.dairty

import io.micronaut.mqtt.annotation.MqttSubscriber
import io.micronaut.mqtt.annotation.Topic
import java.util.HashMap
import java.nio.charset.StandardCharsets.UTF_8

@MqttSubscriber
class MqttListener {

    val dataMap = HashMap<String, String>();

    @Topic("#")
    fun receive(@Topic topic: String, data: ByteArray) {
        val value = String(data, UTF_8)
        println("Received message: $value on topic: $topic")
        dataMap.put(topic, value)
    }
}