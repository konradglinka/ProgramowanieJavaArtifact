package AnotherClasses;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenFileHelper {
    public void openPdfFile(String path) throws IOException {
                File pdfFile = new File(path);
                if (pdfFile.exists()) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(pdfFile);
                    }
                }
        }
    }

