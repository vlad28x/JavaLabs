import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TCPServer {
    private static int PORT;
    private static String pathLog;
    private static Logger logger;
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        if(args.length == 1) {
            try {
                TCPServer tcpServer = new TCPServer();
                boolean cont = true;
                pathLog = args[0];
                File fileLog = new File(pathLog);
                if (!fileLog.exists()) {
                    try {
                        if(fileLog.createNewFile()) {
                            System.out.println("Создан файл - журнал клиента.");
                        }
                    } catch (IOException e) {
                        cont = false;
                        System.out.println("Системе не удается найти указанный путь до журнала.");
                    }
                }
                if(cont) {
                    logger = new Logger(pathLog);
                    tcpServer.go();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Укажите в командной строке путь до файла \"Журнал сервера\"");
        }
    }

    public TCPServer() throws IllegalArgumentException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите порт: ");
            while (true) {
                try {
                    PORT = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Введите целочисленное значение порта! ");
                }
            }
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Не удаётся открыть сокет для сервера: " + e.toString());
        }
    }

    public void go() {
        class Listener implements Runnable {
            Socket socket;

            public Listener(Socket socket) {
                this.socket = socket;
            }

            @Override
            public void run() {
                System.out.println("Слушатель запущен");
                try {
                    //Принимать сообщения от клиента
                    BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //Отправлять сообщения клиенту
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    //Ждем сообщение от клиента
                    String expression = in.readLine();
                    logger.println("Принято от клиента: " + expression);
                    try{
                        double sum = evaluate(expression);
                        if(Double.isInfinite(sum)) {
                            logger.println("Отправлено клиенту: При вычислении возникло переполнение.");
                            out.write("При вычислении возникло переполнение.\n");
                        }
                        else {
                            logger.println("Отправлено клиенту: " + sum);
                            out.write(sum + "\n");
                        }
                    } catch (NumberFormatException e) {
                        logger.println("Невозможно разобрать полученную строку.");
                        out.write("Невозможно разобрать полученную строку.\n");
                    }
                    out.flush(); // выталкиваем все из буфера
                    //Закрытие потоков
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            public double evaluate(String str) throws NumberFormatException {
                ArrayList<Double> numbersArray = new ArrayList<>();
                ArrayList<Character> signsArray = new ArrayList<>();
                boolean success = true;
                String regex = "\\+|-|\\*|\\/";
                String[] numbersString = str.split(regex);
                double[] numbers = new double[numbersString.length];
                for(int i = 0; i < numbers.length; ++i) {
                    numbers[i] = Double.parseDouble(numbersString[i]);
                }
                char[] signs = new char[numbers.length - 1];
                int j = 0;
                for(int i = 0; i < str.length(); ++i) {
                    if(Character.toString(str.charAt(i)).matches(regex)) {
                        signs[j] = str.charAt(i);
                        ++j;
                    }
                }
                for(int i = 0; i <= signs.length; ++i) {
                    if(i == signs.length) numbersArray.add(numbers[i]);
                    else if(signs[i] == '+') {
                        numbersArray.add(numbers[i]);
                        signsArray.add(signs[i]);
                    }
                    else if(signs[i] == '-') {
                        numbersArray.add(numbers[i]);
                        signsArray.add(signs[i]);
                    }
                    else if(signs[i] == '*' || signs[i] == '/') {
                        for(j = i + 1; j < signs.length; ++j) {
                            if(signs[j] == '+' || signs[j] == '-') break;
                        }
                        double result = numbers[i];
                        for(int k = i; k < j; ++k) {
                            if(signs[k] == '*') result *= numbers[k + 1];
                            else result /= numbers[k + 1];
                        }
                        numbersArray.add(result);
                        if(j != signs.length) signsArray.add(signs[j]);
                        i = j;
                    }
                }
                double summ = numbersArray.get(0);
                for (int i = 0; i < signsArray.size(); ++i) {
                    if(signsArray.get(i) == '+') summ += numbersArray.get(i + 1);
                    else summ -= numbersArray.get(i + 1);
                }
                return summ;
            }
        }
        System.out.println("Сервер запущен, порт: " + serverSocket.getLocalPort());
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Listener listener = new Listener(socket);
                Thread thread = new Thread(listener);
                thread.start();
            } catch (IOException e) {
                System.err.println("Исключение: " + e.toString());
            }

        }
    }
}
