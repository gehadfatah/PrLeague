package com.football.pl_fixture.ui.fixture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.football.pl_fixture.R
import com.football.pl_fixture.databinding.ListItemMatchesDateBinding
import com.football.pl_fixture.data.model.MatchesItem
import com.football.pl_fixture.databinding.ItemMatchBinding
import com.football.pl_fixture.ui.bindingadapter.setFavourite
import com.football.pl_fixture.ui.fixture.viewmodels.MatchItemViewModel
import com.football.pl_fixture.utils.toDate
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FixtureAdapter(val onFavouriteClickListener: OnFavouriteClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var recyclerView: RecyclerView? = null
    var matches = ArrayList<Any>()

    fun addMatches(matches: List<MatchesItem>) {
        this.matches.clear()
        var scrollToPosition = 0
        val groupedMatches: Map<String, List<MatchesItem>> = matches.groupBy { toDate(it.date!!) }
        val dates = groupedMatches.keys
        val currentDate = SimpleDateFormat(
            "EEE, d MMM yyyy",
            Locale.getDefault()
        ).format(Date(System.currentTimeMillis()))

        dates.forEach { date ->
            val dateMatches: List<MatchesItem>? = groupedMatches[date]
            dateMatches?.let {
                this.matches.add(date)
                if (date == currentDate) scrollToPosition = this.matches.size - 1
                this.matches.addAll(dateMatches)
            }
        }

        notifyDataSetChanged()

        recyclerView?.scrollToPosition(scrollToPosition)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FixtureItems.DATES.value)
            DatesHolder(
                ListItemMatchesDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else FixtureHolder(
            ItemMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DatesHolder -> {
                holder.mBinding.TVDate.text = matches[holder.adapterPosition].toString()
            }

            is FixtureHolder -> {
                val match = matches[position] as MatchesItem
                holder.bind(match)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val matches = matches[position]
        return if (matches is String) FixtureItems.DATES.value else FixtureItems.MATCHES.value
    }

    inner class FixtureHolder(private val mBinding: ItemMatchBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        private val viewModel = MatchItemViewModel()
        fun bind(matchesItem: MatchesItem) {
            viewModel.bind(matchesItem)
            mBinding.matchItemVM = viewModel

            mBinding.ivFav.setOnClickListener {
                //setfav( mBinding.ivFav,matchesItem.isFavourite)
                mBinding.ivFav.onClick(it)
                onFavouriteClickListener.onFavouriteClicked(matchesItem)
            }

            mBinding.ivAwayFlag.visibility =
                if (matchesItem.awayteam.crest.isNullOrEmpty()) View.VISIBLE else View.GONE
            mBinding.ivHomeFlag.visibility =
                if (matchesItem.homeTeam.crest.isNullOrEmpty()) View.VISIBLE else View.GONE
        }
    }

    fun setFav(likeButton: ImageView, isFavourite: Boolean) {
        if (isFavourite.not()) {
            Glide.with(likeButton.context)
                .load(R.drawable.ic_favorites_active)
                .placeholder(
                    ContextCompat.getDrawable(
                        likeButton.context,
                        R.drawable.ic_launcher_foreground
                    )
                )
                .into(likeButton)
        } else {

            Glide.with(likeButton.context)
                .load(R.drawable.ic_favorites_grey)
                .placeholder(
                    ContextCompat.getDrawable(
                        likeButton.context,
                        R.drawable.ic_launcher_foreground
                    )
                )
                .into(likeButton)

        }
    }

    inner class DatesHolder(val mBinding: ListItemMatchesDateBinding) :
        RecyclerView.ViewHolder(mBinding.root)

    enum class FixtureItems(val value: Int) {
        DATES(0), MATCHES(1)
    }

}