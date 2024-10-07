
package com.example.androidbootcampiwatepref.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import datastore.AppDataStore
import kotlinx.coroutines.flow.update

class MainViewModel(private val dataStore: AppDataStore) : ViewModel() {

    // 各インデックスの「いいね」数を保持するリスト
    private val _counts = MutableStateFlow(List(10) { 0 })//リストに変更して複数のカウントを保持
    val counts: StateFlow<List<Int>> = _counts.asStateFlow()

    init {
        // 各インデックスの「いいね」数をデータストアから取得
        (0 until 10).forEach { index ->
            viewModelScope.launch {
                dataStore.getCount(index).collect { count ->
                    _counts.update { currentList ->
                        currentList.toMutableList().also { it[index] = count }
                    }
                }
            }
        }
    }

    // インデックスごとの「いいね」数を更新して保存
    fun setCount(index: Int) {//countからindexに引数を変更、インデックスごとのいいね数を更新できるように修正
        _counts.update { currentList ->
            currentList.toMutableList().also { it[index] = it[index] + 1 }
        }

        // データストアに「いいね」数を保存
        viewModelScope.launch {
            dataStore.setCount(index, _counts.value[index])
        }
    }
}


