package com.example.prueba

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prueba.Interface.ApolloOnclick
import com.example.prueba.adapters.ApolloPagerAdapter
import com.example.prueba.models.Apollo
import com.example.prueba.viewmodels.NasaViewModel
import com.example.prueba.viewmodels.ViewModelFactory
import com.example.prueba.views.ApolloFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class NasaActivity : AppCompatActivity(),
    ApolloFragment.OnFragmentInteractionListener,
    SearchView.OnQueryTextListener, SearchView.OnCloseListener, ApolloOnclick {


    var listApollo: ArrayList<Apollo> = arrayListOf()
    private var mQuery: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mnasaViewModel: NasaViewModel
    private lateinit var pagerAdapter: ApolloPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        (application as App).getAppComponent().doInjection(this)

        mnasaViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NasaViewModel::class.java)

        search_apollo.setOnQueryTextListener(this)
        search_apollo.setOnCloseListener(this)

        mnasaViewModel.emptyData()
        mnasaViewModel.loadData()
        mnasaViewModel.getApollo()


    }

    private val showadapter = Observer<List<Apollo>> {
        if (it.isEmpty()) {
            mnasaViewModel.getApollo()
        } else {
            listApollo = arrayListOf()
            listApollo.addAll(it)
            mnasaViewModel.getFavoriteWithApollo()
            tabsApollo.setupWithViewPager(viewPagerApollo)

        }

    }

    private val ShowPager = Observer<List<Apollo>> {
        if (listApollo.size > 0) {
            pagerAdapter = ApolloPagerAdapter(
                listApollo,
                it as ArrayList<Apollo>,
                this,
                supportFragmentManager
            )
            viewPagerApollo.adapter = pagerAdapter
            pagerAdapter.notifyDataSetChanged()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_bar_tabs, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        setupViewModel()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_Item -> {
                mnasaViewModel.deleteTable()
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.Refresh_Item -> {
                mnasaViewModel.getApollo()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun setupViewModel() {
        mnasaViewModel.getDataApollo.observe(this, showadapter)
        mnasaViewModel.getFavoriteWithApollo.observe(this, ShowPager)

    }

    private fun filterApollo(query: String?) {

        if (query != null && query.isNotEmpty()) {
            mQuery = query.trim()
            mnasaViewModel.filterAPollo(mQuery)
        } else {
            mQuery = ""

        }

    }

    fun onClickButton(view: View) {

        searchReveal(
            view,
            search_apollo.visibility == View.INVISIBLE
        )

    }

    private fun searchReveal(view: View, reveal: Boolean) {
        val x: Int = search_apollo.x.toInt() + view.width / 2
        val y: Int = search_apollo.y.toInt() + view.height / 2

        val startRadius = 0
        val endRadius = 600

        val anim: Animator
        if (reveal) {
            anim = ViewAnimationUtils.createCircularReveal(
                search_apollo,
                x,
                y,
                startRadius.toFloat(),
                endRadius.toFloat()
            )
            search_apollo.visibility = View.VISIBLE
            search_apollo.isIconified = false
        } else {
            anim = ViewAnimationUtils.createCircularReveal(
                search_apollo,
                x,
                y,
                endRadius.toFloat(),
                view.width / 2f
            )
            search_apollo.setQuery("", false)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    search_apollo.visibility = View.INVISIBLE
                }
            })
        }

        anim.start()
    }

    private inline fun FragmentManager.inTransaction(
        typeAdd: Boolean,
        func: FragmentTransaction.() -> FragmentTransaction
    ) {
        if (typeAdd) {
            beginTransaction().func().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else {
            beginTransaction().func().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit()
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        filterApollo(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filterApollo(newText)
        return false
    }

    override fun onClose(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onClickListener(apollo: Apollo, isTouch: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAddClicked() {
        mnasaViewModel.getApollo()
    }


}