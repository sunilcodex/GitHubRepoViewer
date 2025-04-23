package com.noname.app.ui.Library.StatusView;

public class StatusView {
    private ViewClass viewClass;
    private CustomStatusViewLayout customStatusViewLayout;

    public StatusView(ViewClass viewClass, CustomStatusViewLayout customStatusViewLayout) {
        this.viewClass = viewClass;
        this.customStatusViewLayout = customStatusViewLayout;
    }

    public ViewClass getViewClass() {
        return viewClass;
    }

    public void setViewClass(ViewClass viewClass) {
        this.viewClass = viewClass;
    }
    public CustomStatusViewLayout getViewLayout() {
        return customStatusViewLayout;
    }

    public void setViewLayout(int viewLayout) {
        customStatusViewLayout = customStatusViewLayout;
    }
}
