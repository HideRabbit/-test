package komponenty;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kruczkiewicz
 */
public class ramka extends JPanel {

    JTextField nazwa = new JTextField(20);
    JTextArea komentarz = new JTextArea(4, 21);
    JFrame okno;

    public void panel() {
        setSize(200, 150);
        JLabel etykieta_nazwy = new JLabel("Napisz");
        JLabel etykieta_komentarza = new JLabel("Rozmowa");
        komentarz.setLineWrap(true);
        komentarz.setWrapStyleWord(true);
        add(etykieta_nazwy);
        add(nazwa);
        add(etykieta_komentarza);
        add(komentarz);
        JScrollPane obszar_przewijany1 = new JScrollPane(komentarz,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(obszar_przewijany1);
    }

    public JTextField getNazwa() {
        return nazwa;
    }
 
    public void wyswietl_wiadomosci(String wiad)
    {
        komentarz.setText(komentarz.getText()+wiad);
    }

    public void GUI(String tytul) {
        okno = new JFrame(tytul);
        okno.setSize(300, 200);
        panel();
        okno.setContentPane(this);
        okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        okno.setVisible(true);
    }
    
    public void zamknij()
    {
        okno.dispose();
    }
}
