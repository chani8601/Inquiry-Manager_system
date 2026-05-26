package communication;

import communication.data.InquiryManagerActions;
import communication.data.RequestComm;
import communication.data.Response;
import data.Complaint;
import data.Inquiry;
import data.Question;
import data.Request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InquiryManagerClient
{
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private Scanner scanner = new Scanner(System.in);

    public InquiryManagerClient(Socket socket)
    {
        this.socket = socket;

        try
        {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void runMenu(){
        while (true)
        {
            System.out.println("press 1-client, 2-Representative");

            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice)
            {
                case 1:

                    clientMenu();

                    break;

                case 2:

                    RepresentativeMenu();

                    break;

                default:

                    System.out.println("the number is not valid");
            }
        }
    }

    private void clientMenu()
    {
        while (true)
        {
            System.out.println(
                    "press 1-create inquiry, " +
                            "2-get count by month, " +
                            "3-get inquiry status," +
                            "4-cancel inquiry " +
                            "5-get current handled inquiries count"+
                            "0-exit"
            );

            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice)
            {
                case 1:

                    inquiryCreation();

                    break;

                case 2:

                    getInquiriesCountByMonth();

                    break;

                case 3:

                    getInquiryStatus();

                    break;
                case 4:
                    cancelInquiry();
                    break;
                case 5:
                    getCurrentHandledInquiriesCount();
                    break;
                case 0:

                    return;

                default:

                    System.out.println("the number is not valid");
            }
        }
    }

    private void getCurrentHandledInquiriesCount() {
        createCommunication(InquiryManagerActions.GET_CURRENT_HANDLED_INQUIRIES_COUNT);
    }

    private void cancelInquiry()
    {
        System.out.println("insert inquiry code to cancel");
        int code = scanner.nextInt();
        scanner.nextLine();
        createCommunication(InquiryManagerActions.CANCEL_INQUIRY, code);
    }

    private void RepresentativeMenu()
    {
        System.out.println(
                "press 1-login, " +
                        "2-logout, " +
                        "3-get all inquiries in queue"
        );

        int choice = scanner.nextInt();

        scanner.nextLine();

        switch (choice)
        {
            case 1:

                System.out.println("insert Representative id");

                int loginId = scanner.nextInt();


                RepresentativeLogin(loginId);

                break;

            case 2:

                System.out.println("insert Representative id");

                int logoutId = scanner.nextInt();

                RepresentativeLogout(logoutId);

                break;

            case 3:

                getAllInqueries();

                break;

            default:

                System.out.println("the number is not valid");
        }
    }

    private void RepresentativeLogin(int RepresentativeId)
    {
        createCommunication(
                InquiryManagerActions.Representative_LOGIN,
                RepresentativeId
        );
    }

    private void RepresentativeLogout(int RepresentativeId)
    {
        createCommunication(
                InquiryManagerActions.Representative_LOGOUT,
                RepresentativeId
        );
    }

    private void getInquiryStatus()
    {
        System.out.println("insert inquiry id to get it status");

        String id = scanner.nextLine();

        createCommunication(
                InquiryManagerActions.GET_INQUIRY_STATUS,
                id
        );
    }

    public void inquiryCreation()
    {
        System.out.println(
                "press 1-question, 2-request, 3-complaint 0-to exit"
        );

        int choice = scanner.nextInt();

        scanner.nextLine();

        while (choice != 0)
        {
            Inquiry currentInquiry = createOneInquiry(choice);

            if (currentInquiry != null)
            {
                createCommunication(
                        InquiryManagerActions.ADD_INQUIRY,
                        currentInquiry
                );
            }

            System.out.println(
                    "press 1-question, 2-request, 3-complaint 0-to exit"
            );

            choice = scanner.nextInt();

            scanner.nextLine();
        }
    }

    public Inquiry createOneInquiry(int choice)
    {
        Inquiry currentInquiry;

        System.out.println("insert description");

        String description = scanner.nextLine();

        switch (choice)
        {
            case 1:

                currentInquiry = new Question();

                ((Question) currentInquiry)
                        .fillData(description);

                break;

            case 2:

                currentInquiry = new Request();

                ((Request) currentInquiry)
                        .fillData(description);

                break;

            case 3:

                System.out.println("insert branch");

                String branch = scanner.nextLine();

                currentInquiry = new Complaint();

                ((Complaint) currentInquiry)
                        .fillData(description, branch);

                break;

            default:

                System.out.println("the choice is not valid");

                return null;
        }

        return currentInquiry;
    }

    private void getAllInqueries()
    {
        createCommunication(
                InquiryManagerActions.ALL_INQUIRY
        );
    }

    private void createCommunication(
            InquiryManagerActions action,
            Object... data
    )
    {
        try
        {
            RequestComm request =
                    new RequestComm(action, data);

            oos.writeObject(request);

            oos.flush();

            Response response =
                    (Response) ois.readObject();

            System.out.println(
                    "Status: " + response.getStatus()
            );

            System.out.println(
                    "Message: " + response.getMessage()
            );

            if (response.getResult() != null)
            {
                System.out.println(
                        "Result: " + response.getResult()
                );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getInquiriesCountByMonth()
    {
        try
        {
            System.out.println("Enter month:");

            int month = scanner.nextInt();

            scanner.nextLine();

            RequestComm request =
                    new RequestComm(
                            InquiryManagerActions.GET_INQUIRIES_COUNT_BY_MONTH,
                            month
                    );

            oos.writeObject(request);

            oos.flush();

            Response response =
                    (Response) ois.readObject();

            System.out.println(
                    "Status: " + response.getStatus()
            );

            System.out.println(
                    "Message: " + response.getMessage()
            );

            if (response.getResult() != null)
            {
                System.out.println(
                        "Result: " + response.getResult()
                );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}