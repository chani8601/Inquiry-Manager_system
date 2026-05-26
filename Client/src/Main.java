
import communication.InquiryManagerClient;

import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8888);

            System.out.println("Connected to server");

            InquiryManagerClient client = new InquiryManagerClient(socket);
            client.runMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}