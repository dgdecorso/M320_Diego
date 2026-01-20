package Filmdatabase.ui;

import Filmdatabase.domain.Genre;
import Filmdatabase.service.FilmService;

import java.util.Scanner;

public class ConsoleUI {

    private final FilmService filmService = new FilmService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== FilmDB ===");
            System.out.println("1) Film hinzufügen");
            System.out.println("2) Filme anzeigen");
            System.out.println("3) Beenden");
            System.out.print("> ");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> addMovie();
                case "2" -> filmService.printAllMovies();
                case "3" -> running = false;
                default -> System.out.println("Ungültige Eingabe");
            }
        }
    }

    private void addMovie() {
        try {
            System.out.print("Titel: ");
            String title = scanner.nextLine();

            System.out.print("Jahr: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("Laufzeit (Minuten): ");
            int runtime = Integer.parseInt(scanner.nextLine());

            System.out.print("Genre (ACTION, DRAMA, SCIFI): ");
            Genre genre = Genre.valueOf(scanner.nextLine().toUpperCase());

            filmService.addMovie(title, year, runtime, genre);
            System.out.println("Film hinzugefügt!");

        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
