package com.smile.studio.recipe.model

import com.smile.studio.recipe.model.greendao.DaoSession
import com.smile.studio.recipe.model.greendao.Type

class GlobalApp {

    var daoSession: DaoSession? = null
    var categories: ArrayList<Type> = ArrayList()

    companion object {

        private var instance: GlobalApp? = null

        fun getInstance(): GlobalApp {
            if (instance == null) {
                synchronized(GlobalApp::class.java) {
                    instance = GlobalApp()
                }
            }
            return instance!!
        }
    }


}