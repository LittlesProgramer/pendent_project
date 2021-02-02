package pakietWisielec;

import javax.swing.*;
import java.awt.*;

public class StartClass {
    public static void main(String[] args){
        Ramka ramka = new Ramka();
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setVisible(true);
        ramka.pack();
    }
}
