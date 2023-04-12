package kr.disdong.knitting.server.module.storage.spec

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.disdong.knitting.auth.kakao.dto.AccessTokenClaims
import kr.disdong.knitting.common.annotation.CurrentUserClaims
import kr.disdong.knitting.storage.common.dto.FileResponse
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Tag(name = "파일", description = "파일용 api 입니다.")
interface FileSpec {

    @Operation(
        summary = "파일 업로드 api",
        // parameters = [
        //     Parameter(
        //         name = "file",
        //         description = "파일",
        //         content = [Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)],
        //     ),
        // ],
    )
    fun upload(
        @CurrentUserClaims claims: AccessTokenClaims,
        @RequestPart("file") file: MultipartFile,
    ): FileResponse

    @Operation(
        summary = "단건 파일 조회 api",
    )
    fun getById(id: Long): FileResponse

    @Operation(
        summary = "다건 파일 조회 api",
    )
    fun getByIds(
        @RequestParam(value = "ids") ids: List<Long>
    ): List<FileResponse>
}
