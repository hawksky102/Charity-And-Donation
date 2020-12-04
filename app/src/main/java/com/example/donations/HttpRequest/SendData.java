package com.example.donations.HttpRequest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donations.Fragments.AcceptedDonationStudent;
import com.example.donations.Fragments.AddPaymentDonor;
import com.example.donations.Fragments.AllPaymentsDonor;
import com.example.donations.Fragments.ApplyDonationStudent;
import com.example.donations.Fragments.ChallanViewFragment;
import com.example.donations.Fragments.DonorDashboard;
import com.example.donations.Fragments.LoginFragment;
import com.example.donations.Fragments.PendingDonationStudent;
import com.example.donations.Fragments.PendingPaymentDonor;
import com.example.donations.Fragments.RejectedDonationStudent;
import com.example.donations.Fragments.SignupDonorFragment;
import com.example.donations.Fragments.SignupFragment;
import com.example.donations.Fragments.StudentDashboard;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SendData {

    Context context;

   public SendData(Context mcontext){
        context = mcontext;
    }

    public boolean signupStudent(
            final String name, final String fathername, final String phone, final String email, final String address, final String cnic,
            final String batch, final String semester, final String cgpa, final String family_income, final String bank_account,
            final String password, final String filename
    ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_signup_student),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("check", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");
                            if(status.matches("Success")){
                                SignupFragment.clearFields();
                            }
                            else{
                                SignupFragment.showError();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("father_name", fathername);
                params.put("phone", phone);
                params.put("email", email);
                params.put("address", address);
                params.put("cnic", cnic);
                params.put("batch", batch);
                params.put("semester", semester);
                params.put("cgpa", cgpa);
                params.put("family_income", filename);
                params.put("bank_account", bank_account);
                params.put("password", password);
                params.put("status", String.valueOf(0));

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }

    public boolean signupDonor(
            final String name, final String phone, final String email, final String cnic, final String password
    ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_signup_donor),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("check", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");
                            if(status.matches("Success")){
                                SignupDonorFragment.clearFields();
                            }
                            else{
                                SignupDonorFragment.showError();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("cnic", cnic);
                params.put("password", password);
                params.put("status", String.valueOf(10));

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }

    public boolean signinMain(
            final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_signin),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("check", response);
                            if(response.length() < 1 || response.isEmpty() || response.equalsIgnoreCase("null")){
                                Log.e("check", "Empty Bitch");
                            }
                            else {
                                JSONObject jsonObject = new JSONObject(response);
                                String check_status = jsonObject.getString("status_reg");
                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                int id = jsonObject.getInt("id");
                                if(Integer.valueOf(check_status) == 10){
                                    LoginFragment.onSuccess(false, name, phone, id);
                                }
                                else if(Integer.valueOf(check_status) == 1){
                                    LoginFragment.onSuccess(true, name, phone, id);
                                }
                                else{
                                    LoginFragment.showError();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_upload_image),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            if(obj.getInt("status") == 1){
                                SignupFragment.sendDataServer(false, obj.getString("file_name"));
                            }
                            else{
                                SignupFragment.showError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    public void uploadBitmapAmount(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_add_payment_image),
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            if(obj.getInt("status") == 1){
                                AddPaymentDonor.sendDataServer(false, obj.getString("file_name"));
                            }
                            else{
                                AddPaymentDonor.showError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    public boolean getPendingDonation(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_pending_donations),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        PendingPaymentDonor.getPendingData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public boolean addDonation(
            final int donor_id, final String date, final String amount, final String description, final String payment_type
    ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_add_donations),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("check", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");
                            if(status.matches("Success")){
                                ChallanViewFragment.saveChallan();
                            }
                            else{
                                ChallanViewFragment.showError();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("donor_id", String.valueOf(donor_id));
                params.put("date", date);
                params.put("amount", amount);
                params.put("description", description);
                params.put("payment_type", payment_type);
                params.put("payment_status", String.valueOf(0));

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean addPayment(final int id, final String filename){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_add_payment),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");
                            if(status.matches("Success")){
                                AddPaymentDonor.checkResponse();
                            }
                            else{
                                AddPaymentDonor.showError();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                params.put("filename", filename);

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getAllPayment(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_all_payment),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        AllPaymentsDonor.getPendingData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getDonorData(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_donor_dashboard),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        DonorDashboard.getUserData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }

    public boolean getStudentData(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_student_dashboard),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        StudentDashboard.getUserData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getDonorDonations(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_donor_donations),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        DonorDashboard.getDonationData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getDonorPending(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_donor_pendings),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        DonorDashboard.getPendingData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean addStudentRequest(
            final int student_id, final String date, final String description, final String amount, final String current_semester, final String apply_for){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_add_student_request),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("check", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("status");
                            if(status.matches("Success")){
                                ApplyDonationStudent.onSucessRequest();
                            }
                            else{
                                ApplyDonationStudent.onErrorRequest();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("student_id", String.valueOf(student_id));
                params.put("date", date);
                params.put("description", description);
                params.put("amount", amount);
                params.put("current_semester", current_semester);
                params.put("apply_for", apply_for);
                params.put("status_recieve", String.valueOf(0));

                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getStudentPending(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_student_pending),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        PendingDonationStudent.getPendingData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getStudentAccepted(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_student_accepted),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        AcceptedDonationStudent.getPendingData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
    public boolean getStudentRejected(final int id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, context.getString(R.string.ip_current)+context.getString(R.string.url_student_rejected),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        RejectedDonationStudent.getPendingData(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                new CustomToast(context).ErrorToast("Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

                return params;
            }
        };
        MySingleton.getmInstance(context).addToRequestque(stringRequest);

        return false;
    }
}
