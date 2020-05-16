package com.chappie.ladang.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

public class ColumnQty {
    private DisplayMetrics displayMetrics;
    private int remaining;
    private int width;

    public ColumnQty(Context context, int i) {
        View inflate = View.inflate(context, i, null);
        inflate.measure(0, 0);
        this.width = inflate.getMeasuredWidth();
        int height = inflate.getMeasuredHeight();
        this.displayMetrics = context.getResources().getDisplayMetrics();
    }

    public int calculateNoOfColumns() {
        int i = this.displayMetrics.widthPixels / this.width;
        this.remaining = this.displayMetrics.widthPixels - (this.width * i);
        if (this.remaining / (i * 2) >= 15) {
            return i;
        }
        int i2 = i - 1;
        this.remaining = this.displayMetrics.widthPixels - (this.width * i2);
        return i2;
    }

    public int calculateSpacing() {
        return this.remaining / (calculateNoOfColumns() * 2);
    }
}
