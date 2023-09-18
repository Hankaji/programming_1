package utils;

import java.io.*;
import java.util.*;

public class Holder<T> implements Serializable {

//    List<T> list;
    private final Map<String, T> map;

    public Holder() {
        this.map = new HashMap<>();
    }

    public Holder(Map<String, T> map) {
        this.map = map;
    }

    public void saveList(String filename) {
        // add path to filename and write object to file
        filename = "./src/data/" + filename;
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("List saved to \"" + filename + "\" successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving list to " + filename);
        }
    }

//    @SuppressWarnings("unchecked")
//    public void populateList(String filename) {
//        // add path to filename
//        filename = "./src/data/" + filename;
//        // write from file to object
//        try (FileInputStream fis = new FileInputStream(filename);
//             ObjectInputStream ois = new ObjectInputStream(fis)) {
//            Holder<T> holder = (Holder<T>) ois.readObject();
//            this.list = holder.getList();
//            System.out.println("List populated from \"" + filename + "\" successfully!");
//        } catch (IOException | ClassNotFoundException e) {
////            e.printStackTrace();
//            System.err.println("Error populating list from " + filename);
//        }
//    }

    @SuppressWarnings("unchecked")
    public static <T> Holder<T> fetchList(String filename) {
        // add path to filename
        filename = "./src/data/" + filename;
        // write from file to object
        try (FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Holder<T> holder = (Holder<T>) ois.readObject();
            System.out.println("List populated from \"" + filename + "\" successfully!");
            return holder;
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("\"" + filename + "\" not found! New file created");
        } catch (ClassNotFoundException e) {
            System.err.println("Error populating list from " + filename);
        }
        return new Holder<T>();
    }

    public Map<String, T> getMap() {
        return map;
    }

    public void removeFromMap(String name) {
        map.remove(name);
    }

    public void removeByValue(T valueToRemove) {

        // Remove the entry
        map.entrySet().removeIf(entry -> Objects.equals(valueToRemove, entry.getValue()));
    }

    public void addItem(String name,T item) {
        map.put(name,item);
    }

    public void printList() {
        System.out.println("Map:");
        for (Map.Entry<String, T> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

}
