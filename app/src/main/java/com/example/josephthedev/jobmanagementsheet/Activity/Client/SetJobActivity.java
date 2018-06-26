package com.example.josephthedev.jobmanagementsheet.Activity.Client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.josephthedev.jobmanagementsheet.Model.AddrLocation;
import com.example.josephthedev.jobmanagementsheet.Model.Contact;
import com.example.josephthedev.jobmanagementsheet.Model.Job;
import com.example.josephthedev.jobmanagementsheet.Model.User;
import com.example.josephthedev.jobmanagementsheet.R;
import com.example.josephthedev.jobmanagementsheet.Services.SubmitJobService;

import java.util.Random;

import static com.example.josephthedev.jobmanagementsheet.Services.LoginService.MyPREFERENCES;


public class SetJobActivity extends AppCompatActivity {

    private Job job = new Job();
    private EditText jobType, customer, descrition, reqDate, refID;
    private User user = new User();
    private AddrLocation addrLocation = new AddrLocation();
    private Button requestJob;
    Intent intent = new Intent();
    private Contact contact = new Contact();
    SharedPreferences sharedPreferences;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_job);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        jobType = findViewById(R.id.job_Type);
        customer = findViewById(R.id.customerName);
        descrition = findViewById(R.id.description);
        reqDate = findViewById(R.id.requiredDate);
        refID = findViewById(R.id.refID);
        requestJob = findViewById(R.id.requestJob);


        user.setUser_ID(sharedPreferences.getString("User_ID", null));
        user.setFirstName(sharedPreferences.getString("FirstName", null));
        user.setLastName(sharedPreferences.getString("LastName", null));

         contact.setPhoneNumber(getIntent().getStringExtra("PhoneNumber"));
         addrLocation.setName(getIntent().getStringExtra("Name"));
         addrLocation.setLatitude(Double.parseDouble(getIntent().getStringExtra("Latitude" )));
         addrLocation.setLongitude(Double.parseDouble(getIntent().getStringExtra("Longitude")));
        refID.setText(getRefCode());
        customer.setText(user.getFirstName() + " " + user.getLastName());

        requestJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateJob();
            }
        });
    }

    protected String getRefCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();

        Random rnd = new Random();
        while (code.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            code.append(SALTCHARS.charAt(index));
        }
        String saltStr = code.toString();
        return saltStr;

    }

    private void validateJob(){
        jobType.setError(null);
        descrition.setError(null);
        reqDate.setError(null);
        customer.setError(null);
        refID.setError(null);

        if (jobType.length() == 0 && jobType.isFocused()){
            jobType.setError("Job Type cannot be empty");
        }
        if (reqDate.length() == 0 && reqDate.isFocused()){
            reqDate.setError("Date cannot be empty");
        }

        if (customer.length() == 0 && customer.isFocused()){
            customer.setError("Customer cannot be empty");
        }

        if (descrition.length() == 0 && descrition.isFocused()){
            descrition.setError("Job Description cannot be empty");
        } else {
            SubmitJobService submitJobService = new SubmitJobService(SetJobActivity.this);
            submitJobService.execute(String.valueOf(user.getUser_ID()), addrLocation.getName(), String.valueOf(addrLocation.getLatitude()), String.valueOf(addrLocation.getLongitude()), refID.getText().toString(),jobType.getText().toString(), descrition.getText().toString(), reqDate.getText().toString(), contact.getPhoneNumber());
        }


    }

}
