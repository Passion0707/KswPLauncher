package com.wits.ksw.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.View;
import com.wits.ksw.R;
import com.wits.ksw.generated.callback.OnClickListener;
import com.wits.ksw.launcher.model.LauncherViewModel;

public class Id7SubHicarViewBindingImpl extends Id7SubHicarViewBinding implements OnClickListener.Listener {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    @Nullable
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    @Nullable
    private final View.OnClickListener mCallback150;
    private long mDirtyFlags;

    static {
        sViewsWithIds.put(R.id.textView2, 4);
    }

    public Id7SubHicarViewBindingImpl(@Nullable DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private Id7SubHicarViewBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, bindings[3], bindings[2], bindings[0], bindings[1], bindings[4]);
        this.mDirtyFlags = -1;
        this.dayTextView.setTag((Object) null);
        this.monthTextView.setTag((Object) null);
        this.phoneConstraintLayout.setTag((Object) null);
        this.phoneImageView.setTag((Object) null);
        setRootTag(root);
        this.mCallback150 = new OnClickListener(this, 1);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int variableId, @Nullable Object variable) {
        if (8 != variableId) {
            return false;
        }
        setNaviViewModel((LauncherViewModel) variable);
        return true;
    }

    public void setNaviViewModel(@Nullable LauncherViewModel NaviViewModel) {
        this.mNaviViewModel = NaviViewModel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    /* access modifiers changed from: protected */
    public boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeNaviViewModelDay((ObservableField) object, fieldId);
            case 1:
                return onChangeNaviViewModelMonth((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeNaviViewModelDay(ObservableField<String> observableField, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeNaviViewModelMonth(ObservableField<String> observableField, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        ObservableField<String> naviViewModelDay = null;
        ObservableField<String> naviViewModelMonth = null;
        String naviViewModelDayGet = null;
        String naviViewModelMonthGet = null;
        LauncherViewModel naviViewModel = this.mNaviViewModel;
        View.OnFocusChangeListener naviViewModelPhoneViewFocusChangeListener = null;
        if ((15 & dirtyFlags) != 0) {
            if ((dirtyFlags & 13) != 0) {
                if (naviViewModel != null) {
                    naviViewModelDay = naviViewModel.day;
                }
                updateRegistration(0, (Observable) naviViewModelDay);
                if (naviViewModelDay != null) {
                    naviViewModelDayGet = naviViewModelDay.get();
                }
            }
            if ((dirtyFlags & 14) != 0) {
                if (naviViewModel != null) {
                    naviViewModelMonth = naviViewModel.month;
                }
                updateRegistration(1, (Observable) naviViewModelMonth);
                if (naviViewModelMonth != null) {
                    naviViewModelMonthGet = naviViewModelMonth.get();
                }
            }
            if (!((dirtyFlags & 12) == 0 || naviViewModel == null)) {
                naviViewModelPhoneViewFocusChangeListener = naviViewModel.phoneViewFocusChangeListener;
            }
        }
        if ((dirtyFlags & 13) != 0) {
            TextViewBindingAdapter.setText(this.dayTextView, naviViewModelDayGet);
        }
        if ((14 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.monthTextView, naviViewModelMonthGet);
        }
        if ((8 & dirtyFlags) != 0) {
            this.phoneImageView.setOnClickListener(this.mCallback150);
        }
        if ((dirtyFlags & 12) != 0) {
            this.phoneImageView.setOnFocusChangeListener(naviViewModelPhoneViewFocusChangeListener);
        }
    }

    public final void _internalCallbackOnClick(int sourceId, View callbackArg_0) {
        LauncherViewModel naviViewModel = this.mNaviViewModel;
        if (naviViewModel != null) {
            naviViewModel.openHicar(callbackArg_0);
        }
    }
}
