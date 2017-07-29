package com.techsell.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techsell.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ankit on 13/09/16.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
    @Bind(R.id.name)
    TextView Name;
    @Bind(R.id.mail)
    TextView Mail;
    @Bind(R.id.profile_image)
    CircleImageView profilepic;
    SharedPreferences pref;

    private View parentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this,parentView);

        populate();
        return parentView;
    }

    private void populate() {

        pref = this.getActivity().getSharedPreferences("Options", Context.MODE_PRIVATE);
        String pic=pref.getString("profile_photo",null);
        String name=pref.getString("name",null);
        String email=pref.getString("email",null);
        Picasso.with(getActivity()).load(pic).into(profilepic);
        Name.setText(name);
        Mail.setText(email);



    }

}
