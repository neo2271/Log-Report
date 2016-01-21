package com.bkav.tandt.logreport;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static String DIAG_FILE_NAME = "diag.cfg";
    public static String DIAG_FILE_PATH = "/sdcard/diag_logs";

    public String[] mFileList;
    //    private File mPath = new File(Environment.getExternalStorageDirectory() + "//yourdir//");
    public File mPath = new File(Environment.getExternalStorageDirectory() + "sdcard");
    public String mChosenFile;
    public static final String FTYPE = ".cfg";
    public static final int DIALOG_LOAD_FILE = 1000;
    public static final int PICKFILE_RESULT_CODE = 1;

    public static Button btnStart;
    public static Button btnStop;
    public static TextView tvOut;
    public static TextView tvTimerLabel, tvTimer, tvTimerMs;
    public static Spinner spMask;
    public static String output;
    public static int idRawCfg;
    public static String txtCfg;
    public Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        // Textview can scroll
        tvOut = (TextView) findViewById(R.id.out);
        tvOut.setMovementMethod(new ScrollingMovementMethod());

        // Timer text
        tvTimerLabel = (TextView) findViewById(R.id.tvTimerLabel);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvTimerMs = (TextView) findViewById(R.id.tvTimerMs);

        spMask = (Spinner) findViewById(R.id.spMask);

        // Add items into spinner dynamically
        addItemSpinner();

        // Spinner chose
        addListenerOnSpinnerItemSelection();

        // Initialize View
        initView();

        // Button Start clicked
        clickStartBtn();

        // Button Stop clicked
        clickStopBtn();
    }

    public void addItemSpinner() {
        List<String> list = new ArrayList<String>();

        list.add("modem_1xevdo_and_intefacing_modules");
        list.add("modem_1xevdo");
        list.add("modem_cgd_test");
        list.add("modem_data_and_intefacing_modules");
        list.add("modem_data_call");
        list.add("modem_data");
        list.add("modem_data_tput");
        list.add("modem_generic");
        list.add("modem_geran_and_intefacing_modules");
        list.add("modem_geran");
        list.add("modem_ims__and_intefacing_modules");
        list.add("modem_ims");
        list.add("modem_ims_test");
        list.add("modem_irat");
        list.add("modem_lte_as_and_intefacing_modules");
        list.add("modem_lte_as");
        list.add("modem_mmode_and_interfacing_modules");
        list.add("modem_mmode");
        list.add("modem_nas_and_interfacing_modules");
        list.add("modem_nas");
        list.add("modem_power_on");
        list.add("modem_qmi_ril_and_interfacing_modules");
        list.add("modem_qmi_ril");
        list.add("modem_rf_and_interfacing_modules");
        list.add("modem_rf");
        list.add("modem_uim_and_interfacing_modules");
        list.add("modem_uim");
        list.add("modem_umts_as_and_interfacing_modules");
        list.add("modem_umts_as");
        list.add("modem_voice_call");
        list.add("Select the required configuration file");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMask.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spMask = (Spinner) findViewById(R.id.spMask);
        spMask.setOnItemSelectedListener(this);
    }

    public void initView() {

        // Initialize btnStart, btnStop
        btnStop.setEnabled(false);
        btnStart.setEnabled(true);

        // Initialize text Timer
        tvTimerLabel.setText(null);
        tvTimer.setText(null);
        tvTimerMs.setText(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        txtCfg = parent.getItemAtPosition(position).toString();

        switch (txtCfg) {
            case "modem_1xevdo_and_intefacing_modules":
                idRawCfg = R.raw.modem_1xevdo_and_intefacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_1xevdo":
                idRawCfg = R.raw.modem_1xevdo;
                configureConfigFile(idRawCfg);
                break;
            case "modem_cgd_test":
                idRawCfg = R.raw.modem_cgd_test;
                configureConfigFile(idRawCfg);
                break;
            case "modem_data_and_intefacing_modules":
                idRawCfg = R.raw.modem_data_and_intefacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_data_call":
                idRawCfg = R.raw.modem_data_call;
                configureConfigFile(idRawCfg);
                break;
            case "modem_data":
                idRawCfg = R.raw.modem_data;
                configureConfigFile(idRawCfg);
                break;
            case "modem_data_tput":
                idRawCfg = R.raw.modem_data_tput;
                configureConfigFile(idRawCfg);
                break;
            case "modem_generic":
                idRawCfg = R.raw.modem_generic;
                configureConfigFile(idRawCfg);
                break;
            case "modem_geran_and_intefacing_modules":
                idRawCfg = R.raw.modem_geran_and_intefacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_geran":
                idRawCfg = R.raw.modem_geran;
                configureConfigFile(idRawCfg);
                break;
            case "modem_ims__and_intefacing_modules":
                idRawCfg = R.raw.modem_ims__and_intefacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_ims":
                idRawCfg = R.raw.modem_ims;
                configureConfigFile(idRawCfg);
                break;
            case "modem_ims_test":
                idRawCfg = R.raw.modem_ims_test;
                configureConfigFile(idRawCfg);
                break;
            case "modem_irat":
                idRawCfg = R.raw.modem_irat;
                configureConfigFile(idRawCfg);
                break;
            case "modem_lte_as_and_intefacing_modules":
                idRawCfg = R.raw.modem_lte_as_and_intefacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_lte_as":
                idRawCfg = R.raw.modem_lte_as;
                configureConfigFile(idRawCfg);
                break;
            case "modem_mmode_and_interfacing_modules":
                idRawCfg = R.raw.modem_mmode_and_interfacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_mmode":
                idRawCfg = R.raw.modem_mmode;
                configureConfigFile(idRawCfg);
                break;
            case "modem_nas_and_interfacing_modules":
                idRawCfg = R.raw.modem_nas_and_interfacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_nas":
                idRawCfg = R.raw.modem_nas;
                configureConfigFile(idRawCfg);
                break;
            case "modem_power_on":
                idRawCfg = R.raw.modem_power_on;
                configureConfigFile(idRawCfg);
                break;
            case "modem_qmi_ril_and_interfacing_modules":
                idRawCfg = R.raw.modem_qmi_ril_and_interfacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_qmi_ril":
                idRawCfg = R.raw.modem_qmi_ril;
                configureConfigFile(idRawCfg);
                break;
            case "modem_rf_and_interfacing_modules":
                idRawCfg = R.raw.modem_rf_and_interfacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_rf":
                idRawCfg = R.raw.modem_rf;
                configureConfigFile(idRawCfg);
                break;
            case "modem_uim_and_interfacing_modules":
                idRawCfg = R.raw.modem_uim_and_interfacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_uim":
                idRawCfg = R.raw.modem_uim;
                configureConfigFile(idRawCfg);
                break;
            case "modem_umts_as_and_interfacing_modules":
                idRawCfg = R.raw.modem_umts_as_and_interfacing_modules;
                configureConfigFile(idRawCfg);
                break;
            case "modem_umts_as":
                idRawCfg = R.raw.modem_umts_as;
                configureConfigFile(idRawCfg);
                break;
            case "modem_voice_call":
                idRawCfg = R.raw.modem_voice_call;
                configureConfigFile(idRawCfg);
                break;
            // Choose .cfg file
            case "Select the required configuration file":
                Toast.makeText(getApplicationContext(), "Please choose the required .cfg file", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
                break;
        }

        // Initialize timer
        timer.resetTimer();

//        Toast.makeText(getApplicationContext(), "The chosen configuration file: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void clickStartBtn() {
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                // Reset and start timer
                timer.resetTimer();
                timer.startTimer();

                // Disable spinner
                spMask.setEnabled(false);

                // Output string
                String outputStart = null;
                output = null;

                ShellExecuter exe = new ShellExecuter();

                // Execute ls -l /sdcard/diag_logs
                outputStart = outputStart + exe.Executer("ls -l /sdcard/diag_logs");
                outputStart = "\n\nLast configuration file: " + txtCfg + "\n\n***** diag_mdlog START *****\n" + outputStart;

                // Execute start diag_mdlog_start
                outputStart = outputStart + exe.Executer("start diag_mdlog_start");
                output = output + outputStart;
                tvOut.setText(output);

                // Enable btnStop
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Start logging...", Toast.LENGTH_SHORT).show();
                Log.d("Output", output);
            }
        });
    }

    public void clickStopBtn() {
        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                String time = "\nTime: " + timer.hours + ":" + timer.minutes + ":" + timer.seconds + "." + timer.milliseconds;

                // Stop timer
                timer.stopTimer();

                // Enable spinner
                spMask.setEnabled(true);

                // Output string
                String outputStop = null;

                ShellExecuter exe = new ShellExecuter();

                // Execute ls -l /sdcard/diag_logs
                outputStop = outputStop + exe.Executer("ls -l /sdcard/diag_logs");
                outputStop = "\n***** diag_mdlog STOP *****\n" + outputStop;

                // Execute start diag_mdlog_stop
                outputStop = outputStop + exe.Executer("start diag_mdlog_stop");
                output = output + outputStop + time;
                tvOut.setText(output);

                // Enable btnStart
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Stop logging...\nThe log file saved in /sdcard/diag_logs", Toast.LENGTH_SHORT).show();
                Log.d("Output", output);
            }
        });
    }

    public void configureConfigFile(int idCfg) {

        File dir = new File(DIAG_FILE_PATH);

        if (dir.mkdirs() || dir.isDirectory()) {

            try {
                copyRAWtoSDCard(idCfg, DIAG_FILE_PATH + File.separator + DIAG_FILE_NAME);
                Toast.makeText(getApplicationContext(), "The " + txtCfg + " file configured to /sdcard/diag_logs", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void copyRAWtoSDCard(int id, String path) throws IOException {
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String filePath = getPath(getApplicationContext(), data.getData());
                    String fileNameExtension = filePath.substring(filePath.lastIndexOf("/") + 1);

                    String filePathCmd = filePath.replace(" ", "\\ ");
                    filePathCmd = filePathCmd.replace("/storage/emulated/0/", "/sdcard/");

                    // Return .cfg file to config file message
                    txtCfg = filePathCmd;

                    String cmd = "cp " + filePath + " " + DIAG_FILE_PATH + "/" + DIAG_FILE_NAME;

                    // Check data output
//                    tvOut.setText("\n\ncmd: " + cmd + "\n\nfilePath: " + filePath + "\n\nfileNameExtension: " + fileNameExtension + "\n\nfilePathCmd: " + filePathCmd);

                    ShellExecuter exe = new ShellExecuter();
                    exe.Executer(cmd);
                    Toast.makeText(getApplicationContext(), "The " + fileNameExtension + " file configured to /sdcard/diag_logs", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}