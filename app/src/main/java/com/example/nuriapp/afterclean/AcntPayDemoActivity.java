package com.example.nuriapp.afterclean;

import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/**
 * Created by ejgo on 2016-05-24.
 */
public class AcntPayDemoActivity extends Activity
{
    public static final String  ACTIVITY_RESULT              = "ActivityResult";
    private       final         Handler             handler  = new Handler();
    public                      WebView             mWebView;

    private boolean bankpay_auth_flag = false;
    private String  bankpay_code      = "";
    private String  bankpay_value     = "";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d( SampleApplication.s_strLogTag, "[AcntPayDemoActivity] called__onCreate" );

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_paydemo);

        // TODO Auto-generated method stub
        mWebView = (WebView)findViewById( R.id.webview );

        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setJavaScriptEnabled( true );
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically( true );
        mWebView.getSettings().setSupportMultipleWindows(false);

        mWebView.addJavascriptInterface(new KCPPayBridge(), "KCPPayApp");

        mWebView.setWebViewClient( new mWebViewClient() );
        mWebView.setWebChromeClient( new WebChromeClient() );

        mWebView.loadUrl( "http://testpay.kcp.co.kr/shop/kcpApp/ax_hub_linux_jsp_test/mobile_sample/order_mobile_acnt_app.jsp?AppUrl=paysample://acnt_pay" );
        //mWebView.loadUrl( "http://172.16.73.51/smart_phone_linux_jsp/sample/acnt/order_acnt_app.jsp?AppUrl=paysample://acnt_pay" );
    }

    private class mWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading( WebView view, String url )
        {
            Log.d( SampleApplication.m_strLogTag,
                    "[AcntPayDemoActivity] called__shouldOverrideUrlLoading - url=[" + url + "]" );

            if (url != null && ( url.contains("market://")||url.contains("kftc-bankpay://") ) )
            {
                Intent intent = null;

                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                } catch (URISyntaxException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);

                try {
                    if (startActivityIfNeeded(intent, -1)) {
                        return true;
                    }
                } catch (ActivityNotFoundException ex) {
                }
                return false;
            }
            else if(url.contains("tel:"))
            {
                return false;
            }
            else
            {
                view.loadUrl( url );
                return false;
            }
        }
    }

    private class KCPPayBridge
    {
        public void launchAcnt( final String arg )
        {
            handler.post( new Runnable() {
                public void run()
                {
                    Log.d( SampleApplication.m_strLogTag, "[AcntPayDemoActivity] KCPPayBridge=[" + arg + "]" );
                    Intent intent = new Intent(Intent.ACTION_MAIN);

                    intent.setComponent(new ComponentName(
                            "com.kftc.bankpay.android",
                            "com.kftc.bankpay.android.activity.MainActivity"));

                    intent.putExtra("requestInfo", arg);

                    startActivityForResult(intent, 1);

                    bankpay_auth_flag = true;
                }
            });
        }
    }

    @Override
    protected void onResume()
    {
        super.onRestart();

        if(bankpay_auth_flag)
        {
            bankpay_auth_flag = false;

            checkFromACNT();
        }
    }

    private void checkFromACNT()
    {
        try
        {
            Log.d( SampleApplication.m_strLogTag,
                    "[AcntPayDemoActivity] called__onResume { bankpay_code=[" + bankpay_code + "], bankpay_value=[" + bankpay_value + "] }" );

            mWebView.loadUrl("javascript:KCP_App_script('"+bankpay_code+"','"+bankpay_value+"')" );
        }
        catch ( Exception e )
        {
            Log.d("msKim", e.getMessage());
        }
        finally
        {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d( SampleApplication.m_strLogTag, "[AcntPayDemoActivity] requestCode : " + requestCode);
        Log.d( SampleApplication.m_strLogTag, "[AcntPayDemoActivity] resultCode : " + resultCode);

        if(data != null)
        {
//		    Log.d( SampleApplication.m_strLogTag, "[AcntPayDemoActivity] dataX : " + data.toURI());

            Log.d( SampleApplication.m_strLogTag, "[AcntPayDemoActivity] dataC : " + data.getExtras().getString("bankpay_code"));
            Log.d( SampleApplication.m_strLogTag, "[AcntPayDemoActivity] dataV : " + data.getExtras().getString("bankpay_value"));

            bankpay_code  = data.getExtras().getString("bankpay_code");
            bankpay_value = data.getExtras().getString("bankpay_value");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected Dialog onCreateDialog( int id )
    {
        Log.d( SampleApplication.m_strLogTag,
                "[AcntPayDemoActivity] called__onCreateDialog - id=[" + id + "]" );

        super.onCreateDialog( id );

        AlertDialog.Builder	 dlgBuilder = new AlertDialog.Builder( this );
        AlertDialog			 alertDlg;

        dlgBuilder.setTitle( "취소" );
        dlgBuilder.setMessage( "결제가 진행중입니다.\n취소하시겠습니까?" );
        dlgBuilder.setCancelable( false );
        dlgBuilder.setPositiveButton( "예",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        dialog.dismiss();

                        finishActivity( "사용자 취소" );
                    }
                }
        );
        dlgBuilder.setNegativeButton( "아니오",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }
        );

        alertDlg = dlgBuilder.create();

        return  alertDlg;
    }

    public void finishActivity( String p_strFinishMsg )
    {
        Intent	intent = new Intent();

        if ( p_strFinishMsg != null )
        {
            intent.putExtra( ACTIVITY_RESULT, p_strFinishMsg );

            setResult( RESULT_OK, intent );
        }
        else
        {

            setResult( RESULT_CANCELED );
        }

        finish();
    }
}
