package com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.view.flex_cover_carousel.flex_cover_card

import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.domain.model.flex_cover_carousel.FlexCoverCard

class FlexCoverCardPresenter {
    /**
     * Binds the view.
     *
     * @param model the data to bind.
     */
    fun bindView(model: FlexCoverCard?, view: FlexCoverCardInterfaceView) {
        model?.let {
            setCoverImage(model.image_header, view)
            setOnClick(model.link, view)
        }
    }

    private fun setCoverImage(cover: String, view: FlexCoverCardInterfaceView) {
        view.setCoverImage(cover)
    }

    private fun setOnClick(link: String?, view: FlexCoverCardInterfaceView) {
        link?.let {
            view.setOnClick(link)
        } ?: run{
            view.dismissClickable()
        }
    }
}