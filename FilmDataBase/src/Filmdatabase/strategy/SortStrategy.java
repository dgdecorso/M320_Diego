package Filmdatabase.strategy;

import Filmdatabase.domain.Movie;

import java.util.List;

public interface SortStrategy {
    void sort(List<Movie> movies);
}
