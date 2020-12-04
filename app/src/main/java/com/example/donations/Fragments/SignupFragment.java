package com.example.donations.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.donations.HttpRequest.MySingleton;
import com.example.donations.HttpRequest.SendData;
import com.example.donations.MainActivity;
import com.example.donations.R;
import com.example.donations.Toast.CustomToast;
import com.example.donations.TouchView.TouchImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    ImageView login;
    static EditText st_name, st_father, st_phone, st_email, st_address, st_cnic, st_batch, st_semester, st_cgpa, st_family_income, st_bank_account, st_password, st_repassword;
    static String name, father_name, phone, email, address, cnic, batch, semester, cgpa, family_income_pic, bank_account, password;
    Button signupButton;
    static Context mcontext;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static Bitmap bitmap;
    private String filePath;
    Dialog dialog_show_image;
    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment imageViewLogin
        View signUpFragment = inflater.inflate(R.layout.fragment_signup, container, false);

        mcontext = getContext();
        st_name = signUpFragment.findViewById(R.id.st_name);
        st_father = signUpFragment.findViewById(R.id.st_father);
        st_phone = signUpFragment.findViewById(R.id.st_phone);
        st_email = signUpFragment.findViewById(R.id.st_email);
        st_address = signUpFragment.findViewById(R.id.st_address);
        st_cnic = signUpFragment.findViewById(R.id.st_cnic);
        st_batch = signUpFragment.findViewById(R.id.st_batch);
        st_semester = signUpFragment.findViewById(R.id.st_semester);
        st_cgpa = signUpFragment.findViewById(R.id.st_cgpa);
        st_family_income = signUpFragment.findViewById(R.id.st_family_pic);
        st_bank_account = signUpFragment.findViewById(R.id.st_account);
        st_password = signUpFragment.findViewById(R.id.st_password);
        st_repassword = signUpFragment.findViewById(R.id.st_repassowrd);
        signupButton = signUpFragment.findViewById(R.id.st_signup);
        login = signUpFragment.findViewById(R.id.imageViewLogin);

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


        st_family_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

        return signUpFragment;
    }

    public void checkAllFields() {

        name = st_name.getText().toString();
        father_name = st_father.getText().toString();
        phone = st_phone.getText().toString();
        email = st_email.getText().toString();
        address = st_address.getText().toString();
        cnic = st_cnic.getText().toString();
        batch = st_batch.getText().toString();
        semester = st_semester.getText().toString();
        cgpa = st_cgpa.getText().toString();
        family_income_pic = st_family_income.getText().toString();
        bank_account = st_bank_account.getText().toString();
        password = st_password.getText().toString();
        String re_password = st_repassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            new CustomToast(getContext()).ErrorToast("Name not found");
        } else if (TextUtils.isEmpty(father_name)) {
            new CustomToast(getContext()).ErrorToast("Father name not found");
        } else if (TextUtils.isEmpty(phone)) {
            new CustomToast(getContext()).ErrorToast("Phone not found");
        } else if (TextUtils.isEmpty(email)) {
            new CustomToast(getContext()).ErrorToast("Email not found");
        } else if (TextUtils.isEmpty(address)) {
            new CustomToast(getContext()).ErrorToast("Address not found");
        } else if (TextUtils.isEmpty(cnic)) {
            new CustomToast(getContext()).ErrorToast("CNIC not found");
        } else if (TextUtils.isEmpty(batch)) {
            new CustomToast(getContext()).ErrorToast("Batch not found");
        } else if (TextUtils.isEmpty(semester)) {
            new CustomToast(getContext()).ErrorToast("Semester not found");
        } else if (TextUtils.isEmpty(cgpa)) {
            new CustomToast(getContext()).ErrorToast("CGPA name not found");
        } else if (TextUtils.isEmpty(bank_account)) {
            new CustomToast(getContext()).ErrorToast("Bank account not found");
        } else if (TextUtils.isEmpty(password)) {
            new CustomToast(getContext()).ErrorToast("Password name not found");
        } else if (TextUtils.isEmpty(re_password)) {
            new CustomToast(getContext()).ErrorToast("Re-password name not found");
        } else if (!password.matches(re_password)) {
            new CustomToast(getContext()).ErrorToast("Password not match");
        } else {
            checkUser(batch);
//            sendDataServer(true, "");
        }


    }

    public static void sendDataServer(boolean checkOption, String filename) {
        if(checkOption){
            SendData sendData = new SendData(mcontext);
            sendData.uploadBitmap(bitmap);
        }
        else {
            SendData sendData = new SendData(mcontext);
            sendData.signupStudent(
                    name, father_name, phone, email, address,
                    cnic, batch, semester, cgpa, family_income_pic,
                    bank_account, password, filename
            );
        }

    }
    public static void clearFields(){
        new CustomToast(mcontext).SuccessToast("Request Sent");
        st_name.setText("");
        st_father.setText("");
        st_phone.setText("");
        st_email.setText("");
        st_address.setText("");
        st_cnic.setText("");
        st_batch.setText("");
        st_semester.setText("");
        st_cgpa.setText("");
        st_family_income.setText("");
        st_bank_account.setText("");
        st_password.setText("");
        st_repassword.setText("");

    }
    public static void showError(){
        new CustomToast(mcontext).SuccessToast("Request Error");
    }

    public void checkPermission(){
        if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            showFileChooser();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                showSelectedImage(picUri, filePath);
            }
            else
            {
                Toast.makeText(
                        getActivity(),"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
    private void showSelectedImage(final Uri imageuri, final String filePath) {

        dialog_show_image = new Dialog(getActivity(), R.style.AppThemeDialog);
        dialog_show_image.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_show_image.setContentView(R.layout.custom_image_selector);
        TouchImageView selected_image = dialog_show_image.findViewById(R.id.uploadedimage);
        ImageView back_option = dialog_show_image.findViewById(R.id.back_option);
        FloatingActionButton upload_image = dialog_show_image.findViewById(R.id.floating_action_button);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        back_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                st_family_income.setText(filePath);
                dialog_show_image.dismiss();

            }
        });
        selected_image.setImageURI(imageuri);
//        try {
//            selected_image.setImageBitmap( MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageuri));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        dialog_show_image.show();


    }

    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;

    }

    public void checkUser(final String studentID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getActivity().getString(R.string.ip_current)+getActivity().getString(R.string.url_student_verify),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("check", response);
                            if(response.length() < 1 || response.isEmpty() || response.equalsIgnoreCase("null")){
                                Log.e("check", "Empty Bitch");
                            }
//                            else {
                                JSONObject jsonObject = new JSONObject(response);
                                String check_status = jsonObject.getString("student_id");
                                if(!TextUtils.isEmpty(check_status )){
                                    sendDataServer(true, "");
                                }
                                else{
                                    new CustomToast(mcontext).ErrorToast("Student not found");
                                }
//                                String name = jsonObject.getString("name");
//                                String phone = jsonObject.getString("phone");
//                                int id = jsonObject.getInt("id");
//                                if(Integer.valueOf(check_status) == 10){
//                                    LoginFragment.onSuccess(false, name, phone, id);
//                                }
//                                else if(Integer.valueOf(check_status) == 1){
//                                    LoginFragment.onSuccess(true, name, phone, id);
//                                }
//                                else{
//                                    LoginFragment.showError();
//                                }
//
//
//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CustomToast(mcontext).ErrorToast("Student not found");
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
                params.put("stid", studentID);


                //Toast.makeText(MainDashboard.this,name.getText().toString(),Toast.LENGTH_LONG).show();

                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
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
