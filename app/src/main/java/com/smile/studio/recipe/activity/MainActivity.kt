package com.smile.studio.recipe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.smile.studio.recipe.R
import com.smile.studio.recipe.fragment.CreateFragment
import com.smile.studio.recipe.fragment.ListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_create.setOnClickListener(this)
        btn_view_list.setOnClickListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.container, CreateFragment.newInstance()).commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_create -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, CreateFragment.newInstance()).commit()
            }
            R.id.btn_view_list -> {
                supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment.newInstance()).commit()
            }

        }
        menu.close(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment != null && fragment is CreateFragment) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
