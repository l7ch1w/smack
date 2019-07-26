package com.snackchat.l7ch1w.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.snackchat.l7ch1w.smack.Utilities.URL_CREATE_USER
import com.snackchat.l7ch1w.smack.Utilities.URL_LOGIN
import com.snackchat.l7ch1w.smack.Utilities.URL_Register
import org.json.JSONException
import org.json.JSONObject

object Authservices {
    var isLoggedIn = false
    var userEmail = ""
    var authToken = ""

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {
        val jsonbody = JSONObject()
        jsonbody.put("email", email)
        jsonbody.put("password", password)
        val requestbody = jsonbody.toString()

        val registerRequest = object : StringRequest(Method.POST, URL_Register, Response.Listener { response ->
            println(response)
            complete(true)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not register user!: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }

    fun LoginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {
        val jsonbody = JSONObject()
        jsonbody.put("email", email)
        jsonbody.put("password", password)
        val requestbody = jsonbody.toString()

        val LoginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener { response ->
            // this is where we parse the json object
            println(response)
            try {
                userEmail = response.getString("user")
                authToken = response.getString("token")
                isLoggedIn = true
                complete(true)
            } catch (e: JSONException){
                Log.d("JSON", "EXC:" + e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->
            // this is where we deal with error
            Log.d("ERROR", "Could not register user!: $error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(LoginRequest)
    }

    fun createUser(context: Context, name: String, email: String, avatarName: String, avatarColor: String, complete: (Boolean) -> Unit){

        val jsonbody = JSONObject()
        jsonbody.put("name", name)
        jsonbody.put("email", email)
        jsonbody.put("avatarName", avatarName)
        jsonbody.put("avatarColor", avatarColor)
        val requestbody = jsonbody.toString()

        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->
            try {
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")
                complete(true)

            } catch (e:JSONException){
                Log.d("ERROR", "EXC:"+ e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not add user!: $error")
            complete(false)
        }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestbody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }
        }
        Volley.newRequestQueue(context).add(createRequest)
    }
}