package org.azukazu.noti.application

import org.azukazu.noti.domain.model.youtube.ChannelId
import org.azukazu.noti.domain.model.youtube.LineUserId
import org.azukazu.noti.infrastructure.transmission.line.LineNotificator
import org.azukazu.noti.infrastructure.transmission.youtube.YoutubeClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 通知を行うアプリケーションサービス
 */
@Service
class NotificationApplicationService(
    private val lineNotificator: LineNotificator,
    private val youtubeClient: YoutubeClient
) {

    /**
     * 指定したチャンネルの投稿動画情報をLINEに通知する
     */
    fun notifyLineOfUploadedVideoInfo(channelId: ChannelId, lineUserId: LineUserId) {
        logger.info("投稿動画情報の通知を開始. チャンネルID = {}, LINEユーザID = {}", channelId.value, lineUserId.value)

        val playListIdRelatedToUploadedVideo = youtubeClient.fetchPlayListIdRelatedToUploadedVideo(channelId)
        val playlistInfoRelatedToUploadedVideo = youtubeClient.fetchPlaylistInfo(playListIdRelatedToUploadedVideo)

        val videos = playlistInfoRelatedToUploadedVideo.videoIds
            .map { videoId -> youtubeClient.fetchVideoInfo(videoId) }

        lineNotificator.notifyToLine(videos, lineUserId)
        logger.info("投稿動画情報の通知が成功. チャンネルID = {}, LINEユーザID = {}", channelId.value, lineUserId.value)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationApplicationService::class.java)
    }
}
