package com.techsell.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.techsell.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ankit on 27/06/16.
 */
public class DashboardFragment extends Fragment {
    private View parentView;
    @Bind(R.id.sellitem)
    Button btnSell;
    @Bind(R.id.buyitem)
    Button btnBuy;

    @OnClick(R.id.sellitem)
    void sell() {
        Toast.makeText(getActivity().getApplicationContext(), "Under Maintainence", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(getActivity(), RateInfoActivity.class);
        startActivity(intent);  */
    }

    @OnClick(R.id.buyitem)
    void buy() {
        Toast.makeText(getActivity(), "Under Maintainence", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(getActivity(), RateInfoActivity.class);
        startActivity(intent);  */
    }


    public DashboardFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ButterKnife.bind(this,parentView);
return parentView;
    }
}


