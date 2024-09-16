package com.example.comunicaa.data.preferences_datastore

interface PreferencesContract {

    suspend fun getRemoteDbVersion(): String?
    suspend fun saveRemoteDbVersion(version: String)
}