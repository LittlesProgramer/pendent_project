package pakietWisielec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AllLeters extends JPanel {

    private TheWindowsFromPasswordCategory theWindowsFromPasswordCategory;
    private static String znakCharFromTheButton;
    private static char tab[] = new char[]{'¹','æ','ê','³','ñ','œ','ó','¿','Ÿ'};

    public AllLeters(TheWindowsFromPasswordCategory theWindowsFromPasswordCategory) {
        this.theWindowsFromPasswordCategory = theWindowsFromPasswordCategory;
        GridLayout layout = new GridLayout(5,9);
        this.setLayout(layout);

        //Dodanie przycisków z literkami do JPanel'u
        //znaki alfabetu
        for(char x = 'a' ; x <= 'z'; x++ ){
            JButton button = new JButton();
            Action action = new AkcjaKlawiatury(String.valueOf(x),String.valueOf(x),button);
            button.setAction(action);
            button.setFont(new Font("Arial",Font.PLAIN,38));
            this.add(button);

            InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            imap.put(KeyStroke.getKeyStroke("typed "+String.valueOf(x)),String.valueOf(x));

            ActionMap amap = this.getActionMap();
            amap.put(String.valueOf(x),action);
        }
        //polskie znaki alfabetu
        for(char el = 0 ; el < tab.length ; el++){
            JButton button = new JButton();
            Action action = new AkcjaKlawiatury(String.valueOf(tab[el]),String.valueOf(tab[el]),button);
            button.setAction(action);
            button.setFont(new Font("Arial",Font.PLAIN,38));
            this.add(button);

            InputMap imap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            imap.put(KeyStroke.getKeyStroke("typed "+String.valueOf(tab[el])),tab[el]);

            ActionMap amap = this.getActionMap();
            amap.put(tab[el],action);//tab[el]
        }
        //cyfry
        for(int x = 0 ; x < 10 ; x++){
            JButton button = new JButton(String.valueOf(x));
            button.setFont(new Font("Arial",Font.PLAIN,38));

            this.add(button);
            button.addActionListener((e)->{
                znakCharFromTheButton = e.getActionCommand();
                theWindowsFromPasswordCategory.checkSignAndPrintItInWindow(znakCharFromTheButton);
                button.setEnabled(false);
            });
        }
    }

    //Metoda pobieraj¹ca aktualnie klikniête literki
    static public String giveTheButtonChar(){
        return znakCharFromTheButton;
    }

    //This is helping function for testing the method giveTheButtonChar()
    public void helpFunctionAllLeters(JPanel panel) {
        JButton button = new JButton(String.valueOf('A'));
        znakCharFromTheButton = button.getText();
        panel.add(button);
    }

    class AkcjaKlawiatury extends AbstractAction{
        public AkcjaKlawiatury(String name,String znakCharFromTheButton,JButton button) {
            putValue(Action.NAME,name);
            putValue("znakZPrzycisku",znakCharFromTheButton);
            putValue("Button",button);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(((JButton)this.getValue("Button")).isEnabled()){
                theWindowsFromPasswordCategory.checkSignAndPrintItInWindow((String)this.getValue("znakZPrzycisku"));
                ((JButton)this.getValue("Button")).setEnabled(false);
            }
        }
    }
}


