package com.kira.mvvm_noteapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    //Application is subclass of Context
    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new insertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new updateNoteAsyncTask(noteDao).execute(note);

    }

    public void delete(Note note) {
        new deleteNoteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes() {
        new deleteAllNotesAsyncTask(noteDao).execute();

    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    //to insert ,update,delete or deleteAllNotes this operation we cann't execute it
    //in the main thread so we will make async task
    public static class insertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public insertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    public static class updateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public updateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    public static class deleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public deleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    public static class deleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public deleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNote();
            return null;
        }
    }

}
