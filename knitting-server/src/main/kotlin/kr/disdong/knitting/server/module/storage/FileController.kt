package kr.disdong.knitting.server.module.storage

import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.annotation.AuthGuard
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.server.module.storage.spec.FileSpec
import kr.disdong.knitting.storage.common.dto.FileResponse
import kr.disdong.knitting.storage.common.service.FileService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("files")
class FileController(
    private val fileService: FileService,
) : FileSpec {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @AuthGuard
    override fun upload(
        @CurrentUserClaims claims: AccessTokenClaims,
        @RequestPart("file") file: MultipartFile
    ): FileResponse {
        return fileService.upload(claims.id, file)
    }

    @GetMapping
    @AuthGuard
    override fun getByIds(
        @CurrentUserClaims claims: AccessTokenClaims,
        @RequestParam(value = "ids") ids: String
    ): List<FileResponse> {
        return fileService.getByIds(claims.id, ids.split(",").map { it.toLong() })
    }

    @GetMapping("{id}")
    @AuthGuard
    override fun getById(
        @CurrentUserClaims claims: AccessTokenClaims,
        id: Long
    ): FileResponse {
        return fileService.getById(claims.id, id)
    }
}
