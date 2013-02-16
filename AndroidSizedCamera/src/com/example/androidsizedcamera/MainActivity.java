package com.example.androidsizedcamera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	final static int RESULT_IMAGE_CAPTURE = 0x0000;
	ImageView imageview = null;
	String imageFilePath = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageview = (ImageView)findViewById(R.id.imageview);
        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try
				{
					//: 캡쳐한 이미지를 저장할 파일 
					imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mycapturedimage.jpg";
					File imageFile = new File(imageFilePath);
					Uri imageFileUri = Uri.fromFile(imageFile);
					
					Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
					startActivityForResult(intent, RESULT_IMAGE_CAPTURE);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	super.onActivityResult(requestCode, resultCode, intent);
    	
    	if(requestCode != RESULT_IMAGE_CAPTURE) return;
    	if(resultCode != RESULT_OK) return;
    	
    	//: 현재 장치 디스플레이의 너비와 높이를 조사한다.
    	Display currentDisplay = getWindowManager().getDefaultDisplay();
    	int dw = currentDisplay.getWidth();
    	int dh = currentDisplay.getHeight();
    	
    	//: 캡쳐된 이미지의 너비와 높이를 조사한다.
    	BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
    	bmpFactoryOptions.inJustDecodeBounds = true;
    	Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
    	
    	int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)dh);
    	int widthRatio  = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)dw);
    	Log.v("HEIGHT RATIO", "" + heightRatio);
    	Log.v("WIDTH RATIO", "" + widthRatio);
    	
    	//: 두 비율 다 1보다 크면 이미지의 가로, 세로 중 한쪽이 화면보다 크다.
    	if(heightRatio > 1 && widthRatio > 1)
    	{
    		if(heightRatio > widthRatio)
    		{
    			//: 높이 비율이 더 커서 그에 따라 맞춘다
    			bmpFactoryOptions.inSampleSize = heightRatio;
    		}
    		else
    		{
    			//: 너비 비율이 더 커서 그에 따라 맞춘다.
    			bmpFactoryOptions.inSampleSize = widthRatio;
    		}
    	}
    	
    	//: 실제로 디코딩한다.
    	bmpFactoryOptions.inJustDecodeBounds = false;
    	bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
    	
    	//: 이미지를 표시한다.
    	imageview.setImageBitmap(bmp);
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
