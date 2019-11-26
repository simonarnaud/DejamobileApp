package com.example.dejamobileapp.database;

import android.content.Context;

import com.example.dejamobileapp.dao.CardDao;
import com.example.dejamobileapp.dao.PurchaseDao;
import com.example.dejamobileapp.dao.UserDao;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.Purchase;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.Gender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Card.class, Purchase.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final int THREAD_NUMBER = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREAD_NUMBER);
    private static final String DB_NAME = "dejamobile.db";

    public abstract UserDao userDao();
    public abstract CardDao cardDao();
    public abstract PurchaseDao purchaseDao();

    public static AppDatabase getAppDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).addCallback(roomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }


    //Database Populator
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();
                CardDao cardDao = INSTANCE.cardDao();
                PurchaseDao purchaseDao = INSTANCE.purchaseDao();

                userDao.deleteAllUsers();
                cardDao.deleteAllCards();
                purchaseDao.deleteAllPurchases();

                User user = new User(0, "Simon", "Arnaud", "email", Gender.MALE, "password", false);
                userDao.insert(user);


                /* Card card = new Card(0, 123456789, 123, CardScheme.MASTERCARD, user.getUserId(), false);
                cardDao.insert(card);
                Purchase purchase = new Purchase(0, card.getCardId(), 20, "test", "impots", new Date(), false);
                purchaseDao.insertAllPurchases(purchase);*/
            });
        }
    };
}
