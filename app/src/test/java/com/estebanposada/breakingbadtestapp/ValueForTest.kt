package com.estebanposada.breakingbadtestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList

fun <T> LiveData<T>.getValueForTest(): T? {
    var value: T? = null
    val observer = Observer<T> {
        value = it
    }
    observeForever(observer)
    removeObserver(observer)
    return value
}

fun <T> LiveData<PagedList<T>>.fetchData() {
    getValueForTest()!!
}

fun <T> LiveData<PagedList<T>>.fetchOneMorePage() {
    val pagedList = getValueForTest()!!
    val lastLoadedItemIndex = pagedList.loadedCount - 1
    pagedList.loadAround(lastLoadedItemIndex)
}