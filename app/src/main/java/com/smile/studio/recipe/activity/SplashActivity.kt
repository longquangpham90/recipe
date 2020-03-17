package com.smile.studio.recipe.activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.smile.studio.recipe.R
import com.smile.studio.recipe.model.GlobalApp
import com.smile.studio.recipe.model.greendao.DaoMaster
import com.smile.studio.recipe.model.greendao.Type

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        val mHelper = DaoMaster.DevOpenHelper(this, "recipe.db", null)
        val db: SQLiteDatabase = mHelper.writableDatabase
        mHelper.onUpgrade(db, 1, 1)
        val daoMaster = DaoMaster(db)
        GlobalApp.getInstance().daoSession = daoMaster.newSession()
        GlobalApp.getInstance().daoSession?.typeDao?.insertOrReplace(Type(1, 1, "Cake"))
        GlobalApp.getInstance().daoSession?.typeDao?.insertOrReplace(Type(2, 2, "Rice"))
        GlobalApp.getInstance().daoSession?.typeDao?.insertOrReplace(Type(3, 3, "Vegetable"))
        GlobalApp.getInstance().categories = GlobalApp.getInstance().daoSession?.typeDao?.queryBuilder()?.list() as ArrayList<Type>
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500L)
    }
}
