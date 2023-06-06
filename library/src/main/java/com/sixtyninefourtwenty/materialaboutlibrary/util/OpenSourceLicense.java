package com.sixtyninefourtwenty.materialaboutlibrary.util;

import androidx.annotation.StringRes;

import com.sixtyninefourtwenty.materialaboutlibrary.R;

public enum OpenSourceLicense {
    APACHE_2(R.string.license_apache2),
    MIT(R.string.license_mit),
    GNU_GPL_3(R.string.license_gpl),
    BSD(R.string.license_bsd);

    @StringRes
    private final int resourceId;

    @StringRes
    public int getResourceId() {
        return resourceId;
    }

    OpenSourceLicense(int resourceId) {
        this.resourceId = resourceId;
    }

}
