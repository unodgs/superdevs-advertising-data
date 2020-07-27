package com.dgs.advertisingdata

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.dgs.advertisingdata")
        .start()
}

