package org.azukazu.noti.infrastructure.transmission.line

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import org.azukazu.noti.domain.model.youtube.LineUserId
import org.azukazu.noti.domain.model.youtube.VideoInfo
import org.springframework.stereotype.Component

/**
 * LINEへの通知を行うNotificator
 */
@Component
@LineMessageHandler
class LineNotificator(
    private val lineMessagingClient: LineMessagingClient
) {

    /**
     * 動画情報一覧を通知する
     */
    fun notifyToLine(videoInfos: List<VideoInfo>, lineUserId: LineUserId) {

        val sentenceForLine = generateSentence(videoInfos)

        lineMessagingClient
            .pushMessage(PushMessage(lineUserId.value, TextMessage(sentenceForLine)))
            .get()
    }

    private fun generateSentence(videoInfos: List<VideoInfo>): String =
        videoInfos
            .joinToString("\n\n") { VideoInfoDto.from(it).generateSentence() }
}
