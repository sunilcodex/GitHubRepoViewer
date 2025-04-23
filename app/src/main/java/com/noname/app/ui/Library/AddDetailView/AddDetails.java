package com.noname.app.ui.Library.AddDetailView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.noname.app.R;


public class AddDetails extends RelativeLayout {


    private boolean isShowIcon;
    private boolean isComplite;
    private boolean isActive = true;
    private String heading;
    private Drawable icon;

    public AddDetails(Context context) {
        this(context, null);
    }

    public AddDetails(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddDetails(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_add_details, this);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.addDetailsResource, defStyleAttr, 0);
        isShowIcon = attributes.getBoolean(R.styleable.addDetailsResource_isShowIcon, false);
        heading = attributes.getString(R.styleable.addDetailsResource_heading);
        icon = attributes.getDrawable(R.styleable.addDetailsResource_icon);

        attributes.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {

        }
    }


    private ImageView type_img, add_update_img;
    private TextView heading_view;
    private Space space;

    private void initView() {

        type_img = findViewById(R.id.typeImg);
        add_update_img = findViewById(R.id.add_update_img);
        heading_view = findViewById(R.id.heading_view);
        space = findViewById(R.id.space);

        Update();

        findViewById(R.id.rootDetail).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onSelectedListener != null && isActive) {
                    onSelectedListener.onSelected();
                }
            }
        });
    }

    private void Update() {

        type_img.setVisibility(isShowIcon ? View.VISIBLE : View.GONE);
        space.setVisibility(isShowIcon ? View.VISIBLE : View.GONE);
        add_update_img.setVisibility(isActive ? View.VISIBLE : View.INVISIBLE);
        if(isShowIcon) {
            type_img.setImageDrawable(icon);
        }
        heading_view.setText(heading);
        heading_view.setTextColor(isComplite && isActive ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"));
        type_img.setColorFilter(isComplite && isActive ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"), android.graphics.PorterDuff.Mode.SRC_IN);
        add_update_img.setImageResource(isComplite ? R.drawable.baseline_edit_24 : R.drawable.plus_ic);
    }


    public void setheading(String heading) {
        this.heading = heading;
        Update();
    }


    public void setComplite(boolean isComplite) {
        this.isComplite = isComplite;
        Update();
    }


    public boolean isComplite() {
        return isComplite;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener {
        void onSelected();
    }


}
