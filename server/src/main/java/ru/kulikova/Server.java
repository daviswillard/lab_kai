package ru.kulikova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private final String EXIT_MESSAGE = "exit";

  private Logger logger;
  private CalculatorService calculator;

  private int port;

  public Server(int port, String filePath) {
    this.port = port;
    logger = new Logger(filePath);
    calculator = new CalculatorService();
  }

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      print(String.format("%s:%d Сервер запущен и ждёт подключение...",
          InetAddress.getLocalHost().toString(), port));
      Socket socket = serverSocket.accept();
      print("Клиент подключен. Ожидаю математическое выражение...");

      while (true) {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String message = reader.readLine();

				if (message == EXIT_MESSAGE) {
					break;
				}

        logger.log(message);
        double answer = calculator.calculate(message);
        print(message + " = " + answer);

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(answer);
      }

    } catch (IOException ex) {
      System.err.println("Server exception: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  private void print(String text) {
    System.out.println(text);
  }

}
