package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Holder<T> implements Serializable {

    List<T> list;

    public Holder() {
        this.list = new ArrayList<>();
    }

    public Holder(ArrayList<T> list) {
        this.list = list;
    }

    public void saveList(String filename) {
        // add path to filename and write object to file
        filename = "./src/data/" + filename;
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("List saved to " + filename + " successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving list to " + filename);
        }
    }

    public void populateList(String filename) {
        // add path to filename
        filename = "./src/data/" + filename;
        // write from file to object
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Holder<T> holder = (Holder<T>) ois.readObject();
            this.list = holder.getList();
            System.out.println("List populated from " + filename + " successfully!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error populating list from " + filename);
        }
    }

    public List<T> getList() {
        return list;
    }

    public void removeFromList(T item) {
        list.remove(item);
    }

    public void addToList(T item) {
        list.add(item);
    }



}
