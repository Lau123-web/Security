package com.example.akj_asm

class ItemStatus {
    var fStatus = ""
    var lStatus = ""
    var wStatus = ""
    var other = ""
    var roomID = 0

    constructor(fStatus:String,lStatus:String,wStatus:String,other:String,roomID:Int){
        this.fStatus = fStatus
        this.lStatus = lStatus
        this.wStatus = wStatus
        this.other = other
        this.roomID = roomID
    }


}