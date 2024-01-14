package ru.kulikova.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ServerLogger {
  private final String location;

  public FileOutputStream getLogger() throws IOException {
    File file = new File(location);
    if (!file.exists()) {
      file.createNewFile();
    }
    return new FileOutputStream(location);
  }
}
