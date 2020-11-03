package org.azukazu.noti.infrastructure.transmission.youtube.dto

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
