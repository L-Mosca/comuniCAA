package com.example.comunicaa.data.preferences_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.comunicaa.BuildConfig
import com.example.comunicaa.domain.models.user.UserModel
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesHelper @Inject constructor(@ApplicationContext private val context: Context) :
    PreferencesContract {

    companion object {
        private const val PREFERENCES_NAME = "${BuildConfig.NAME}.${BuildConfig.FLAVOR}.Preferences"

        // Keys
        private val remoteDatabaseVersion =
            stringPreferencesKey(name = "$PREFERENCES_NAME.remoteDatabaseVersion")
        private val userData = stringPreferencesKey(name = "$PREFERENCES_NAME.userData")
    }

    private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)
    private val dataStore = context.dataStore


    override suspend fun getRemoteDbVersion(): String? {
        val data = dataStore.getData<String>(remoteDatabaseVersion)
        return data
    }

    override suspend fun saveRemoteDbVersion(version: String) {
        dataStore.edit { pref ->
            pref[remoteDatabaseVersion] = version
        }
    }

    override suspend fun getUserData(): UserModel? {
        return Gson().fromJson(dataStore.getData<String>(userData), UserModel::class.java)
    }

    override suspend fun saveUserData(userModel: UserModel) {
        dataStore.edit { pref ->
            pref[userData] = Gson().toJson(userModel)
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { pref ->
            pref[userData] = ""
        }
    }
}

suspend inline fun <reified T> DataStore<Preferences>.getData(key: Preferences.Key<String>): T? {
    return runCatching {
        this.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val data = preferences[key]
            if (data.isNullOrEmpty()) null
            else Gson().fromJson(data, T::class.java)
        }.first()
    }.getOrNull()
}