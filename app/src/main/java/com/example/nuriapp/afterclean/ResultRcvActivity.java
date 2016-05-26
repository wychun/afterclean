package com.example.nuriapp.afterclean;

/**
 * Created by ejgo on 2016-05-24.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ResultRcvActivity extends Activity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d( SampleApplication.m_strLogTag, "[ResultRcvActivity] called__onCreate" );

        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        SampleApplication myApp    = (SampleApplication)getApplication();
        Intent            myIntent = getIntent();

        Log.d(  SampleApplication.m_strLogTag,
                "[ResultRcvActivity] launch_uri=[" + myIntent.getData().toString() + "]" );

        if ( myIntent.getData().getScheme().equals( "paysample" ) == true )
        {
            myApp.b_type      = true;
            myApp.m_uriResult = myIntent.getData();
        }
        else
        {
            myApp.m_uriResult = null;
        }

        finish();
    }
}