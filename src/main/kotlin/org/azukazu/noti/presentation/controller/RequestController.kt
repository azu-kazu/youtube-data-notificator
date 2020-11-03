package org.azukazu.noti.presentation.controller

import org.azukazu.noti.application.NotificationApplicationService
import org.azukazu.noti.domain.model.youtube.ChannelId
import org.azukazu.noti.domain.model.line.LineUserId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 通知依頼を受け付けるコントローラ
 */
@RestController
@RequestMapping("/noti-request")
class RequestController(
    private val notificationApplicationService: NotificationApplicationService
) {

    @PostMapping
    fun request(@RequestBody request: NotificationRequest) =
        notificationApplicationService.notifyLineOfUploadedVideoInfo(
            ChannelId(request.channelId),
            LineUserId(request.lineUserId))
}

data class NotificationRequest(
    val channelId: String,
    val lineUserId: String
)
