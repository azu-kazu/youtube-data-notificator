package org.azukazu.noti.domain.model.youtube

/**
 * 動画情報
 */
class VideoInfo (

    /** 動画ID */
    val id: VideoId,

    /** タイトル */
    val title: String,

    /** 視聴回数 */
    val viewCount: Long,

    /** 高評価数 */
    val likeCount: Long,

    /** 低評価数 */
    val dislikeCount: Long,

    /** コメント数 */
    val commentCount: Long
)
