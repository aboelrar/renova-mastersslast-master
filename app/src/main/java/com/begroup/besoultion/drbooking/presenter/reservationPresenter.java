package com.begroup.besoultion.drbooking.presenter;

import com.begroup.besoultion.drbooking.model.reservationModel;
import com.begroup.besoultion.drbooking.interfacee.MVP;

import java.util.ArrayList;

public class reservationPresenter implements MVP.interfaces.presenter {
private MVP.interfaces.Model model;
private MVP.interfaces.View views;


public reservationPresenter(MVP.interfaces.View view) {
        this.views = view;
        initPresenter();
        }
private void initPresenter() {
        model = new reservationModel();
        views.element();
        }

@Override
public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
        }
        }