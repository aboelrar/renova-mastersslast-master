package com.begroup.besoultion.drbooking.presenter;

import com.begroup.besoultion.drbooking.model.doctorModel;
import com.begroup.besoultion.drbooking.interfacee.searchInterface;

import java.util.ArrayList;

public class doctorPresenter implements searchInterface.interfaces.presenter {
    private searchInterface.interfaces.Model model;
    private searchInterface.interfaces.View views;


    public doctorPresenter(searchInterface.interfaces.View view) {
        this.views = view;
        initPresenter();
    }
    private void initPresenter() {
        model = new doctorModel();
        views.element();
    }

    @Override
    public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
    }

    @Override
    public void getTasneefData() {
        ArrayList dataa=model.getTasneefData();
        views.setviewdata(dataa);
    }
}
