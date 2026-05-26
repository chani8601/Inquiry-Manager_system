package repository;

import java.io.*;

public class NextCodeValRepository {
    private final String  PATH="data/nextCodeVal";

    public synchronized int get() {
        File file = new File(PATH);
        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();

            if (line == null || line.isEmpty()) {
                return 0;
            }

            return Integer.parseInt(line);

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public synchronized void update(int nextCodeVal) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            bw.write(String.valueOf(nextCodeVal));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
