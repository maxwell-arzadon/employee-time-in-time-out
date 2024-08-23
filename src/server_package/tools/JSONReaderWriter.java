package server_package.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class JSONReaderWriter <T> {
    public JSONReaderWriter(){
    }

    public List<T> readJSONFile(String path, Type type) throws IOException {
        List<T> list;
        BufferedReader reader = new BufferedReader(new FileReader
                (path));
        Gson gson = new Gson();
        list = gson.fromJson(reader,type);
        reader.close();
        return list;
    }

    public void writeToJSON(String path, List<T>list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(list,writer);
        writer.close();
    }
}