package com.noname.app.ui.Library.AddOption;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;


import com.noname.app.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;


public class AddOptionView extends RelativeLayout {


    private Context context;
    private boolean isShowIcon;
    private boolean isAddEdit;
    private boolean isComplite;
    private String heading;
    private String subHeading;
    private Drawable icon;
    private ArrayList<String> itemsList;

    public AddOptionView(Context context) {
        this(context, null);
    }

    public AddOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_add_option_view, this);
        this.context = context;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.addOptionDetailsResource, defStyleAttr, 0);
        isShowIcon = attributes.getBoolean(R.styleable.addOptionDetailsResource_isdeOptShowIcon, false);
        isAddEdit = attributes.getBoolean(R.styleable.addOptionDetailsResource_isAddEditShowIcon, true);
        heading = attributes.getString(R.styleable.addOptionDetailsResource_optHeading);
        subHeading = attributes.getString(R.styleable.addOptionDetailsResource_optSubHeading);
        icon = attributes.getDrawable(R.styleable.addOptionDetailsResource_optIcon);

        attributes.recycle();
    }
    //endregion

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
    private TextView heading_view, subHeading_view;
    private Space space;
    private ViewGroup listGrop_view;

    private void initView() {

        type_img = findViewById(R.id.typeImg);
        add_update_img = findViewById(R.id.add_update_img);
        heading_view = findViewById(R.id.heading_view);
        subHeading_view = findViewById(R.id.sub_heading_view);
        space = findViewById(R.id.space);
        listGrop_view = findViewById(R.id.listGrop_view);

        if (heading == null) {
            heading = "";
        }
        if (subHeading == null) {
            subHeading = "";
        }
        itemsList = new ArrayList<String>();

        Update();

        findViewById(R.id.rootDetail).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectedListener != null) {
                    onSelectedListener.onSelected();
                }
            }
        });
    }

    private void Update() {

        type_img.setVisibility(isShowIcon ? View.VISIBLE : View.GONE);
        space.setVisibility(isShowIcon ? View.VISIBLE : View.GONE);
        add_update_img.setVisibility(isAddEdit ? View.VISIBLE : View.GONE);

        subHeading_view.setVisibility(!subHeading.isEmpty() ? View.VISIBLE : View.GONE);
        listGrop_view.setVisibility(!itemsList.isEmpty() ? View.VISIBLE : View.GONE);

        if (isShowIcon) {
            type_img.setImageDrawable(icon);
        }
        heading_view.setText(heading);
        subHeading_view.setText(subHeading);
        heading_view.setTextColor(isComplite ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"));
        subHeading_view.setTextColor(isComplite ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"));
        type_img.setColorFilter(isComplite ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"), android.graphics.PorterDuff.Mode.SRC_IN);
        add_update_img.setImageResource(isComplite ? R.drawable.baseline_arrow_forward_ios_24 : R.drawable.plus_ic);

        listGrop_view.removeAllViews();
        if (!itemsList.isEmpty()) {
            for (String name : itemsList) {
                listGrop_view.addView(createChip(name));
            }
        }

    }

    public void setListView(ArrayList<String> itemsList) {
        this.itemsList = itemsList;
        Update();
    }

    public void setsubHeading(String subHeading) {
        this.subHeading = subHeading;
        Update();
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


    public OnSelectedListener onSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnSelectedListener {
        void onSelected();
    }

    private Chip createChip(String label)  {
        View view = LayoutInflater.from(context).inflate(R.layout.choice_chip, listGrop_view, false);
        Chip chip = (Chip) view.findViewById(R.id.choice_chip);
        chip.setText(label);
        return chip;
    }


}
