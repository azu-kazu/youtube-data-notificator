package org.azukazu.noti.infrastructure.transmission.youtube

import org.azukazu.noti.domain.model.youtube.ChannelId
import org.azukazu.noti.domain.model.youtube.PlaylistInfo
import org.azukazu.noti.domain.model.youtube.PlaylistId
import org.azukazu.noti.domain.model.youtube.VideoInfo
import org.azukazu.noti.domain.model.youtube.VideoId
import org.azukazu.noti.infrastructure.transmission.youtube.dto.ChannelsApiDto
import org.azukazu.noti.infrastructure.transmission.youtube.dto.PlaylistItemsApiDto
import org.azukazu.noti.infrastructure.transmission.youtube.dto.VideosApiDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * Youtube Data API を使用するクライアント
 */
@Component
class YoutubeClient(
    private val restTemplate: RestTemplate,
    @Value("\${youtube.api.accesstoken}")
    private val accessToken: String
) {

    companion object {
        private const val CHANNELS_API_URL = "https://www.googleapis.com/youtube/v3/channels?part=contentDetails&fields=items(contentDetails(relatedPlaylists(uploads)))"
        private const val PLAYLIST_ITEMS_API_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&fields=items(snippet(resourceId(videoId)))"
        private const val VIDEOS_API_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&fields=items(snippet(title),statistics)"
    }

    /**
     * 投稿動画に関する再生リストのIDを取得
     */
    fun fetchPlayListIdRelatedToUploadedVideo(channelId: ChannelId): PlaylistId {

        val uri = buildString {
            append(CHANNELS_API_URL)
            append("&key=$accessToken")
            append("&id=${channelId.value}")
        }

        val channelsApiDto =
            restTemplate.getForObject(
                uri,
                ChannelsApiDto::class.java)
                ?: throw IllegalArgumentException("指定したチャンネルは存在しません. チャンネルID = ${channelId.value}")

        return PlaylistId(channelsApiDto.items[0].contentDetails.relatedPlaylists.uploads)
    }

    /**
     * 再生リスト情報を取得
     */
    fun fetchPlaylistInfo(playlistId: PlaylistId): PlaylistInfo {

        val uri = buildString {
            append(PLAYLIST_ITEMS_API_URL)
            append("&key=$accessToken")
            append("&playlistId=${playlistId.value}")
        }

        val playlistItemsApiDto =
            restTemplate.getForObject(
                uri,
                PlaylistItemsApiDto::class.java)
                ?: throw IllegalArgumentException("指定した再生リストは存在しません. 再生リストID = ${playlistId.value}")

        val videoIds = playlistItemsApiDto.items
            .map { item -> VideoId(item.snippet.resourceId.videoId) }

        return PlaylistInfo(
            playlistId,
            videoIds
        )
    }

    /**
     * 動画情報を取得
     */
    fun fetchVideoInfo(videoId: VideoId): VideoInfo {

        val uri = buildString {
            append(VIDEOS_API_URL)
            append("&key=$accessToken")
            append("&id=${videoId.value}")
        }

        val videosApiDtos =
            restTemplate.getForObject(
                uri,
                VideosApiDto::class.java)
                ?: throw IllegalArgumentException("指定した動画は存在しません. 動画ID = ${videoId.value}")

        return VideoInfo(
            videoId,
            videosApiDtos.items[0].snippet.title,
            videosApiDtos.items[0].statistics.viewCount,
            videosApiDtos.items[0].statistics.likeCount,
            videosApiDtos.items[0].statistics.dislikeCount,
            videosApiDtos.items[0].statistics.commentCount
        )
    }
}
