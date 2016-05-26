package com.example.nuriapp.afterclean;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import org.apache.http.util.EncodingUtils;

import java.net.URISyntaxException;

//import kr.co.kcp.util.PackageState;


/**
 * Created by ejgo on 2016-05-24.
 */
public class PayDemoScriptXActivity extends Activity {
    public static final String   ACTIVITY_RESULT         = "ActivityResult";
    public static final int      PROGRESS_STAT_NOT_START = 1;
    public static final int      PROGRESS_STAT_IN        = 2;
    public static final int      PROGRESS_DONE           = 3;
    public static       String   CARD_CD                 = "";
    public static       String   QUOTA                   = "";
    public WebView mWebView;
    private final       Handler  handler                 = new Handler();
    public              int      m_nStat                 = PROGRESS_STAT_NOT_START;
    private final       String   SCHEME                  = "paysample://card_pay";
    /** Called when the activity is first created. */
    @Override
    //@SuppressLint("SetJavaScriptEnabled") /*버전에 따라 추가.*/
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(SampleApplication.m_strLogTag, "[PayDemoActivity] called__onCreate");

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_paydemo);

        String url      = "http://testpay.kcp.co.kr/shop/kcpApp/ax_hub_linux_jsp_test/mobile_sample/order_mobile_card_app.jsp";
        //String url      = "http://172.16.73.51/smart_phone_linux_jsp/sample/card/order_card_1.jsp";
        String postData = "AppUrl=" + SCHEME;

        mWebView = (WebView)findViewById( R.id.webview );

        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.getSettings().setJavaScriptEnabled( true );

        mWebView.setWebChromeClient( new WebChromeClient() );
        mWebView.setWebViewClient  ( new mWebViewClient()  );

     //   mWebView.postUrl( url, EncodingUtils.getBytes(postData, "BASE64") );
    }

    private boolean url_scheme_intent( WebView view, String url )
    {
        Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] called__test - url=[" + url + "]" );

        //chrome 버젼 방식 : 2014.01 추가
        if ( url.startsWith( "intent" ) )
        {
            //ILK 용
            if( false /*url.contains( "com.lotte.lottesmartpay" ) */ )
            {
                try{
                    startActivity( Intent.parseUri(url, Intent.URI_INTENT_SCHEME) );
                } catch ( URISyntaxException e ) {
                    Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] URISyntaxException=[" + e.getMessage() + "]" );
                    return false;
                } catch ( ActivityNotFoundException e ) {
                    Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] ActivityNotFoundException=[" + e.getMessage() + "]" );
                    return false;
                }
            }
