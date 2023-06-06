package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class AbstractActionCheckableItem extends AbstractCheckableItem {

    private final Runnable onClickAction;
    private final Runnable onLongClickAction;

    public Runnable getOnClickAction() {
        return onClickAction;
    }
    public Runnable getOnLongClickAction() {
        return onLongClickAction;
    }

    protected AbstractActionCheckableItem(Builder<?> builder) {
        super(builder);
        onClickAction = builder.onClickAction;
        onLongClickAction = builder.onLongClickAction;
    }

    public static abstract class ViewHolder extends AbstractCheckableItem.ViewHolder {
        private AbstractActionCheckableItem actionCheckableItem;
        private final View.OnClickListener onItemClick = v -> actionCheckableItem.getOnClickAction().run();
        private final View.OnLongClickListener onItemLongClick = v -> {
            actionCheckableItem.getOnLongClickAction().run();
            return true;
        };

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract View getActionArea();

        @Override
        public void setupItem(AbstractItem item) {
            super.setupItem(item);
            actionCheckableItem = (AbstractActionCheckableItem) item;

            var actionArea = getActionArea();

            int pL = 0, pT = 0, pR = 0, pB = 0;
            if (Build.VERSION.SDK_INT < 21) {
                pL = actionArea.getPaddingLeft();
                pT = actionArea.getPaddingTop();
                pR = actionArea.getPaddingRight();
                pB = actionArea.getPaddingBottom();
            }

            var onClickAction = actionCheckableItem.getOnClickAction();
            var onLongClickAction = actionCheckableItem.getOnLongClickAction();

            if (onClickAction != null || onLongClickAction != null) {
                var outValue = new TypedValue();
                itemView.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.selectableItemBackground, outValue, true);
                actionArea.setBackgroundResource(outValue.resourceId);
            } else {
                actionArea.setBackgroundResource(0);
            }

            actionArea.setOnClickListener(onClickAction != null ? onItemClick : null);
            actionArea.setOnLongClickListener(onLongClickAction != null ? onItemLongClick : null);

            if (Build.VERSION.SDK_INT < 21) {
                actionArea.setPadding(pL, pT, pR, pB);
            }

        }
    }

    public static abstract class Builder<T extends Builder<T>> extends AbstractCheckableItem.Builder<T> {
        private Runnable onClickAction;
        private Runnable onLongClickAction;

        public T setOnClickAction(Runnable onClickAction) {
            this.onClickAction = onClickAction;
            return self();
        }

        public T setOnLongClickAction(Runnable onLongClickAction) {
            this.onLongClickAction = onLongClickAction;
            return self();
        }

        @NonNull
        @Override
        public abstract AbstractActionCheckableItem build();
    }

}
