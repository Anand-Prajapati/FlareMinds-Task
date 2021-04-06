package com.example.flareminds.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.flareminds.repository.LoginRepository;
import com.example.flareminds.roomdatabase.User;

import java.util.List;


public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    private LiveData<List<User>> getAllData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repository = new LoginRepository(application);
        getAllData = repository.getAllData();

    }

    public void insert(User data) {
        repository.insertData(data);
    }

    public LiveData<List<User>> getGetAllData() {
        return getAllData;
    }

    public LiveData<User> getCurrentUser(String email, String password){
        LiveData<User> user =  repository.getCurrentUser(email,password);
        return user;
    }

}

