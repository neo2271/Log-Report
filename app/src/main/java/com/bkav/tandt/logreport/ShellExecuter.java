package com.bkav.tandt.logreport;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by tandt on 1/18/16.
 */
public class ShellExecuter {
    public ShellExecuter() {

    }

    public String Executer(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);

            // Execute with SU permission
            // p = Runtime.getRuntime().exec(new String[] { "su", "-c", command
            // });
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));

            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Waits for the command to finish.

        return output.toString();
    }
}
