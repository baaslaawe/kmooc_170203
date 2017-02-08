package com.nile.kmooc.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nile.kmooc.base.BaseFragment;
import com.nile.kmooc.user.FormField;

public class FormFieldTextAreaFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.nile.kmooc.R.layout.fragment_form_field_textarea, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final FormField formField = (FormField) getActivity().getIntent().getSerializableExtra(FormFieldActivity.EXTRA_FIELD);
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(formField.getLabel());
        final EditText editText = (EditText) view.findViewById(com.nile.kmooc.R.id.text);
        editText.setHint(formField.getPlaceholder());
        editText.setText(getActivity().getIntent().getStringExtra(FormFieldActivity.EXTRA_VALUE));
        view.findViewById(com.nile.kmooc.R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK,
                        new Intent()
                                .putExtra(FormFieldActivity.EXTRA_FIELD, formField)
                                .putExtra(FormFieldActivity.EXTRA_VALUE, editText.getText().toString()));
                getActivity().finish();
            }
        });
    }
}
