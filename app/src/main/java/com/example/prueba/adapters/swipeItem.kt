package com.example.prueba.adapters

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba.App
import com.example.prueba.R
import com.example.prueba.views.ApolloFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class swipeItem(var adapter: ApolloAdapter, val recyclerView: RecyclerView,contextFragment: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    val context = contextFragment
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(context , c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
            .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.red))
            .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            .addSwipeRightActionIcon(R.drawable.ic_baseline_star_35)
            .create()
            .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when(direction){
            ItemTouchHelper.LEFT -> {
                var pos = viewHolder.adapterPosition

                adapter.deleteItem(pos,recyclerView)}
            ItemTouchHelper.RIGHT ->{
                var pos = viewHolder.adapterPosition
                adapter.addFavorite(pos,recyclerView,viewHolder )

            }
        }


    }


}