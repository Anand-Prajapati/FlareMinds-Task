package com.example.flareminds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.flareminds.databinding.ActivityMainBinding;
import com.example.flareminds.roomdatabase.User;
import com.example.flareminds.viewmodel.LoginViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        loginViewModel = ViewModelProviders.of(MainActivity.this).get(LoginViewModel.class);

        final SharedPreferences sPref = getSharedPreferences("User", MODE_PRIVATE);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    binding.etEmail.setError("Please Enter Your E-mail Address");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.etPassword.setError("Please Enter Password");
                    return;
                }
                if (password.length() < 6) {
                    binding.etPassword.setError("Too Small Password");
                    return;
                }
                List<User> user = loginViewModel.getGetAllData().getValue();
                boolean flag = true;
                for (User user1 : user) {
                    if (user1.getEmail().equals(email) && user1.getPassword().equals(password)) {
                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putString("email", user1.getEmail());
                        editor.putString("password", user1.getPassword());
                        editor.commit();
                        sendUserToHomeActivity();
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    User data = new User();
                    data.setEmail(email);
                    data.setPassword(password);
                    loginViewModel.insert(data);

                    SharedPreferences.Editor editor = sPref.edit();

                    editor.putString("email", data.getEmail());
                    editor.putString("password", data.getPassword());
                    editor.commit();

                    sendUserToHomeActivity();
                }
            }
        });

        loginViewModel.getGetAllData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                final SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
                String email = sp.getString("email", "Not found");
                String password = sp.getString("password", "Not found");

                for (User user : users) {
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        break;
                    }
                }
            }
        });


    }

    private void sendUserToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}