package com.example.friendchat

class Messages {
    var msg : String? = "";
    var senderid : String? = "";

    constructor(msg : String, id : String) {
        this.msg = msg
        this.senderid = id
    }

}
