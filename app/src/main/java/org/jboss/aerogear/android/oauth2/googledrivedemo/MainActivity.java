/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.android.oauth2.googledrivedemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.saga.oauthtestsing.app.R;

import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.authorization.AuthzModule;
import org.jboss.aerogear.android.impl.authz.AuthzConfig;
import org.jboss.aerogear.android.impl.authz.oauth2.OAuth2AuthzModule;
import org.jboss.aerogear.android.impl.pipeline.PipeConfig;
import org.jboss.aerogear.android.pipeline.Pipe;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.jboss.aerogear.android.oauth2.googledrivedemo.Constants.AUTHZ_ACCOOUNT_ID;
import static org.jboss.aerogear.android.oauth2.googledrivedemo.Constants.AUTHZ_CLIENT_ID;
import static org.jboss.aerogear.android.oauth2.googledrivedemo.Constants.AUTHZ_CLIENT_SECRET;
import static org.jboss.aerogear.android.oauth2.googledrivedemo.Constants.AUTHZ_ENDPOINT;
import static org.jboss.aerogear.android.oauth2.googledrivedemo.Constants.AUTHZ_REDIRECT_URL;
import static org.jboss.aerogear.android.oauth2.googledrivedemo.Constants.AUTHZ_TOKEN_ENDPOINT;

public class MainActivity extends ActionBarActivity {

    private AuthzModule authzModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.authz();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(authzModule.isAuthorized()) {
            retrieveFiles();
        } else {
            authz();
        }
        return super.onOptionsItemSelected(item);
    }

    private void authz() {
        try {

            AuthzConfig authzConfig = new AuthzConfig(new URL(Constants.AUTHZ_URL), "restMod");
            authzConfig.setAuthzEndpoint(AUTHZ_ENDPOINT);
            authzConfig.setAccessTokenEndpoint(AUTHZ_TOKEN_ENDPOINT);
            authzConfig.setAccountId(AUTHZ_ACCOOUNT_ID);
            authzConfig.setClientId(AUTHZ_CLIENT_ID);
            authzConfig.setClientSecret(AUTHZ_CLIENT_SECRET);
            authzConfig.setRedirectURL(AUTHZ_REDIRECT_URL);
            authzConfig.getAdditionalAuthorizationParams().add(Pair.create("access_type", "offline"));
            authzConfig.getAdditionalAuthorizationParams().add(Pair.create("approval_prompt", "force"));
            authzConfig.setScopes(new ArrayList<String>() {{
                add("https://www.googleapis.com/auth/drive");
            }});

            final OAuth2AuthzModule module = new OAuth2AuthzModule(authzConfig);

            authzModule = new OAuth2AuthzModule(authzConfig);

            authzModule.requestAccess(this, new Callback<String>() {
                @Override
                public void onSuccess(String o) {
                    Log.d("TOKEN ++ ", o + "");
                    retrieveFiles();
                }


                @Override
                public void onFailure(Exception e) {
                    Log.e("MainActivity", e.getMessage(), e);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void retrieveFiles() {

        displayLoading();

        URL googleDriveURL = null;
        try {
            googleDriveURL = new URL("https://www.googleapis.com/drive/v2");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Pipeline pipeline = new Pipeline(googleDriveURL);

        PipeConfig pipeConfig = new PipeConfig(googleDriveURL, Files.class);
        pipeConfig.setAuthzModule(authzModule);
        pipeConfig.getResponseParser().getMarshallingConfig().setDataRoot("items");
        pipeline.pipe(Files.class, pipeConfig);

        Pipe<Files> documentsPipe = pipeline.get("files", this);
        documentsPipe.read(new Callback<List<Files>>() {
            @Override
            public void onSuccess(final List<Files> files) {
                Toast.makeText(getApplicationContext(), getString(R.string.fetched, files.size()), Toast.LENGTH_LONG).show();
                displayDriveFiles(files);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("EXCEPTION", e.getMessage(), e);
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void displayDriveFiles(List<Files> files) {
        DriveFragment driveFragment = DriveFragment.newInstance(files);
        displayFragment(driveFragment);
    }

    private void displayLoading() {
        LoadingFragment loadingFragment = new LoadingFragment();
        displayFragment(loadingFragment);
    }

}
