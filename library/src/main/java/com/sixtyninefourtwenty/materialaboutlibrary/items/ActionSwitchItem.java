package com.sixtyninefourtwenty.materialaboutlibrary.items;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalActionSwitchItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;

public class ActionSwitchItem extends AbstractActionCheckableItem {

    private ActionSwitchItem(Builder builder) {
        super(builder);
    }

    @Override
    public ItemType getType() {
        return ItemType.ACTION_SWITCH;
    }

    @NonNull
    @Override
    public String toString() {
        return "MaterialAboutActionSwitchItem {" +
                "onClickAction=" + getOnClickAction() +
                ", onLongClickAction=" + getOnLongClickAction() +
                "} extends " + super.toString();
    }

    public static class ViewHolder extends AbstractActionCheckableItem.ViewHolder {

        private final MalActionSwitchItemBinding binding;

        public ViewHolder(MalActionSwitchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @NonNull
        @Override
        public CompoundButton getToggleComponent() {
            return binding.malSwitch;
        }

        @Override
        public View getActionArea() {
            return binding.malSwitchAction;
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

    public static final class Builder extends AbstractActionCheckableItem.Builder<Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @NonNull
        @Override
        public ActionSwitchItem build() {
            return new ActionSwitchItem(this);
        }
    }

}
