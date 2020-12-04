package com.example.donations.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonorDashboard extends Fragment {

    static TextView donor_name, donor_phone, donor_cnic, donor_email, donor_payment, donor_pending;

    static String name, phone, cnic, email, payment, pending;

    public DonorDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View donorDashboard = inflater.inflate(R.layout.fragment_donor_dashboard, container, false);

        donor_name = donorDashboard.findViewById(R.id.dono_name);
        donor_phone = donorDashboard.findViewById(R.id.donor_phone);
        donor_cnic = donorDashboard.findViewById(R.id.donor_cnic);
        donor_email = donorDashboard.findViewById(R.id.donor_email);
        donor_payment = donorDashboard.findViewById(R.id.donor_payment);
        donor_pending = donorDashboard.findViewById(R.id.donor_pending);



        sendUserData();
        sendDonorRequest();
        sendDonorPending();
        return donorDashboard;
    }

    public void sendUserData(){
        SendData sendData = new SendData(getContext());
        sendData.getDonorData(LoginFragment.userid);
    }
    public void sendDonorRequest(){
        SendData sendData = new SendData(getContext());
        sendData.getDonorDonations(LoginFragment.userid);
    }
    public void sendDonorPending(){
        SendData sendData = new SendData(getContext());
        sendData.getDonorPending(LoginFragment.userid);
    }
    public static void getUserData(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            name = jsonObject.getString("name");
            phone = jsonObject.getString("phone");
            email = jsonObject.getString("email");
            cnic = jsonObject.getString("cnic");

            Log.d("abc", name);

            donor_name.setText(name);
            donor_phone.setText(phone);
            donor_email.setText(email);
            donor_cnic.setText(cnic);

        }
        catch (JSONException ex){
ex.printStackTrace();
        }
    }

    public static void getDonationData(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(!jsonObject.isNull("amount")) {
                int amount = jsonObject.getInt("amount");
                donor_payment.setText(String.valueOf(amount));
            }
            else{
                donor_payment.setText(String.valueOf(0));
            }
        }
        catch (JSONException ex){

        }

    }
    public static void getPendingData(String response){
        try {
            Log.e("Please Please", response);
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(!jsonObject.isNull("amount")){
                int amount= jsonObject.getInt("amount");
                donor_pending.setText(String.valueOf(amount));
            }
            else {
                donor_pending.setText(String.valueOf(0));
            }
        }
        catch (JSONException ex){

        }

    }


}
