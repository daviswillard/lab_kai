package ru.kulikova;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements Runnable {

  private final ServerInfo serverInfo;
  private final FileOutputStream logger;
  private static final int CHAR_BUFFER_SIZE = 100;
  private final Scanner scanner = new Scanner(System.in);
  private final char[] readBytes = new char[CHAR_BUFFER_SIZE];

  public Client(ServerInfo serverInfo, FileOutputStream logger) {
    this.serverInfo = serverInfo;
    this.logger = logger;
  }

  @Override
  public void run() {
    try (
        Socket socket = new Socket(
            InetAddress.getByAddress(serverInfo.getAddress().getBytes()), serverInfo.getPort()
        );
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
        } else if (readResult == 0) {
          continue;
        }
        String result = new String(readBytes);
        System.out.println(result);
        logger.write(result.getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    }
  }
}
