package com.sixtyninefourtwenty.materialaboutlibrary.items;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.sixtyninefourtwenty.materialaboutlibrary.util.IconGravity;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

import java.util.UUID;

public abstract class AbstractItem {

    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    public abstract ItemType getType();

    @NonNull
    @Override
    public abstract String toString();

    protected AbstractItem(Builder<?> builder) {
        title = builder.title;
        titleRes = builder.titleRes;
        subtext = builder.subtext;
        subtextRes = builder.subtextRes;
        icon = builder.icon;
        iconRes = builder.iconRes;
        iconGravity = builder.iconGravity;
    }

    private final CharSequence title;
    private final int titleRes;
    private final CharSequence subtext;
    private final int subtextRes;
    private final Drawable icon;
    private final int iconRes;
    private final IconGravity iconGravity;

    public CharSequence getTitle() {
        return title;
    }
    public int getTitleRes() {
        return titleRes;
    }
    public CharSequence getSubtext() {
        return subtext;
    }
    public int getSubtextRes() {
        return subtextRes;
    }
    public Drawable getIcon() {
        return icon;
    }
    public int getIconRes() {
        return iconRes;
    }
    public IconGravity getIconGravity() {
        return iconGravity;
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }

        @NonNull
        public abstract ImageView getIconHolder();
        @NonNull
        public abstract TextView getTitleHolder();
        @NonNull
        public abstract TextView getSubtextHolder();

        @CallSuper
        public void setupItem(AbstractItem item) {
            var text = item.getTitle();
            int textRes = item.getTitleRes();
            var titleHolder = getTitleHolder();

            titleHolder.setVisibility(VISIBLE);
            if (text != null) {
                titleHolder.setText(text);
            } else if (textRes != 0) {
                titleHolder.setText(textRes);
            } else {
                titleHolder.setVisibility(GONE);
            }

            var subText = item.getSubtext();
            int subTextRes = item.getSubtextRes();
            var subTextHolder = getSubtextHolder();

            subTextHolder.setVisibility(VISIBLE);
            if (subText != null) {
                subTextHolder.setText(subText);
            } else if (subTextRes != 0) {
                subTextHolder.setText(subTextRes);
            } else {
                subTextHolder.setVisibility(GONE);
            }

            var iconHolder = getIconHolder();
            iconHolder.setVisibility(VISIBLE);
            var drawable = item.getIcon();
            int drawableRes = item.getIconRes();
            if (drawable == null && drawableRes == 0) {
                iconHolder.setVisibility(GONE);
            } else {
                var params = (LinearLayout.LayoutParams) iconHolder.getLayoutParams();
                params.gravity = item.getIconGravity().getGravityInt();
                iconHolder.setLayoutParams(params);
                if (drawable != null) {
                    iconHolder.setImageDrawable(drawable);
                } else {
                    iconHolder.setImageResource(drawableRes);
                }
            }
        }
    }

    public static abstract class Builder<T extends Builder<T>> {
        private CharSequence title = null;
        private int titleRes = 0;
        private CharSequence subtext = null;
        private int subtextRes = 0;
        private Drawable icon = null;
        private int iconRes = 0;
        private IconGravity iconGravity = IconGravity.CENTER;

        public T setTitle(@Nullable CharSequence title) {
            this.title = title;
            titleRes = 0;
            return self();
        }

        public T setTitle(@StringRes int titleRes) {
            this.titleRes = titleRes;
            title = null;
            return self();
        }

        public T setSubtext(@Nullable CharSequence subtext) {
            this.subtext = subtext;
            subtextRes = 0;
            return self();
        }

        public T setSubtext(@StringRes int subtextRes) {
            this.subtextRes = subtextRes;
            subtext = null;
            return self();
        }

        public T setIcon(@Nullable Drawable icon) {
            this.icon = icon;
            iconRes = 0;
            return self();
        }

        public T setIcon(@DrawableRes int iconRes) {
            this.iconRes = iconRes;
            icon = null;
            return self();
        }

        public T setIconGravity(@NonNull IconGravity gravity) {
            this.iconGravity = gravity;
            return self();
        }

        @NonNull
        public abstract AbstractItem build();

        protected abstract T self();
    }

}
