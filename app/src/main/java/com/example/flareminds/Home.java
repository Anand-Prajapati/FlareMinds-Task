package com.example.flareminds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.flareminds.databinding.ActivityHomeBinding;
import com.example.flareminds.roomdatabase.User;
import com.example.flareminds.roomdatabase.UserDetails;
import com.example.flareminds.viewmodel.LoginViewModel;
import com.example.flareminds.viewmodel.UserDetailsViewModel;

import java.util.List;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    UserDetailsViewModel viewModel;
    LoginViewModel loginViewModel;
    int userId;
    String password, email;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(Home.this, R.layout.activity_home);

        loginViewModel = ViewModelProviders.of(Home.this).get(LoginViewModel.class);
        viewModel = ViewModelProviders.of(Home.this).get(UserDetailsViewModel.class);

        final SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);
        email = sPref.getString("email", "Not found");
        password = sPref.getString("password", "Not found");

        loginViewModel.getGetAllData().observe(Home.this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (User user : users) {
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        userId = user.getId();
                        if (userId != 0) {
                            viewModel.getGetAllData().observe(Home.this, new Observer<List<UserDetails>>() {
                                @Override
                                public void onChanged(List<UserDetails> userDetails) {
                                    for (UserDetails user : userDetails) {
                                        if (userId == user.getId()) {
                                            binding.tvName.setText(user.getName());
                                            binding.tvMobile.setText(user.getMobile());
                                            binding.tvAddress.setText(user.getAddress());
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        sendUserToUserDetailsScreen(userId);
                                        flag = true;
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

    }

    private void sendUserToUserDetailsScreen(int userId) {
        Intent intent = new Intent(Home.this, UserDetailScreen.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().toString().equals("Logout")) {
            final SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString("email", "");
            editor.putString("password", "");
            editor.commit();

            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}