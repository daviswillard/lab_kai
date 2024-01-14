package ru.kulikova;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

  private File file;
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

  public Logger(String filePath) {
    file = new File(filePath);
    checkOnExistsWithCreate();
  }

  public void log(String text) {
    checkOnExistsWithCreate();
    try (FileWriter writer = new FileWriter(file.getPath(), true)) {
      writer.append(
          String.format("%s: %s %s", dateFormat.format(new Date()), text, System.lineSeparator()));
      writer.flush();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void checkOnExistsWithCreate() {
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}