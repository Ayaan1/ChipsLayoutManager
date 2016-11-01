package com.beloo.widget.chipslayoutmanager.layouter;

import android.graphics.Rect;
import android.support.annotation.Nullable;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.cache.IViewCacheStorage;

public class RTLLayouterFactory extends AbstractLayouterFactory {

    public RTLLayouterFactory(ChipsLayoutManager layoutManager, IViewCacheStorage cacheStorage) {
        super(cacheStorage, layoutManager);
    }


    private Rect createOffsetRectForUpLayouter(Rect anchorRect) {
        return new Rect(
                //we shouldn't include anchor view here, so anchorLeft is a rightOffset
                anchorRect == null ? layoutManager.getPaddingLeft() : anchorRect.right,
                anchorRect == null ? layoutManager.getPaddingTop() : anchorRect.top,
                0,
                anchorRect == null ? layoutManager.getPaddingBottom() : anchorRect.bottom);
    }

    public ILayouter getUpLayouter(@Nullable Rect anchorRect) {
        Rect offsetRect = createOffsetRectForUpLayouter(anchorRect);

        AbstractLayouter layouter = new RTLUpLayouter(layoutManager,
                layoutManager.getChildGravityResolver(),
                cacheStorage,
                offsetRect,
                new CriteriaAdditionalRow(new CriteriaUpLayouterFinished(), getAdditionalRowsCount()));

        layouter.setMaxViewsInRow(getMaxViewsInRow());
        return layouter;
    }

    private Rect createOffsetRectForDownLayouter(Rect anchorRect) {
        return new Rect(
                0,
                anchorRect == null ? layoutManager.getPaddingTop() : anchorRect.top,
                anchorRect == null ? layoutManager.getPaddingRight() : anchorRect.right,
                anchorRect == null ? layoutManager.getPaddingBottom() : anchorRect.bottom);
    }

    public ILayouter getDownLayouter(@Nullable Rect anchorRect) {
        Rect offsetRect = createOffsetRectForDownLayouter(anchorRect);

        AbstractLayouter layouter = new RTLDownLayouter(layoutManager,
                layoutManager.getChildGravityResolver(),
                cacheStorage,
                offsetRect,
                new CriteriaAdditionalRow(new CriteriaDownLayouterFinished(), getAdditionalRowsCount()));

        layouter.setMaxViewsInRow(getMaxViewsInRow());
        return layouter;
    }

}
