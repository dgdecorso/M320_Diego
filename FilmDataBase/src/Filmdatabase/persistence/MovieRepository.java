package Filmdatabase.persistence;

import Filmdatabase.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements Repository<Movie> {

    private final List<Movie> movies = new ArrayList<>();

    @Override
    public void save(Movie movie) {
        movies.add(movie);
    }

    @Override
    public List<Movie> findAll() {
        return new ArrayList<>(movies);
    }
}
