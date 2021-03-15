import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ClientHandler {
    private static final Pattern MESSAGE_PATTERN = Pattern.compile ("^/w (\\w+) (.+)", Pattern.MULTILINE);
    private static final String MESSAGE_SEND_PATTERN = "/w %s %s";


    private final Thread handleThread;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final ChatServer server;
    private final String username;
    private final Socket socket;

    public ClientHandler(String username, Socket socket, ChatServer server) throws IOException {
        this.username = username;
        this.socket = socket;
        this.server = server;
        this.inp = new DataInputStream (socket.getInputStream ());
        this.out = new DataOutputStream (socket.getOutputStream ());
        this.handleThread = new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread ().isInterrupted ()) {
                        String msg = inp.readUTF ();
                        System.out.printf ("Message from user %s: %s%n", username, msg);
                        Matcher matcher = MESSAGE_PATTERN.matcher (msg);
                        if (matcher.matches ()) {
                            String userTo = matcher.group (1);
                            String message = matcher.group (2);
                            server.sendMessage (userTo, username, message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace ();
                } finally {
                    System.out.printf ("Client %s disconnected%n", username);
                    try {
                        socket.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                    server.unsubscribeClient (ClientHandler.this);
                }
            }
        });
        handleThread.start ();
    }

    public void sendMessage(String userTo, String msg) throws IOException {
        out.writeUTF (String.format (MESSAGE_SEND_PATTERN, userTo, msg));
    }

    public String getUsername() {
        return username;
    }


    /**
     * 1. Разобраться с кодом.
     * 2. Добавить отключение неавторизованных пользователей по таймауту
     * (120 сек. ждем после подключения клиента.
     * Если он не авторизовался за это время, закрываем соединение).
     */


    // Первый вариант решения:
    public void run() {
        try {
            while (true) {
                server.sendMessagetoAllclients («новый клиент присоединился к чату»);
                break;
            }
            while ( try){
                //если от клиента не пришло смс
                if (inMessage.hasNext ()) {
                    String clientMessage = inMessage.nextLine ();
                }
                // клиент выходит из чата
                if (clientMessage.equalsIgnoreCase (“##session##end##)) {
                    break;
                }
                //останавливаем выполнение потока на 120 секунд
                Thread.sleep (120);
            }
        } catch (InterruptException ex) {
            ex.printStackTrace ();
        } finally {
            this.close ();

            //отправляем смс
            Public void sendMsg (String msg){

                try {
                    out.Message.printf (msg);
                    out.Message.flush ();
                } catch (Exception ex) {
                    ex.printStackTrace ();
                }
            }
            //клиент выходит из чата
            public void close () {
                //удаляем клиента
                server.removeClient (this);
                client_count—;
                server.sendMessageToAllClients (клиентов в чате = +clients_count);
            }


            // Второй вариант решения:
            Future<?> awaitingAuth = Executors.newSingleThreadExecutor ().submit (() -> {
                while (true) {
                    if (inMessage.hasNext ()) {
                        String clientMessage = inMessage.nextLine ();
                        if (isAuth (clientMessage)) {
                            return getLogin (clientMessage);
                        }
                    }

                }
                return null;
            });

            try {
                String login = awaitingAuth.get (120, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                //обработка таймаута авторизации
            }
        }
    }
}
