package com.example.androidcamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	final static int RESULT_IMAGE_CAPTURE = 0x0000;
	ImageView imageview = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageview = (ImageView)findViewById(R.id.imageview);
        
        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, RESULT_IMAGE_CAPTURE);
			}
		});
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	super.onActivityResult(requestCode, resultCode, intent);
    	
    	if(requestCode != RESULT_IMAGE_CAPTURE) return;
    	if(resultCode != RESULT_OK) return;
    	
    	Bundle extras = intent.getExtras();
    	Bitmap bitmap = (Bitmap)extras.get("data");
    	if(bitmap != null)
    	{
    		imageview.setImageBitmap(bitmap);
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    
}
