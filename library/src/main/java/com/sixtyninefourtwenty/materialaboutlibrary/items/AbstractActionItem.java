package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class AbstractActionItem extends AbstractItem {

    private final Runnable onClickAction;
    private final Runnable onLongClickAction;

    public Runnable getOnClickAction() {
        return onClickAction;
    }
    public Runnable getOnLongClickAction() {
        return onLongClickAction;
    }

    protected AbstractActionItem(Builder<?> builder) {
        super(builder);
        onClickAction = builder.onClickAction;
        onLongClickAction = builder.onLongClickAction;
    }

    public static abstract class ViewHolder extends AbstractItem.ViewHolder {
        private AbstractActionItem actionItem;
        private final View.OnClickListener onItemClick = v -> actionItem.getOnClickAction().run();
        private final View.OnLongClickListener onItemLongClick = v -> {
            actionItem.getOnLongClickAction().run();
            return true;
        };

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void setupItem(AbstractItem item) {
            super.setupItem(item);
            actionItem = (AbstractActionItem) item;
            int pL = 0, pT = 0, pR = 0, pB = 0;
            if (Build.VERSION.SDK_INT < 21) {
                pL = itemView.getPaddingLeft();
                pT = itemView.getPaddingTop();
                pR = itemView.getPaddingRight();
                pB = itemView.getPaddingBottom();
            }

            var onClickAction = actionItem.getOnClickAction();
            var onLongClickAction = actionItem.getOnLongClickAction();
            if (onClickAction != null || onLongClickAction != null) {
                var outValue = new TypedValue();
                itemView.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.selectableItemBackground, outValue, true);
                itemView.setBackgroundResource(outValue.resourceId);
            } else {
                itemView.setBackgroundResource(0);
            }

            itemView.setOnClickListener(onClickAction != null ? onItemClick : null);
            itemView.setOnLongClickListener(onLongClickAction != null ? onItemLongClick : null);

            if (Build.VERSION.SDK_INT < 21) {
                itemView.setPadding(pL, pT, pR, pB);
            }

        }
    }

    public static abstract class Builder<T extends Builder<T>> extends AbstractItem.Builder<T> {

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
        public abstract AbstractActionItem build();

    }

}
