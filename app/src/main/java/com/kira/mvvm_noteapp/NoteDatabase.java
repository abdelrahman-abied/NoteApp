package com.kira.mvvm_noteapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_table")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //==========================================================================================
    //the under this comment for put data to recyclerview when it started so it is not important
    //to add you can ignore it in other project and if you not add it
    // you must delete this ((  addCallback(roomCallback)  ))from above code thanks.

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public PopulateAsyncTask(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "description 1", 1));
            noteDao.insert(new Note("Title 2", "description 2", 2));
            noteDao.insert(new Note("Title 3", "description 3", 3));

            return null;
        }
    }
    //==========================================================================================
}
