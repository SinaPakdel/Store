package com.sina.core.model.tools

fun interface Mapper<F, T> {
    fun map(from: F): T
}
fun <F, T> Mapper<F, T>.map(collection: Collection<F>) = collection.map { F -> map(F) }