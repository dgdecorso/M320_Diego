package Filmdatabase.domain;

import java.util.UUID;

public abstract class MediaItem {

    protected final UUID id;
    protected final String title;
    protected final int year;

    protected MediaItem(String title, int year) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.year = year;
    }

    public UUID getId() {
        return id;
    }

    public abstract String getDisplayTitle();
}
