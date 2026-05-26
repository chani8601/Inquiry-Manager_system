package repository;

import data.Inquiry;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InquiryRepository {

    private final File folder;

    public InquiryRepository(File folder) {
        this.folder = folder;
        folder.mkdir();
    }

    public Collection<Inquiry> readAll() {

        List<Inquiry> res = new ArrayList<>();

        if (!folder.exists()) {
            return res;
        }

        File[] subFolders = folder.listFiles();
        if (subFolders == null) return res;

        for (File subFolder : subFolders) {
            readSubFileInquiries(subFolder, res);
        }

        return res;
    }

    private static void readSubFileInquiries(File subFolder, List<Inquiry> res) {

        File[] files = subFolder.listFiles();
        if (files == null) return;

        for (File file : files) {
            readOneInquiry(file, res);
        }
    }

    private static void readOneInquiry(File file, List<Inquiry> res) {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {

            Inquiry inquiry = (Inquiry) in.readObject();
            res.add(inquiry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create(Inquiry inquiry) {

        try {
            File typeFolder = new File(folder, inquiry.getType().toLowerCase());
            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }

            File file = new File(typeFolder, inquiry.getCode() + ".bin");

            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(inquiry);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int code, String type) {
        File file = new File(folder, type.toLowerCase() + "/" + code + ".bin");
        return file.exists() && file.delete();
    }

    public int countByMonth(int month){
        if (folder == null || !folder.exists())
        {
            return 0;
        }

        return countFilesByMonth(folder, month);
    }

    private int countFilesByMonth(File mainFolder, int month){
        int count = 0;

        File[] subFolders = mainFolder.listFiles();

        if (subFolders == null)
        {
            return 0;
        }

        for (File subFolder : subFolders)
        {
            if (!subFolder.isDirectory())
            {
                continue;
            }

            count += countFilesInFolder(subFolder, month);
        }

        return count;
    }

    private int countFilesInFolder(File folder, int month){
        int count = 0;

        File[] files = folder.listFiles();

        if (files == null)
        {
            return 0;
        }

        for (File file : files)
        {
            if (!file.isFile())
            {
                continue;
            }

            LocalDate date = readDate(file);

            if (date != null && date.getMonthValue() == month)
            {
                count++;
            }
        }

        return count;
    }

    private boolean isSameMonth(LocalDate date, int month){
        return date != null &&
                date.getMonthValue() == month;
    }

    private LocalDate readDate(File file) {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {

            Inquiry inquiry = (Inquiry) in.readObject();

            return inquiry.getCreationDate().toLocalDate();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Inquiry findByCode(String inquiryCode) {
        File[] typeFolders = folder.listFiles(File::isDirectory);

        if (typeFolders == null) {
            return null;
        }

        for (File typeFolder : typeFolders) {

            File file = new File(typeFolder, inquiryCode + ".bin");

            if (file.exists() && file.isFile()) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                    return (Inquiry) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error reading inquiry file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}