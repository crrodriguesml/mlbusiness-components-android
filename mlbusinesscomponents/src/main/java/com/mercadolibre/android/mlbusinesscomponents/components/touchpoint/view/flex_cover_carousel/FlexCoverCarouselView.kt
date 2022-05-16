package com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.view.flex_cover_carousel

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ViewFlipper
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mercadolibre.android.mlbusinesscomponents.R
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.callback.OnClickCallback
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.domain.model.AdditionalEdgeInsets
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.domain.model.flex_cover_carousel.FlexCoverCard
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.domain.model.flex_cover_carousel.FlexCoverCarouselResponse
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.view.AbstractTouchpointChildView
import com.mercadolibre.android.mlbusinesscomponents.components.utils.ScaleUtils

class FlexCoverCarouselView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractTouchpointChildView<FlexCoverCarouselResponse>(context, attrs, defStyleAttr),
    FlexCoverCarouselViewInterface {

    private val presenter: FlexCoverCarouselPresenter
    private val flipper: ViewFlipper
    private val viewPager: ViewPager
    private val viewPagerAdapter: FlexCoverCardViewPagerAdapter


    init {
        inflate(context, R.layout.touchpoint_flex_cover_carousel_view, this)
        presenter = FlexCoverCarouselPresenter()
        flipper = findViewById(R.id.touchpoint_flex_cover_carousel_view_flipper)
        viewPager = findViewById(R.id.flex_cover_carousel_view_pager)
        viewPagerAdapter = FlexCoverCardViewPagerAdapter(getContext())
        initViewPager()
    }

    private fun initViewPager() {
        viewPager.adapter = viewPagerAdapter
        setMargins()
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //no op..
            }

            override fun onPageSelected(position: Int) {
                //no op..
            }

            override fun onPageScrollStateChanged(state: Int) {
                //no op..
            }
        })
        viewPager.setPageTransformer(false) { page: View, position: Float ->
            page.translationX = if (viewPager.currentItem == viewPagerAdapter.count - 1) {
                (viewPager.paddingRight - viewPager.paddingLeft).toFloat()
            } else {
                0f
            }
        }
    }

    override fun bind(model: FlexCoverCarouselResponse?) {
        model?.let { presenter.mapResponse(it, this) }
    }

    override fun getStaticHeight(): Int {
        return 0
    }

    override fun print() {
        //no op..
    }

    override fun setVisibilityGone() {
        visibility = GONE
    }

    override fun setItemsList(items: List<FlexCoverCard>) {
        viewPagerAdapter.setElementsView(items)
        viewPager.currentItem = 0
        presenter.getMaxHeight(viewPagerAdapter.elementsList, this)
    }

    override fun decorate() {
        additionalInsets?.let { setViewPagerPaddingsFromInsets(it) }
    }

    private fun setViewPagerPaddingsFromInsets(
        additionalInsets: AdditionalEdgeInsets
    ) {
        viewPager.setPadding(
            getInsetInPx(additionalInsets.left),
            getInsetInPx(additionalInsets.top),
            getInsetInPx(additionalInsets.right),
            getInsetInPx(additionalInsets.bottom)
        )
    }

    private fun getInsetInPx(inset: Int): Int {
        return ScaleUtils.getPxFromDp(context, inset.toFloat()).toInt()
    }

    override fun setViewPagerHeight(maxHeight: Int) {
        val params: ViewGroup.LayoutParams = viewPager.layoutParams
        params.height = maxHeight + resources.getDimensionPixelSize(R.dimen.ui_3m)

    }

    private fun setMargins() {
        viewPager.pageMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            MARGIN_BETWEEN_PAGES.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    override fun setOnClickCallback(onClickCallback: OnClickCallback?) {
        this.onClickCallback = onClickCallback
        this.onClickCallback?.let { viewPagerAdapter.setOnClickCallback(it) }
    }

    companion object {
        private const val MARGIN_BETWEEN_PAGES = 8
    }
}