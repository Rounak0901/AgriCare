package com.vesit.paddy_ver1.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vesit.paddy_ver1.Model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao getUserDao();

}