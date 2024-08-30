package com.ynemreuslu.switchbottomnavigaiton.utils


import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView


class MenuManager(private val bottomNavigationView: BottomNavigationView) {

    fun isItemPresent(id: Int): Boolean {
        return bottomNavigationView.menu.findItem(id) != null
    }

    fun getMenuSize(): Int {
        return bottomNavigationView.menu.size()
    }

    fun addItem(id: Int, title: String, iconResId: Int) {
        if (!isItemPresent(id)) {
            bottomNavigationView.menu.add(Menu.NONE, id, Menu.NONE, title).setIcon(iconResId)
        }
    }

    fun removeItem(id: Int) {
        val item = bottomNavigationView.menu.findItem(id)
        if (item != null) {
            bottomNavigationView.menu.removeItem(id)
        }
    }
}