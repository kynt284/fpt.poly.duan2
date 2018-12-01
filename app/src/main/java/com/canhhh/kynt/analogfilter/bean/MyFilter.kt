package com.canhhh.kynt.analogfilter.bean

class MyFilter {
    var mNameFilter : String = ""
    var mImageInt : Int = 0
    var mImageString : String = ""


    constructor(nameFilter: String, imageInt: Int) {
        mNameFilter = nameFilter
        mImageInt = imageInt
    }


    constructor(nameFilter: String, imageString: String) {
        this.mNameFilter = nameFilter
        this.mImageString = imageString
    }


}