package communication;

import communication.data.*;
import data.Inquiry;
import service.InquiryManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandleClient extends Thread
{
    private Socket clientSocket;
    private InquiryManager inquiryManager;

    public HandleClient(Socket clientSocket, InquiryManager inquiryManager)
    {
        this.clientSocket = clientSocket;
        this.inquiryManager = inquiryManager;
    }

    @Override
    public void run()
    {
        try (
                ObjectOutputStream out =
                        new ObjectOutputStream(clientSocket.getOutputStream());

                ObjectInputStream in =
                        new ObjectInputStream(clientSocket.getInputStream())
        )
        {
            while (true)
            {
                RequestComm request =
                        (RequestComm) in.readObject();

                Object result =
                        handleActionRequest(request);

                Response response =
                        createResponse(result);

                out.writeObject(response);
                out.flush();
            }
        }
        catch (Exception e)
        {
            System.out.println("client disconnected: " +
                    e.getClass().getSimpleName());
        }
        finally
        {
            try
            {
                clientSocket.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private Object handleActionRequest(RequestComm request)
    {
        if (request == null || request.getAction() == null)
        {
            return null;
        }

        switch (request.getAction())
        {
            case ALL_INQUIRY:
                return inquiryManager.getAllInquiries();

            case ADD_INQUIRY:

            {
                Object[] data =(Object[]) request.getData();

                if (data == null || data.length == 0)
                {
                    return null;
                }

                Inquiry inquiry =
                        (Inquiry) data[0];

                return inquiryManager.addInquiry(inquiry);
            }

            case GET_INQUIRIES_COUNT_BY_MONTH:
            {
                Object[] data =
                        (Object[]) request.getData();

                if (data == null || data.length == 0)
                {
                    return null;
                }

                int month = (Integer) data[0];

                return inquiryManager.getInquiriesCountByMonth(month);
            }

            case Representative_LOGIN:
            {
                Object[] data = (Object[]) request.getData();

                if (data == null || data.length == 0)
                    return new Response(null, ResponseStatus.FAIL, "invalid request");

                int id = (int) data[0];

                boolean success = inquiryManager.loginRepresentative(id);

                if (success)
                    return new Response(true, ResponseStatus.SUCCESS, "Representative logged in");
                else
                    return new Response(null, ResponseStatus.FAIL, "Representative id not found");
            }

            case Representative_LOGOUT:
            {
                Object[] data = (Object[]) request.getData();

                if (data == null || data.length == 0)
                    return new Response(null, ResponseStatus.FAIL, "invalid request");

                int id =(int) data[0];

                boolean success = inquiryManager.logoutRepresentative(id);

                if (success)
                    return new Response(true, ResponseStatus.SUCCESS, "Representative logged out");
                else
                    return new Response(null, ResponseStatus.FAIL, "Representative id not found");
            }

            case CANCEL_INQUIRY:
            {
                Object[] data =
                        (Object[]) request.getData();

                if (data == null || data.length == 0)
                    return null;

                Object value = data[0];

                if (!(value instanceof Integer))
                {
                    System.out.println(
                            "ERROR: expected Integer but got " +
                                    (value == null ? "null" : value.getClass().getSimpleName())
                    );
                    return null;
                }

                int code = (Integer) value;

                return inquiryManager.cancelInquiry(code);
            }
            case GET_INQUIRY_STATUS: {
                Object[] statusData = (Object[]) request.getData();

                if (statusData == null || statusData.length == 0) {
                    return null;
                }

                String inquiryCode = String.valueOf(statusData[0]);

                return inquiryManager.getInquiryStatus(inquiryCode);
            }
            case GET_CURRENT_HANDLED_INQUIRIES_COUNT:
                return inquiryManager.currentHandledInquiriesCount.get();
            default:
                return null;
        }
    }

    private Response createResponse(Object result)
    {
        if (result == null)
        {
            return new Response(
                    null,
                    ResponseStatus.FAIL,
                    "operation failed"
            );
        }

        if (result instanceof Response)
        {
            return (Response) result;
        }
        if (result instanceof Boolean)
        {
            return ((Boolean) result)
                    ? new Response(true, ResponseStatus.SUCCESS, "ok")
                    : new Response(null, ResponseStatus.FAIL, "operation failed");
        }

        return new Response(
                result,
                ResponseStatus.SUCCESS,
                "ok"
        );
    }
}