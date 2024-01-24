package ru.protei.barbolinsp.domain

sealed class StateLoading <out T>  {
    object Loading : StateLoading<Nothing>()
    data class Success<T>(val data: T) : StateLoading<T>()
    data class Error(val exception: Exception) : StateLoading<Nothing>()
}