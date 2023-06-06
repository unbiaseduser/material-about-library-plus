package com.sixtyninefourtwenty.materialaboutlibrary.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RestrictTo;

import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalActionCheckboxItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalActionItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalActionSwitchItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalCheckboxItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalSwitchItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalTitleItemBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.items.AbstractItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.ActionCheckBoxItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.ActionItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.ActionSwitchItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.CheckBoxItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.SwitchItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.TitleItem;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class ViewTypeManagerImpl extends AbstractViewTypeManager {

    @Override
    public AbstractItem.ViewHolder getViewHolder(ItemType type, ViewGroup vg) {
        var inflater = LayoutInflater.from(vg.getContext());
        return switch (type) {
            case ACTION -> new ActionItem.ViewHolder(MalActionItemBinding.inflate(inflater, vg, false));
            case TITLE -> new TitleItem.ViewHolder(MalTitleItemBinding.inflate(inflater, vg, false));
            case SWITCH -> new SwitchItem.ViewHolder(MalSwitchItemBinding.inflate(inflater, vg, false));
            case CHECKBOX -> new CheckBoxItem.ViewHolder(MalCheckboxItemBinding.inflate(inflater, vg, false));
            case ACTION_SWITCH -> new ActionSwitchItem.ViewHolder(MalActionSwitchItemBinding.inflate(inflater, vg, false));
            case ACTION_CHECKBOX -> new ActionCheckBoxItem.ViewHolder(MalActionCheckboxItemBinding.inflate(inflater, vg, false));
        };
    }

}
