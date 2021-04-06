package com.example.flareminds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.flareminds.databinding.ActivityUserDetailBinding;
import com.example.flareminds.roomdatabase.UserDetails;
import com.example.flareminds.viewmodel.UserDetailsViewModel;

public class UserDetailScreen extends AppCompatActivity {

    ActivityUserDetailBinding binding;
    UserDetailsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(UserDetailScreen.this,R.layout.activity_user_detail);

        Intent intent = getIntent();
        final int userId = intent.getIntExtra("userId",0);
        viewModel = ViewModelProviders.of(UserDetailScreen.this).get(UserDetailsViewModel.class);

        binding.btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString();
                String mobile = binding.etMobile.getText().toString();
                String address = binding.etAddress.getText().toString();

                if (TextUtils.isEmpty(name)){
                    binding.etName.setError("Please Enter Your Name");
                }
                if (TextUtils.isEmpty(mobile)){
                    binding.etMobile.setError("Please Enter Your Mobile Number");
                }
                if (TextUtils.isEmpty(address)){
                    binding.etAddress.setError("Please Enter Your Address");
                }

                UserDetails user = new UserDetails();
                user.setId(userId);
                user.setAddress(address);
                user.setMobile(mobile);
                user.setName(name);

                viewModel.insert(user);

                Toast.makeText(UserDetailScreen.this, "User Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserDetailScreen.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}