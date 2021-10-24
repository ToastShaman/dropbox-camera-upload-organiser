package com.github.toastshaman.dropbox.commands

import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.ListFolderResult
import com.dropbox.core.v2.files.RelocationBatchV2Result
import com.dropbox.core.v2.files.RelocationPath
import com.github.toastshaman.dropbox.domain.Path
import com.github.toastshaman.dropbox.domain.formatAsYearMonth
import com.github.toastshaman.dropbox.domain.toLocalDate
import com.github.toastshaman.dropbox.retry.retry
import org.slf4j.LoggerFactory
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import java.time.Duration
import java.util.concurrent.Callable

// https://www.dropbox.com/developers/apps/
// https://github.com/dropbox/dropbox-sdk-java
class CameraUploads(
    private val client: DbxClientV2
) {

    fun prepareFileList(source: Path, destination: Path) = listFiles(source).map {
        val lastModified = it.clientModified.toLocalDate().formatAsYearMonth()
        val newDestination = destination.add(lastModified).add(it.name)
        RelocationPath(it.pathLower, newDestination.value)
    }

    fun moveBatch(
        actions: List<RelocationPath>,
        onRetry: (String) -> Unit = {}
    ): RelocationBatchV2Result {
        val start = client.files()
            .moveBatchV2Builder(actions)
            .withAutorename(true)
            .start()

        return retry(delay = Duration.ofSeconds(5)) {
            onRetry(start.asyncJobIdValue)
            val response = client.files().copyBatchCheckV2(start.asyncJobIdValue)
            if (!response.isComplete) error("""${start.asyncJobIdValue} not yet finished""") else response.completeValue
        }
    }

    private fun listFiles(path: Path) = generateSequence({ client.files().listFolder(path.value) }) {
        if (it.hasMore) client.files().listFolderContinue(it.cursor) else null
    }.flatMap(ListFolderResult::getEntries).filterIsInstance<FileMetadata>()
}


