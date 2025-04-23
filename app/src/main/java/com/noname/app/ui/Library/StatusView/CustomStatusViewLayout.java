package com.noname.app.ui.Library.StatusView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.noname.app.R;

public class CustomStatusViewLayout extends LinearLayout {

    private LottieAnimationView lottieAnimationView;
    private TextView title_view;
    private ImageView imageView;
    private int textColor;

    private String description;
    private ViewFileType viewFileType;


    public CustomStatusViewLayout(Context context, int rawRes) {
        super(context);
        this.viewFileType = ViewFileType.LOTIE_FILE;
        init(context, rawRes);
    }

    public CustomStatusViewLayout(Context context, ViewFileType viewFileType, int rawRes) {
        super(context);
        this.viewFileType = viewFileType;
        init(context, rawRes);
    }

    public CustomStatusViewLayout(Context context, ViewFileType viewFileType, int rawRes, String description) {
        super(context);
        this.viewFileType = viewFileType;
        this.description = description;
        this.textColor = ContextCompat.getColor(context, R.color.black);
        init(context, rawRes);
    }

    public CustomStatusViewLayout(Context context, int rawRes, String description) {
        super(context);
        this.viewFileType = ViewFileType.LOTIE_FILE;
        this.description = description;
        this.textColor = ContextCompat.getColor(context, R.color.black);
        init(context, rawRes);
    }

    public CustomStatusViewLayout(Context context, ViewFileType viewFileType, int rawRes, int textColor, String description) {
        super(context);
        this.viewFileType = viewFileType;
        this.description = description;
        this.textColor = textColor;
        init(context, rawRes);
    }

    public CustomStatusViewLayout(Context context, int rawRes,  int textColor, String description) {
        super(context);
        this.viewFileType = ViewFileType.LOTIE_FILE;
        this.description = description;
        this.textColor = textColor;
        init(context, rawRes);
    }


    private void init(Context context, int rawRes) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.status_view_layout, this, true);

        // Set MATCH_PARENT layout parameters for CustomStatusViewLayout
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        setLayoutParams(layoutParams);

        title_view = findViewById(R.id.title_view);

        if (description != null) {
            title_view.setText(description);
            title_view.setTextColor(textColor);
        }

        lottieAnimationView = findViewById(R.id.animationView);
        imageView = findViewById(R.id.imageView);

        if (viewFileType == ViewFileType.LOTIE_FILE) {
            lottieAnimationView.setAnimation(rawRes);
            // Set other Lottie properties
            lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        } else  {
            imageView.setImageDrawable(VectorDrawableCompat.create(context.getResources(), rawRes, null));
        }
    }

    public void executeCode() {

        if (viewFileType == ViewFileType.LOTIE_FILE) {
            lottieAnimationView.playAnimation();
        }
    }

}