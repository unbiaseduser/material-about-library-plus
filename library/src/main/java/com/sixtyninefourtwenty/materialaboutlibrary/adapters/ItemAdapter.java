package com.sixtyninefourtwenty.materialaboutlibrary.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.sixtyninefourtwenty.materialaboutlibrary.items.AbstractItem;
import com.sixtyninefourtwenty.materialaboutlibrary.util.AbstractViewTypeManager;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ItemType;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ViewTypeManagerImpl;

public final class ItemAdapter extends ListAdapter<AbstractItem, AbstractItem.ViewHolder> {

    private final AbstractViewTypeManager viewTypeManager;

    ItemAdapter() {
        this(new ViewTypeManagerImpl());
    }

    ItemAdapter(AbstractViewTypeManager customViewTypeManager) {
        super(DIFF_CALLBACK);
        setHasStableIds(true);
        this.viewTypeManager = customViewTypeManager;
    }

    @NonNull
    @Override
    public AbstractItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return viewTypeManager.getViewHolder(ItemType.VALUES.get(viewType), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractItem.ViewHolder holder, int position) {
        holder.setupItem(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().getMostSignificantBits() & Long.MAX_VALUE;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    public static final DiffUtil.ItemCallback<AbstractItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(AbstractItem oldItem, AbstractItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(AbstractItem oldItem, AbstractItem newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    };

}
