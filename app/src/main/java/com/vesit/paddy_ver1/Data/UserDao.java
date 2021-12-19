package com.vesit.paddy_ver1.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vesit.paddy_ver1.Model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User where user_email= :mail and user_password= :password")
    User getUser(String mail, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}