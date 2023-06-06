package com.sixtyninefourtwenty.materialaboutlibrary.util;

import android.view.Gravity;

import androidx.annotation.GravityInt;

public enum IconGravity {
    TOP(Gravity.TOP), CENTER(Gravity.CENTER), BOTTOM(Gravity.BOTTOM);

    @GravityInt
    private final int gravityInt;

    @GravityInt
    public int getGravityInt() {
        return gravityInt;
    }

    IconGravity(@GravityInt int gravityInt) {
        this.gravityInt = gravityInt;
    }
}
