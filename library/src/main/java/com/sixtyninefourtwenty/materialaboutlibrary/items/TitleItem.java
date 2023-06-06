package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalTitleItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

public class TitleItem extends AbstractActionItem {

    private TitleItem(Builder builder) {
        super(builder);
    }

    @Override
    public ItemType getType() {
        return ItemType.TITLE;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutTitleItem{" +
                "text=" + getTitle() +
                ", textRes=" + getTitleRes() +
                ", desc=" + getSubtext() +
                ", descRes=" + getSubtextRes() +
                ", icon=" + getIcon() +
                ", iconRes=" + getIconRes() +
                ", onClickAction=" + getOnClickAction() +
                ", onLongClickAction=" + getOnLongClickAction() +
                '}';
    }

    public static class ViewHolder extends AbstractActionItem.ViewHolder {
        private final MalTitleItemBinding binding;

        public ViewHolder(MalTitleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        @Override
        public ImageView getIconHolder() {
            return binding.malItemImage;
        }

        @NonNull
        @Override
        public TextView getTitleHolder() {
            return binding.malItemText;
        }

        @NonNull
        @Override
        public TextView getSubtextHolder() {
            return binding.malItemSubtext;
        }
    }

    public static final class Builder extends AbstractActionItem.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @NonNull
        @Override
        public TitleItem build() {
            return new TitleItem(this);
        }
    }

}
