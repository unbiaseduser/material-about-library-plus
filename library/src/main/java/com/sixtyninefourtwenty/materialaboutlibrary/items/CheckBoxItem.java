package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalCheckboxItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

/**
 * Created by François Dexemple on 04/05/2018
 */
public class CheckBoxItem extends AbstractCheckableItem {

    private CheckBoxItem(Builder builder) {
        super(builder);
    }

    @Override
    public ItemType getType() {
        return ItemType.CHECKBOX;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutCheckBoxItem{" +
                "text=" + getTitle() +
                ", textRes=" + getTitleRes() +
                ", subText=" + getSubtext() +
                ", subTextRes=" + getSubtextRes() +
                ", subTextChecked=" + getSubTextChecked() +
                ", subTextCheckedRes=" + getSubTextCheckedRes() +
                ", icon=" + getIcon() +
                ", iconRes=" + getIconRes() +
                ", iconGravity=" + getIconGravity() +
                "} extends " + super.toString();
    }

    public static class ViewHolder extends AbstractCheckableItem.ViewHolder {
        private final MalCheckboxItemBinding binding;

        public ViewHolder(MalCheckboxItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        @Override
        public CompoundButton getToggleComponent() {
            return binding.malCheckbox;
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

    public static final class Builder extends AbstractCheckableItem.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @NonNull
        @Override
        public CheckBoxItem build() {
            return new CheckBoxItem(this);
        }
    }

}
