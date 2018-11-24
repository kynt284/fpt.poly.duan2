package com.canhhh.kynt.analogfilter.bean

class MyFilter {
    var mNameFilter : String = ""
    var mImageInt : Int = 0
    var mImageString : String = ""


    constructor(mNameFilter: String, mImageInt: Int) {
        this.mNameFilter = mNameFilter
        this.mImageInt = mImageInt
    }


    constructor(mNameFilter: String, mImageString: String) {
        this.mNameFilter = mNameFilter
        this.mImageString = mImageString
    }


}