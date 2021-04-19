package com.example.assignment

class DatabaseModel{
    var roomID = ""
    var name = ""
    var pNo = 0
    var numG = 0
    var custR = ""
    var bDay = ""
    var tCost= ""
    var doneP=""
    var startD=""
    var endD=""

    constructor() {

    }
    constructor(roomID:String,name:String,pNo:Int,numG:Int,custR:String,bDay:String,tCost:String,doneP:String,startD:String,endD:String) {
        this.roomID = roomID
        this.name = name
        this.pNo = pNo
        this.numG = numG
        this.custR = custR
        this.bDay = bDay
        this.tCost = tCost
        this.doneP = doneP
        this.startD = startD
        this.endD = endD
    }
}