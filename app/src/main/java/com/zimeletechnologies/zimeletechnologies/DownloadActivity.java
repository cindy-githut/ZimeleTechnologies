package com.zimeletechnologies.zimeletechnologies;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;

public class DownloadActivity extends AppCompatActivity {

    private BroadcastReceiver mDLCompleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        setToolbar();

        final String url = getString(R.string.image_url);

        final String pdfUrl = getString(R.string.pdf_url);


        findViewById(R.id.btnImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                download(url, "image_test");

            }
        });

        findViewById(R.id.btnPDF).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                download(pdfUrl, "pdf_test");

            }
        });

    }
    private void setToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setTitle("Download");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
    private void download(final String url, String dirType){

        setContentView(R.layout.image_load);

        final ImageView imageDownloaded = (ImageView) findViewById(R.id.imgResult);

        DownloadManager.Request request = null;

        try {
            request = new DownloadManager.Request(Uri.parse(url));
        } catch (IllegalArgumentException e) {

        }
                /* allow mobile and WiFi downloads */
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Zemele Technologies");
        request.setDescription("Downloading file");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        final String suffix = url.substring(url.lastIndexOf(".") + 1).toLowerCase();

                /* set the destination path for this download */
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS +
                File.separator + dirType,  "zemeletechnologies." + suffix);

        final DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        final long DL_ID = dm.enqueue(request);

                /* get notified when the download is complete */
        mDLCompleteReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                        /* our download */
                if (DL_ID == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)) {

                            /* get the path of the downloaded file */
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(DL_ID);
                    Cursor cursor = dm.query(query);
                    if (!cursor.moveToFirst()) {
                        return;
                    }

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            != DownloadManager.STATUS_SUCCESSFUL) {
                        return;
                    }

                    Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
                    String path = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                    if(suffix.equals("pdf")){

                        Uri file= Uri.parse(path);
                        String mimeType =  MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.toString()));

                        try{
                            Intent i;
                            i = new Intent(Intent.ACTION_VIEW);
                            i.setDataAndType(file,mimeType);
                            startActivity(i);
                            finish();

                        }catch (ActivityNotFoundException e) {
                            Toast.makeText(DownloadActivity.this,
                                    "No Application Available to view this file type",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        String paths = path.replace("file://","");
                        File image = new File(paths);
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                        imageDownloaded.setImageBitmap(bitmap);

                    }
                }
            }
        };

        registerReceiver(mDLCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDLCompleteReceiver != null)
            unregisterReceiver(mDLCompleteReceiver);
    }
}
