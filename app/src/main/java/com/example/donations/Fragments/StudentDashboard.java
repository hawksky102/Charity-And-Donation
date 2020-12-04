package com.example.donations.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.donations.Activities.LoginActivity;
import com.example.donations.HttpRequest.MySingleton;
import com.example.donations.HttpRequest.SendData;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDashboard extends Fragment {


    public StudentDashboard() {
        // Required empty public constructor
    }

    static TextView donor_name, donor_phone, donor_cnic, donor_email, donor_payment, donor_pending, electric_paid, other_paid, other_paid_f, amount_r, semester1,semester8, semester2, semester3, semester4, semester5, semester6, semester7,
            total_applied, total_approved, total_reject, pending_total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View donorDashboard = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        donor_name = donorDashboard.findViewById(R.id.dono_name);
        donor_phone = donorDashboard.findViewById(R.id.donor_phone);
        donor_cnic = donorDashboard.findViewById(R.id.donor_cnic);
        donor_email = donorDashboard.findViewById(R.id.donor_email);
        donor_payment = donorDashboard.findViewById(R.id.donor_payment);
        donor_pending = donorDashboard.findViewById(R.id.donor_pending);
        electric_paid = donorDashboard.findViewById(R.id.electric_paid);
        other_paid = donorDashboard.findViewById(R.id.other_paid);
        other_paid_f = donorDashboard.findViewById(R.id.other_paid_f);
        amount_r = donorDashboard.findViewById(R.id.amount_r);
        total_approved = donorDashboard.findViewById(R.id.total_approved);
        total_reject = donorDashboard.findViewById(R.id.total_reject);
        pending_total = donorDashboard.findViewById(R.id.pending_total);

        semester1 = donorDashboard.findViewById(R.id.semester1);
        semester2 = donorDashboard.findViewById(R.id.semester2);
        semester3 = donorDashboard.findViewById(R.id.semester3);
        semester4 = donorDashboard.findViewById(R.id.semester4);
        semester5 = donorDashboard.findViewById(R.id.semester5);
        semester6 = donorDashboard.findViewById(R.id.semester6);
        semester7 = donorDashboard.findViewById(R.id.semester7);
        semester8 = donorDashboard.findViewById(R.id.semester8);

        total_applied = donorDashboard.findViewById(R.id.total_applied);

        sendUserData();
        getRecieved();
        getStudentAccepted(LoginFragment.userid);
        getStudentRejected(LoginFragment.userid);
        getStudentPending(LoginFragment.userid);
        totalApplied();
        return donorDashboard;
    }
    public void sendUserData(){
        SendData sendData = new SendData(getContext());
        sendData.getStudentData(LoginFragment.userid);
    }

    public static void getUserData(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String name = jsonObject.getString("name");
            String phone = jsonObject.getString("phone");
            String email = jsonObject.getString("batch");
            String cnic = jsonObject.getString("cnic");
            String semester = jsonObject.getString("semester");
            String cgpa = jsonObject.getString("cgpa");
            String father = jsonObject.getString("father_name");

            Log.d("abc", name);

            donor_name.setText(name);
            donor_phone.setText(phone);
            donor_email.setText(email);
            donor_cnic.setText(cnic);
            other_paid.setText(semester);
            other_paid_f.setText(cgpa);
            electric_paid.setText(father);

        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    public void getRecieved(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getActivity().getString(R.string.ip_current)+getActivity().getString(R.string.url_student_r),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            int totalAmount = 0;
                            Log.e("check", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            total_applied.setText(String.valueOf(jsonArray.length()));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                totalAmount += Integer.valueOf(jsonObject.getString("amount"));
                                if(Integer.valueOf(jsonObject.getString("apply_semester")) == 1){
                                    semester1.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 2){
                                    semester2.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 3){
                                    semester3.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 4){
                                    semester4.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 5){
                                    semester5.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 6){
                                    semester6.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 7){
                                    semester7.setText(jsonObject.getString("amount"));
                                }
                                else if(Integer.valueOf(jsonObject.getString("apply_semester")) == 8){
                                    semester8.setText(jsonObject.getString("amount"));
                                }

                            }
                            amount_r.setText("Rs "+String.valueOf(totalAmount));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(getContext()).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(LoginFragment.userid));


                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestque(stringRequest);
    }

    public void getStudentAccepted(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getActivity().getString(R.string.ip_current)+getActivity().getString(R.string.url_student_accepted),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("checkacc", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            total_approved.setText(String.valueOf(jsonArray.length()));
                        }
                        catch (Exception ex){

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(getActivity()).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);


    }
    public void getStudentRejected(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getActivity().getString(R.string.ip_current)+getActivity().getString(R.string.url_student_rejected),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            total_reject.setText(String.valueOf(jsonArray.length()));
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                            }
                        }
                        catch (Exception ex){

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(getActivity()).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);


    }

    public void totalApplied(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getActivity().getString(R.string.ip_current)+getActivity().getString(R.string.url_student_r_a),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            int totalAmount = 0;
                            Log.e("check", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            total_applied.setText(String.valueOf(jsonArray.length()));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(getContext()).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(LoginFragment.userid));


                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestque(stringRequest);
    }

    public void getStudentPending(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getContext().getString(R.string.ip_current)+getContext().getString(R.string.url_student_pending),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            pending_total.setText(String.valueOf(jsonArray.length()));
                        }
                        catch (Exception ex){

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(getContext()).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestque(stringRequest);


    }

}
