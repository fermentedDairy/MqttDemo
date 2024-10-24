package org.fermented.dairty

import io.micronaut.mqtt.annotation.Topic
import io.micronaut.mqtt.annotation.v5.MqttPublisher
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets

@MicronautTest
class MqttListenerTest {
    @Inject
    lateinit var listener: MqttListener

    @Inject
    lateinit var publisher: TestMqttPublisher

    @Test
    fun checkSubscriptionsAreReceived() {
        val publishedvalue = "3.145"
        publisher.publish(publishedvalue.toByteArray(StandardCharsets.UTF_8))
        Thread.sleep(1000)
        assertAll("Validating data store",
            { assertTrue(listener.dataMap.containsKey("test/Topic")) },
            { assertEquals(publishedvalue, listener.dataMap["test/Topic"]) }
        )
    }

    @MqttPublisher
    interface TestMqttPublisher {

        @Topic("test/Topic")
        fun publish(data: ByteArray?)
    }
}