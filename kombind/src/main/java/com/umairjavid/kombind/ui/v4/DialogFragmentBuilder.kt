package com.umairjavid.kombind.ui.v4

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

open class DialogFragmentBuilder<out T: DialogFragment>(private val clazz: Class<T>, protected val arguments: Bundle, protected val fragmentTag: String) {
    fun build(): T {
        try {
            val fragment = clazz.newInstance()
            fragment.arguments = arguments
            return fragment
        } catch (e: java.lang.InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    fun forceShow(fragmentManager: FragmentManager): T {
        val fragment = build()
        fragment.show(fragmentManager, fragmentTag)
        return fragment
    }

    fun show(fragmentManager: FragmentManager): T {
        val fragment = fragmentManager.findFragmentByTag(fragmentTag)

        return if (fragment != null) {
            clazz.cast(fragment)
        } else {
            forceShow(fragmentManager)
        }
    }
}
