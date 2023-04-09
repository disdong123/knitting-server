package kr.disdong.knitting.server.common.dto

class PagedList<T>(
    val page: Int,
    val size: Int,
    val total: Int,
    val items: List<T>
)
