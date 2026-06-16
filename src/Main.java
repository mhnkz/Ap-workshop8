import model.Note;
import service.NoteService;
import exception.DuplicateNoteException;
import util.DateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static NoteService service = new NoteService();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Personal Notebook");
        System.out.println("=========================================");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add note");
            System.out.println("2. Delete note");
            System.out.println("3. View notes");
            System.out.println("4. Export note");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                addNote();
            } else if (input.equals("2")) {
                deleteNote();
            } else if (input.equals("3")) {
                viewNotes();
            } else if (input.equals("4")) {
                exportNote();
            } else if (input.equals("5")) {
                System.out.println("Goodbye!");
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    private static void addNote() {
        System.out.println("\n--- Add Note ---");
        System.out.print("Title (or 'back'): ");
        String title = scanner.nextLine().trim();

        if (title.equalsIgnoreCase("back")) {
            return;
        }

        if (title.isEmpty()) {
            System.out.println("Title required!");
            return;
        }

        if (service.findNote(title) != null) {
            System.out.println("Title already exists!");
            return;
        }

        System.out.println("Content (empty line to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            content.append(line).append("\n");
        }

        if (content.length() == 0) {
            System.out.println("Content required!");
            return;
        }

        try {
            service.addNote(title, content.toString().trim(), DateUtil.getCurrentDate());
            System.out.println("Note added!");
        } catch (DuplicateNoteException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteNote() {
        System.out.println("\n--- Delete Note ---");
        List<Note> notes = service.getAllNotes();

        if (notes.isEmpty()) {
            System.out.println("No notes!");
            return;
        }

        showList(notes);
        System.out.print("Title to delete (or 'back'): ");
        String title = scanner.nextLine().trim();

        if (title.equalsIgnoreCase("back")) {
            return;
        }

        if (service.deleteNote(title)) {
            System.out.println("Deleted!");
        } else {
            System.out.println("Not found!");
        }
    }

    private static void viewNotes() {
        System.out.println("\n--- View Notes ---");
        List<Note> notes = service.getAllNotes();

        if (notes.isEmpty()) {
            System.out.println("No notes!");
            return;
        }

        showList(notes);
        System.out.print("Title to view (or 'back'): ");
        String title = scanner.nextLine().trim();

        if (title.equalsIgnoreCase("back")) {
            return;
        }

        Note note = service.findNote(title);
        if (note != null) {
            System.out.println("\n--- Content ---");
            System.out.println(note.getContent());
            System.out.println("------------------------");
        } else {
            System.out.println("Not found!");
        }
    }

    private static void exportNote() {
        System.out.println("\n--- Export Note ---");
        List<Note> notes = service.getAllNotes();

        if (notes.isEmpty()) {
            System.out.println("No notes!");
            return;
        }

        showList(notes);
        System.out.print("Title to export (or 'back'): ");
        String title = scanner.nextLine().trim();

        if (title.equalsIgnoreCase("back")) {
            return;
        }

        if (service.exportNote(title, "export")) {
            System.out.println("Exported to export folder!");
        } else {
            System.out.println("Not found!");
        }
    }

    private static void showList(List<Note> notes) {
        System.out.println("\n--- Notes ---");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i));
        }
        System.out.println("------------------------");
    }
}