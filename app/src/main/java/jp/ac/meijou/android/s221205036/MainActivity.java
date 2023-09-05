package jp.ac.meijou.android.s221205036;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import jp.ac.meijou.android.s221205036.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = PrefDataStore.getInstance(this);

        prefDataStore.getString("name")
                .ifPresent(name -> binding.textView.setText(name));

        binding.saveButton.setOnClickListener(view -> {
            var text = binding.editTextText.getText().toString();
            prefDataStore.setString("name",text);
        });

        binding.changeButton.setOnClickListener(view -> {
            var text = binding.editTextText.getText().toString();
            binding.textView.setText(text);
        });

        binding.resetButton.setOnClickListener(view -> {
            prefDataStore.setString("name", null);
        });

        Log.d("Mizuki", "onCreate text: " + binding.textView.getText());
    }

    @Override
    protected void onStart() {
        super.onStart();
        prefDataStore.getString("name")
                .ifPresent(name -> binding.textView.setText(name));
        Log.d("Mizuki", "onStart text: " + binding.textView.getText());
    }
}