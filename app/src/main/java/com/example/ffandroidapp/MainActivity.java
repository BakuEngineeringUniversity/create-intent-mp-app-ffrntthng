package com.example.ffandroidapp;
;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        TextView websiteLink = findViewById(R.id.websiteLink);
        Button callButton = findViewById(R.id.callButton);
        Button playMusicButton = findViewById(R.id.playMusicButton);

        // Website Link - Open browser with specified URL
        websiteLink.setOnClickListener(v -> {
            String url = "https://beu.edu.az/"; // Replace with your website URL
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        // Call Button - Verify PIN before making the call
        callButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter PIN");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String pin = input.getText().toString();
                if (pin.equals("1234")) { // Replace "1234" with your desired PIN
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+994703447921")); // Replace with your phone number
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid PIN!", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        // Play Music Button - Play a sample music file
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sample_music); // Replace with your audio file in res/raw folder
        playMusicButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                Toast.makeText(this, "Music Paused", Toast.LENGTH_SHORT).show();
            } else {
                mediaPlayer.start();
                Toast.makeText(this, "Music Playing", Toast.LENGTH_SHORT).show();
            }
        });

        // Release MediaPlayer resources when activity is destroyed
        this.addOnDestroyListener(mediaPlayer);
    }

    private void addOnDestroyListener(MediaPlayer mediaPlayer) {
        this.runOnUiThread(() -> mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
        }));
    }
}

