package com.example.donations.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.donations.HttpRequest.SendData;
import com.example.donations.NavigationDrawer.NavigationActivity;
import com.example.donations.NavigationDrawer.NavigationDonor;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltipUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    ImageView signUp;
    static EditText email, password;
    String loginEmail, loginPassword;
    Button loginButton;
    static Context mcontext;
    static String username, userphone;
    static int userid;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View loginFragment = inflater.inflate(R.layout.fragment_login, container, false);

        mcontext = getContext();
        email = loginFragment.findViewById(R.id.email);
        password = loginFragment.findViewById(R.id.password);

        signUp = loginFragment.findViewById(R.id.imageViewSignUp);
        loginButton = loginFragment.findViewById(R.id.login_btn);

        final SimpleTooltip tooltip = new SimpleTooltip.Builder(getActivity())
                .anchorView(signUp)
                .text("Signup")
                .gravity(Gravity.TOP)
                .dismissOnOutsideTouch(false)
                .dismissOnInsideTouch(false)
                .arrowColor(Color.parseColor("#83cccccc"))
                .modal(true)
                .animated(true)
                .animationDuration(2000)
                .animationPadding(SimpleTooltipUtils.pxFromDp(20))
                .contentView(R.layout.tooltip_custom, R.id.tv_text)
                .focusable(true)
                .build();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tooltip.show();
                Button studentFrag = tooltip.findViewById(R.id.btn_student);
                studentFrag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadFragment(new SignupFragment());
                        tooltip.dismiss();
                    }
                });

                Button donorFrag = tooltip.findViewById(R.id.btn_donor);
                donorFrag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadFragment(new SignupDonorFragment());
                        tooltip.dismiss();
                    }
                });

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });

        return loginFragment;
    }

    void checkFields(){
        loginEmail = email.getText().toString().trim();
        loginPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(loginEmail)){
            new CustomToast(getContext()).ErrorToast("Email not found");
        }
        else if(TextUtils.isEmpty(loginPassword)){
            new CustomToast(getContext()).ErrorToast("Password not found");
        }
        else{
            sendDataServer();
        }
    }

    public void sendDataServer(){
        SendData sendData = new SendData(getContext());
        sendData.signinMain(loginEmail, loginPassword);
    }

    public static void onSuccess(boolean checkUser, String name, String phone, int id){
        new CustomToast(mcontext).SuccessToast("Logging you in");
        email.setText("");
        password.setText("");
        username = name;
        userphone = phone;
        userid = id;
        if(checkUser){
            mcontext.startActivity(new Intent(mcontext, NavigationActivity.class));
        }
        else{
            mcontext.startActivity(new Intent(mcontext, NavigationDonor.class));
        }

    }
    public static void showError(){
        new CustomToast(mcontext).ErrorToast("Incorrect email or password");
    }


    private boolean loadFragment(Fragment fragment) { //Switch fragments
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up) // Fade in/out animation
                    .replace(R.id.fragment_container_login, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
