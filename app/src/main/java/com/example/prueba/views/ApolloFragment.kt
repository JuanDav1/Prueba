package com.example.prueba.views

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.prueba.App
import com.example.prueba.Interface.ApolloOnclick
import com.example.prueba.R
import com.example.prueba.adapters.ApolloAdapter
import com.example.prueba.adapters.swipeItem
import com.example.prueba.models.Apollo
import com.example.prueba.models.Favorite
import com.example.prueba.viewmodels.NasaViewModel
import com.example.prueba.viewmodels.ViewModelFactory
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ApolloFragment : Fragment(), ApolloOnclick {


    private lateinit var listApollo: List<Apollo>
    private lateinit var listFavorite: List<Apollo>
    private var listener: OnFragmentInteractionListener? = null
    lateinit var apolloAdapter: ApolloAdapter
    var listId: ArrayList<Int> = arrayListOf()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var mnasaViewModel: NasaViewModel
    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listApollo = it.getSerializable(ARG_PARAM1) as ArrayList<Apollo>
            listFavorite = it.getSerializable(ARG_PARAM2) as ArrayList<Apollo>
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((context as AppCompatActivity).application as App).getAppComponent().doInjection(this)
        mnasaViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NasaViewModel::class.java)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    private val showAdapter = Observer<List<Apollo>> {
        (listApollo as ArrayList).addAll(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apollo, container, false)
        mRecyclerView = view.findViewById(R.id.recyclerApollo)

        apolloAdapter = ApolloAdapter(listApollo as ArrayList<Apollo>, true, activity as FragmentActivity, this)
        mRecyclerView.adapter = apolloAdapter

        val touchHelper =
            ItemTouchHelper(swipeItem(apolloAdapter, mRecyclerView, activity as FragmentActivity))
        touchHelper.attachToRecyclerView(mRecyclerView)

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(listApollo: ArrayList<Apollo>, listFavorite: ArrayList<Apollo>) =
            ApolloFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, listApollo)
                    putSerializable(ARG_PARAM2, listFavorite)
                }
            }
    }

    interface OnFragmentInteractionListener {
        fun onAddClicked()    }

    override fun onClickListener(apollo: Apollo, isTouch: Boolean) {
        if (isTouch) {
            val informationFragment = InformationFragment.newInstance(apollo)
            informationFragment.show(childFragmentManager, "information_fragment")
        } else {

            mnasaViewModel.addFavorite(apollo)
            mnasaViewModel.getApollo()
            listener?.onAddClicked()
        }


    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}