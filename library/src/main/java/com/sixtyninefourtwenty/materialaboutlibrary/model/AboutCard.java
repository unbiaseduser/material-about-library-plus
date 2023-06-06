package com.sixtyninefourtwenty.materialaboutlibrary.model;

import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.sixtyninefourtwenty.materialaboutlibrary.items.AbstractItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class AboutCard {

    private final UUID id = UUID.randomUUID();

    private final CharSequence title;
    private final int titleRes;

    private final int titleColor;
    private final int cardColor;
    private final boolean outline;

    private final RecyclerView.Adapter<?> customAdapter;
    private final View customView;
    private final List<AbstractItem> items = new ArrayList<>();

    private AboutCard(Builder builder) {
        this.title = builder.title;
        this.titleRes = builder.titleRes;
        this.titleColor = builder.titleColor;
        this.cardColor = builder.cardColor;
        customView = builder.customView;
        items.addAll(builder.items);
        this.customAdapter = builder.customAdapter;
        this.outline = builder.outline;
    }

    public CharSequence getTitle() {
        return title;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getCardColor() {
        return cardColor;
    }

    public boolean isOutline() {
        return outline;
    }

    public List<AbstractItem> getItems() {
        return items;
    }

    public static final class Builder {
        private CharSequence title = null;
        @StringRes
        private int titleRes = 0;
        @ColorInt
        private int titleColor = 0;
        @ColorInt
        private int cardColor = 0;
        private boolean outline = true;
        private final List<AbstractItem> items = new ArrayList<>();
        private View customView = null;
        private RecyclerView.Adapter<?> customAdapter = null;

        public Builder setTitle(@NonNull CharSequence title) {
            this.title = title;
            titleRes = 0;
            return this;
        }

        public Builder setTitle(@StringRes int titleRes) {
            this.titleRes = titleRes;
            title = null;
            return this;
        }

        public Builder setTitleColor(@ColorInt int color) {
            this.titleColor = color;
            return this;
        }

        public Builder setCardColor(@ColorInt int cardColor) {
            this.cardColor = cardColor;
            return this;
        }

        /**
         * Use outlined card design - true by default
         * @param outline false to enable elevation
         */
        public Builder setOutline(boolean outline) {
            this.outline = outline;
            return this;
        }

        @NonNull
        public Builder addItem(@NonNull AbstractItem item) {
            items.add(item);
            return this;
        }

        public Builder setCustomAdapter(@NonNull RecyclerView.Adapter<?> customAdapter) {
            this.customAdapter = customAdapter;
            return this;
        }

        public Builder setCustomView(@NonNull View customView) {
            this.customView = customView;
            return this;
        }

        public AboutCard build() {
            return new AboutCard(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public RecyclerView.Adapter<?> getCustomAdapter() {
        return customAdapter;
    }

    public View getCustomView() {
        return customView;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutCard{" +
                "id='" + id + '\'' +
                ", title=" + title +
                ", titleRes=" + titleRes +
                ", titleColor=" + titleColor +
                ", customAdapter=" + customAdapter +
                ", outline=" + outline +
                ", cardColor=" + cardColor + '}';
    }

}
