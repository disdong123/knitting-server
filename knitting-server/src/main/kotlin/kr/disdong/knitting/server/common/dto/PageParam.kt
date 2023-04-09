package kr.disdong.knitting.server.common.dto

import org.springframework.web.bind.annotation.RequestParam

class PageParam(
    @RequestParam(defaultValue = "1")
    val page: Int = 1,
    @RequestParam(defaultValue = "10")
    val size: Int = 10,
)
