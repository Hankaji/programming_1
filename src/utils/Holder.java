package utils;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Holder<T> implements Serializable {

    List<T> list;

    public Holder() {
        list = new ArrayList<>();
    }

    public Holder(ArrayList<T> list) {
        this.list = list;
    }

    public void populateList(ArrayList<T> list) {
        this.list = list;
    }

    public void saveList(String fileName) {
        String path = "../../data/" + fileName;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            System.out.println("Object serialized and saved to" + fileName + " successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
