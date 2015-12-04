package com.pinpointer.andrew.androidcameraexample;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;



public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our preview and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }



    // Checking camera features
    // Once you obtain access to a camera, you can get further information
    // about its capabilities using the Camera.getParameters() method and
    // checking the returned Camera.Parameters object for supported capabilities.
    // When using API Level 9 or higher, use the Camera.getCameraInfo() to determine
    // if a camera is on the front or back of the device, and the orientation of the image.




        /*
    Accessing cameras
If you have determined that the device on which your application is running has a camera,
you must request to access it by getting an instance of Camera
(unless you are using an intent to access the camera).

To access the primary camera, use the Camera.open()
 method and be sure to catch any exceptions, as shown in the code below:
     */
    // Caution: Always check for exceptions when using Camera.open().
    // Failing to check for exceptions if the camera is in use or
    // does not exist will cause your application to be shut down by the system.
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open(); // attempt to get a Camera instance

        } catch (Exception e) {
            Log.d("Camera main activity",e.getMessage());
            // Camera is not available (in use or does not exist)
        }
        return camera;
    }

    // To make sure the camera is released when the activity has stopped.
    // Other applications on the user's phone can use the camera afterwards


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
}
