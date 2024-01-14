package ru.kulikova;

import java.util.*;

public class Main {

  public static void main(String[] args) { // port = 8080
    String serverFilePath = args[0];
    init(serverFilePath);
  }

  private static void init(String filePath) {
    Scanner in = new Scanner(System.in);
    System.out.println("Введите порт: ");
    int port = in.nextInt();
//    int port = 8080; // для дебага
    Server server = new Server(port, filePath);
    server.run();
  }
}