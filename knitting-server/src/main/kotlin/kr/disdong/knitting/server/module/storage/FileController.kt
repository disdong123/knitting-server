package kr.disdong.knitting.server.module.storage

import kr.disdong.knitting.storage.common.FileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("files")
class FileController(
    private val fileService: FileService,
) {

    @PostMapping
    fun upload(file: MultipartFile) {
        return fileService.upload(file)
    }

    @GetMapping("{id}")
    fun getById(id: Long) {

    }
}
