package com.sixtyninefourtwenty.materialaboutlibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.sixtyninefourtwenty.materialaboutlibrary.adapters.CardAdapter;
import com.sixtyninefourtwenty.materialaboutlibrary.model.AboutList;
import com.sixtyninefourtwenty.materialaboutlibrary.util.AbstractViewTypeManager;
import com.sixtyninefourtwenty.materialaboutlibrary.util.ViewTypeManagerImpl;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;

@SuppressWarnings("unused")
public abstract class MaterialAboutFragment extends Fragment {

    @NonNull
    private AboutList aboutList = new AboutList.Builder().build();
    private CoordinatorLayout root;
    private RecyclerView recyclerView;
    private CardAdapter adapter;

    protected abstract AboutList getMaterialAboutList();

    protected boolean shouldAnimate() {
        return true;
    }

    public CoordinatorLayout getRoot() {
        return root;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = new CoordinatorLayout(requireContext());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        recyclerView = new RecyclerView(requireContext());
        adapter = new CardAdapter(getViewTypeManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        var animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator sia) {
            sia.setSupportsChangeAnimations(false);
        }

        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.mal_baseline_half));
        recyclerView.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(20);
        root.addView(recyclerView);
        new ListTask().execute();
        return root;
    }

    @NonNull
    protected AbstractViewTypeManager getViewTypeManager() {
        return new ViewTypeManagerImpl();
    }

    @NonNull
    protected AboutList getList() {
        return aboutList;
    }

    protected void setMaterialAboutList(@NonNull AboutList aboutList) {
        this.aboutList = aboutList;
        adapter.submitList(aboutList.getCards());
    }

    private void displayList() {
        if (shouldAnimate()) {
            recyclerView.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(600)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
        } else {
            recyclerView.setAlpha(1f);
            recyclerView.setTranslationY(0f);
        }
    }

    protected void refreshMaterialAboutList() {
        setMaterialAboutList(aboutList);
    }

    private final class ListTask {

        public void execute() {
            final var d = BuildersKt.<AboutList>async(LifecycleOwnerKt.getLifecycleScope(getViewLifecycleOwner()),
                    (CoroutineContext) Dispatchers.getDefault(),
                    CoroutineStart.DEFAULT,
                    (s, c) -> getMaterialAboutList());
            d.invokeOnCompletion(throwable -> {
                requireActivity().runOnUiThread(() -> {
                    setMaterialAboutList(d.getCompleted());
                    displayList();
                });
                return Unit.INSTANCE;
            });
        }

    }
}
