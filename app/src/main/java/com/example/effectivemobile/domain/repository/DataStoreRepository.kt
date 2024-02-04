package com.example.effectivemobile.domain.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.effectivemobile.data.local.Dao
import com.example.effectivemobile.data.local.ProductDatabase
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val database: ProductDatabase
) {

    val userFirstName = dataStore.data.map {
        it[USER_FIRST_NAME] ?: ""
    }.distinctUntilChanged()

    val userLastName = dataStore.data.map {
        it[USER_LAST_NAME] ?: ""
    }.distinctUntilChanged()

    val userPhoneNumber = dataStore.data.map {
        it[USER_PHONE_NUMBER] ?: ""
    }.distinctUntilChanged()

    val isUserRegistered = dataStore.data.map {
        it[IS_USER_REGISTERED] ?: false
    }.distinctUntilChanged()

    suspend fun setFirstName(firstName: String) {
        dataStore.edit {
            it[USER_FIRST_NAME] = firstName
        }
    }

    suspend fun setLastName(lastName: String) {
        dataStore.edit {
            it[USER_LAST_NAME] = lastName
        }
    }

    suspend fun setPhoneNumber(phoneNumber: String) {
        dataStore.edit {
            it[USER_PHONE_NUMBER] = phoneNumber
        }
    }

    suspend fun setIsUserRegistered(isUserRegistered: Boolean) {
        dataStore.edit {
            it[IS_USER_REGISTERED] = isUserRegistered
        }
    }

    suspend fun clearDatastore() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val USER_FIRST_NAME = stringPreferencesKey("USER_NAME")
        private val USER_LAST_NAME = stringPreferencesKey("USER_LAST_NAME")
        private val USER_PHONE_NUMBER = stringPreferencesKey("USER_PHONE_NUMBER")
        private val IS_USER_REGISTERED = booleanPreferencesKey("IS_USER_REGISTERED")
    }
}