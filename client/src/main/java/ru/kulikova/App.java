package ru.kulikova;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import ru.kulikova.model.Client;
import ru.kulikova.model.Server;
import ru.kulikova.model.Logger;
import ru.kulikova.utility.ServerLogger;

@Slf4j
public class App {
  static File serverLocation;
  static File loggerLocation;

  static {
    serverLocation = new File("client/src/main/resources/server.yaml");
    loggerLocation = new File("client/src/main/resources/logger.yaml");
  }

  public static void main(String[] args) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    mapper.findAndRegisterModules();

    Server server;
    Logger logger;
    try {
      server = mapper.readValue(serverLocation, Server.class);
      logger = mapper.readValue(loggerLocation, Logger.class);
    } catch (IOException ex) {
      log.error(ex.getMessage());
      return;
    }

    ServerLogger serverLogger = new ServerLogger(logger.getLocation());
    FileOutputStream logStream;
    try {
      logStream = serverLogger.getLogger();
    } catch (IOException ex) {
      log.error(ex.getMessage());
      return;
    }
    Thread client  = new Thread(new Client(server, logStream));
    client.start();
  }
}
