package klient;


import komponenty.Komponent_komunikatora;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;



public class nowyklient3  {
    private ArrayList <String> komunikaty = new ArrayList<String >();

     void poczatek(int kport) {
        Socket gniazdo_klienta;
        ObjectInputStream wejscie;
        ObjectOutputStream wyjscie;
        try{
        String info[] = {"Klient",
            "Wyjatek serwera1 ", "Wyjatek serwera2 ",
            "Klient startuje na hoscie " + InetAddress.getLocalHost().getHostName() + "\n",
            "Odebrano wiadomosc od serwera: "};
        
            String s = InetAddress.getLocalHost().getHostName();
            gniazdo_klienta = new Socket(s, kport);
            wejscie = new ObjectInputStream(gniazdo_klienta.getInputStream());
            wyjscie = new ObjectOutputStream(gniazdo_klienta.getOutputStream());
            wyjscie.flush();
            Komponent_komunikatora klient = new Komponent_komunikatora(gniazdo_klienta, wejscie, wyjscie, info, komunikaty);
            Thread t = new Thread(klient);
            t.start();
        } catch (IOException e) {
        }     
    }

    public static void main(String args[]) throws Exception {   
        nowyklient3 k2 = new nowyklient3();
        k2.poczatek(15000);
    }
}
