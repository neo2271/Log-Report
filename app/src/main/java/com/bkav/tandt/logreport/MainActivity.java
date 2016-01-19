package com.bkav.tandt.logreport;

import android.app.Activity;
import android.os.Bundle;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static String FILE_NAME = "diag.cfg";
    public static String PATH_DIAG_FILE = "/sdcard/diag_logs";

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
                outputStart = "\n\nLast log: " + txtCfg + "\n\n***** diag_mdlog START *****\n" + outputStart;

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

        File dir = new File(PATH_DIAG_FILE);

        if (dir.mkdirs() || dir.isDirectory()) {

            try {
                copyRAWtoSDCard(idCfg, PATH_DIAG_FILE + File.separator + FILE_NAME);
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

}
