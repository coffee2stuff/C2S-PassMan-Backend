package com.peteralexbizjak.utils.helpers

fun <T> concatenate(vararg lists: List<T>): List<T> = listOf(*lists).flatten()