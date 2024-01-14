package ru.kulikova.model;

import static ru.kulikova.utility.Constants.CHAR_BUFFER_SIZE;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class Client implements Runnable {

  private final Server server;
  private final FileOutputStream logger;
  private final Scanner scanner = new Scanner(System.in);
  private final char[] readBytes = new char[CHAR_BUFFER_SIZE];

  @Override
  public void run() {
    try (Socket socket = new Socket(server.getAddress(), server.getPort());
        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream())
      ) {
      while (true) {
        String message;
        message = scanner.nextLine();
        writer.write(message);
        int readResult = reader.read(readBytes, 0, CHAR_BUFFER_SIZE);
        if (readResult < 0) {
          break;
        }
        String result = new String(readBytes);
        log.info(result);
        logger.write(result.getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }
}
