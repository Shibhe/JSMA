package com.example.josephthedev.jobmanagementsheet.Activity.Client;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.josephthedev.jobmanagementsheet.Model.AddrLocation;
import com.example.josephthedev.jobmanagementsheet.Model.Job;
import com.example.josephthedev.jobmanagementsheet.Model.User;
import com.example.josephthedev.jobmanagementsheet.R;
import com.example.josephthedev.jobmanagementsheet.Services.SubmitJobService;

import java.util.Random;

import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SetJobActivity extends AppCompatActivity {

    private Job job = new Job();
    private EditText jobType, customer, descrition, reqDate, refID;
    private User user;
    private AddrLocation addrLocation;
    private Button requestJob;

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_job);

        jobType = findViewById(R.id.job_Type);
        customer = findViewById(R.id.job_Type);
        descrition = findViewById(R.id.job_Type);
        reqDate = findViewById(R.id.job_Type);
        refID = findViewById(R.id.job_Type);
        requestJob = findViewById(R.id.reqJob);

        job.setRef_ID(getRefCode());

        refID.setText(job.getRef_ID());
        customer.setText(user.getFirstName() + " " + user.getLastName());
    }

    protected String getRefCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();

        Random rnd = new Random();
        while (code.length() < 18) { // length of the random string.
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
            submitJobService.execute(String.valueOf(user.getUser_ID()), addrLocation.getName(), String.valueOf(addrLocation.getLatitude()), String.valueOf(addrLocation.getLongitude()), refID.getText().toString(),jobType.getText().toString(), descrition.getText().toString(), reqDate.getText().toString());
        }


    }
}
