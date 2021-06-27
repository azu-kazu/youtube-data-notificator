package org.azukazu.noti.infrastructure.transmission.youtube.youtube_data_api.dto

/**
 * 動画情報DTO
 * Youtube Data API（Channels）
 */
data class ChannelsApiDto(
    val items: List<ItemsDtoForChannels>
)

data class ItemsDtoForChannels(
    val contentDetails: ContentDetailsDto
)

data class ContentDetailsDto(
    val relatedPlaylists: RelatedPlaylistsDto
)

data class RelatedPlaylistsDto(
    val uploads: String
)
