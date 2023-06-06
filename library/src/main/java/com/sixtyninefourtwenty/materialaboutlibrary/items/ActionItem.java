package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalActionItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

public class ActionItem extends AbstractActionItem {

    private ActionItem(Builder builder) {
        super(builder);
    }

    @Override
    public ItemType getType() {
        return ItemType.ACTION;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutActionItem{" +
                "text=" + getTitle() +
                ", textRes=" + getTitleRes() +
                ", subText=" + getSubtext() +
                ", subTextRes=" + getSubtextRes() +
                ", icon=" + getIcon() +
                ", iconRes=" + getIconRes() +
                ", iconGravity=" + getIconGravity() +
                ", onClickAction=" + getOnClickAction() +
                ", onLongClickAction=" + getOnLongClickAction() +
                '}';
    }

    public static class ViewHolder extends AbstractActionItem.ViewHolder {
        private final MalActionItemBinding binding;

        public ViewHolder(MalActionItemBinding binding) {
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
            return binding.malActionItemSubtext;
        }
    }

    public static final class Builder extends AbstractActionItem.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @NonNull
        @Override
        public ActionItem build() {
            return new ActionItem(this);
        }
    }

}
