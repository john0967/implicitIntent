package com.counttoast.implicitintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageview1;
    Button button, button1, button2;
    EditText edtt, edtt1, edtt2;
    private static final int CAMERA_REQUEST_CODE=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button2);
        button1=(Button)findViewById(R.id.button3);
        button2=(Button)findViewById(R.id.button4);
        edtt=(EditText)findViewById(R.id.edt1);
        edtt1=(EditText)findViewById(R.id.edt2);
        edtt2=(EditText)findViewById(R.id.edt3);
        imageview1=(ImageView)findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(edtt.getText().toString()));
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loc = edtt1.getText().toString();

                Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt = edtt2.getText().toString();
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from(MainActivity.this)
                        .setType(mimeType)
                        .setChooserTitle("hello")
                        .setText(txt)
                        .startChooser();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CAMERA_REQUEST_CODE){
            if (resultCode==RESULT_OK){
                Bitmap bitmap=(Bitmap) data.getExtras().get("data");
                imageview1.setImageBitmap(bitmap);
            }
        }
    }

}