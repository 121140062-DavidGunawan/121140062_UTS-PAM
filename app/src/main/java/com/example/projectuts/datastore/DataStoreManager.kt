package com.example.projectuts.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager (context : Context) {

    companion object{
        val USERNAME = preferencesKey<String>("username")
        val PASSWORD = preferencesKey<String>("password")
        val GITHUB = preferencesKey<String>("github")
        val NIM = preferencesKey<String>("nim")
        val EMAIL = preferencesKey<String>("email")
        val ISLOGIN = preferencesKey<Boolean>("is_login")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "user_data"
    )

    suspend fun saveToDataStore (user: String, pw: String, git: String, nim: String, mail: String) {
        dataStore.edit {
            it[USERNAME] = user
            it[PASSWORD] = pw
            it[GITHUB] = git
            it[NIM] = nim
            it[EMAIL] = mail
        }
    }

    fun getUsername(): Flow<String> {
        return readUserFlow
    }

    fun getGitHub(): Flow<String> {
        return readGitFlow
    }

    fun getNim(): Flow<String> {
        return readNimFlow
    }

    fun getEmail(): Flow<String> {
        return readMailFlow
    }
    suspend fun updateLogin (loginStatus: Boolean) {
        dataStore.edit {
            it[ISLOGIN] = loginStatus
        }
    }

    val readUserFlow: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }
    val readPwFlow: Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }
    val readGitFlow: Flow<String> = dataStore.data.map {
        it[GITHUB] ?: ""
    }
    val readNimFlow: Flow<String> = dataStore.data.map {
        it[NIM] ?: ""
    }
    val readMailFlow: Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }
    val readLoginFlow: Flow<Boolean> = dataStore.data.map {
        it[ISLOGIN] ?: false
    }

}