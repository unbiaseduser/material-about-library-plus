package com.sixtyninefourtwenty.materialaboutlibrary;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sixtyninefourtwenty.materialaboutlibrary.items.ActionItem;
import com.sixtyninefourtwenty.materialaboutlibrary.items.TitleItem;
import com.sixtyninefourtwenty.materialaboutlibrary.model.AboutCard;
import com.sixtyninefourtwenty.materialaboutlibrary.util.IconGravity;
import com.sixtyninefourtwenty.materialaboutlibrary.util.OpenSourceLicense;

@SuppressWarnings({"JavaDoc", "unused"})
public final class ConvenienceBuilder {

    private ConvenienceBuilder() {}

    public static TitleItem createAppTitleItem(Context c, @Nullable CharSequence subtext) {
        var appContext = c.getApplicationContext();
        var packageManager = appContext.getPackageManager();
        var appInfo = appContext.getApplicationInfo();
        var appName = packageManager.getApplicationLabel(appInfo);
        var appIcon = packageManager.getApplicationIcon(appInfo);
        return new TitleItem.Builder()
                .setTitle(appName)
                .setSubtext(subtext)
                .setIcon(appIcon)
                .build();
    }

    /**
     * Creates an item with version info read from the PackageManager for current application.
     *
     * @param icon
     * @param text
     * @param includeVersionCode
     * @return Item to add to card.
     */
    public static ActionItem createVersionActionItem(Context c, Drawable icon, CharSequence text, boolean includeVersionCode) {
	    var versionName = "";
	    int versionCode;
	    try {
		    var pInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
		    versionName = pInfo.versionName;
		    versionCode = pInfo.versionCode;
	    } catch (PackageManager.NameNotFoundException e) {
            //the Throwable(String, Throwable) constructor is only on sdk 19+
		    throw (AssertionError) new AssertionError("This should not happen").initCause(e);
	    }
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(versionName + (includeVersionCode ? " (" + versionCode + ")" : ""))
                .setIcon(icon)
                .build();
    }

    public static Runnable createWebViewDialogOnClickAction(final Context c, final CharSequence dialogTitle, final String htmlString, final boolean isStringUrl, final boolean supportZoom) {
        return createWebViewDialogOnClickAction(c, dialogTitle, c.getString(R.string.mal_close), htmlString, isStringUrl, supportZoom);
    }

    public static Runnable createWebViewDialogOnClickAction(final Context c, final CharSequence dialogTitle, final CharSequence dialogNegativeButton, final String htmlString, final boolean isStringUrl, final boolean supportZoom) {
        return () -> {
            final var wv = new WebView(c);
            wv.getSettings().setSupportZoom(supportZoom);
            if (!supportZoom) {
                wv.getSettings().setLoadWithOverviewMode(true);
                wv.getSettings().setUseWideViewPort(true);
            }
            if (isStringUrl) {
                wv.loadUrl(htmlString);
            } else {
                wv.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
            }
            new MaterialAlertDialogBuilder(c).setTitle(dialogTitle)
                    .setView(wv)
                    .setNegativeButton(dialogNegativeButton, (dialog, id) -> {
                        wv.destroy();
                        dialog.dismiss();
                    })
                    .show();
        };
    }

    public static ActionItem createWebViewDialogItem(Context c, Drawable icon, CharSequence text, @Nullable CharSequence subText, CharSequence dialogTitle, String htmlString, boolean isStringUrl, boolean supportZoom) {
        return createWebViewDialogItem(c, icon, text, subText, dialogTitle, c.getString(R.string.mal_close), htmlString, isStringUrl, supportZoom);
    }

    public static ActionItem createWebViewDialogItem(Context c, Drawable icon, CharSequence text, @Nullable CharSequence subText, CharSequence dialogTitle, CharSequence dialogNegativeButton, String htmlString, boolean isStringUrl, boolean supportZoom) {
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(subText)
                .setIcon(icon)
                .setOnClickAction(createWebViewDialogOnClickAction(c, dialogTitle, dialogNegativeButton, htmlString, isStringUrl, supportZoom))
                .build();
    }

    public static Runnable createWebsiteOnClickAction(final Context c, final Uri websiteUrl) {
        return () -> {
            var i = new Intent(Intent.ACTION_VIEW)
                    .setData(websiteUrl);
            try {
                c.startActivity(i);
            } catch (ActivityNotFoundException e) {
                // No activity to handle intent
                Toast.makeText(c, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Creates an ActionItem which will open a url when tapped
     *
     * @param text
     * @param icon
     * @param websiteUrl (subText)
     * @return Item to add to card.
     */
    public static ActionItem createWebsiteActionItem(Context c, Drawable icon, CharSequence text, boolean showAddress, final Uri websiteUrl) {
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(showAddress ? websiteUrl.toString() : null)
                .setIcon(icon)
                .setOnClickAction(createWebsiteOnClickAction(c, websiteUrl))
                .build();
    }

    /**
     * Creates a Runnable that will open
     * the Google Play store listing for the app.
     */
    public static Runnable createRateOnClickAction(final Context c) {
        final var goToMarket = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + c.getPackageName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }

        return () -> {
            try {
                c.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                c.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + c.getPackageName())));
            }
        };
    }

