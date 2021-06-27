package org.azukazu.noti.application

import org.azukazu.noti.domain.model.youtube.*
import org.springframework.stereotype.Component

/**
 * Youtube情報を取得するクライアント
 */
@Component
interface YoutubeClient {

    /**
     * 投稿動画に関する再生リストのIDを取得
     */
    fun fetchPlayListIdRelatedToUploadedVideo(channelId: ChannelId): PlaylistId

    /**
     * 再生リストを取得
     */
    fun fetchPlaylist(playlistId: PlaylistId): Playlist

    /**
     * 動画情報を取得
     */
    fun fetchVideo(videoId: VideoId): Video
}
