package kynt.fpt.analogfilter.utills


import android.content.Context
import android.content.SharedPreferences


object UnlockManager {
    private const val KEY_UN_LOCK_GRADIENT = "KEY_UN_LOCK_GRADIENT"
    private const val KEY_UN_LOCK_GRAIN = "KEY_UN_LOCK_GRAIN"
    private const val KEY_UN_LOCK_LIGHT = "KEY_UN_LOCK_LIGHT"

    const val UNLOCK_GRADIENT = 1
    const val UNLOCK_GRAIN = 2
    const val UNLOCK_LIGHT = 3


    fun setUnlock(context: Context, unlock: Int, numberUnLock: Int) {
        val sharedPreferences = context.getSharedPreferences("KEY_PRE", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = sharedPreferences.edit()
        if (unlock == UNLOCK_GRADIENT) {
            editor.putInt(KEY_UN_LOCK_GRADIENT, numberUnLock)
        }
        if (unlock == UNLOCK_GRAIN) {
            editor.putInt(KEY_UN_LOCK_GRAIN, numberUnLock)
        }
        if (unlock == UNLOCK_LIGHT) {
            editor.putInt(KEY_UN_LOCK_LIGHT, numberUnLock)
        }
        editor.apply()
    }


    fun getUnLock(context: Context, unlock: Int): Int {
        val sharedPreferences = context.getSharedPreferences("KEY_PRE", Context.MODE_PRIVATE)
        val defaultUnlock = 30
        if (unlock == UNLOCK_GRADIENT) {
            return sharedPreferences.getInt(KEY_UN_LOCK_GRADIENT, defaultUnlock)
        }
        if (unlock == UNLOCK_GRAIN) {
            return sharedPreferences.getInt(KEY_UN_LOCK_GRAIN, defaultUnlock)
        }
        return if (unlock == UNLOCK_LIGHT) {
            sharedPreferences.getInt(KEY_UN_LOCK_LIGHT, defaultUnlock)
        } else 0
    }


}
