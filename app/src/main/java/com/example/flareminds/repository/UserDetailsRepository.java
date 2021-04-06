package com.example.flareminds.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.flareminds.roomdatabase.LoginDao;
import com.example.flareminds.roomdatabase.LoginDatabase;
import com.example.flareminds.roomdatabase.User;
import com.example.flareminds.roomdatabase.UserDetails;
import com.example.flareminds.roomdatabase.UserDetailsDao;

import java.util.List;

public class UserDetailsRepository {
    private UserDetailsDao userDetailsDao;
    private LiveData<List<UserDetails>> allData;

    public UserDetailsRepository(Application application){
        LoginDatabase db = LoginDatabase.getDatabase(application);
        userDetailsDao = db.userDetailsDao();
        allData = userDetailsDao.getDetails();
    }

    public LiveData<List<UserDetails>> getAllData() {
        return allData;
    }

    public void insertUserDetails(UserDetails data) {
        new UserDetailsInsertion(userDetailsDao).execute(data);
    }

    private static class UserDetailsInsertion extends AsyncTask<UserDetails, Void, Void> {

        private UserDetailsDao dao;

        private UserDetailsInsertion(UserDetailsDao dao) {

            this.dao = dao;

        }

        @Override
        protected Void doInBackground(UserDetails... userDetails) {
            dao.insertDetails(userDetails[0]);
            return null;
        }
    }
}
