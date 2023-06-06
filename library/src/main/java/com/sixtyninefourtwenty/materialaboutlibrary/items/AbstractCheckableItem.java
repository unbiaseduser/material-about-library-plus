package com.sixtyninefourtwenty.materialaboutlibrary.items;

import static android.view.View.GONE;

import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public abstract class AbstractCheckableItem extends AbstractItem {

	private final OnCheckedChangedAction onCheckedChangedAction;
	private boolean checked;
	private final CharSequence subTextChecked;
	private final int subTextCheckedRes;

	public CharSequence getSubTextChecked() {
		return subTextChecked;
	}
	public int getSubTextCheckedRes() {
		return subTextCheckedRes;
	}

	protected AbstractCheckableItem(Builder<?> builder) {
		super(builder);
		onCheckedChangedAction = builder.onCheckedChanged;
		checked = builder.checked;
		subTextChecked = builder.subTextChecked;
		subTextCheckedRes = builder.subTextCheckedRes;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public OnCheckedChangedAction getOnCheckedChangedAction() {
		return onCheckedChangedAction;
	}

	@NonNull
	@Override
	public String toString() {
		return "MaterialAboutCheckableItem{" +
				", onCheckedChangedAction=" + onCheckedChangedAction +
				", checked=" + checked +
				'}';
	}

	public static abstract class ViewHolder extends AbstractItem.ViewHolder {
		private AbstractCheckableItem checkableItem;
		private boolean broadcasting;
		private final CompoundButton.OnCheckedChangeListener toggleListener = (buttonView, isChecked) -> {
			checkableItem.setChecked(isChecked);
			// Avoid infinite recursions if this.aSwitch.setChecked() is called from a listener or below
			if (broadcasting) {
				return;
			}
			broadcasting = true;
			if (!checkableItem.getOnCheckedChangedAction().onCheckedChanged(checkableItem, isChecked)) {
				checkableItem.setChecked(!isChecked);
				getToggleComponent().setChecked(!isChecked);
			} else {
				updateSubText(checkableItem);
			}
			broadcasting = false;
		};

		public ViewHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(v -> getToggleComponent().toggle());
			var outValue = new TypedValue();
			itemView.getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.selectableItemBackground, outValue, true);
			itemView.setBackgroundResource(outValue.resourceId);
		}

		@NonNull
		public abstract CompoundButton getToggleComponent();

		@Override
		public void setupItem(AbstractItem item) {
			super.setupItem(item);
			checkableItem = (AbstractCheckableItem) item;
			updateSubText(checkableItem);

			int pL = 0, pT = 0, pR = 0, pB = 0;
			if (Build.VERSION.SDK_INT < 21) {
				pL = itemView.getPaddingLeft();
				pT = itemView.getPaddingTop();
				pR = itemView.getPaddingRight();
				pB = itemView.getPaddingBottom();
			}

			var toggle = getToggleComponent();
			toggle.setChecked(checkableItem.isChecked());
			toggle.setOnCheckedChangeListener(checkableItem.getOnCheckedChangedAction() != null ? toggleListener : null);

			if (Build.VERSION.SDK_INT < 21) {
				itemView.setPadding(pL, pT, pR, pB);
			}

		}

		protected void updateSubText(AbstractCheckableItem item) {
			var subtextHolder = getSubtextHolder();
			if (item.isChecked() && item.getSubTextChecked() != null) {
				subtextHolder.setText(item.getSubTextChecked());
			} else if (item.isChecked() && item.getSubTextCheckedRes() != 0) {
				subtextHolder.setText(item.getSubTextCheckedRes());
			} else if (item.getSubtext() != null) {
				subtextHolder.setText(item.getSubtext());
			} else if (item.getSubtextRes() != 0) {
				subtextHolder.setText(item.getSubtextRes());
			} else {
				subtextHolder.setVisibility(GONE);
			}
		}
	}

	public static abstract class Builder<T extends Builder<T>> extends AbstractItem.Builder<T> {
		private OnCheckedChangedAction onCheckedChanged;
		private boolean checked;
		private CharSequence subTextChecked = null;
		private int subTextCheckedRes = 0;

		public T setOnCheckedChanged(@NonNull OnCheckedChangedAction onCheckedChanged) {
			this.onCheckedChanged = onCheckedChanged;
			return self();
		}

		public T setChecked(boolean checked) {
			this.checked = checked;
			return self();
		}

		public T setSubtextChecked(@Nullable CharSequence subTextChecked) {
			this.subTextChecked = subTextChecked;
			subTextCheckedRes = 0;
			return self();
		}

		public T setSubtextChecked(@StringRes int subTextCheckedRes) {
			this.subTextCheckedRes = subTextCheckedRes;
			subTextChecked = null;
			return self();
		}

		@NonNull
		@Override
		public abstract AbstractCheckableItem build();
	}
}
