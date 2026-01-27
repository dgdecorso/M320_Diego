package Filmdatabase.persistence;

import java.util.List;

public interface Repository<T> {
    void save(T item);
    List<T> findAll();
}
