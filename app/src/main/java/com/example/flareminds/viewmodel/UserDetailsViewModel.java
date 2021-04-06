package com.example.flareminds.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.flareminds.repository.UserDetailsRepository;
import com.example.flareminds.roomdatabase.User;
import com.example.flareminds.roomdatabase.UserDetails;

import java.util.List;


public class UserDetailsViewModel extends AndroidViewModel {

    private UserDetailsRepository repository;
    private LiveData<List<UserDetails>> getAllData;

    public UserDetailsViewModel(@NonNull Application application) {
        super(application);

        repository = new UserDetailsRepository(application);
        getAllData = repository.getAllData();
    }

    public void insert(UserDetails data) {
        repository.insertUserDetails(data);
    }

    public LiveData<List<UserDetails>> getGetAllData() {
        return getAllData;
    }

}
