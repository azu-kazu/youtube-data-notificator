package org.azukazu.noti.application

import org.azukazu.noti.domain.model.line.LineUserId
import org.azukazu.noti.domain.model.youtube.Video
import org.springframework.stereotype.Component

/**
 * LINEへの通知を行うNotificator
 */
@Component
interface LineNotificator {

    /**
     * 動画情報一覧を通知する
     */
    fun notifyOfVideoInfo(videos: List<Video>, lineUserId: LineUserId)

}