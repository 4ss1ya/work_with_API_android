package com.example.api_hw;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLat, editTextLon;
    private TextView textViewWeatherResult;
    private Button buttonFetchWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация элементов интерфейса
        editTextLat = findViewById(R.id.editTextLat);
        editTextLon = findViewById(R.id.editTextLon);
        textViewWeatherResult = findViewById(R.id.textViewWeatherResult);
        buttonFetchWeather = findViewById(R.id.buttonFetchWeather);

        // Настройка кнопки для получения данных о погоде
        buttonFetchWeather.setOnClickListener(v -> {
            String lat = editTextLat.getText().toString().trim();
            String lon = editTextLon.getText().toString().trim();
            String apiKey = "65b9ea4ac1a03377072964b0382e8267"; // Замените на ваш ключ API

            if (!lat.isEmpty() && !lon.isEmpty()) {
                fetchWeatherData(lat, lon, apiKey);
            } else {
                Toast.makeText(this, "Пожалуйста, введите широту и долготу", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWeatherData(String lat, String lon, String apiKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = service.getWeather(lat, lon, apiKey);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    String weatherDetails = "Температура: " + weather.getTemp() +
                            "\nГород: " + weather.getName() +
                            "\nОписание: " + weather.getDescription() +
                            "\nСтрана: " + weather.getCountry();
                    textViewWeatherResult.setText(weatherDetails);
                } else {
                    textViewWeatherResult.setText("Не удалось получить данные о погоде");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                textViewWeatherResult.setText("Ошибка при запросе: " + t.getMessage());
            }
        });
    }
}
