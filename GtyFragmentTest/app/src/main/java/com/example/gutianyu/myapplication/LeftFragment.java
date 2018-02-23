package com.example.gutianyu.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gutianyu on 2018/2/23.
 */

public class LeftFragment  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater  inflater, ViewGroup  container, Bundle svedInstanceState){
        View  view=inflater.inflate(R.layout.left_fragment,container,false);
        return   view;
    }
}
