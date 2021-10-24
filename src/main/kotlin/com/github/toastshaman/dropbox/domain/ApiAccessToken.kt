package com.github.toastshaman.dropbox.domain

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class ApiAccessToken private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<ApiAccessToken>(::ApiAccessToken)
}