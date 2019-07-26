package com.snackchat.l7ch1w.smack.Services

import android.graphics.Color
import java.util.*

object UserDataService {
    var id = ""
    var name = ""
    var email = ""
    var avatarColor = ""
    var avatarName = ""

    fun logout(){
        id = ""
        name = ""
        email = ""
        avatarColor = ""
        avatarName = ""
        Authservices.userEmail = ""
        Authservices.authToken = ""
        Authservices.isLoggedIn = false
    }

    fun returnAvatarColor(components: String): Int{
        val strippedColor = components
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
        var r = 0
        var g = 0
        var b = 0

        val scanner = Scanner(strippedColor)
        if (scanner.hasNext()){
            r = (scanner.nextDouble()*255).toInt()
            g = (scanner.nextDouble()*255).toInt()
            b = (scanner.nextDouble()*255).toInt()
        }

        return Color.rgb(r,g,b)
    }

}