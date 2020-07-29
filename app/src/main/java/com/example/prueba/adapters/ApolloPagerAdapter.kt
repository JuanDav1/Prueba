package com.example.prueba.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.prueba.R
import com.example.prueba.models.Apollo
import com.example.prueba.views.ApolloFragment
import com.example.prueba.views.FavoriteFragment

class ApolloPagerAdapter(
    private var listApollo: ArrayList<Apollo>,
    private var listFavorite: ArrayList<Apollo>,
    var context: Context,
    fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    override fun getItem(position: Int): Fragment {


            return when(position) {
                0 -> ApolloFragment.newInstance(listApollo,listFavorite)
                1 -> FavoriteFragment.newInstance(listFavorite)
                else ->FavoriteFragment.newInstance(arrayListOf())

        }

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.all)
            1 -> context.getString(R.string.favorite)
            else -> null
        }
    }
}

