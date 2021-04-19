package com.example.security

class Profile
{
    var firstname = ""
    var lastname = ""
    var phoneNo = ""
    var address = ""

    constructor() {

    }
    constructor(phoneNo: String, address: String) {
        this.phoneNo = phoneNo
        this.address = address
    }
}