//            //ILK 용 -- 삭제 처리
//            else if ( url.contains( "com.ahnlab.v3mobileplus" ) )
//            {
//                try {
//                    view.getContext().startActivity(Intent.parseUri(url, 0));
//                } catch ( URISyntaxException        e ) {
//                    Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] URISyntaxException=[" + e.getMessage() + "]" );
//                    return false;
//                } catch ( ActivityNotFoundException e ) {
//                    Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] ActivityNotFoundException=[" + e.getMessage() + "]" );
//                    return false;
//                }
//            }
            //폴라리스 용
            else
            {
                Intent intent = null;

                try {
                    intent = Intent.parseUri( url, Intent.URI_INTENT_SCHEME );
                } catch ( URISyntaxException ex ) {
                    Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] URISyntaxException=[" + ex.getMessage() + "]" );
                    return false;
                }

                // 앱설치 체크를 합니다.
                if ( getPackageManager().resolveActivity( intent, 0 ) == null )
                {
                    String packagename = intent.getPackage();

                    if ( packagename != null )
                    {
                        startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:" + packagename) ) );

                        return true;
                    }
                }

                intent = new Intent( Intent.ACTION_VIEW, Uri.parse( intent.getDataString() ) );

                try{
                    startActivity( intent );
                }catch( ActivityNotFoundException e ) {
                    Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] ActivityNotFoundException=[" + e.getMessage() + "]" );
                    return false;
                }
            }
        }
        // 기존 방식
    /*    else
        {
            if ( url.startsWith( "ispmobile" ) )
            {
                if( !new PackageState( this ).getPackageDownloadInstallState( "kvp.jjy.MispAndroid" ) )
                {
                    startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse( "market://details?id=kvp.jjy.MispAndroid320" ) ) );

                    return true;
                }
            }
            else if ( url.startsWith( "paypin" ) )
            {
                if( !new PackageState( this ).getPackageDownloadInstallState( "com.skp.android.paypin" ) )
                {
                    if( !url_scheme_intent( view, "tstore://PRODUCT_VIEW/0000284061/0" ) )
                    {
                        url_scheme_intent( view, "market://details?id=com.skp.android.paypin&feature=search_result#?t=W251bGwsMSwxLDEsImNvbS5za3AuYW5kcm9pZC5wYXlwaW4iXQ.k" );
                    }

                    return true;
                }
            }

            // 삼성과 같은 경우 어플이 없을 경우 마켓으로 이동 할수 있도록 넣은 샘플 입니다.
            // 실제 구현시 업체 구현 여부에 따라 삭제 처리 하시는것이 좋습니다.
            if ( url.startsWith( "mpocket.online.ansimclick" ) )
            {
                if( !new PackageState( this ).getPackageDownloadInstallState( "kr.co.samsungcard.mpocket" ) )
                {
                    Toast.makeText(this, "어플을 설치 후 다시 시도해 주세요.", Toast.LENGTH_LONG).show();

                    startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse( "market://details?id=kr.co.samsungcard.mpocket" ) ) );

                    return true;
                }
            }

            try
            {
                startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( url ) ) );
            }
            catch(Exception e)
            {
                // 어플이 설치 안되어 있을경우 오류 발생. 해당 부분은 업체에 맞게 구현
                Toast.makeText(this, "해당 어플을 설치해 주세요.", Toast.LENGTH_LONG).show();

                if( url.contains( "tstore://" ) )
                {
                    return false;
                }
            }
        }*/

        return true;
    }

    private class mWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading( WebView view, String url )
        {
            Log.d( SampleApplication.m_strLogTag, "[PayDemoActivity] called__shouldOverrideUrlLoading - url=[" + url + "]" );

            if (url != null && !url.equals("about:blank"))
            {
                if( url.startsWith("http://") || url.startsWith("https://"))
                {
                    if (url.contains("http://market.android.com")            ||
                            url.contains("http://m.ahnlab.com/kr/site/download") ||
                            url.endsWith(".apk")                                   )
                    {
                        return url_scheme_intent( view, url );
                    }
                    else
                    {
                        view.loadUrl( url );
                        return false;
                    }
                }
                else if(url.startsWith("mailto:"))
                {
                    return false;
                }
                else if(url.startsWith("tel:"))
                {
                    return false;
                }
                else
                {
                    return url_scheme_intent( view, url );
                }
            }

            return true;
        }
    }

    @Override
    protected void onRestart()
    {
        super.onResume();

        Log.d( SampleApplication.m_strLogTag,
                "[PayDemoActivity] called__onResume + INPROGRESS=[" + m_nStat + "]" );

        GlobalApplication myApp = (GlobalApplication)getApplication();

        if(myApp.m_uriResult != null)
        {
            if( (myApp.m_uriResult.getQueryParameter("isp_res_cd") == null? "":
                    myApp.m_uriResult.getQueryParameter("isp_res_cd")             ).equals("0000"))
            {
                Log.d( SampleApplication.m_strLogTag,
                        "[PayDemoActivity] ISP Result = 0000" );

                mWebView.loadUrl( "http://testpay.kcp.co.kr/lds/smart_phone_linux_jsp/sample/card/samrt_res.jsp?result=OK&a=" + myApp.m_uriResult.getQueryParameter("a") );
            }
            else
            {
                Log.d( SampleApplication.m_strLogTag,
                        "[PayDemoActivity] ISP Result = cancel" );
            }
        }

        if ( m_nStat == PROGRESS_STAT_IN )
        {
            checkFrom();

            myApp.b_type = false;
        }

        myApp.m_uriResult = null;
    }

    private void checkFrom()
    {
        try
        {
            SampleApplication myApp = (SampleApplication)getApplication();

            if ( myApp.m_uriResult != null )
            {
                m_nStat = PROGRESS_DONE;

                String strResultInfo = myApp.m_uriResult.getQueryParameter( "approval_key" );

                if ( strResultInfo == null || strResultInfo.length() <= 4 )  finishActivity( "ISP 결제 오류" );

                String  strResCD = strResultInfo.substring( strResultInfo.length() - 4 );

                Log.d(  SampleApplication.m_strLogTag,
                        "[PayDemoActivity] result=[" + strResultInfo + "]+" + "res_cd=[" + strResCD + "]" );

                if ( strResCD.equals( "0000" ) == true )
                {
                    String strApprovalKey = "";

                    strApprovalKey = strResultInfo.substring( 0, strResultInfo.length() - 4  );

                    Log.d(  SampleApplication.m_strLogTag,
                            "[PayDemoActivity] approval_key=[" + strApprovalKey + "]" );

                    mWebView.loadUrl( "https://devpggw.kcp.co.kr:8100/app.do?ActionResult=app&approval_key=" + strApprovalKey );
                    //mWebView.loadUrl( "https://smpay.kcp.co.kr/app.do?ActionResult=app&approval_key=" + strApprovalKey );
                }
                else if ( strResCD.equals( "3001" ) == true )
                {
                    finishActivity( "ISP 결제 사용자 취소" );
                }
                else
                {
                    finishActivity( "ISP 결제 기타 오류" );
                }
            }
        }
        catch ( Exception e )
        {
        }
        finally
        {
        }
    }

    @Override
    protected Dialog onCreateDialog( int id )
    {
        Log.d(  SampleApplication.m_strLogTag,
                "[PayDemoActivity] called__onCreateDialog - id=[" + id + "]" );

        super.onCreateDialog( id );

        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder( this );
        AlertDialog alertDlg;

        dlgBuilder.setTitle( "취소" );
        dlgBuilder.setMessage( "결제가 진행중입니다.\n취소하시겠습니까?" );
        dlgBuilder.setCancelable( false );
        dlgBuilder.setPositiveButton( "예", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                finishActivity( "사용자 취소" );
            }
        });

        dlgBuilder.setNegativeButton( "아니오", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        alertDlg = dlgBuilder.create();

        return  alertDlg;
    }

    public void finishActivity( String p_strFinishMsg )
    {
        Intent intent = new Intent();

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