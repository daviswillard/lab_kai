package ru.kulikova;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Logger {

  private String location;

  public Logger(String location) {
    this.location = location;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String toString() {
    return "Logger(location=" + this.getLocation() + ")";
  }
}

class ServerInfo {

  private String address;
  private Integer port;

  public ServerInfo(String address, Integer port) {
    this.address = address;
    this.port = port;
  }

  public String getAddress() {
    return this.address;
  }

  public Integer getPort() {
    return this.port;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String toString() {
    return "ServerInfo(address=" + this.getAddress() + ", port=" + this.getPort() + ")";
  }
}

final class ServerLogger {

  private final String location;

  public ServerLogger(String location) {
    this.location = location;
  }

  public FileOutputStream getLogger() throws IOException {
    File file = new File(location);
    if (!file.exists()) {
      file.createNewFile();
    }
    return new FileOutputStream(location);
  }
}