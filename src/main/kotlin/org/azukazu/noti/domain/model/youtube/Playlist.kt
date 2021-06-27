package org.azukazu.noti.domain.model.youtube

/**
 * 再生リスト情報
 */
class Playlist(

    /** 再生リストID */
    val id: PlaylistId,

    /** 動画ID一覧 */
    val videoIds: List<VideoId>
)
