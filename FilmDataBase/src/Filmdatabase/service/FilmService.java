package Filmdatabase.service;

import Filmdatabase.domain.Genre;
import Filmdatabase.domain.Movie;
import Filmdatabase.observer.NotificationService;
import Filmdatabase.persistence.MovieJsonLoader;
import Filmdatabase.persistence.MovieRepository;
import Filmdatabase.strategy.SortByTitle;
import Filmdatabase.strategy.SortStrategy;
import Filmdatabase.validation.MovieValidator;

import java.util.List;
import java.util.stream.Collectors;

public class FilmService {

    private final MovieRepository repository = new MovieRepository();
    private final MovieValidator validator = new MovieValidator();
    private final SortStrategy sortStrategy = new SortByTitle();
    private final NotificationService notificationService = new NotificationService();

    // NEU: JSON import
    public void importFromJson(String path) {
        List<Movie> loaded = new MovieJsonLoader().load(path);
        repository.saveAll(loaded);
        notificationService.notifyAll("Importiert: " + loaded.size() + " Filme aus " + path);
    }

    public void addMovie(String title, int year, int runtime, Genre genre) {
        validator.validateYear(year);
        validator.validateRuntime(runtime);

        Movie movie = new Movie(title, year, runtime, genre);
        repository.save(movie);

        notificationService.notifyAll("Neuer Film: " + movie.getDisplayTitle());
    }

    // NEU: sortierte Liste zurückgeben (für UI)
    public List<Movie> getAllSorted() {
        List<Movie> movies = repository.findAll();
        sortStrategy.sort(movies);
        return movies;
    }

    // NEU: Suche
    public List<Movie> searchByTitle(String query) {
        if (query == null || query.isBlank()) return List.of();
        String q = query.toLowerCase();

        return repository.findAll().stream()
                .filter(m -> m.getDisplayTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    // bleibt: direkt ausgeben
    public void printAllMovies() {
        List<Movie> movies = repository.findAll();
        sortStrategy.sort(movies);

        if (movies.isEmpty()) {
            System.out.println("Keine Filme vorhanden.");
            return;
        }

        movies.forEach(m -> System.out.println("- " + m.getDisplayTitle()));
    }


    public List<Movie> searchByGenre(Genre genre) {
        if (genre == null) return List.of();

        return repository.findAll().stream()
                .filter(m -> m.getGenre() == genre)
                .collect(java.util.stream.Collectors.toList());
    }

}
