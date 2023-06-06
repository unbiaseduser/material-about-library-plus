package com.sixtyninefourtwenty.materialaboutlibrary.util;

import android.view.ViewGroup;

import com.sixtyninefourtwenty.materialaboutlibrary.items.AbstractItem;

public abstract class AbstractViewTypeManager {

    public abstract AbstractItem.ViewHolder getViewHolder(ItemType type, ViewGroup vg);

}
