package mx.tecnm.cdhidalgo.iotapp

import android.view.View

interface ItemListener {
    fun onclick(v: View?, position: Int)
    fun onEdit(v: View, position: Int)
    fun onDel(v: View?, position: Int)
}