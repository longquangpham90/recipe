package com.smile.studio.recipe.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smile.studio.recipe.R
import com.smile.studio.recipe.activity.MainActivity
import com.smile.studio.recipe.adapter.PecipeAdapter
import com.smile.studio.recipe.model.GlobalApp
import com.smile.studio.recipe.model.OnItemClickListenerRecyclerView
import com.smile.studio.recipe.model.greendao.Pecipe
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    var adapter: PecipeAdapter? = null
    var layoutManager: LinearLayoutManager? = null

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PecipeAdapter(activity!!, ArrayList())
        recyclerView.adapter = adapter
        adapter?.onItemClick = object : OnItemClickListenerRecyclerView {
            override fun onClick(view: View?, position: Int) {
                val item = adapter?.mData?.get(position)
                item?.trace()
                (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.container, CreateFragment.newInstance(item!!)).commit()
            }

            override fun onLongClick(view: View?, position: Int) {

            }

        }
        recyclerView.setHasFixedSize(true)
        val mData = GlobalApp.getInstance().daoSession?.pecipeDao?.queryBuilder()?.list() as ArrayList<Pecipe>
        Log.e("Tag", "--- size: ${mData.size}")
        adapter?.addAll(mData)
        val mDividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(mDividerItemDecoration)
    }

}
