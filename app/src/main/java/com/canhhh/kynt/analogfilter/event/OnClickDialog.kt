package com.canhhh.kynt.analogfilter.event


interface OnClickDialog {
    fun onClickLeft()
    fun onClickRight()
    fun onClickExit()

    interface UnlockAds{
        fun onUnlock()
    }

    interface VideoCompleted{
        fun onReceiveBonus()
        fun onFailureBonus()
    }
}
