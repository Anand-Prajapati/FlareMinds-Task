package com.example.flareminds.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.flareminds.roomdatabase.LoginDao;
import com.example.flareminds.roomdatabase.LoginDatabase;
import com.example.flareminds.roomdatabase.User;

import java.util.List;

public class LoginRepository {

    private LoginDao loginDao;
    private LiveData<List<User>> allData;

    public LoginRepository(Application application) {

        LoginDatabase db = LoginDatabase.getDatabase(application);
        loginDao = db.loginDoa();
        allData = loginDao.getDetails();
    }

    public LiveData<User> getCurrentUser(String email,String password){
        LiveData<User> user =  loginDao.getCurrentUser(email,password);
        return user;
    }

    public LiveData<List<User>> getAllData() {
        return allData;
    }

    public void insertData(User data) {
        new LoginInsertion(loginDao).execute(data);
    }

    private static class LoginInsertion extends AsyncTask<User, Void, Void> {

        private LoginDao loginDao;

        private LoginInsertion(LoginDao loginDao) {

            this.loginDao = loginDao;

        }

        @Override
        protected Void doInBackground(User... data) {

            loginDao.insertDetails(data[0]);
            return null;

        }

    }

}
