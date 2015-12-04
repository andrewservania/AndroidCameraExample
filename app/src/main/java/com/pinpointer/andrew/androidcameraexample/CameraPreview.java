package com.pinpointer.andrew.androidcameraexample;


//Creating a preview class
//
//    For users to effectively take pictures or video, they must be able
// to see what the device camera sees. A camera preview class is a SurfaceView
// that can display the live image data coming from a camera, so users can frame
// and capture a picture or video.
//


import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Andrew on 12/4/2015.
 * Reference: http://developer.android.com/guide/topics/media/camera.html
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    // http://developer.android.com/reference/android/util/Log.html
    // Tip: A good convention is to declare TAG constant in your class

    private static final String TAG = "CameraViewClass";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    // The following example code demonstrates how to create a basic camera preview
    // class that can be included in a View layout. This class implements SurfaceHolder.
    // Callback in order to capture the callback events for creating and destroying the
    // view, which are needed for assigning the camera preview input.
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // install a SurfaceHolder. Callback so we get notified when the
        // underlying surface is created and destroyed

        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The surface has been created, now tell the camera where to draw the preview
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity
    }

}
