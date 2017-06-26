package com.example.hasher.contactfb;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentResolver;
import android.provider.ContactsContract;
import android.database.Cursor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private String read_test_img = "/storage/emulated/0/contactfb/test.png";
    private String write_test_img = "/storage/emulated/0/contactfb/test1.png";
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
//                proceedAfterPermission();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sayHello(View view) {
        TextView tv1 = (TextView)findViewById(R.id.textView2);

//        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
        ContentResolver cr = getContentResolver();
        Log.i("tanmay", "here");
        tv1.setText("Loading");
        try {
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            Log.i("tanmay", String.valueOf(cur.getCount()));
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.RawContacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    name = name.replaceAll(" ", "_").toLowerCase();
//                    Log.i("tanmay", name.toLowerCase());
                    File f = new File("/storage/emulated/0/contactfb/" + name + ".jpg");
                    if(f.exists()) {
                        Log.i("tanmay", "found file " + name);
//                        File imagefile = new File("/storage/emulated/0/contactfb/test.jpg");
                        int size = (int) f.length();
                        byte[] photo = new byte[size];
                        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(f));
                        buf.read(photo, 0, photo.length);
                        buf.close();
                        writeDisplayPhoto(getRawContactId(Integer.parseInt(id)), photo);
//                        break;
                    }

    //                Log.i("tanmay", id);
    //                if (cur.getInt(cur.getColumnIndex(
    //                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
    //                    Cursor pCur = cr.query(
    //                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
    //                            null,
    //                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
    //                            new String[]{id}, null);
    //                    while (pCur.moveToNext()) {
    //                        String phoneNo = pCur.getString(pCur.getColumnIndex(
    //                                ContactsContract.CommonDataKinds.Phone.NUMBER));
    //                        Toast.makeText(getApplicationContext(), "Name: " + name
    //                                + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
    //                    }
    //                    pCur.close();
    //                }
                }

    //            File imagefile = new File("/storage/emulated/0/contactfb/test.jpg");
    //            int size = (int) imagefile.length();
    //            byte[] photo = new byte[size];
    //            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(imagefile));
    //            buf.read(photo, 0, photo.length);
    //            buf.close();
    //            ArrayList<ContentProviderOperation> ops =
    //                    new ArrayList<ContentProviderOperation>();
    //            String selectPhone = ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "='"  +
    //                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?";
    //            String[] phoneArgs = new String[]{"5180", String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_WORK)};

    //            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
    //                    .withSelection(selectPhone, phoneArgs)
    //                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "0987654321")
    //                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, new String(photo, "UTF-8"))
    //                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
    //                    .build());
    //            ops.add(ContentProviderOperation
    //                    .newUpdate(
    //                            ContactsContract.Data.CONTENT_URI)
    //                    .withSelection(
    //                            ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?",
    //                            new String[] {
    //                                    "5180",
    //                                    ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
    //                            })
    //                    .withValue(ContactsContract.CommonDataKinds.Photo.DATA15, photo).build());
    ////
    //
    //            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    //            ops.add(ContentProviderOperation
    //                    .newInsert(ContactsContract.Data.CONTENT_URI)
    //                    .withValueBackReference(ContactsContract.Data.CONTACT_ID, 5180)
    //                    .withValue(
    //                            ContactsContract.Data.MIMETYPE,
    //                            ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
    //                    .withValue(ContactsContract.CommonDataKinds.Photo.DATA15, photo)
    //                    .build());
    //            ops.add(ContentProviderOperation
    //                    .newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
    //                    .withValueBackReference(ContactsContract.Data.CONTACT_ID,
    //                            5180)
    //                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
    //                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "jkh").build());

    //                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    //            setContactPhoto(cr, photo, 5180);

    //            int x = getRawContactId(5180);
    //            writeDisplayPhoto(x, photo);

    //            ContentValues values = new ContentValues();
    //            values.put(ContactsContract.Data.RAW_CONTACT_ID, "5180");
    //            values.put(ContactsContract.Data.IS_SUPER_PRIMARY, 1);
    ////            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photo);
    //            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE );
    //            values.put(MediaStore.Images.Media.DISPLAY_NAME, "test.png");
    ////            values.put(MediaStore.Images.Media.DATE_ADDED, currentTime);
    //            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
    //            values.put(MediaStore.Images.Media.ORIENTATION, 0);
    //            values.put(MediaStore.Images.Media.DATA, read_test_img);
    //            values.put(MediaStore.Images.Media.SIZE, size);
    //            cr.insert(ContactsContract.Data.CONTENT_URI, values);
    //            writeDisplayPhoto(5179, photo);
                Log.i("tanmay", "done");
                tv1.setText("Done");
            }
        } catch (Exception e) {
            Log.i("tanmay", e.getMessage());
        }

    }

    public void writeDisplayPhoto(long rawContactId, byte[] photo) {
        Uri rawContactPhotoUri = Uri.withAppendedPath(
                ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, rawContactId),
                ContactsContract.RawContacts.DisplayPhoto.CONTENT_DIRECTORY);
        Log.i("tanmay", ContactsContract.DisplayPhoto.DISPLAY_MAX_DIM);
        try {
            AssetFileDescriptor fd =
                    getContentResolver().openAssetFileDescriptor(rawContactPhotoUri, "rw");
            OutputStream os = fd.createOutputStream();
            os.write(photo);
            os.close();
            fd.close();
        } catch (IOException e) {
            // Handle error cases.
            Log.i("tanmay", e.getMessage());
            e.printStackTrace();
        }
    }

    public int getRawContactId(int contactId)
    {
        ContentResolver context = getContentResolver();
        String[] projection=new String[]{ContactsContract.RawContacts._ID};
        String selection=ContactsContract.RawContacts.CONTACT_ID+"=?";
        String[] selectionArgs=new String[]{String.valueOf(contactId)};
        Cursor c=context.query(ContactsContract.RawContacts.CONTENT_URI,projection,selection,selectionArgs , null);
        if (c.moveToFirst()) {
            int rawContactId=c.getInt(c.getColumnIndex(ContactsContract.RawContacts._ID));
            Log.i("tanmay","Contact Id: "+contactId+" Raw Contact Id: "+rawContactId);
            return rawContactId;
        }
//        int rawContactId=c.getInt(c.getColumnIndex(ContactsContract.RawContacts._ID));

        return -1;
    }
}
