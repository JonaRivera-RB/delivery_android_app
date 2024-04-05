package com.example.delivery.data.models

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class User (
    @Expose
    val id: String? = null,

    @Expose
    val name: String ?= null,

    @Expose
    val lastname: String ?= null,

    @Expose
    val email: String ?= null,

    @Expose
    val phone: String ?= null,

    @Expose
    val password: String ?= null,

    @Expose
    val image: String? = null,

    @Expose
    @SerializedName(value = "session_token")
    val sessionToken: String? = null,

    @Expose
    @SerializedName(value = "is_available")
    val isAvailable: String? = null,

    @SerializedName("roles")
    @Expose
    val dataString: String? = null
): Parcelable {

    val roles: List<Rol>? = dataString?.let { jsonString ->
        try {
            val jsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
            // Aquí necesitarás extraer la lista de roles del objeto JsonObject
            // y deserializarla a una lista de objetos Rol
            // Esto depende de cómo se esté estructurando el JSON en dataString
            // Suponiendo que la lista de roles esté bajo la clave "roles"
            val rolesJsonArray = jsonObject.getAsJsonArray("roles")
            val rolesList = mutableListOf<Rol>()
            rolesJsonArray?.forEach { roleJson ->
                rolesList.add(Gson().fromJson(roleJson, Rol::class.java))
            }
            rolesList
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}