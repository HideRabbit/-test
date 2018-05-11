package serwer;

// cd E:\Dydaktyka\d1\pai\gniazda\komunikator2\build\classes\

import komponenty.Komponent_komunikatora;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//ten program nalezy uruchomic jako pierwszy

public class nowyserwer3 implements Runnable {
    
    public static int sPort;
    private ServerSocket serwer;
    

    public nowyserwer3(int port_) {
        sPort = port_;
        try {
            serwer = new ServerSocket(sPort);
            System.out.println("Serwer startuje na hoscie " +
                    InetAddress.getLocalHost().getHostName());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        Socket gniazdo_klienta = null;
        ObjectOutputStream output;
        ObjectInputStream input;
        while (true) {
            try {
                gniazdo_klienta = serwer.accept(); //oczekiwanie na klienta
                if (gniazdo_klienta == null) {
                    System.out.println("Minal czas akceptacji");
                    continue;
                }
                output = new ObjectOutputStream(gniazdo_klienta.getOutputStream());
                output.flush();
                input = new ObjectInputStream(gniazdo_klienta.getInputStream());
                //tworzenie watku do obslugi klienta
                String info[]={"Serwer",
                    "Wyjatek serwera1 ", "Wyjatek serwera2 ",
                "Serwer startuje na hoscie " +InetAddress.getLocalHost().getHostName() + "\n",
                "Odebrano wiadomosc od klienta: "};
                Komponent_komunikatora komp_klienta =
                        new Komponent_komunikatora(gniazdo_klienta, input, output, info);
                Thread watek_komponentu_klienta = new Thread(komp_klienta);
                watek_komponentu_klienta.start();
            } catch (IOException e) {
                System.out.println("Nie mozna polaczyc sie z klientem " + e);
                System.exit(1);
            }// przerwanie pracy serwera nie jest zalecane w praktyce
        }
    }
 
    public static void main(String args[]) throws Exception {
        int Port = 15000;
        nowyserwer3 s2 = new nowyserwer3(Port);
        Thread t = new Thread(s2);
        t.start();
    }
}
