package com.sixtyninefourtwenty.materialaboutlibrary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sixtyninefourtwenty.materialaboutlibrary.R;
import com.sixtyninefourtwenty.materialaboutlibrary.databinding.MalListCardBinding;
import com.sixtyninefourtwenty.materialaboutlibrary.model.AboutCard;
import com.sixtyninefourtwenty.materialaboutlibrary.util.AbstractViewTypeManager;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ViewTypeManagerImpl;

public final class CardAdapter extends ListAdapter<AboutCard, CardAdapter.MaterialAboutListViewHolder> {

    private final AbstractViewTypeManager viewTypeManager;

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public CardAdapter() {
        this(new ViewTypeManagerImpl());
    }

    public CardAdapter(@NonNull AbstractViewTypeManager customViewTypeManager) {
        super(DIFF_CALLBACK);
        setHasStableIds(true);
        viewTypeManager = customViewTypeManager;
    }

    @NonNull
    @Override
    public MaterialAboutListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewGroup instanceof RecyclerView) {
            var binding = MalListCardBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            return new MaterialAboutListViewHolder(binding, viewPool, viewTypeManager);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialAboutListViewHolder holder, int position) {
        var card = getItem(position);

        if (card.isOutline()) {
            holder.cardView.setStrokeWidth(holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.mal_stroke_width));
            holder.cardView.setCardElevation(0);
        } else {
            holder.cardView.setStrokeWidth(0);
            holder.cardView.setCardElevation(holder.itemView.getContext().getResources().getDimension(R.dimen.mal_card_elevation));
        }

        int cardColor = card.getCardColor();
        if (cardColor != 0) {
            holder.cardView.setCardBackgroundColor(cardColor);
        } else {
            holder.cardView.setCardBackgroundColor(holder.cardView.getCardBackgroundColor().getDefaultColor());
        }

        var title = card.getTitle();
        int titleRes = card.getTitleRes();

        holder.title.setVisibility(View.VISIBLE);
        if (title != null) {
            holder.title.setText(title);
        } else if (titleRes != 0) {
            holder.title.setText(titleRes);
        } else {
            holder.title.setVisibility(View.GONE);
        }

        int titleColor = card.getTitleColor();

        if (holder.title.getVisibility() == View.VISIBLE) {
            if (titleColor != 0) {
                holder.title.setTextColor(titleColor);
            } else {
                holder.title.setTextColor(holder.title.getTextColors().getDefaultColor());
            }
        }

        if (card.getCustomView() != null) {
            holder.useCustomView(card.getCustomView());
        } else if (card.getCustomAdapter() != null) {
            holder.useCustomAdapter(card.getCustomAdapter());
        } else {
            holder.useMaterialAboutItemAdapter();
            ((ItemAdapter) holder.adapter).submitList(card.getItems());
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public static final class MaterialAboutListViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView cardView;
        private final TextView title;
        private final RecyclerView recyclerView;
        private final RecyclerView.RecycledViewPool viewPool;
        RecyclerView.Adapter<?> adapter;
        private final AbstractViewTypeManager manager;

        MaterialAboutListViewHolder(MalListCardBinding binding, RecyclerView.RecycledViewPool viewPool, AbstractViewTypeManager manager) {
            super(binding.getRoot());
            this.manager = manager;
            this.viewPool = viewPool;
            cardView = binding.getRoot();
            title = binding.malListCardTitle;
            recyclerView = binding.malCardRecyclerview;
            recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            recyclerView.setNestedScrollingEnabled(false);
        }

        public void useCustomView(View customView) {
            cardView.removeAllViews();
            cardView.addView(customView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        public void useMaterialAboutItemAdapter() {
            if (!(adapter instanceof ItemAdapter)) {
                adapter = new ItemAdapter(manager);
                recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                recyclerView.setRecycledViewPool(viewPool);
                recyclerView.setAdapter(adapter);
            }
        }

        public void useCustomAdapter(RecyclerView.Adapter<?> newAdapter) {
            if (adapter == null || !(adapter.getClass().isInstance(newAdapter))) {
                recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                recyclerView.setRecycledViewPool(null);
                recyclerView.setAdapter(newAdapter);
            }
        }
    }

    public static final DiffUtil.ItemCallback<AboutCard> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(AboutCard oldItem, AboutCard newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(AboutCard oldItem, AboutCard newItem) {
            boolean result;
            result = oldItem.toString().equals(newItem.toString());
            if (oldItem.getItems().size() != newItem.getItems().size()) return false;
            for (int i = 0; i < oldItem.getItems().size(); i++) {
                if (!oldItem.getItems().get(i).toString().equals(newItem.getItems().get(i).toString()))
                    return false;
            }
            return result;
        }
    };
}
