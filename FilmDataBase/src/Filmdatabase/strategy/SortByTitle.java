package Filmdatabase.strategy;

import Filmdatabase.domain.Movie;

import java.util.Comparator;
import java.util.List;

public class SortByTitle implements SortStrategy {

    @Override
    public void sort(List<Movie> movies) {
        movies.sort(Comparator.comparing(m -> m.getDisplayTitle()));
    }
}
