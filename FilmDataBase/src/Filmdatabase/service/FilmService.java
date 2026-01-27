package Filmdatabase.service;

import Filmdatabase.domain.Genre;
import Filmdatabase.domain.Movie;
import Filmdatabase.observer.NotificationService;
import Filmdatabase.persistence.MovieRepository;
import Filmdatabase.strategy.SortByTitle;
import Filmdatabase.strategy.SortStrategy;
import Filmdatabase.validation.MovieValidator;

import java.util.List;

public class FilmService {

    private final MovieRepository repository = new MovieRepository();
    private final MovieValidator validator = new MovieValidator();
    private final SortStrategy sortStrategy = new SortByTitle(); // <- KEIN Cast
    private final NotificationService notificationService = new NotificationService();

    public void addMovie(String title, int year, int runtime, Genre genre) {
        validator.validateYear(year);
        validator.validateRuntime(runtime);

        Movie movie = new Movie(title, year, runtime, genre);
        repository.save(movie);

        notificationService.notifyAll("Neuer Film: " + movie.getDisplayTitle());
    }

    public void printAllMovies() {
        List<Movie> movies = repository.findAll();
        sortStrategy.sort(movies);

        if (movies.isEmpty()) {
            System.out.println("Keine Filme vorhanden.");
            return;
        }

        movies.forEach(m -> System.out.println("- " + m.getDisplayTitle()));
    }
}
