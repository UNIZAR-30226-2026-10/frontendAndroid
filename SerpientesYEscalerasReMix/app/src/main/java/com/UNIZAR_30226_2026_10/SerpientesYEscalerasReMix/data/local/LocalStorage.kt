package com.UNIZAR_30226_2026_10.SerpientesYEscalerasReMix.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Se declara en el contexto global de Android un nuevo campo "dataLocal" que contendrá todos
// aquellos valores que queramos guardar entre ejecuciones diferentes de la app.
// Concretamente se guardan en el fichero interno "user_prefs" (Singleton)
private val Context.dataLocal by preferencesDataStore(name = "user_prefs")

class LocalStorage(private val context: Context) {

    private val logKey = booleanPreferencesKey("logStatus")
    private val emailKey = stringPreferencesKey("email")
    private val passwdKey = stringPreferencesKey("passwd")

    suspend fun getLogin(): Boolean {
        val preferences = context.dataLocal.data.first()
        return preferences[logKey] ?: false // En el caso de que no lo encuentre: false
    }

    suspend fun setLogin(loggedIn: Boolean) {
        context.dataLocal.edit { preferences ->
            preferences[logKey] = loggedIn
        }
    }

    suspend fun getEmail(): String {
        val preferences = context.dataLocal.data.first()
        return preferences[emailKey] ?: "" // En el caso de que no lo encuentre: ""
    }

    suspend fun setEmail(email: String) {
        context.dataLocal.edit { preferences ->
            preferences[emailKey] = email
        }
    }

    suspend fun getPasswd(): String {
        val preferences = context.dataLocal.data.first()
        return preferences[passwdKey] ?: "" // En el caso de que no lo encuentre: ""
    }

    suspend fun setPasswd(passwd: String) {
        context.dataLocal.edit { preferences ->
            preferences[passwdKey] = passwd
        }
    }

    suspend fun clearLogin() {
        context.dataLocal.edit { preferences ->
            preferences.remove(emailKey)
            preferences.remove(logKey)
            preferences.remove(passwdKey)
        }
    }
}