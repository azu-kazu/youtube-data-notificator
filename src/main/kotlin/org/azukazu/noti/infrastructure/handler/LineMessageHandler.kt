package org.azukazu.noti.infrastructure.handler

import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory

@Slf4j
@LineMessageHandler
class LineMessageHandler {

    @EventMapping
    fun handleText(receiveInfoFromLine: MessageEvent<TextMessageContent?>): TextMessage {
        logger.info("LINEからの情報 = {}", receiveInfoFromLine)

        // 送信
        return TextMessage(receiveInfoFromLine.message.toString())
    }

    @EventMapping
    fun handleEvent(event: Event) {
        logger.info("event: $event")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LineMessageHandler::class.java)
    }
}
