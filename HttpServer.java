/**
 * Created by Brenton on 3/7/2016.
 */
import java.io.*;
import java.net.*;
import java.util.*;
public class HttpServer extends Thread
{
    String Begin = "<HTML>" +
            "<TITLE> HTTP MULTIPLE CHOICE <TITLE>" +
            "<BODY>";

    String ending = "<h1> Test </h1></BODY>" +
            "</HTML>";

    Socket client = null;
    BufferedReader readIn = null;
    DataOutputStream outTo = null;
    public static void main(String[] args) throws Exception
    {
        ServerSocket socket = new ServerSocket(55555);
        System.out.println("Waiting for connection");
        while (true)
        {
            Socket connected = socket.accept();
            (new HttpServer(connected)).start();
        }
    }

    public HttpServer(Socket client)
    {
        this.client = client;
    }

    public void run()
    {
        try
        {
            System.out.println(client.getInetAddress() + " just connected");
            readIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outTo = new DataOutputStream(client.getOutputStream());
            String req = readIn.readLine();

            StringTokenizer tok = new StringTokenizer(req);
            String method = tok.nextToken();
            String query = tok.nextToken();
            StringBuffer response = new StringBuffer();
            response.append("<b> This is a multiple choice Test </b><BR>");
            System.out.println("The request is: ");
            while(readIn.ready())
            {
                response.append(req + "<BR>");
                System.out.println(req);
                req = readIn.readLine();
            }
            if(method.equals("GET"))
            {
                if(query.equals("/"))
                {
                    outTo.writeBytes("HTTP/1.1 200 OK" + "\r\n");
                    outTo.writeBytes("Server: Java HTTPServer");
                    outTo.writeBytes("Content-Type: text/html" + "\r\n");
                    String test = "<h1> test </h1>";
                    int len = test.length();
                    outTo.writeBytes("Content-length:" + len + "\r\n");
                    outTo.writeBytes("Connection: close\r\n");
                    outTo.writeBytes("\r\n");
                    outTo.writeBytes(test);
                   // outTo.close();
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Failed");
        }
    }
}
