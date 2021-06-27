package org.azukazu.noti.infrastructure.transmission.youtube.youtube_data_api.dto

/**
 * 動画情報DTO
 * Youtube Data API（Playlists）
 */
data class PlaylistItemsApiDto(
    val items: List<ItemsDtoForPlaylistItems>
)

data class ItemsDtoForPlaylistItems(
    val snippet: SnippetDtoForPlaylistItems
)

data class SnippetDtoForPlaylistItems(
    val resourceId: ResourceIdDto
)

data class ResourceIdDto(
    val videoId: String
)
