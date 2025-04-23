package com.noname.app.ui.Library.AddDetailView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import com.noname.app.R;


public class AddDetailsWithTitle extends RelativeLayout {

    private Context context;
    private boolean isShowIcon;
    private boolean isComplite;
    private boolean isLineShow;
    private boolean isAddSineShowIcon;
    private String title;
    private String heading;
    private Drawable icon;

    public AddDetailsWithTitle(Context context) {
        this(context, null);
    }

    public AddDetailsWithTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddDetailsWithTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.layout_add_details_with_title, this);
        this.context = context;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.addTitleDetailsResource, defStyleAttr, 0);
        isShowIcon = attributes.getBoolean(R.styleable.addTitleDetailsResource_isDetailsShowIcon, false);
        isLineShow = attributes.getBoolean(R.styleable.addTitleDetailsResource_isLineShow, false);
        isAddSineShowIcon = attributes.getBoolean(R.styleable.addTitleDetailsResource_isAddSineShowIcon, false);
        title = attributes.getString(R.styleable.addTitleDetailsResource_detailsTitle);
        heading = attributes.getString(R.styleable.addTitleDetailsResource_detailsHeading);
        icon = attributes.getDrawable(R.styleable.addTitleDetailsResource_detailsIcon);

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
    private TextView heading_view, title_view;
    private Space space;
    private View lineView;
    private Typeface faceNormal, faceBold;

    private void initView() {

        type_img = findViewById(R.id.typeImg);
        add_update_img = findViewById(R.id.add_update_img);
        heading_view = findViewById(R.id.heading_view);
        title_view = findViewById(R.id.title_view);
        space = findViewById(R.id.space);
        lineView = findViewById(R.id.lineView);

        if (title == null) {
            title = "";
        }
        if (heading == null) {
            heading = "";
        }

        faceNormal = ResourcesCompat.getFont(context, R.font.lato_light);
        faceBold = ResourcesCompat.getFont(context, R.font.lato_bold);

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

        title_view.setVisibility(!title.isEmpty() ? View.VISIBLE : View.GONE);
        type_img.setVisibility(isShowIcon ? View.VISIBLE : View.GONE);
        space.setVisibility(isShowIcon ? View.VISIBLE : View.GONE);

        add_update_img.setVisibility(isAddSineShowIcon ? View.VISIBLE : View.GONE);
        lineView.setVisibility(isLineShow ? View.VISIBLE : View.GONE);


        if(isShowIcon) {
            type_img.setImageDrawable(icon);
        }

        title_view.setText(title);
        heading_view.setText(heading);
        lineView.setBackgroundColor(isComplite ? Color.parseColor("#000000") : Color.parseColor("#E0E0E0"));
        heading_view.setTextColor(isComplite ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"));
        heading_view.setTypeface(isComplite ? faceBold : faceNormal);
        type_img.setColorFilter(isComplite ? Color.parseColor("#000000") : Color.parseColor("#9E9E9E"), android.graphics.PorterDuff.Mode.SRC_IN);
        add_update_img.setImageResource(isComplite ? R.drawable.baseline_arrow_forward_ios_24 : R.drawable.plus_ic);
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


}
