package org.azukazu.noti.infrastructure.transmission.line.messaging_api

import org.azukazu.noti.domain.model.youtube.Video

/**
 * 動画情報DTO
 * Youtube Data API（Videos）
 */
data class VideoInfoDto(
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
) {

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

        fun from(video: Video): VideoInfoDto =
            VideoInfoDto(
                video.title,
                video.viewCount,
                video.likeCount,
                video.dislikeCount,
                video.commentCount
            )
    }
}
