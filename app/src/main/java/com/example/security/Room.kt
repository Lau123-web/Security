package com.example.akj_asm

class Room {

    var checkin = ""
    var expcheckout = ""
    var roomID = 0
    var status = ""

    constructor() {

    }

    constructor(checkin:String,expcheckout:String,roomID:Int,status:String){

        this.checkin = checkin
        this.expcheckout = expcheckout
        this.roomID = roomID
        this.status = status
    }


}