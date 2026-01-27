package Filmdatabase.persistence;

import com.google.gson.*;
import Filmdatabase.domain.Genre;
import Filmdatabase.domain.Movie;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;


public class MovieJsonLoader {

    private final Gson gson = new Gson();

    /**
     * Unterstützt:
     *  A) Root ist Array: [ {...}, {...} ]
     *  B) Root ist Objekt mit "movies": { "movies": [ {...} ] }
     */
    public List<Movie> load(String filePath) {
        try {
            String json = Files.readString(Path.of(filePath));
            JsonElement root = JsonParser.parseReader(new StringReader(json));

            JsonArray arr;
            if (root.isJsonArray()) {
                arr = root.getAsJsonArray();
            } else if (root.isJsonObject() && root.getAsJsonObject().has("movies")) {
                arr = root.getAsJsonObject().getAsJsonArray("movies");
            } else {
                throw new IllegalArgumentException("Unbekanntes JSON-Format (Array oder Objekt mit 'movies' erwartet).");
            }

            List<Movie> movies = new ArrayList<>();
            for (JsonElement el : arr) {
                if (!el.isJsonObject()) continue;
                JsonObject o = el.getAsJsonObject();

                String title = getString(o, "title", "name");
                int year = getYear(o);
                int runtime = getInt(o, 0, "runtime", "runtimeMinutes", "duration");
                Genre genre = mapGenre(o);

                movies.add(new Movie(title, year, runtime, genre));
            }
            return movies;

        } catch (Exception e) {
            throw new RuntimeException("Konnte JSON nicht laden: " + filePath, e);
        }
    }

    private String getString(JsonObject o, String... keys) {
        for (String k : keys) {
            if (o.has(k) && !o.get(k).isJsonNull()) return o.get(k).getAsString();
        }
        return "(ohne Titel)";
    }

    private int getInt(JsonObject o, int def, String... keys) {
        for (String k : keys) {
            if (o.has(k) && !o.get(k).isJsonNull()) {
                try { return o.get(k).getAsInt(); } catch (Exception ignored) {}
                try { return Integer.parseInt(o.get(k).getAsString()); } catch (Exception ignored) {}
            }
        }
        return def;
    }

    private int getYear(JsonObject o) {
        // year direkt
        int y = getInt(o, 0, "year");
        if (y != 0) return y;

        // release_date -> yyyy-mm-dd
        String rd = getString(o, "release_date", "released");
        if (rd != null && rd.length() >= 4) {
            try { return Integer.parseInt(rd.substring(0, 4)); } catch (Exception ignored) {}
        }
        return 0;
    }

    private Genre mapGenre(JsonObject o) {
        // Viele JSONs haben genres als Array ["Drama","Action"] oder als String "Drama"
        String g = null;

        if (o.has("genres") && o.get("genres").isJsonArray()) {
            JsonArray ga = o.getAsJsonArray("genres");
            if (!ga.isEmpty() && !ga.get(0).isJsonNull()) g = ga.get(0).getAsString();
        } else if (o.has("genre") && !o.get("genre").isJsonNull()) {
            g = o.get("genre").getAsString();
        }

        if (g == null) return Genre.DRAMA; // fallback (oder mach Genre.OTHER)

        g = g.trim().toUpperCase();
        // einfache Mapping-Regeln
        if (g.contains("SCI")) return Genre.SCIFI;
        if (g.contains("ACTION")) return Genre.ACTION;
        if (g.contains("COMED")) return Genre.COMEDY;
        if (g.contains("HORROR")) return Genre.HORROR;
        if (g.contains("DRAMA")) return Genre.DRAMA;

        return Genre.DRAMA; // fallback
    }
}
