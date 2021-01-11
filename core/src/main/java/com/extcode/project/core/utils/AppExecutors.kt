package com.extcode.project.core.utils

import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor

class AppExecutors @VisibleForTesting constructor(private val diskIO: Executor) {
    fun diskIO(): Executor = diskIO
}
