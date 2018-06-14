package cl.pingon.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cl.pingon.movies.models.Movie;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "cl.pingon.movies.KEY.MOVIE_ID";
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.movieEt);
        Button save = findViewById(R.id.saveBtn);
        Button last = findViewById(R.id.lastBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if (name.trim().length() > 0) {
                    Movie movie = new Movie(name, false);
                    movie.save();
                    movies = getMovies();
                    editText.setText("");
                    Toast.makeText(MainActivity.this, "Película guardada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Escriba un nombre por favor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = movies.size();
                if (size > 0) {
                    Movie lastMovie = movies.get(size - 1);
                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                    intent.putExtra(MOVIE_ID, lastMovie.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No hay películas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        movies = getMovies();
    }

    private List<Movie> getMovies() {
        return Movie.find(Movie.class, "watched = 0");
    }
}
