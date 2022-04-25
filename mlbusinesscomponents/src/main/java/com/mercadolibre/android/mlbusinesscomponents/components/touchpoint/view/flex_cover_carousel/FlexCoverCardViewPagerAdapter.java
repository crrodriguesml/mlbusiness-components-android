package com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.view.flex_cover_carousel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.callback.OnClickCallback;
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.domain.model.cover_carousel.model.cover_card.CoverCardInterfaceModel;
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.view.cover_carousel.cover_card.CoverCardInterfaceView;
import com.mercadolibre.android.mlbusinesscomponents.components.touchpoint.view.flex_cover_carousel.flex_cover_card.FlexCoverCardView;

import java.util.ArrayList;
import java.util.List;

public class FlexCoverCardViewPagerAdapter extends PagerAdapter {

    private final Context context;
    @Nullable private OnClickCallback onClickCallback;
    private final List<CoverCardInterfaceView> elementsView;

    /* default */ FlexCoverCardViewPagerAdapter(final Context context) {
        this.context = context;
        elementsView = new ArrayList<>();
    }

    /* default */ void setElementsView(final List<CoverCardInterfaceModel> itemsView) {
        if (itemsView.size() <= elementsView.size()) {
            while (itemsView.size() != elementsView.size()) {
                elementsView.remove(elementsView.size() - 1);
            }
        }

        addItemsInElementsView(itemsView);
        notifyDataSetChanged();
    }

    /* default */ List<CoverCardInterfaceView> getElementsList() {
        return elementsView;
    }

    /* default */ void setOnClickCallback(@Nullable final OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }

    private void addItemsInElementsView(final List<CoverCardInterfaceModel> itemsView) {
        FlexCoverCardView view;
        int itemsViewIndex = 0;

        for (final CoverCardInterfaceModel model: itemsView) {

            if (itemsViewIndex < elementsView.size()) {
                elementsView.get(itemsViewIndex).bind(model);
            } else {
                view = new FlexCoverCardView(context);
                view.setOnClickCallback(onClickCallback);
                view.bind(model);
                elementsView.add(elementsView.size(), view);
            }

            itemsViewIndex++;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        final int virtualPosition = position % elementsView.size();
        container.addView(elementsView.get(virtualPosition).getView());
        return elementsView.get(virtualPosition);
    }

    @Override
    public int getCount() {
        return elementsView.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull final View view, @NonNull final Object object) {
        return view.equals(object);
    }
}
