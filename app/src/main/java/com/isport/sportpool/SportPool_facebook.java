package com.isport.sportpool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by bom on 6/24/2015.
 */
public class SportPool_facebook extends Activity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private String shareTitle;
    private String shareDetail;
    private String shareURL;
    private String shareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Facebook for  sharing
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        if(getIntent().getExtras() != null)
		{
        	shareTitle =(String) getIntent().getExtras().getString("shareTitle");
        	shareDetail =(String) getIntent().getExtras().getString("shareDetail");
        	shareURL =(String) getIntent().getExtras().getString("shareURL");
        	shareImage = (String) getIntent().getExtras().getString("shareImage");

		}
        
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(shareTitle)
                    .setContentDescription(shareDetail)
                    .setContentUrl(Uri.parse(shareURL))
                    .setImageUrl(Uri.parse(shareImage))
                    .build();

            shareDialog.show(linkContent);
        }

        //ShareDialog.show(this, content);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int  resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //ShareDialog.show(this, content);
        finish();
    }

}
