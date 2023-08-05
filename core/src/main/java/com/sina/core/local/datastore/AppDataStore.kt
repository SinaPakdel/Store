package com.sina.core.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sina.core.common.constants.Constants
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_CURRENT_CUSTOMER_ORDER_ID
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_CUSTOMER_EMAIL
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_CUSTOMER_ID
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_CUSTOMER_LOGGED_IN
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_BY_TYPE
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_BY_TYPE_ID
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_TYPE
import com.sina.core.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_TYPE_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AppDataStore @Inject constructor(private val appDataStore: DataStore<Preferences>) {
    private object PreferencesKey {
        val userLoggedIn = booleanPreferencesKey(PREFERENCES_CUSTOMER_LOGGED_IN)
        val currentOrderId = intPreferencesKey(PREFERENCES_CURRENT_CUSTOMER_ORDER_ID)
        val customerId = intPreferencesKey(PREFERENCES_CUSTOMER_ID)
        val customerEmail = stringPreferencesKey(PREFERENCES_CUSTOMER_EMAIL)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        val selectedSearchOrderType = stringPreferencesKey(PREFERENCES_SEARCH_ORDER_TYPE)
        val selectedSearchOrderTypeId = intPreferencesKey(PREFERENCES_SEARCH_ORDER_TYPE_ID)
        val selectedSearchOrderByType = stringPreferencesKey(PREFERENCES_SEARCH_ORDER_BY_TYPE)
        val selectedSearchOrderByTypeId = intPreferencesKey(PREFERENCES_SEARCH_ORDER_BY_TYPE_ID)
    }

    suspend fun saveUserLoggedIn(userLoggedIn: Boolean) = appDataStore.edit { preferences ->
        preferences[PreferencesKey.userLoggedIn] = userLoggedIn
    }

    suspend fun saveCustomerId(customerId: Int) = appDataStore.edit { preferences ->
        preferences[PreferencesKey.customerId] = customerId
    }

    suspend fun saveCustomerEmail(customerEmail: String) = appDataStore.edit { preferences ->
        preferences[PreferencesKey.customerEmail] = customerEmail
    }

    suspend fun saveCurrentOrderId(currentOrderId: Int) = appDataStore.edit { preferences ->
        preferences[PreferencesKey.currentOrderId] = currentOrderId
    }

    suspend fun saveBackOnline(backOnline: Boolean) = appDataStore.edit { preferences ->
        preferences[PreferencesKey.backOnline] = backOnline
    }

    val readBackOnline: Flow<Boolean> = appDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else throw exception
    }.map { preferences: Preferences ->
        val backOnline =
            preferences[PreferencesKey.backOnline] ?: false
        backOnline
    }


    val customerId =
        appDataStore.data.catch { }.map { preferences ->
            preferences[PreferencesKey.customerId] ?: Constants.DEFAULT_CUSTOMER_ID
        }

    val readCustomerEmail =
        appDataStore.data.catch { }.map { preferences ->
            preferences[PreferencesKey.customerEmail] ?: Constants.DEFAULT_CUSTOMER_EMAIL
        }

    val readUserLoggedIn: Flow<Boolean> = appDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else throw exception
    }.map { preferences: Preferences ->
        // TODO: change userLoggedIn Default value from true to false
        val useLoggedIn = preferences[PreferencesKey.userLoggedIn] ?: false
        useLoggedIn
    }


    val readCurrentOrderId: Flow<Int> = appDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else throw exception
    }.map { preferences ->
        val currentOrderId =
            preferences[PreferencesKey.currentOrderId] ?: Constants.DEFAULT_CURRENT_ORDER_ID
        currentOrderId
    }

    val readSearchType: Flow<SearchType> = appDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception

        }.map { preferences ->
            val selectedSearchOrderType = preferences[PreferencesKey.selectedSearchOrderType]
                ?: Constants.DEFAULT_SEARCH_ORDER_TYPE
            val selectedSearchOrderTypeId = preferences[PreferencesKey.selectedSearchOrderTypeId]
                ?: Constants.DEFAULT_SEARCH_ORDER_TYPE_ID
            val selectedSearchOrderByType = preferences[PreferencesKey.selectedSearchOrderByType]
                ?: Constants.DEFAULT_SEARCH_ORDER_BY_TYPE
            val selectedSearchOrderByTypeId =
                preferences[PreferencesKey.selectedSearchOrderByTypeId]
                    ?: Constants.DEFAULT_SEARCH_ORDER_BY_TYPE_ID
            Timber.e("Filters OrderByType:: $selectedSearchOrderByType")

            SearchType(
                selectedSearchOrderType,
                selectedSearchOrderTypeId,
                selectedSearchOrderByType,
                selectedSearchOrderByTypeId
            )
        }

    suspend fun saveSearchOrderType(searchOrderTypeChip: String, searchOrderTypeIdChip: Int) {
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKey.selectedSearchOrderType] = searchOrderTypeChip
//            mutablePreferences[PreferencesKey.selectedSearchOrderTypeId] = searchOrderTypeIdChip
        }
    }

    suspend fun saveSearchOrderByType(searchOrderByType: String, searchOrderByTypeId: Int) {
        Timber.e("Filters before $searchOrderByType")
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKey.selectedSearchOrderByType] = searchOrderByType
            mutablePreferences[PreferencesKey.selectedSearchOrderByTypeId] = searchOrderByTypeId
            Timber.e("Filters: after dataStore$searchOrderByType,$searchOrderByTypeId")
        }
    }


}

data class SearchType(
    val selectedSearchOrderType: String,
    val selectedSearchOrderTypeId: Int,
    val selectedSearchOrderByType: String,
    val selectedSearchOrderByTypeId: Int,
)