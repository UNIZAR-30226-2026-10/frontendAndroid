package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// Se declara en el contexto global de Android un nuevo campo "dataLocal" que contendrá todos
// aquellos valores que queramos guardar entre ejecuciones diferentes de la app.
// Concretamente se guardan en el fichero interno "user_prefs" (Singleton)
private val Context.dataLocal by preferencesDataStore(name = "user_prefs")

class LocalStorage(private val context: Context) {

    private val logKey = booleanPreferencesKey("logStatus")
    private val emailKey = stringPreferencesKey("email")

    suspend fun setLogin(loggedIn: Boolean) {
        context.dataLocal.edit { preferences ->
            preferences[logKey] = loggedIn
        }
    }

    suspend fun setEmail(email: String) {
        context.dataLocal.edit { preferences ->
            preferences[emailKey] = email
        }
    }

    suspend fun clearLogin() {
        context.dataLocal.edit { preferences ->
            preferences.remove(emailKey)
            preferences.remove(logKey)
        }
    }
}