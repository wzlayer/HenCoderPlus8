package com.example.core

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val viewHashMap: HashMap<Int, View> = HashMap<Int, View>()

//    protected fun <T : View> getView(@IdRes id: Int): T {
//        var view = viewHashMap[id]
//        if (view == null)
//        {
//            view = itemView.findViewById<View>(id)
//            viewHashMap[id] = view
//        }
//        return view as T
//    }

    protected fun <T : View?> getView(@IdRes id: Int): T? {
        var view = viewHashMap[id]
        if (view == null) {
            view = itemView.findViewById(id)
            viewHashMap[id] = view
        }
        // TODO
        return view as T?
    }

    protected fun setText(@IdRes id: Int, text: String?) {
        getView<TextView>(id)?.text = text
    }
}