package com.mytaxi.sheraz.ui.modules.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.mytaxi.sheraz.R
import com.mytaxi.sheraz.ui.modules.home.fakemodel.FakeTaxiModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_grid.view.*

class HomeGridAdapter(
    private val mContext: Context,
    private val mListener: View.OnClickListener,
    private val mItems: List<FakeTaxiModel.Taxi>
) : RecyclerView.Adapter<HomeGridAdapter.ViewHolder>() {

    private var selectedIndex: Int = -1
    private val scaleUpAnimation: Animation by lazy { AnimationUtils.loadAnimation(mContext, R.anim.scale_up) }
    private val scaleDownAnimation: Animation by lazy { AnimationUtils.loadAnimation(mContext, R.anim.scale_down) }

    override fun onCreateViewHolder(viewGroup: ViewGroup, view_type: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_home_grid, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(itemContentModel: FakeTaxiModel.Taxi) {
            setUpViews(itemContentModel)
            handleClicks(itemContentModel)
        }

        private fun setUpViews(itemContentModel: FakeTaxiModel.Taxi) {

            if (itemView.tag == null) {
                itemView.tag = itemContentModel
            }

            Picasso.get().load(itemContentModel.driverImage)
                .noFade()
                .placeholder(R.drawable.ic_driver)
                .into(itemView.ivProfileImage)

            itemView.tvDriverName.text = itemContentModel.driverName
            itemView.tvEstimatedTimeOfArrival.text = itemContentModel.vehicleEstimatedTimeOfArrival
            itemView.tvEstimatedDistanceValue.text = itemContentModel.vehicleDistanceFromUser
            itemView.tvVehicleModelValue.text = itemContentModel.vehicleModel
            itemView.tvVehicleIdValue.text = itemContentModel.id.toString()

            when (itemContentModel.fleetType) {
                "POOLING" -> {
                    itemView.tvAvailabilityValue.text = "AVAILABLE"
                    itemView.tvAvailabilityValue.setTextColor(mContext.resources.getColor(android.R.color.holo_green_light))
                }
                "TAXI" -> {
                    itemView.tvAvailabilityValue.text = "UNAVAILABLE"
                    itemView.tvAvailabilityValue.setTextColor(mContext.resources.getColor(android.R.color.holo_red_light))
                }
            }

            when (itemContentModel.driverRating) {
                1 -> {
                    itemView.ivRatingStar1.visibility = View.VISIBLE
                    itemView.ivRatingStar2.visibility = View.GONE
                    itemView.ivRatingStar3.visibility = View.GONE
                    itemView.ivRatingStar4.visibility = View.GONE
                    itemView.ivRatingStar5.visibility = View.GONE
                }
                2 -> {
                    itemView.ivRatingStar1.visibility = View.VISIBLE
                    itemView.ivRatingStar2.visibility = View.VISIBLE
                    itemView.ivRatingStar3.visibility = View.GONE
                    itemView.ivRatingStar4.visibility = View.GONE
                    itemView.ivRatingStar5.visibility = View.GONE
                }
                3 -> {
                    itemView.ivRatingStar1.visibility = View.VISIBLE
                    itemView.ivRatingStar2.visibility = View.VISIBLE
                    itemView.ivRatingStar3.visibility = View.VISIBLE
                    itemView.ivRatingStar4.visibility = View.GONE
                    itemView.ivRatingStar5.visibility = View.GONE
                }
                4 -> {
                    itemView.ivRatingStar1.visibility = View.VISIBLE
                    itemView.ivRatingStar2.visibility = View.VISIBLE
                    itemView.ivRatingStar3.visibility = View.VISIBLE
                    itemView.ivRatingStar4.visibility = View.VISIBLE
                    itemView.ivRatingStar5.visibility = View.GONE
                }
                5 -> {
                    itemView.ivRatingStar1.visibility = View.VISIBLE
                    itemView.ivRatingStar2.visibility = View.VISIBLE
                    itemView.ivRatingStar3.visibility = View.VISIBLE
                    itemView.ivRatingStar4.visibility = View.VISIBLE
                    itemView.ivRatingStar5.visibility = View.VISIBLE
                }
            }
        }

        private fun handleClicks(itemContentModel: FakeTaxiModel.Taxi) {

            if (selectedIndex == adapterPosition) {
                itemView.ivTickMark.visibility = View.VISIBLE
                itemView.ivTickMark.startAnimation(scaleUpAnimation)
            } else {
                itemView.ivTickMark.visibility = View.GONE
            }

            itemView.setOnClickListener {
                when (itemContentModel.fleetType) {
                    "POOLING" -> {

                        // We select only POOLING/AVAILABLE TAXIs only
                        if (itemView.ivTickMark.visibility == View.VISIBLE) {
                            itemView.ivTickMark.startAnimation(scaleDownAnimation)
                            selectedIndex = -1
                            mListener.onClick(null) // send null to indicate deselection
                        } else {
                            selectedIndex = adapterPosition
                            mListener.onClick(itemView) // send view object to indicate selection
                        }
                        notifyDataSetChanged()
                    }
                    "TAXI" -> {
                        // We select only POOLING/AVAILABLE TAXIs only
                        itemView.ivTickMark.visibility = View.GONE
                        selectedIndex = -1
                    }
                }
            }
        }

        /**
         * Methods for testing purposes
         */

        fun getSelectedIndex(): Int {
            return selectedIndex
        }

        fun isCurrentItemSelected(): Boolean {
            return adapterPosition == selectedIndex
        }

    }

    companion object {
        private val TAG = HomeGridAdapter::class.java.simpleName
    }
}
