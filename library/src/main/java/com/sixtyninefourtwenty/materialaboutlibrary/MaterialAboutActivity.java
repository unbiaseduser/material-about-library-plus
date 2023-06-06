package com.sixtyninefourtwenty.materialaboutlibrary;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
public abstract class MaterialAboutActivity extends AppCompatActivity {

    @NonNull
    private AboutList aboutList = new AboutList.Builder().build();
    private CoordinatorLayout root;
    private RecyclerView recyclerView;
    private CardAdapter adapter;

    @NonNull
    protected abstract AboutList getMaterialAboutList();

    @NonNull
    protected CharSequence getActivityTitle() {
        return getString(R.string.mal_title_about);
    }

    /**
     * Get a reference to the root {@link CoordinatorLayout} in case a snackbar needs to be displayed
     * @return the activity's root
     */
    @NonNull
    public CoordinatorLayout getRoot() {
        return root;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = new CoordinatorLayout(this);
        recyclerView = new RecyclerView(this);
        recyclerView.setClipToPadding(false);
        recyclerView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.mal_baseline_half));
        recyclerView.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setAlpha(0f);
        recyclerView.setTranslationY(20);
        root.addView(recyclerView);
        setContentView(root);

        setTitle(getActivityTitle());

        initViews();

        new ListTask().execute();
    }

    private void initViews() {
        var actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        adapter = new CardAdapter(getViewTypeManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        var animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator sia) {
            sia.setSupportsChangeAnimations(false);
        }
    }

    @NonNull
    protected AbstractViewTypeManager getViewTypeManager() {
        return new ViewTypeManagerImpl();
    }

    @NonNull
    protected AboutList getList() {
        return aboutList;
    }

    protected boolean shouldAnimate() {
        return true;
    }

    protected void refreshMaterialAboutList() {
        setMaterialAboutList(aboutList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onTaskFinished(@Nullable AboutList aboutList) {
        if (aboutList != null) {
            this.aboutList = aboutList;
            adapter.submitList(this.aboutList.getCards());

            if (shouldAnimate()) {
                recyclerView.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(600)
                        .setInterpolator(new FastOutSlowInInterpolator()).start();
            } else {
                recyclerView.setAlpha(1f);
                recyclerView.setTranslationY(0f);
            }
        } else {
            finish();//?? why we remain here anyway?
        }
    }

    protected void setMaterialAboutList(@NonNull AboutList aboutList) {
        this.aboutList = aboutList;
        adapter.submitList(this.aboutList.getCards());
    }

    private final class ListTask {

        public void execute() {
            final var d = BuildersKt.<AboutList>async(LifecycleOwnerKt.getLifecycleScope(MaterialAboutActivity.this),
                    (CoroutineContext) Dispatchers.getDefault(),
                    CoroutineStart.DEFAULT,
                    (s, c) -> getMaterialAboutList());
            d.invokeOnCompletion(throwable -> {
                if (!isFinishing()) {
                    runOnUiThread(() -> onTaskFinished(d.getCompleted()));
                }
                return Unit.INSTANCE;
            });
        }
    }
}
