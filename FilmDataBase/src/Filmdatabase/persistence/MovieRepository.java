package Filmdatabase.persistence;

import Filmdatabase.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private final List<Movie> movies = new ArrayList<>();

    public void save(Movie movie) {
        movies.add(movie);
    }

    // NEU: für JSON-Import
    public void saveAll(List<Movie> list) {
        movies.addAll(list);
    }

    public List<Movie> findAll() {
        return new ArrayList<>(movies);
    }

    // OPTIONAL (nur wenn du willst)
    public void clear() {
        movies.clear();
    }
}
