package com.example.prueba.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.App
import com.example.prueba.Interface.ApolloOnclick
import com.example.prueba.R
import com.example.prueba.adapters.ApolloAdapter
import com.example.prueba.models.Apollo
import com.example.prueba.viewmodels.FavoriteViewModel
import com.example.prueba.viewmodels.NasaViewModel
import com.example.prueba.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import java.util.EnumSet.of
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FavoriteFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var favoriteRecyclerView: ApolloAdapter
    lateinit var recyclerView: RecyclerView

    private lateinit var listFavorite: List<Apollo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listFavorite = it.getSerializable(ARG_PARAM1) as ArrayList<Apollo>
            favoriteViewModel.getFavoriteWithApollo.observe(this, showAdapter)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((context as AppCompatActivity).application as App).getAppComponent().doInjection(this)
        favoriteViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.recyclerFavorite
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

favoriteViewModel.getFavoriteWithApollo()

    }
    private val showAdapter = Observer<List<Apollo>> {
        favoriteRecyclerView = ApolloAdapter(it as ArrayList<Apollo>,false, activity as FragmentActivity, context as ApolloOnclick)
        recyclerView.adapter = favoriteRecyclerView
    }


    companion object {

        @JvmStatic
        fun newInstance(listApollo: ArrayList<Apollo>) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, listApollo)
                }
            }
    }
}