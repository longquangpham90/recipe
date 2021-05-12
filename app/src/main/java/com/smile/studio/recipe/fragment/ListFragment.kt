package com.smile.studio.recipe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smile.studio.recipe.R
import com.smile.studio.recipe.activity.MainActivity
import com.smile.studio.recipe.adapter.PecipeAdapter
import com.smile.studio.recipe.adapter.TypeAdapter
import com.smile.studio.recipe.model.GlobalApp
import com.smile.studio.recipe.model.OnItemClickListenerRecyclerView
import com.smile.studio.recipe.model.greendao.Pecipe
import com.smile.studio.recipe.model.greendao.PecipeDao
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    var adapterCategory: TypeAdapter? = null
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
        layoutManager = LinearLayoutManager(requireActivity())
        adapterCategory = TypeAdapter(requireActivity(), GlobalApp.getInstance().categories)
        spinner.adapter = adapterCategory
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, postion: Int, p3: Long) {
                val type = adapterCategory?.mData?.get(postion)
                type?.trace()
                var mData: ArrayList<Pecipe>? = null
                if (type?.pid != 1) {
                    mData = GlobalApp.getInstance().daoSession?.pecipeDao?.queryBuilder()?.where(PecipeDao.Properties.Type?.eq(type?.pid))?.list() as ArrayList<Pecipe>
                } else {
                    mData = GlobalApp.getInstance().daoSession?.pecipeDao?.queryBuilder()?.list() as ArrayList<Pecipe>
                }
                adapter?.clear()
                adapter?.addAll(mData)
            }

        }
        recyclerView.layoutManager = layoutManager
        adapter = PecipeAdapter(requireActivity(), ArrayList())
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
        adapter?.addAll(mData)
        val mDividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(mDividerItemDecoration)
    }

}
