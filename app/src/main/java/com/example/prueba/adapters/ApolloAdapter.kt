package com.example.prueba.adapters

import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.prueba.Interface.ApolloOnclick
import com.example.prueba.R
import com.example.prueba.models.Apollo
import com.example.prueba.models.Favorite
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_apolo.view.*

class ApolloAdapter(
    var listApollo: ArrayList<Apollo>,
    var favorite: Boolean,
    val context: Context,
    private val listener: ApolloOnclick
) :
    RecyclerView.Adapter<ApolloAdapter.ViewHolder>() {
    private var showIcon: Boolean = false

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tittle: TextView = view.tittleApollo
        var image: ImageView = view.apoloImage
        var error: TextView = view.errorImage
        var cardView: CardView = view.cardViewApollo
        var star: ImageView = view.favoriteItem
        fun bind(apollo: Apollo, boolean: Boolean) {

            if (boolean) {
                if (apollo.isFavorite) {
                    star.visibility = View.VISIBLE
                } else {
                    star.visibility = View.GONE
                }
            }

            tittle.text = apollo.tittle
            error.visibility = View.GONE
            if (apollo.image.contains("null")) {
                error.visibility = View.VISIBLE

            } else {
                error.visibility = View.GONE
            }
            image.loadUrl(apollo.image)


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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_apolo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listApollo.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listApollo[position]

        holder.bind(item, favorite)
        holder.cardView.setOnClickListener {

            listener.onClickListener(item, true)
        }
        if (showIcon) {
            holder.star.visibility = View.VISIBLE
            showIcon = false
        }

    }

    fun deleteItem(position: Int, recyclerView: RecyclerView) {
        var delePosition = listApollo[position]
        listApollo.removeAt(position)
        notifyItemRemoved(position)
        Snackbar.make(recyclerView, "Remove Item", Snackbar.LENGTH_LONG)

            .setAction("Cancel") {
                listApollo.add(position, delePosition)
                notifyItemInserted(position)
            }.setActionTextColor(Color.RED).show()


    }

    fun addFavorite(position: Int, recyclerView: RecyclerView, view: RecyclerView.ViewHolder) {
        val apollo = listApollo[position]
        notifyItemChanged(apollo.id)

        Snackbar.make(recyclerView, "Add Favorite: ${apollo.tittle}", Snackbar.LENGTH_LONG).show()

        val model = Apollo(
            apollo.id,
            apollo.image,
            apollo.tittle,
            apollo.dataCreate,
            apollo.description,
            apollo.nasaId,
            apollo.mediaType,
            true
        )

        listener.onClickListener(model, false)
    }

}