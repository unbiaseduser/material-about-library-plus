package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalActionCheckboxItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

public class ActionCheckBoxItem extends AbstractActionCheckableItem {

    private ActionCheckBoxItem(Builder builder) {
        super(builder);
    }

    @Override
    public ItemType getType() {
        return ItemType.ACTION_CHECKBOX;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutActionCheckBoxItem{" +
                "onClickAction=" + getOnClickAction() +
                ", onLongClickAction=" + getOnLongClickAction() +
                "} extends " + super.toString();
    }

    public static class ViewHolder extends AbstractActionCheckableItem.ViewHolder {
        private final MalActionCheckboxItemBinding binding;

        public ViewHolder(MalActionCheckboxItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        @Override
        public CompoundButton getToggleComponent() {
            return binding.malCheckbox;
        }

        @Override
        public View getActionArea() {
            return binding.malCheckboxAction;
        }

        @NonNull
        @Override
        public ImageView getIconHolder() {
            return binding.malCheckboxImage;
        }

        @NonNull
        @Override
        public TextView getTitleHolder() {
            return binding.malCheckboxText;
        }

        @NonNull
        @Override
        public TextView getSubtextHolder() {
            return binding.malCheckboxSubtext;
        }
    }

    public static final class Builder extends AbstractActionCheckableItem.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @NonNull
        @Override
        public ActionCheckBoxItem build() {
            return new ActionCheckBoxItem(this);
        }
    }

}
