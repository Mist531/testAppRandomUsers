package com.example.testapprandomusers.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge

fun <T1, T2, R> Flow<T1>.combineWithDefault(
    other: Flow<T2>,
    default: T2,
    transform: suspend (a: T1, b: T2) -> R
): Flow<R> = this.combine(
    merge(other, flowOf(default)),
    transform
)