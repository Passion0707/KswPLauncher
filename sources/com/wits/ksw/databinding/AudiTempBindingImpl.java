package com.wits.ksw.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.Observable;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.CompoundButtonBindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.wits.ksw.settings.audi.vm.AudiSystemViewModel;

public class AudiTempBindingImpl extends AudiTempBinding {
    @Nullable
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    @Nullable
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    @NonNull
    private final RadioButton mboundView1;
    @NonNull
    private final RadioButton mboundView2;

    public AudiTempBindingImpl(@Nullable DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private AudiTempBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, bindings[0]);
        this.mDirtyFlags = -1;
        this.mboundView1 = bindings[1];
        this.mboundView1.setTag((Object) null);
        this.mboundView2 = bindings[2];
        this.mboundView2.setTag((Object) null);
        this.timeRadioGroup.setTag((Object) null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
        if (17 != variableId) {
            return false;
        }
        setVm((AudiSystemViewModel) variable);
        return true;
    }

    public void setVm(@Nullable AudiSystemViewModel Vm) {
        this.mVm = Vm;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(17);
        super.requestRebind();
    }

    /* access modifiers changed from: protected */
    public boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeVmTempUnit((ObservableInt) object, fieldId);
    }

    private boolean onChangeVmTempUnit(ObservableInt VmTempUnit, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
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
        AudiSystemViewModel vm = this.mVm;
        boolean vmTempUnitInt0 = false;
        ObservableInt vmTempUnit = null;
        RadioGroup.OnCheckedChangeListener vmOnTempUnitChangeListener = null;
        boolean vmTempUnitInt1 = false;
        int vmTempUnitGet = 0;
        if ((dirtyFlags & 7) != 0) {
            if (vm != null) {
                vmTempUnit = vm.tempUnit;
            }
            updateRegistration(0, (Observable) vmTempUnit);
            if (vmTempUnit != null) {
                vmTempUnitGet = vmTempUnit.get();
            }
            vmTempUnitInt0 = vmTempUnitGet == 0;
            vmTempUnitInt1 = vmTempUnitGet == 1;
            if (!((dirtyFlags & 6) == 0 || vm == null)) {
                vmOnTempUnitChangeListener = vm.onTempUnitChangeListener;
            }
        }
        if ((7 & dirtyFlags) != 0) {
            CompoundButtonBindingAdapter.setChecked(this.mboundView1, vmTempUnitInt0);
            CompoundButtonBindingAdapter.setChecked(this.mboundView2, vmTempUnitInt1);
        }
        if ((dirtyFlags & 6) != 0) {
            this.timeRadioGroup.setOnCheckedChangeListener(vmOnTempUnitChangeListener);
        }
    }
}
