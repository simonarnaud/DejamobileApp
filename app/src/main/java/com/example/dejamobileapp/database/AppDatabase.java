package com.example.dejamobileapp.database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dejamobileapp.dao.CardDao;
import com.example.dejamobileapp.dao.PurchaseDao;
import com.example.dejamobileapp.dao.UserDao;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.Purchase;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.CardScheme;
import com.example.dejamobileapp.utils.Gender;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDb(INSTANCE).execute();
        }
    }





    //background task to move
    private static class PopulateDb extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;
        private final CardDao cardDao;
        private final PurchaseDao purchaseDao;

        public PopulateDb(AppDatabase instance) {
            userDao = instance.userDao();
            cardDao = instance.cardDao();
            purchaseDao = instance.purchaseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllUsers();
            cardDao.deleteAllCards();
            purchaseDao.deleteAllPurchases();

            User user = new User(0,"Simon", "Arnaud", "simn.arnaud@hotmail.fr", Gender.MALE,false);
            Card card = new Card(0,123456789L, 123, CardScheme.MASTERCARD, (int) userDao.insert(user),false);
            Purchase purchase = new Purchase(0,(int) cardDao.insert(card), 20, "test", "Banque populaire",new Date(),false);
            purchaseDao.insertAllPurchases(purchase);

            return null;
        }
    }
}
