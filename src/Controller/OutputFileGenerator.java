package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface OutputFileGenerator {
    void saveToFile(ArrayList<String> data, Path path) throws IOException;
}
