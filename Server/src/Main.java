import communication.HandleClient;
import repository.*;
import service.InquiryManager;
import data.Representative;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int port = 8888;
        Scanner scanner = new Scanner(System.in);

        InquiryRepository dataRepository = new InquiryRepository(new File("data"));
        InquiryRepository archiveRepository = new InquiryRepository(new File("archive"));
        RepresentativeRepository repRepo = new RepresentativeRepository();
        NextCodeValRepository codeRepo = new NextCodeValRepository();

        InquiryManager manager =
                InquiryManager.getInstance(dataRepository, archiveRepository,codeRepo, repRepo);

        boolean isServerRunning = false;

        while (true) {
            System.out.println("Select mode:");
            System.out.println("1 - Server mode (clients)");
            System.out.println("2 - Admin mode (add/delete representatives)");

            int mode = scanner.nextInt();

            if (mode == 1) {
                if (isServerRunning) {
                    continue;
                }
                isServerRunning = true;
                new Thread(() -> {
                    try {
                        ServerSocket serverSocket = new ServerSocket(port);
                        System.out.println("Server started on port " + port);

                        while (true) {
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("Client connected: " +
                                    clientSocket.getRemoteSocketAddress());

                            HandleClient handler =
                                    new HandleClient(clientSocket, manager);
                            handler.start();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            else if (mode == 2) {
                boolean inAdminMode = true;
                while (inAdminMode) {
                    System.out.println("\nChoose action:");
                    System.out.println("1 - Add representative");
                    System.out.println("2 - Delete representative");
                    System.out.println("3 - Back to Main Menu (Select Mode)");

                    int choice = scanner.nextInt();

                    if (choice == 1) {
                        System.out.println("First name:");
                        String name = scanner.next();

                        System.out.println("ID:");
                        int id = scanner.nextInt();

                        Representative rep = new Representative(name, id);
                        manager.addRepresentative(rep);
                        System.out.println("Representative added");

                    } else if (choice == 2) {
                        System.out.println("Representative code:");
                        int code = scanner.nextInt();

                        boolean result = manager.deleteRepresentative(code);
                        System.out.println(result ? "Deleted successfully" : "Not found");

                    } else if (choice == 3) {
                        inAdminMode = false;
                    }
                }
            }
        }
    }
}