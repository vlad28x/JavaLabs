import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private static int PORT;
    private static String HOST;
    private static String pathLog;
    private static Logger logger;

    public static void main(String[] args) {
        if(args.length == 2) {
            boolean cont = true;
            Scanner scanner = new Scanner(System.in);
            HOST = args[0];
            try {
                PORT = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Укажите целочисленное значение порта!");
            }
            System.out.println("Введите путь до файла \"Журнал клиента\":");
            pathLog = scanner.nextLine();
            File fileLog = new File(pathLog);
            if (!fileLog.exists()) {
                try {
                    if(fileLog.createNewFile()) {
                        System.out.println("Создан файл - журнал клиента.");
                    }
                } catch (IOException e) {
                    cont = false;
                    System.out.println("Системе не удается найти указанный путь.");
                }
            }
            logger = new Logger(pathLog);
            if(cont) {
                try {
                    Socket socket = new Socket(HOST, PORT);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    //Читать сообщения с сервера
                    BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //Отправлять сообщения на сервер
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    System.out.println("Введите арифметическое выражение вида «9+6*5-7.5/8»:");
                    String word = reader.readLine();
                    //Отправляем сообщение на сервер
                    out.write(word + "\n");
                    logger.println("Отправили на сервер: " + word);
                    //Выталкиваем все из буфера
                    out.flush();
                    //Ждем ответа от сервера
                    String receive = in.readLine();
                    logger.println("Приняли от сервера: " + receive);
                    //Закрытие потоков
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Не удается подключиться к серверу: " + e.toString());
                }
            }
        } else {
            System.out.println("Укажите в командной строке адрес и порт сервера.");
        }

    }
}
