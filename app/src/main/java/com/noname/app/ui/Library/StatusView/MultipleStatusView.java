package com.noname.app.ui.Library.StatusView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MultipleStatusView extends RelativeLayout {

    private static final LayoutParams DEFAULT_LAYOUT_PARAMS = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    private final LayoutInflater mInflater;
    private final ArrayList<StatusView> statusViewsList = new ArrayList<>();
    private StatusView statusView;

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    public final void InitializeStatus(StatusView statusView) {
        statusViewsList.add(statusView);
    }

    public ViewClass getCurrentView() {
        return (ViewClass) this.getTag();
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    public void showViewByViewClass(ViewClass viewClass, Context context) {
        if (viewClass == ViewClass.REMOVE_VIEW) {
            this.removeAllViews();
        } else {
            this.removeAllViews();
            final int size = statusViewsList.size();
            for (int i = 0; i < size; i++) {
                statusView = statusViewsList.get(i);
                if (statusView.getViewClass() == viewClass) {
                    checkNull(DEFAULT_LAYOUT_PARAMS, "Layout params is null.");
                    if (statusView.getViewLayout() != null) {
                        CustomStatusViewLayout customStatusViewLayout = statusView.getViewLayout();
                        customStatusViewLayout.executeCode();
                        this.addView(customStatusViewLayout);
                    }
                    this.setTag(viewClass);
                    break;
                }
            }
        }
    }


    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    public StatusView getStatusView() {
        return statusView;
    }
}