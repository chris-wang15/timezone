package com.tools.timezone.db

interface EntityMapper<T, R> {
    fun mapFromEntity(t: T): R

    fun mapToEntity(r: R): T
}