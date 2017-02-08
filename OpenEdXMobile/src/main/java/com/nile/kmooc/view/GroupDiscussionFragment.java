package com.nile.kmooc.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.R;

public class GroupDiscussionFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_discussion, container, false);
    }
}
