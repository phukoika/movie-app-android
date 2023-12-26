package com.example.movieapp.models

data class User(
    var userName : String? = "",
    var imgUri: String? = ""
) : java.io.Serializable{
    public fun setName(userName: String){
        this.userName = userName
    }
    public fun setUri(Uri: String){
        this.imgUri = Uri
    }
}
