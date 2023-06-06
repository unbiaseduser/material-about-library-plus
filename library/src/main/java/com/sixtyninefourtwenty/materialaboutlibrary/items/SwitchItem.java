package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalSwitchItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

/**
 * Created by François Dexemple on 04/05/2018
 */
public class SwitchItem extends AbstractCheckableItem {

    private SwitchItem(Builder builder) {
        super(builder);
    }

    @Override
    public ItemType getType() {
        return ItemType.SWITCH;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutSwitchItem{" +
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
        private final MalSwitchItemBinding binding;

        public ViewHolder(MalSwitchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        @Override
        public CompoundButton getToggleComponent() {
            return binding.malSwitch;
        }

        @NonNull
        @Override
        public ImageView getIconHolder() {
            return binding.malSwitchImage;
        }

        @NonNull
        @Override
        public TextView getTitleHolder() {
            return binding.malSwitchText;
        }

        @NonNull
        @Override
        public TextView getSubtextHolder() {
            return binding.malSwitchSubtext;
        }
    }

    public static final class Builder extends AbstractCheckableItem.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @NonNull
        @Override
        public SwitchItem build() {
            return new SwitchItem(this);
        }
    }

}
