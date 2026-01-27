package Filmdatabase.ui;

import Filmdatabase.domain.Movie;
import Filmdatabase.service.FilmService;
import Filmdatabase.persistence.MovieJsonLoader;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final FilmService filmService = new FilmService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        // JSON einmal beim Start laden:
        filmService.importFromJson("src/Filmdatabase/data/movies.json");

        boolean running = true;
        while (running) {
            System.out.println("\n=== FilmDB ===");
            System.out.println("1) Filme anzeigen");
            System.out.println("2) Film suchen (Titel enthält)");
            System.out.println("0) Beenden");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> showAll();
                case "2" -> search();
                case "0" -> running = false;
                default -> System.out.println("Ungültige Eingabe");
            }
        }
    }

    private void showAll() {
        List<Movie> movies = filmService.getAllSorted();
        if (movies.isEmpty()) {
            System.out.println("Keine Filme vorhanden.");
            return;
        }
        for (Movie m : movies) {
            System.out.println("- " + m.getDisplayTitle());
        }
    }

    private void search() {
        System.out.print("Suchtext: ");
        String q = scanner.nextLine().trim();

        List<Movie> results = filmService.searchByTitle(q);
        if (results.isEmpty()) {
            System.out.println("Keine Treffer.");
            return;
        }
        for (Movie m : results) {
            System.out.println("- " + m.getDisplayTitle());
        }
    }
}
