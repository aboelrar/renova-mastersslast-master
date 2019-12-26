package com.begroup.besoultion.drbooking.presenter;

import com.begroup.besoultion.drbooking.model.appoienmentmodel;
import com.begroup.besoultion.drbooking.interfacee.MVP;

import java.util.ArrayList;

public class appoienmentsPresenter implements MVP.interfaces.presenter {
    private MVP.interfaces.Model model;
    private MVP.interfaces.View views;


    public appoienmentsPresenter(MVP.interfaces.View view) {
        this.views = view;
        initPresenter();
    }
    private void initPresenter() {
        model = new appoienmentmodel();
        views.element();
    }

    @Override
    public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
    }
}