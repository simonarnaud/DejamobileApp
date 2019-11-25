package com.example.dejamobileapp.database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dejamobileapp.dao.CardDao;
import com.example.dejamobileapp.dao.UserDao;
import com.example.dejamobileapp.model.Card;
import com.example.dejamobileapp.model.User;
import com.example.dejamobileapp.utils.CardScheme;
import com.example.dejamobileapp.utils.Gender;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Card.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "dejamobile.db";

    public abstract UserDao userDao();
    public abstract CardDao cardDao();

    public static AppDatabase getAppDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new PopulateDb(INSTANCE).execute();
                        }
                    }).build();
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

        public PopulateDb(AppDatabase instance) {
            userDao = instance.userDao();
            cardDao = instance.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllUsers();
            cardDao.deleteAllCards();

            User user = new User("Simon", "Arnaud", "simn.arnaud@hotmail.fr", Gender.MALE);
            Card card = new Card(123456789L, 123, CardScheme.MASTERCARD, (int) userDao.insert(user));
            cardDao.insertAllCards(card);
            return null;
        }
    }
}
