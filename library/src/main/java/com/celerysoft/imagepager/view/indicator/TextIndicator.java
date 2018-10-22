package com.celerysoft.imagepager.view.indicator;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.celerysoft.imagepager.R;

import java.util.Locale;

/**
 * Indicator that only display text.
 * TODO: switch to AppCompatTextView
 */
public class TextIndicator extends TextView implements Indicator {
    private static final String TAG = "TextIndicator";

    private Context mContext;

    private int mImageCount;
    private int mCurrentImagePosition = 0;

    private String noImagesText;
    // TODO: remove hardcoded value(for default - ok, otherwise must be set by setter\builder method
    private float mTextSize = 16;

    public TextIndicator(Context context) {
        super(context);
        mContext = context;

        setGravity(Gravity.CENTER);
        setTextSize(mTextSize);

        setBackgroundResource(R.drawable.indicator_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextColor(context.getResources().getColor(R.color.text_indicator_text_color, null));
        } else {
            setTextColor(context.getResources().getColor(R.color.text_indicator_text_color));
        }
    }

    public void setNoImagesText(String noImagesText) {
        this.noImagesText = noImagesText;
    }

    @Override public void setTextSize(float size) {
        super.setTextSize(size);

        mTextSize = size;
    }

    @Override public void onPageSelected(int position) {
        String text = String.format(Locale.getDefault(), "%d/%d", position + 1, mImageCount);
        setText(text);
        mCurrentImagePosition = position;
    }

    @Override public void onPageDeleted() {
        mImageCount -= 1;
        mCurrentImagePosition = mCurrentImagePosition < mImageCount ? mCurrentImagePosition : mImageCount - 1;
        String text;
        if (mImageCount <= 0) {
            setTextSize(mTextSize * 1.25f);

            text = TextUtils.isEmpty(noImagesText) ? mContext.getString(
                    R.string.no_images) : noImagesText;
            if (mImageCount < 0) {
                Log.e(TAG, "image count less than 0, it could not be happened!");
            }
        } else {
            setTextSize(mTextSize);
            text = String.format(Locale.getDefault(),
                    "%d/%d",
                    mCurrentImagePosition + 1,
                    mImageCount);
        }
        setText(text);
    }

    @Override public void onPageAdapterChanged(int imageCount) {
        mImageCount = imageCount;
        mCurrentImagePosition = 0;
        String text = String.format(Locale.getDefault(), "1/%d", mImageCount);
        setText(text);
    }
}
