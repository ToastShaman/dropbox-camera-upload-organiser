package com.github.toastshaman.dropbox.retry

import java.time.Duration

fun <R> retry(
    maxAttempts: Int = Int.MAX_VALUE,
    delay: Duration = Duration.ofSeconds(0),
    action: () -> R
): R {
    require(maxAttempts > 0) { "maxAttempts must be greater than 0" }
    return runCatching(action).getOrElse {
        val leftAttempts = maxAttempts.dec()
        if (leftAttempts == 0) throw it
        runCatching { Thread.sleep(delay.toMillis()) }
        retry(leftAttempts, delay, action)
    }
}