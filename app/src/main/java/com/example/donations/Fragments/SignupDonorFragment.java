package com.example.donations.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupDonorFragment extends Fragment {

    ImageView login;
    static EditText do_name, do_phone, do_cnic, do_email, do_password, do_repassword;
    String name, phone, email, cnic, password;
    Button signupButton;
    static Context mcontext;
    public SignupDonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View signupDonorFragment = inflater.inflate(R.layout.fragment_signup_donor, container, false);

        mcontext = getContext();
        do_name = signupDonorFragment.findViewById(R.id.do_name);
        do_phone = signupDonorFragment.findViewById(R.id.do_phone);
        do_cnic = signupDonorFragment.findViewById(R.id.do_cnic);
        do_email = signupDonorFragment.findViewById(R.id.do_email);
        do_password = signupDonorFragment.findViewById(R.id.do_password);
        do_repassword = signupDonorFragment.findViewById(R.id.do_repassword);
        signupButton = signupDonorFragment.findViewById(R.id.do_signup);
        login = signupDonorFragment.findViewById(R.id.imageViewLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new LoginFragment());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAllFields();
            }
        });

        return signupDonorFragment;
    }

    public void checkAllFields() {

        name = do_name.getText().toString();
        phone = do_phone.getText().toString();
        email = do_email.getText().toString();
        cnic = do_cnic.getText().toString();
        password = do_password.getText().toString();
        String re_password = do_repassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            new CustomToast(getContext()).ErrorToast("Name not found");
        } else if (TextUtils.isEmpty(phone)) {
            new CustomToast(getContext()).ErrorToast("Phone not found");
        } else if (TextUtils.isEmpty(email)) {
            new CustomToast(getContext()).ErrorToast("Email not found");
        } else if (TextUtils.isEmpty(cnic)) {
            new CustomToast(getContext()).ErrorToast("CNIC not found");
        } else if (TextUtils.isEmpty(password)) {
            new CustomToast(getContext()).ErrorToast("Password name not found");
        } else if (TextUtils.isEmpty(re_password)) {
            new CustomToast(getContext()).ErrorToast("Re-password name not found");
        } else if (!password.matches(re_password)) {
            new CustomToast(getContext()).ErrorToast("Password not match");
        } else {
            sendDataServer();
        }


    }

    public void sendDataServer() {
        SendData sendData = new SendData(getContext());
        sendData.signupDonor(name,phone, email, cnic, password);

    }

    public static void clearFields(){
        new CustomToast(mcontext).SuccessToast("Signed Up");
        do_name.setText("");
        do_email.setText("");
        do_phone.setText("");
        do_cnic.setText("");
        do_password.setText("");
        do_repassword.setText("");

    }
    public static void showError(){
        new CustomToast(mcontext).SuccessToast("Signed Failed");
    }

    private boolean loadFragment(Fragment fragment) { //Switch fragments
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_botton_up, R.anim.slide_bottom_down) // Fade in/out animation
                    .replace(R.id.fragment_container_login, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
