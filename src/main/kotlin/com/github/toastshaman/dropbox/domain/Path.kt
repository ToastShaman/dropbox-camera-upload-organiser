package com.github.toastshaman.dropbox.domain

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class Path private constructor(value: String) : StringValue(value.lowercase()) {
    fun add(p: String) = of((value.split("/") + p).joinToString("/") { it })

    companion object : NonBlankStringValueFactory<Path>(::Path)
}