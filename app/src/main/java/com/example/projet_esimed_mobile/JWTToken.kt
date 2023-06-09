package com.example.projet_esimed_mobile

import android.content.Context
import android.content.SharedPreferences


class JWTToken private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
    private var token: String = sharedPreferences.getString("token", "") ?: ""

    var tokenWithBearer: String
        get() = "Bearer $token"
        set(value) {
            token = value
            sharedPreferences.edit().putString("token", token).apply()
        }

    fun isValid(): Boolean {
        return token.isNotEmpty()
    }

    companion object {
        private var INSTANCE: JWTToken? = null

        fun getInstance(context: Context): JWTToken {
            if (INSTANCE == null) {
                INSTANCE = JWTToken(context)
            }
            return INSTANCE!!;
        }
    }
}