package ru.kulikova;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class App {
  static File serverLocation;
  static File loggerLocation;

  static {
    serverLocation = new File("client/src/main/resources/server.txt");
    loggerLocation = new File("client/src/main/resources/logger.txt");
  }

  public static void main(String[] args) {

    ServerInfo serverInfo;
    Logger logger;
    try (
        BufferedReader serverReader= new BufferedReader(new FileReader(serverLocation));
        BufferedReader loggerReader= new BufferedReader(new FileReader(loggerLocation))
    ) {
      String line;
      StringBuilder sb = new StringBuilder();
      String[] lines;
      while ((line = serverReader.readLine()) != null) {
        sb.append(line);
        sb.append('\n');
      }
      lines = sb.toString().split("\n");
      serverInfo = new ServerInfo(lines[0], Integer.valueOf(lines[1]));
      sb = new StringBuilder();
      while ((line = loggerReader.readLine()) != null) {
        sb.append(line);
        sb.append('\n');
      }
      lines = sb.toString().split("\n");
      logger = new Logger(lines[0]);
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
      return;
    }

    System.out.println(serverInfo.getAddress() + serverInfo.getPort() + logger.getLocation());

    ServerLogger serverLogger = new ServerLogger(logger.getLocation());
    FileOutputStream logStream;
    try {
      logStream = serverLogger.getLogger();
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
      return;
    }
    Thread client = new Thread(new Client(serverInfo, logStream));
    client.start();
  }
}
