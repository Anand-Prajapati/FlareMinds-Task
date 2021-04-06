package com.example.flareminds.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginDao {

    @Insert
    void insertDetails(User data);

    @Query("select * from LoginDetails")
    LiveData<List<User>> getDetails();

    @Query("select * from LoginDetails where Email LIKE :email and Password LIKE :Password")
    LiveData<User> getCurrentUser(String email, String Password);
}
