package org.azukazu.noti.infrastructure.transmission.youtube.dto

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
