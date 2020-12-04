package com.example.donations.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.donations.Adapters.CustomListAdapter;
import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingDonationStudent extends Fragment {

    static ArrayList<String> description;
    static ArrayList<String> date;
    static ArrayList<String> type;
    static ArrayList<String> amount;
    static ArrayList<Integer> id;

    static String[] descriptionArray;
    static String[] dateArray;
    static String[] amountArray;
    static String[] typeArray;
    static Integer[] countserverArray;

    static int totalPending = 0;
    static ListView listView;
    static TextView total_count;

    static Context mcontext;

    public PendingDonationStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pendingDonation = inflater.inflate(R.layout.fragment_pending_donation_student, container, false);

        mcontext = getActivity();
        total_count = pendingDonation.findViewById(R.id.all_count);
        listView = pendingDonation.findViewById(R.id.list_view_pending_all);


        getPendingDonations();

        return pendingDonation;
    }

    public void getPendingDonations() {
        SendData sendData = new SendData(getContext());
        sendData.getStudentPending(LoginFragment.userid);
    }
    public static void getPendingData(String response) {
        totalPending = 0;
        description = new ArrayList<String>();
        date = new ArrayList<String>();
        type = new ArrayList<String>();
        amount = new ArrayList<String>();
        id = new ArrayList<Integer>();

        try {
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    description.add(jsonObject.getString("description"));
                    date.add(jsonObject.getString("date"));
                    type.add("Apply for semester : "+jsonObject.getString("apply_semester"));
                    amount.add(jsonObject.getString("amount"));
                    id.add(jsonObject.getInt("id"));
                    totalPending = totalPending + Integer.valueOf(jsonObject.getString("amount"));
                }
                descriptionArray = new String[description.size()];
                dateArray = new String[date.size()];
                amountArray = new String[amount.size()];
                typeArray = new String[type.size()];

                descriptionArray = description.toArray(descriptionArray);
                dateArray = date.toArray(dateArray);
                amountArray = amount.toArray(amountArray);
                typeArray = type.toArray(typeArray);

                total_count.setText("Total Pending: " + String.valueOf(totalPending));
                CustomListAdapter whatever = new CustomListAdapter((Activity) mcontext, typeArray, descriptionArray, dateArray, amountArray);
                listView.setAdapter(whatever);

            }
        } catch (Exception ex) {

        }

    }

}
