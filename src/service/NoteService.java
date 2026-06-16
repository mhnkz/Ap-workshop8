package service;

import model.Note;
import exception.DuplicateNoteException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteService {
    private String storageFile = "data/notes.dat";
    private List<Note> notes;

    public NoteService() {
        loadNotes();
    }

    private void loadNotes() {
        File file = new File(storageFile);
        if (!file.exists()) {
            notes = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            notes = (List<Note>) ois.readObject();
        } catch (Exception e) {
            notes = new ArrayList<>();
        }
    }

    private void saveNotes() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(notes);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addNote(String title, String content, String date) throws DuplicateNoteException {
        for (Note n : notes) {
            if (n.getTitle().equalsIgnoreCase(title)) {
                throw new DuplicateNoteException("Title already exists!");
            }
        }

        notes.add(new Note(title, content, date));
        saveNotes();
    }

    public Note findNote(String title) {
        for (Note n : notes) {
            if (n.getTitle().equalsIgnoreCase(title)) {
                return n;
            }
        }
        return null;
    }

    public List<Note> getAllNotes() {
        return notes;
    }

    public boolean deleteNote(String title) {
        Note toRemove = null;
        for (Note n : notes) {
            if (n.getTitle().equalsIgnoreCase(title)) {
                toRemove = n;
                break;
            }
        }

        if (toRemove != null) {
            notes.remove(toRemove);
            saveNotes();
            return true;
        }
        return false;
    }

    public boolean exportNote(String title, String path) {
        Note note = findNote(title);
        if (note == null) {
            return false;
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(dir, title + ".txt")))) {
            writer.println("Title: " + note.getTitle());
            writer.println("Date: " + note.getCreatedDate());
            writer.println("----------------------------------------");
            writer.println(note.getContent());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}