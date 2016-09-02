package com.example.harmeet.thetrail2;

/**
 * Created by Harmeet on 26-08-2016.
 */
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RestaurantCustomizeOptionDialogFragment extends DialogFragment {

    ConfirmClicked mCallback;

    public interface ConfirmClicked{
        public void addToCurrentCustomization(int customizationId, HashSet<Integer> options);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.restaurant_customize_option, container, false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        rootView.setMinimumWidth((int)(width*0.8));
        rootView.setMinimumHeight((int)(height*0.7));

        Bundle args = getArguments();
        final RestaurantBean.Customization customization = (RestaurantBean.Customization) args.getSerializable("customization");

        ((TextView)rootView.findViewById(R.id.restaurant_customize_option_name)).setText(customization.name);
        if(customization.type.equals("radio")) {
            ((TextView) rootView.findViewById(R.id.restaurant_customize_option_description)).setText("Please Select any one option.");
            LinearLayout optionListLayout = (LinearLayout) rootView.findViewById(R.id.customization_options_list_linear_layout);
            final RadioGroup radioGroup = new RadioGroup(getContext());
            radioGroup.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            radioGroup.setOrientation(LinearLayout.VERTICAL);

            LinearLayout optionPriceListLayout = (LinearLayout) rootView.findViewById(R.id.customization_options_list_price);

            ArrayList<RestaurantBean.Option> options = customization.options;
            int radioButtonStartId=9211;
            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setId(radioButtonStartId+i);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                radioButton.setText(options.get(i).optionName);
                if (options.get(i).isChargable) {
                    //((TextView)optionViewPrice.findViewById(R.id.customization_option_price)).setText("Rs. " + options.get(i).price);
                } else {
                    //((TextView)optionViewPrice.findViewById(R.id.customization_option_price)).setText(" ");
                }
                radioGroup.addView(radioButton);
                //optionList.addView(radioButton);
                //optionPriceList.addView(optionViewPrice);
                TextView textView = new TextView(getContext());
                int px = getResources().getDimensionPixelSize(R.dimen.one_dp);
                textView.setPadding(6*px,6*px,6*px,6*px);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText("Rs. "+options.get(i).price);
                optionPriceListLayout.addView(textView);
            }
            optionListLayout.addView(radioGroup,0);

            rootView.findViewById(R.id.confirm_customization_option).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int radioButtonIndex = radioGroup.getCheckedRadioButtonId()-9211;
                    mCallback = (ConfirmClicked)getActivity();
                    HashSet<Integer> optionList = new HashSet<Integer>();
                    optionList.add(customization.options.get(radioButtonIndex).optionId);

                    mCallback.addToCurrentCustomization(customization.customizationId, optionList);
                    Log.d("confirm",customization.customizationId+"----"+customization.options.get(radioButtonIndex).optionId);
                    dismiss();
                }
            });
        }

        else if(customization.type.equals("checkbox")){
            ((TextView) rootView.findViewById(R.id.restaurant_customize_option_description)).setText("Please Select any one option.");
            LinearLayout optionListLayout = (LinearLayout) rootView.findViewById(R.id.customization_options_list_linear_layout);
            final LinearLayout checkboxGroup = new RadioGroup(getContext());
            checkboxGroup.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            checkboxGroup.setOrientation(LinearLayout.VERTICAL);

            LinearLayout optionPriceListLayout = (LinearLayout) rootView.findViewById(R.id.customization_options_list_price);

            ArrayList<RestaurantBean.Option> options = customization.options;
            int checkboxStartId = 7329;

            for (int i = 0; i < options.size(); i++) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setId(checkboxStartId+i);
                checkBox.setText(options.get(i).optionName);
                checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                checkboxGroup.addView(checkBox);

                if (options.get(i).isChargable) {
                    //((TextView)optionViewPrice.findViewById(R.id.customization_option_price)).setText("Rs. " + options.get(i).price);
                } else {
                    //((TextView)optionViewPrice.findViewById(R.id.customization_option_price)).setText(" ");
                }

                TextView textView = new TextView(getContext());
                int px = getResources().getDimensionPixelSize(R.dimen.one_dp);
                textView.setPadding(6*px,6*px,6*px,6*px);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText("Rs. "+options.get(i).price);
                optionPriceListLayout.addView(textView);

            }

            optionListLayout.addView(checkboxGroup,0);

            rootView.findViewById(R.id.confirm_customization_option).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback = (ConfirmClicked)getActivity();

                    //int checkboxIndex;
                    HashSet<Integer> optionList = new HashSet<Integer>();

                    for (int l=0;l<checkboxGroup.getChildCount();l++){
                        CheckBox checkBox = (CheckBox) checkboxGroup.getChildAt(l);
                        if (checkBox.isChecked()){
                            optionList.add(customization.options.get(l).optionId);
                        }
                    }

                    //int checkboxIndex = radioGroup.getCheckedRadioButtonId()-7329;

                    //optionList.add(customization.options.get(radioButtonIndex).optionId);

                    mCallback.addToCurrentCustomization(customization.customizationId, optionList);
                    //Log.d("confirm",customization.customizationId+"----"+customization.options.get(radioButtonIndex).optionId);
                    dismiss();
                }
            });

        }

        rootView.findViewById(R.id.cancel_customization_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

}