    /**
     * Creates an ActionItem which will open the play store when tapped
     * @return Item to add to card.
     */
    public static ActionItem createRateActionItem(Context c, Drawable icon, CharSequence text, @Nullable CharSequence subText) {
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(subText)
                .setIcon(icon)
                .setOnClickAction(createRateOnClickAction(c))
                .build();
    }

    public static Runnable createEmailOnClickAction(final Context c, String email, String emailSubject) {
        return createEmailOnClickAction(c, email, emailSubject, c.getString(R.string.mal_send_email));
    }

    /**
     * Creates a MaterialAboutItemOnClickAction that will open
     * an email intent with specified address.
     *
     * @param email email address
     * @return onClickAction
     */
    public static Runnable createEmailOnClickAction(final Context c, String email, String emailSubject, final CharSequence chooserTitle) {

        final var emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email))
                .putExtra(Intent.EXTRA_SUBJECT, emailSubject);

        return () -> {
            try {
                c.startActivity(Intent.createChooser(emailIntent, chooserTitle));
            } catch (ActivityNotFoundException e) {
                // No activity to handle intent
                Toast.makeText(c, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Creates an ActionItem which will open the an email intent when tapped
     *
     * @param text
     * @param icon
     * @param email email address (also used as subText)
     * @return Item to add to card.
     */
    public static ActionItem createEmailItem(Context c, Drawable icon, CharSequence text, boolean showEmail, String email, String emailSubject, CharSequence chooserTitle) {
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(showEmail ? email : null)
                .setIcon(icon)
                .setOnClickAction(createEmailOnClickAction(c, email, emailSubject, chooserTitle))
                .build();
    }

    public static ActionItem createEmailItem(Context c, Drawable icon, CharSequence text, boolean showEmail, String email, String emailSubject) {
        return createEmailItem(c, icon, text, showEmail, email, emailSubject, c.getString(R.string.mal_send_email));
    }

    /**
     * Creates a MaterialAboutItemOnClickAction that will open
     * the dialer with specified number.
     *
     * @param number phone number
     * @return onClickAction
     */
    public static Runnable createPhoneOnClickAction(final Context c, String number) {
        final Intent phoneIntent = new Intent(Intent.ACTION_DIAL)
                .setData(Uri.parse("tel:" + number));

        return () -> {
            try {
                c.startActivity(phoneIntent);
            } catch (ActivityNotFoundException e) {
                // No activity to handle intent
                Toast.makeText(c, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Creates an ActionItem which will open the dialer when tapped
     *
     * @param text
     * @param icon
     * @param number phone number (also used as subText)
     * @return Item to add to card.
     */
    public static ActionItem createPhoneItem(Context c, Drawable icon, CharSequence text, boolean showNumber, String number) {
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(showNumber ? number : null)
                .setIcon(icon)
                .setOnClickAction(createPhoneOnClickAction(c, number))
                .build();
    }

    /**
     * Creates a MaterialAboutItemOnClickAction that will open
     * maps with a query.
     * Query can be either lat,lng(label) or written address
     *
     * @param addressQuery address query
     * @return onClickAction
     */
    public static Runnable createMapOnClickAction(final Context c, String addressQuery) {
        final var mapIntent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse("geo:0,0").buildUpon().appendQueryParameter("q", addressQuery).build());
        return () -> {
            try {
                c.startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {
                // No activity to handle intent
                Toast.makeText(c, R.string.mal_activity_exception, Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Creates an ActionItem which will open maps when tapped
     * Query can be either lat,lng(label) or written address
     *
     * @param text
     * @param subText can be set to null
     * @param icon
     * @param addressQuery addressQuery
     * @return Item to add to card.
     */
    public static ActionItem createMapItem(Context c, Drawable icon, CharSequence text, CharSequence subText, String addressQuery) {
        return new ActionItem.Builder()
                .setTitle(text)
                .setSubtext(subText)
                .setIcon(icon)
                .setOnClickAction(createMapOnClickAction(c, addressQuery))
                .build();
    }

    public static AboutCard createLicenseCard(Context c, Drawable icon, CharSequence libraryTitle, CharSequence year, CharSequence name, OpenSourceLicense license) {
        final var licenseItem = new ActionItem.Builder()
                .setIcon(icon)
                .setIconGravity(IconGravity.TOP)
                .setTitle(libraryTitle)
                .setSubtext(c.getString(license.getResourceId(), year, name))
                .build();

        return new AboutCard.Builder().addItem(licenseItem).build();
    }

}
