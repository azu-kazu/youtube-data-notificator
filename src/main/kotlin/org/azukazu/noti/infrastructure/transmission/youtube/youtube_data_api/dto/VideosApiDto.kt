package org.azukazu.noti.infrastructure.transmission.youtube.youtube_data_api

data class VideosApiDto(
    val items: List<ItemsDtoForVideos>
)

data class ItemsDtoForVideos(
    val snippet: SnippetDtoForVideos,
    val statistics: StatisticsDto
)

data class SnippetDtoForVideos(
    val title: String
)

data class StatisticsDto(
    val viewCount: Long,
    val likeCount: Long,
    val dislikeCount: Long,
    val favoriteCount: Long,
    val commentCount: Long
)
