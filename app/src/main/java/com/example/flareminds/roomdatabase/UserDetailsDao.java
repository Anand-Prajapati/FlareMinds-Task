package com.example.flareminds.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDetailsDao {


    @Query("select * from UserDetails")
    LiveData<List<UserDetails>> getDetails();

    @Insert
    void insertDetails(UserDetails data);

}
