package datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(
    private val context: Context
) {
    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    // 各写真のインデックスに基づいた動的キーの生成
    private fun countKey(index: Int) = intPreferencesKey("count_$index")

    // 写真ごとの「いいね」数を取得する関数に変更
    fun getCount(index: Int): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[countKey(index)] ?: 0//対象の番号のいいね数を取得
        }
    }

    // 写真ごとの「いいね」数を保存
    suspend fun setCount(index: Int, count: Int) {//複数のいいねを保存するため、indexに加えてcountを引数としている
        context.dataStore.edit { preferences ->
            preferences[countKey(index)] = count
            //countに対象のいいね数を格納する
        }
    }
}
