package com.github.toastshaman.dropbox

import com.github.toastshaman.dropbox.commands.CameraUploadsCommand
import picocli.CommandLine
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    exitProcess(CommandLine(CameraUploadsCommand()).execute(*args))
}