package com.example.friendchat

class User {
    var email : String = ""
    var name : String = ""
    var uid : String = ""

    constructor(name : String, email : String, uid : String) {
        this.email = email
        this.name = name
        this.uid = uid
    }
}