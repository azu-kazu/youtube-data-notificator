package org.azukazu.noti.infrastructure.transmission.youtube.youtube_data_api

import org.azukazu.noti.application.YoutubeClient
import org.azukazu.noti.domain.model.youtube.*
import org.azukazu.noti.infrastructure.transmission.youtube.youtube_data_api.dto.ChannelsApiDto
import org.azukazu.noti.infrastructure.transmission.youtube.youtube_data_api.dto.PlaylistItemsApiDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * Youtube Data API を使用するクライアント
 */
@Component
class YoutubeDataApiClient(
    private val restTemplate: RestTemplate,
    @Value("\${youtube.api.accesstoken}")
    private val accessToken: String
) : YoutubeClient {

    companion object {
        private const val CHANNELS_API_URL = "https://www.googleapis.com/youtube/v3/channels"
        private const val PLAYLIST_ITEMS_API_URL = "https://www.googleapis.com/youtube/v3/playlistItems"
        private const val VIDEOS_API_URL = "https://www.googleapis.com/youtube/v3/videos"
    }

    override fun fetchPlayListIdRelatedToUploadedVideo(channelId: ChannelId): PlaylistId {

        val uri = buildString {
            append(CHANNELS_API_URL)
            append("?part=contentDetails")
            append("&fields=items(contentDetails(relatedPlaylists(uploads)))") // 投稿動画の再生リストを指定
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

    override fun fetchPlaylist(playlistId: PlaylistId): Playlist {

        val uri = buildString {
            append(PLAYLIST_ITEMS_API_URL)
            append("?part=snippet")
            append("&fields=items(snippet(resourceId(videoId)))")
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

        return Playlist(
            playlistId,
            videoIds
        )
    }

    override fun fetchVideo(videoId: VideoId): Video {

        val uri = buildString {
            append(VIDEOS_API_URL)
            append("?part=snippet,statistics")
            append("&fields=items(snippet(title),statistics)")
            append("&key=$accessToken")
            append("&id=${videoId.value}")
        }

        val videosApiDtos =
            restTemplate.getForObject(
                uri,
                VideosApiDto::class.java)
                ?: throw IllegalArgumentException("指定した動画は存在しません. 動画ID = ${videoId.value}")

        return Video(
            videoId,
            videosApiDtos.items[0].snippet.title,
            videosApiDtos.items[0].statistics.viewCount,
            videosApiDtos.items[0].statistics.likeCount,
            videosApiDtos.items[0].statistics.dislikeCount,
            videosApiDtos.items[0].statistics.commentCount
        )
    }
}
