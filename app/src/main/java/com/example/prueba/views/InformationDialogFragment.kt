package com.example.prueba.views

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.prueba.R
import com.example.prueba.models.Apollo
import kotlinx.android.synthetic.main.fragment_information.view.*
import kotlinx.android.synthetic.main.item_apolo.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InformationFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private lateinit var apollo: Apollo


    override fun getTheme(): Int = R.style.CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            apollo = it.getSerializable(ARG_PARAM1) as Apollo

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_information, container, false)
        view.imageApollo.loadUrl(apollo.image)
        view.description.movementMethod= ScrollingMovementMethod()
        view.title.text= apollo.tittle
        view.nasaID.text = apollo.nasaId
        view.dataCreated.text = apollo.dataCreate
        view.description.text = apollo.description

        return view

    }
    private fun ImageView.loadUrl(url: String) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions().placeholder(R.drawable.default_item).transforms(
                    RoundedCorners(35)
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .into(this)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(apollo: Apollo) =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, apollo)
                }
            }
    }
}