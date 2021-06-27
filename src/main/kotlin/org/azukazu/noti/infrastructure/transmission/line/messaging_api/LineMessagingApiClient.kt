package org.azukazu.noti.infrastructure.transmission.line.messaging_api

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import org.azukazu.noti.application.LineNotificator
import org.azukazu.noti.domain.model.line.LineUserId
import org.azukazu.noti.domain.model.youtube.Video
import org.springframework.stereotype.Component

/**
 * LINE Messaging API を使用するクライアント
 */
@Component
@LineMessageHandler
class LineMessagingApiClient(
    private val lineMessagingClient: LineMessagingClient
) : LineNotificator {

    override fun notifyOfVideoInfo(videos: List<Video>, lineUserId: LineUserId) {

        val sentence = generateSentence(videos)

        lineMessagingClient
            .pushMessage(PushMessage(lineUserId.value, TextMessage(sentence)))
            .get()
    }

    private fun generateSentence(videos: List<Video>): String =
        videos
            .joinToString("\n\n") { VideoInfoDto.from(it).generateSentence() }
}
