package org.azukazu.noti.infrastructure.transmission.line

import org.azukazu.noti.domain.model.youtube.VideoInfo

/**
 * 動画情報DTO(LINE通知用)
 */
data class VideoInfoDto (
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
){

    /**
     * 文章の生成
     */
    fun generateSentence(): String =
        """
            動画タイトル：${title}
            再生回数：${viewCount}
            高評価数：${likeCount}
            低評価数：${dislikeCount}
            コメント数：${commentCount}
            """.trimIndent()

    companion object {

        fun from(videoInfo: VideoInfo): VideoInfoDto =
            VideoInfoDto(
                videoInfo.title,
                videoInfo.viewCount,
                videoInfo.likeCount,
                videoInfo.dislikeCount,
                videoInfo.commentCount
            )
    }
}
