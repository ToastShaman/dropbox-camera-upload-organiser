package com.github.toastshaman.dropbox.commands

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.github.toastshaman.dropbox.domain.ApiAccessToken
import com.github.toastshaman.dropbox.domain.Path
import org.slf4j.LoggerFactory
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.util.concurrent.Callable

@Command(
    name = "camera-uploads",
    mixinStandardHelpOptions = true,
    version = ["camera-uploads 1.0"]
)
class CameraUploadsCommand : Callable<Int> {

    private val log = LoggerFactory.getLogger("CameraUploadsCommand")

    @Parameters(index = "0", description = ["The source directory"])
    private lateinit var sourcePath: String

    @Parameters(index = "1", description = ["The destination directory"])
    private lateinit var destinationPath: String

    override fun call(): Int {
        val accessToken = System.getenv("DROPBOX_API_ACCESS_TOKEN").let(ApiAccessToken::of)

        val client = DbxClientV2(
            DbxRequestConfig.newBuilder("u83Vbu1LYv")
                .withAutoRetryEnabled()
                .build(),
            accessToken.value
        )

        val uploads = CameraUploads(client)

        val fileList = uploads
            .prepareFileList(Path.of(sourcePath), Path.of(destinationPath))
            .toList()

        log.info("Moving ${fileList.size} file(s)")

        uploads.moveBatch(fileList) {
            log.info("Waiting for job $it to complete...")
        }

        log.info("${fileList.size} file(s) successfully moved")

        return fileList.size
    }
}


