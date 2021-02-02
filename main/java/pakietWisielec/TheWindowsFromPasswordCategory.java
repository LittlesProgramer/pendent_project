package pakietWisielec;
import javax.swing.*;
import java.awt.*;

public class TheWindowsFromPasswordCategory extends JComponent {

    private PaintWisielec paintWisielec = null;
    private String difficultGame = null;
    private int correct = 0;
    private int uncorrect = 0;
    private static String passwordText;
    private JLabel correctAnswer;
    private JLabel uncorrectAnswer;
    private int wygrana = 0;


    public TheWindowsFromPasswordCategory(String passwordText,JLabel correctAnswer,JLabel uncorrectAnswer,PaintWisielec paintWisielec,String difficultGame) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER)); //Layout ustawiony dla JComponent aby rysowa� na JComponent'cie

        this.difficultGame = difficultGame;
        this.paintWisielec = paintWisielec;
        this.passwordText = passwordText;
        this.correctAnswer = correctAnswer;
        this.uncorrectAnswer = uncorrectAnswer;
        createWindowsFileForText(passwordText);
    }

    private void createWindowsFileForText(String passwordCategory){
            for (int x = 0; x < passwordCategory.length(); x++) {

                JTextField field = new JTextField(2);
                if (passwordCategory.charAt(x) == ' ') {
                    this.add(field);
                    field.setEnabled(false);
                    field.setBorder(null);
                    field.setBackground(this.getBackground());
                    wygrana++;
                } else {
                    this.add(field).setVisible(true);
                }
            }
    }

    //Metoda sprawdzaj�ca zawieranie si� wybranej literki przez usera w ha�le, i je�li takowa/e istniej� wpisanie ich
    public void checkSignAndPrintItInWindow(String passwordChar){

            Component tabComponents[] = this.getComponents();

            //Petla rysuj�ca literki wewn�trz okienek je�li owe zawieraj� si� w wyrazie
            for (int x = 0; x < passwordText.length(); x++) {
                if (passwordText.charAt(x) == passwordChar.charAt(0)) {
                    ((JTextField) tabComponents[x]).setText(passwordChar);
                    wygrana++;
                }
            }

        //Warunek ustalaj�cy czy literka klikni�ta przez usera zawiera si� w napisie, a tym samym czy rysowa� wisielca czy nie
        if (passwordText.contains(passwordChar)) {
            //Pozytywny Wynik

            if(difficultGame.contains("�atwy")){
                if(uncorrect <= 16){correct = wygrana;}
                correctAnswer.setText("trafionych: "+String.valueOf(correct));
                if(wygrana == passwordText.length() && uncorrect <= 16) { JOptionPane.showMessageDialog(null, "WYGRA�E�"); return; }
            }else if(difficultGame.contains("�redni")){
                if(uncorrect <= 11){correct = wygrana;}
                correctAnswer.setText("trafionych: "+String.valueOf(correct));
                if(wygrana == passwordText.length() && uncorrect <= 16) { JOptionPane.showMessageDialog(null, "WYGRA�E�"); return; }
            }else{
                if(uncorrect <= 6){correct = wygrana;}
                correctAnswer.setText("trafionych: "+String.valueOf(correct));
                if(wygrana == passwordText.length() && uncorrect <= 16) { JOptionPane.showMessageDialog(null, "WYGRA�E�"); return; }
            }


        } else {
            //negatywny wynik

            if(difficultGame.contains("�atwy")){
                if(wygrana < passwordText.length() && uncorrect < 17){uncorrect = uncorrect + 1;}
            }else if(difficultGame.contains("�redni")){
                if(wygrana < passwordText.length() && uncorrect < 11){uncorrect = uncorrect + 1;}
            }else{
                if(wygrana < passwordText.length() && uncorrect < 6){uncorrect = uncorrect + 1;}
            }

            uncorrectAnswer.setText("nietrafionych: "+String.valueOf(uncorrect));
            uncorrectAnswer.setForeground(Color.red);

            if(difficultGame.contains("�atwy")){
                if(uncorrect < 17 && wygrana < passwordText.length()){
                    paintWisielec.rysujWisielca(uncorrect);
                }else if(wygrana < passwordText.length()){
                     paintWisielec.rysujWisielca(uncorrect);
                     JOptionPane.showMessageDialog(null,"Jak przykro przegra�e� na naj�atwiejszym poziomie!!!");
                }

            }else if(difficultGame.contains("�redni")){
                if(uncorrect < 11 && wygrana < passwordText.length()){
                    paintWisielec.rysujWisielca(uncorrect);
                }else if(wygrana < passwordText.length()){
                    paintWisielec.rysujWisielca(uncorrect);
                    JOptionPane.showMessageDialog(null,"Jak przykro przegra�e� na �rednim poziomie !!!");
                }
            }else{
                if(uncorrect < 6 && wygrana < passwordText.length()){
                    paintWisielec.rysujWisielca(uncorrect);
                }else if(wygrana < passwordText.length()){
                    paintWisielec.rysujWisielca(uncorrect);
                    JOptionPane.showMessageDialog(null,"Jak przykro przegra�e� na najtrudniejszym poziomie !!!");
                }
            }
        }
    }

    //Test helping Function
    public void helpFunction(String helpText){
        createWindowsFileForText(helpText);
    }
}
