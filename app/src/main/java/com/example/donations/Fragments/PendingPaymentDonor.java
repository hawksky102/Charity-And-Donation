package com.example.donations.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donations.Adapters.CustomListAdapter;
import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingPaymentDonor extends Fragment {

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

    public PendingPaymentDonor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pendingPayment = inflater.inflate(R.layout.fragment_pending_payment_donor, container, false);

        mcontext = getActivity();
        total_count = pendingPayment.findViewById(R.id.all_count);
        listView = pendingPayment.findViewById(R.id.list_view_pending_all);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long idd) {
                Log.e("UMER", String.valueOf(id.get((int) idd)));
                showConfirmation(id.get((int) idd), amount.get((int) idd), date.get((int) idd), description.get((int) idd), type.get((int) idd));
//                id_now = item_id.get((int) id);


            }
        });

        getPendingDonations();

        return pendingPayment;
    }

    public void getPendingDonations() {
        SendData sendData = new SendData(getContext());
        sendData.getPendingDonation(LoginFragment.userid);
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
                    type.add(jsonObject.getString("payment_type"));
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

    public void showConfirmation(final int id, final String amount, final String date, final String description, final String type) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Please Confirm")
                .setMessage("Do you want to add payment? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadFragment(new AddPaymentDonor(id, date, description, amount, type));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
    private boolean loadFragment(Fragment fragment) { //Switch fragments
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up) // Fade in/out animation
                    .replace(R.id.frame_donor, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
