package komponenty;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Komponent_komunikatora implements Runnable, ActionListener {

    private Socket gniazdo_klienta;
    private ObjectOutputStream wyjscie;
    private ObjectInputStream wejscie;
    private ramka panel = new ramka(); // panel okienka graficznego
    protected ArrayList<String> komunikaty = new ArrayList();
    String m1 = "", m = "", n = "";

    String info[];

    public Komponent_komunikatora(Socket gniazdo_klienta,
            ObjectInputStream wejscie, ObjectOutputStream wyjscie, String info[]) {
        this.gniazdo_klienta = gniazdo_klienta;
        this.wejscie = wejscie;
        this.wyjscie = wyjscie;
        this.info = info;
        //**@note: Frame
        panel.getNazwa().addActionListener(this);
        /*tworzenie okienka graficznego*/
        panel.GUI(info[0]);
        /**
         * 
         * note: nowykomunikator3
         */
    }

    public Komponent_komunikatora(Socket gniazdo_klienta,
            ObjectInputStream wejscie, ObjectOutputStream wyjscie, String info[], ArrayList<String> tablica) {
        this.gniazdo_klienta = gniazdo_klienta;
        this.wejscie = wejscie;
        this.wyjscie = wyjscie;
        this.info = info;
        //**@note: Frame
        panel.getNazwa().addActionListener(this);
        /*tworzenie okienka graficznego*/
        panel.GUI(info[0]);
        this.komunikaty = tablica;
        /**
         * 
         * note: nowyserwer3
         */
    }
    //wysy³anie wiadomoœæi - metoda wywo³ywana w metodzie actionPerformed panela

    public void wyslij(String lan) {
        m1 = lan;
        if (gniazdo_klienta != null) {
            try {
                wyjscie.writeObject(m1);
                dodaj(m1);
            } catch (IOException e) {
                System.out.println(info[1] + e);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object zrodlo = evt.getSource();
        if (zrodlo == panel.getNazwa()) {
            m1 = panel.getNazwa().getText();
            wyslij(m1);
        }
        panel.repaint();
    }
    
    public synchronized void dodaj(String nowy) {
        try {
            komunikaty.add(nowy);
            System.out.println(komunikaty.toString());
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    //odbior wiadomosci
    @Override
    public void run() {
        try {
            panel.wyswietl_wiadomosci(info[3]);
            while (true) {
                m = (String) wejscie.readObject();
                panel.wyswietl_wiadomosci(info[4] + m + "\n");
                if (m.equals("czesc")) {
                    Thread.sleep(3000);
                    m1 = "czesc";
                    wyjscie.writeObject(m1); //wysyla "czesc" po odbiorze "czesc"
                    break;      //i przerywa odbior- przechodzi do bloku zamykania polaczenia
                }

            }
            //zamykanie polaczenia
            wejscie.close();
            wyjscie.close();
            gniazdo_klienta.close();
            gniazdo_klienta = null;
            // panel.zamknij();
        } catch (Exception e) {
            System.out.println(info[2] + e);
        }
    }
}
