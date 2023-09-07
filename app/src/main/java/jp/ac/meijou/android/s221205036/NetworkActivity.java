package jp.ac.meijou.android.s221205036;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Optional;

import jp.ac.meijou.android.s221205036.databinding.ActivityNetworkBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkActivity extends AppCompatActivity {

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();
    private final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class);
    private ActivityNetworkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetworkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // "https://placehold.jp/350x350.png"
        //getImage("https://placehold.jp/350x350.png");

        binding.buttonGet.setOnClickListener(view -> {
            var back_color = binding.editColor.getText();
            var color = binding.editColor2.getText();
            var img_width = binding.editWidth.getText();
            var img_height = binding.editHeight.getText();
            var text = binding.editTextText2.getText().toString();

            var url = "https://placehold.jp/" + back_color + "/" + color + "/" + img_width + "x"
                    + img_height + "/";

            url = Uri.parse(url)
                    .buildUpon()
                    .appendQueryParameter("text", text)
                    .build()
                    .toString();


            getImage(url);
        });
    }

    private void getImage(String url){
        var request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(() -> binding.imageView.setImageBitmap(bitmap));
            }
        });
    }
}