package pakietWisielec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Ramka extends JFrame {

    private int sliderSkalaValue;
    private JLabel labCategory = new JLabel("Kategoria");
    private JTextField textCategory = new JTextField(10);
    private JLabel labWordOfCategory = new JLabel("S³owo z danej kategori");
    private JTextField textWordOfCategory = new JTextField(10);
    private JButton acceptButton ;
    private JComboBox<String> difficultGame = new JComboBox<String>(new String[]{"poziom trudnoœci ³atwy","poziom trudnoœci œredni","poziom trudnoœci trudny"});
    private AllLeters allLeters;
    private TheWindowsFromPasswordCategory theWindowsFromPasswordCategory;
    private JLabel correctAnswer = new JLabel("trafionych: ");
    private JLabel uncorrectAnswer = new JLabel("nietrafionych: ");
    private PaintWisielec drawingPanel = new PaintWisielec(false);

    private JPanel panelForDescriptionAndSlider = new JPanel();
    private JLabel labelForSkalaSlider = new JLabel("przyrost skalowania ludzika wisielca");
    private JSlider sliderSkalowania = new JSlider(0,5,1);

    public Ramka(){

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(labCategory,new GBC(0,0).setWeight(0,0).setFill(GBC.HORIZONTAL).setInsets(3));
        labCategory.setFont(new Font("Arial",Font.PLAIN,28));
        add(textCategory,new GBC(1,0).setWeight(10,0).setFill(GBC.HORIZONTAL).setInsets(3));
        textCategory.setFont(new Font("Arial",Font.PLAIN,28));
        add(labWordOfCategory,new GBC(2,0).setWeight(0,0).setFill(GBC.HORIZONTAL).setInsets(3));
        labWordOfCategory.setFont(new Font("Arial",Font.PLAIN,28));
        add(textWordOfCategory,new GBC(3,0).setWeight(10,0).setFill(GBC.HORIZONTAL).setInsets(3));
        textWordOfCategory.setFont(new Font("Arial",Font.PLAIN,28));
        add(difficultGame,new GBC(2,1,2,1).setWeight(10,0).setFill(GBC.HORIZONTAL).setInsets(3));
        difficultGame.setFont(new Font("Arial",Font.PLAIN,28));
        Action akcjaPrzycisku = new ActionAkceptuj("Start");
        acceptButton = new JButton(akcjaPrzycisku);
        add(acceptButton,new GBC(0,1,2,1).setWeight(10,0).setFill(GBC.HORIZONTAL).setInsets(3));
        acceptButton.setFont(new Font("Arial",Font.PLAIN,28));
        add(correctAnswer,new GBC(1,4,2,1).setWeight(10,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        add(uncorrectAnswer,new GBC(3,4,2,1).setWeight(10,0).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));

        add(drawingPanel,new GBC(4,0,1,6).setWeight(100,100).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setInsets(3));
        drawingPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        add(panelForDescriptionAndSlider,new GBC(0,5,4,1).setWeight(100,100).setFill(GBC.HORIZONTAL).setAnchor(GBC.CENTER).setInsets(3));
        //ustalenie flowLayoutu dla panelForDescriptionAndSlider
        panelForDescriptionAndSlider.setLayout(new FlowLayout(FlowLayout.CENTER));

        //dodanie do panelu opisu i suwaka Skalowania
        panelForDescriptionAndSlider.add(labelForSkalaSlider);
        panelForDescriptionAndSlider.add(sliderSkalowania);

        //aktywacja suwaka
        sliderSkalowania.setPaintTicks(true);
        sliderSkalowania.setPaintLabels(true);
        sliderSkalowania.setPaintTrack(true);
        sliderSkalowania.setMajorTickSpacing(1);
        sliderSkalowania.setMinorTickSpacing(1);

        //dodanie s³uchacza do suwaka
        sliderSkalowania.addChangeListener((event)->{
            JSlider slider = (JSlider)event.getSource();
            sliderSkalaValue = slider.getValue();
            PaintWisielec.ustawValueSkaliSuwaka(sliderSkalaValue);
        });

        //Akcja dla przycisku ENTER
        InputMap imap = acceptButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"buttonEnter");

        ActionMap amap = acceptButton.getActionMap();
        amap.put("buttonEnter",akcjaPrzycisku);
    }

    public boolean checkFilledFields(JTextField field){
        if(field.getText().equals("")){
            return false;
        }else{ return true; }
    }

    //Test Helping Function
    public JTextField getTextField(int x){
        if(x == 1){
            return textCategory;
        }else if(x == 2) {
            return textWordOfCategory;
        }else{ throw new IllegalArgumentException("Niew³asciwe argumenty dla funkcji dopuszczalne to 1 i 2"); }
    }

    class ActionAkceptuj extends AbstractAction{
        public ActionAkceptuj(String name) {
            putValue(Action.NAME,name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //sprawdzenie czy pola Kategori i has³a dla  kategorii s¹ wype³nione
            try{
                if (checkFilledFields(textCategory) && checkFilledFields(textWordOfCategory)) {

                    labCategory.setEnabled(false);
                    textCategory.setEnabled(false);
                    labWordOfCategory.setVisible(false);
                    textWordOfCategory.setVisible(false);
                    difficultGame.setEnabled(false);
                    acceptButton.setEnabled(false);

                    add(new JLabel("KATEGORIA TO: "+" "+textCategory.getText()),new GBC(0,3,1,1).setWeight(10,10).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setInsets(3));
                    theWindowsFromPasswordCategory = new TheWindowsFromPasswordCategory(textWordOfCategory.getText(),correctAnswer,uncorrectAnswer,drawingPanel,difficultGame.getItemAt(difficultGame.getSelectedIndex()));
                    add(theWindowsFromPasswordCategory,new GBC(1,3,3,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(3));

                    allLeters = new AllLeters(theWindowsFromPasswordCategory);
                    add(allLeters,new GBC(0,2,4,1).setWeight(10,10).setFill(GBC.BOTH).setInsets(10,3,3,3));
                    drawingPanel.rysujWisielca(0);
                    pack();
                }else{throw new IllegalArgumentException("Proszê wprowadziæ kategorie oraz has³o odpowiadaj¹ce kategori"); }

            }catch(IllegalArgumentException argExc){ JOptionPane.showMessageDialog(null,argExc.getMessage());}
        }
    }
}
