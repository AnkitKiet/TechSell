package com.techsell.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.techsell.R;
import com.techsell.activities.BuyActivity;
import com.techsell.activities.SellActivity;
import com.techsell.ui.SnackBar;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ankit on 27/06/16.
 */
public class DashboardFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private View parentView;
    @Bind(R.id.sellitem)
    Button btnSell;
    @Bind(R.id.buyitem)
    Button btnBuy;
    @Bind(R.id.withdraw)
    Button btnWithdraw;
    @Bind(R.id.returni)
    Button btnReturn;
    @Bind(R.id.slider)
    SliderLayout mDemoSlider;

    @OnClick(R.id.sellitem)
    void sellitem() {
       // SnackBar.show(getActivity(), "Sell Item");
       Intent i = new Intent(getActivity(), SellActivity.class);

        startActivity(i);
    }

    @OnClick(R.id.buyitem)
    void buyitem() {
       // SnackBar.show(getActivity(), "Buy Item");
        Intent i = new Intent(getActivity(), BuyActivity.class);

        startActivity(i);
    }

    @OnClick(R.id.withdraw)
    void withdraw() {

        SnackBar.show(getActivity(), "Withdraw Item");
      /* Intent intent = new Intent(getActivity(), WithdrawActivity.class);
        startActivity(intent);  */
    }

    @OnClick(R.id.returni)
    void returni() {
        SnackBar.show(getActivity(), "Return Item");
       /* Intent intent = new Intent(getActivity(), ReturnActivity.class);
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

        populate();
        return parentView;
    }

    private void populate() {

        ButterKnife.bind(this, parentView);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Grab Mobile Deals", "http://www.telegraph.co.uk/content/dam/technology/2016/04/26/iPhone_7_illustration_Yasser_Farahi_1000c-xlarge_trans++NJjoeBT78QIaYdkJdEY4Cl-p60QdeVSMOQCpDAhRog0-large_trans++qVzuuqpFlyLIwiB6NTmJwfSVWeZ_vEN7c6bHu2jJnT8.jpg");
        url_maps.put("Grab Laptop Deals", "https://cdn1.vox-cdn.com/thumbor/1N-Bvku_93wiB4Bnr8cUl59Fb7E=/cdn0.vox-cdn.com/uploads/chorus_asset/file/6439949/20160429-macbook-vs-versus-macbook-air-31_FIN.0.jpg");
        url_maps.put("Grab Utensils Deals", "http://www.butterflyindia.com/wp-content/themes/butterfly/images/kitchenappliances-img.png");
        url_maps.put("Grab Home Appliance Deals", "http://www.dmcsingapore.com.sg/wp-content/uploads/2016/05/banner31.jpg");
/*
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Paper Notes", R.drawable.logo2);
        file_maps.put("Grab Mobiles", R.drawable.logo3);
        file_maps.put("Grab Accessories", R.drawable.logo4);
        file_maps.put("Grab Living Accessories", R.drawable.logo5);
        file_maps.put("Grab Laptops", R.drawable.logo1); */

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

}