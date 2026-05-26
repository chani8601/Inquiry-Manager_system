package repository;

import data.Representative;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RepresentativeRepository {

    private static final String PATH =
            "data/representatives.bin";

    public Collection<Representative> readAll() {

        File file = new File(PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {

            return (List<Representative>) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveAll(Collection<Representative> representatives) {

        try {

            File parent = new File("data");

            if (!parent.exists()) {
                parent.mkdirs();
            }

            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(PATH))) {

                out.writeObject(new ArrayList<>(representatives));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}