package pakietWisielec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class PaintWisielec extends JComponent{

    private Ellipse2D.Double elipsa;
    private Ellipse2D.Double kwadrat;
    private Polygon trojkat;

    private List<Figury> listaFigur = new ArrayList<Figury>();
    private int typFigury = 0;
    private Graphics graphics;
    private Rectangle2D sizeDrawComponent;

    private double temporaryX,temporaryY;//zmienne tymczasowe do obliczenia increaseX i increaseY
    private Point2D increasePointXY = new Point2D.Double();//skalowanie punktow x i y dla rozmiaru okna
    private boolean jednorazoweMalowanieSzubienicy; //zmienna wstrzymuj¹ca malowanie szubienicy przy pierwszym wywo³aniu paintComponent paintComponent jest uruchamiany w dwoch fazach : czyszczenia ekranu i jego malowania
    private static int zmiennaSzybkosciWzrostuWisielca = 2;//zmienna okreœlaj¹ca tempo wzrostu ludzika wisielca
    private static Szubienica szubienica;
    private static BasicStroke fineLine = new BasicStroke(1); // ustawienie grubosci lini cienkiej
    private static BasicStroke thickLine = new BasicStroke(3);// ustawienie grubosci linie grubej

    public PaintWisielec(boolean jednorazoweMalowanieSzubienicy) {

        this.jednorazoweMalowanieSzubienicy = jednorazoweMalowanieSzubienicy;
        this.addComponentListener(new ComponentAdapter() {

            private double increaseX,increaseY; //zmienne skalowania panelu rysowania dla wspo³rzednej X i Y
            @Override
            public void componentResized(ComponentEvent e) {

                if(e.getComponent().getBounds().getWidth() > temporaryX){
                    temporaryX = e.getComponent().getBounds().getWidth();
                    increaseX = increaseX+zmiennaSzybkosciWzrostuWisielca;
                    increasePointXY.setLocation(increaseX,increaseY);
                }else if(e.getComponent().getBounds().getHeight() > temporaryY){
                    temporaryY = e.getComponent().getBounds().getHeight();
                    increaseY = increaseY+zmiennaSzybkosciWzrostuWisielca;
                    increasePointXY.setLocation(increaseX,increaseY);
                }else if(e.getComponent().getBounds().getWidth() < temporaryX){
                    temporaryX = e.getComponent().getBounds().getWidth();
                    if(increaseX >= 0){ increaseX = increaseX - zmiennaSzybkosciWzrostuWisielca;}
                    increasePointXY.setLocation(increaseX,increaseY);
                }else if(e.getComponent().getBounds().getHeight() < temporaryY){
                    temporaryY = e.getComponent().getBounds().getHeight();
                    if(increaseY >= 0){increaseY = increaseY-zmiennaSzybkosciWzrostuWisielca;}
                    increasePointXY.setLocation(increaseX,increaseY);
                }
            }
        });
    }

    //metoda ustawiaj¹ca skalowanie dla ludzika
    protected static void ustawValueSkaliSuwaka(int value){
        zmiennaSzybkosciWzrostuWisielca = value;
    }

    @Override
    protected void paintComponent(Graphics g) {

        this.graphics = g;
        Graphics2D g2 = (Graphics2D)g;
        sizeDrawComponent = g.getClipBounds();

        for(Figury el : listaFigur){
            Szubienica.ustawSizeSzubienicy(increasePointXY,sizeDrawComponent);
            GlowaWisielca.ustawSizeGlowy();
            TulwWisielca.ustawSizeTulw();
            ReceWisielca.ustawSizeLewaReka();
            ReceWisielca.ustawSizePrawaReka();
            NogiWisielca.ustawSizeLewaNoga();
            NogiWisielca.ustawSizePrawaNoga();
            StopyWisielca.ustawSizeLewaStopa();
            StopyWisielca.ustawSizePrawaStopa();
            PepekWisielca.setLocationPepekWisielca();
            OczyWisielca.setLocationOkoLewe();
            OczyWisielca.setLocationOkoPrawe();
            UszyWisielca.setLocationUchoLewe();
            UszyWisielca.setLocationUchoPrawe();
            NosWisielca.setLocationNos();
            UstaWisielca.setLocationUstaWisielca();
            KrawatWisielca.setLocationWezelKrawatuWisielca();
            KrawatWisielca.setLocationZwisKrawatuWisielca();
            KapeluszWisielca.setLocationKapeluszWisielca();

            g2.setColor(el.getColor());
            g2.setStroke(el.getBasicStroke());
            if(el.getFillColor()){
                g2.fill(el.getTypFigury());
            }else{g2.draw(el.getTypFigury());}
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(350,100);
    }

    public static int getGruboscLiniRysujacej(int x) throws IllegalArgumentException{ //arg 1 szerokosc linie cienkiej,arg 2 szerokosc lini grubej, default 1
        try {
            if (x == 1) {
                return (int) fineLine.getLineWidth();
            } else if (x == 2) {
                return (int) thickLine.getLineWidth();
            } else {
                throw new IllegalArgumentException("");
            }
        }catch(IllegalArgumentException illegalArg){ return 1; }
    }

    public void rysujWisielca(int typFigury){
        this.typFigury = typFigury;

        if(typFigury == 0){
                szubienica = new Szubienica(increasePointXY,sizeDrawComponent);
                listaFigur.add(new Figury(Color.BLACK,Szubienica.getPodlogaSzubienicy(),thickLine,false));
                listaFigur.add(new Figury(Color.BLACK,Szubienica.getMasztSzubienicy(),thickLine,false));
                listaFigur.add(new Figury(Color.BLACK,Szubienica.getBelkaSzubienicy(),thickLine,false));
                listaFigur.add(new Figury(Color.BLACK,Szubienica.getSznurekSzubienicy(),thickLine,false));
                listaFigur.add(new Figury(Color.BLACK,Szubienica.getPetelkaSzubienicy(),fineLine,false));
        }

        if(typFigury == 1){ new GlowaWisielca(); listaFigur.add(new Figury(Color.ORANGE,GlowaWisielca.getGlowaWisielca(),thickLine,false)); repaint();}
        if(typFigury == 2){ new TulwWisielca(); listaFigur.add(new Figury(Color.RED,TulwWisielca.getTulwWisielca(),thickLine,false)); repaint();}
        if(typFigury == 3){ new ReceWisielca(); listaFigur.add(new Figury(Color.RED,ReceWisielca.getLewaReka(),fineLine,false)); repaint();}
        if(typFigury == 4){ new ReceWisielca(); listaFigur.add(new Figury(Color.GREEN,ReceWisielca.getPrawaReka(),thickLine,false)); repaint();}
        if(typFigury == 5){ new NogiWisielca(); listaFigur.add(new Figury(Color.MAGENTA,NogiWisielca.getLewaNoga(),fineLine,false)); repaint();}
        if(typFigury == 6){ new NogiWisielca(); listaFigur.add(new Figury(Color.PINK,NogiWisielca.getPrawaNoga(),thickLine,false)); repaint();}
        if(typFigury == 7){ new StopyWisielca(); listaFigur.add(new Figury(Color.BLUE,StopyWisielca.getLewaStopa(),fineLine,false)); repaint();}
        if(typFigury == 8){ new StopyWisielca(); listaFigur.add(new Figury(Color.CYAN,StopyWisielca.getPrawaStopa(),thickLine,false)); repaint();}
        if(typFigury == 9){ new PepekWisielca(); listaFigur.add(new Figury(Color.MAGENTA,PepekWisielca.getPepekWisielca(),thickLine,false)); repaint();}
        if(typFigury == 10){ new OczyWisielca(); listaFigur.add(new Figury(Color.BLUE,OczyWisielca.getOkoLewe(),thickLine,true)); repaint();}
        if(typFigury == 11){ new OczyWisielca(); listaFigur.add(new Figury(Color.BLUE,OczyWisielca.getOkoPrawe(),thickLine,true)); repaint();}
        if(typFigury == 12){ new UszyWisielca(); listaFigur.add(new Figury(Color.ORANGE,UszyWisielca.getUchoLewe(),thickLine,true)); repaint();}
        if(typFigury == 13){ new UszyWisielca(); listaFigur.add(new Figury(Color.ORANGE,UszyWisielca.getUchoPrawe(),thickLine,true)); repaint();}
        if(typFigury == 14){
            new NosWisielca(); listaFigur.add(new Figury(Color.ORANGE,NosWisielca.getDziurkaLewa(),thickLine,false)); repaint();
            new NosWisielca(); listaFigur.add(new Figury(Color.ORANGE,NosWisielca.getDziurkaPrawa(),thickLine,false)); repaint();
        }
        if(typFigury == 15){ new UstaWisielca(); listaFigur.add(new Figury(Color.RED,UstaWisielca.getUstaWisielca(),fineLine,true)); repaint();}
        if(typFigury == 16){
            new KrawatWisielca(); listaFigur.add(new Figury(Color.GREEN,KrawatWisielca.getWezelKrawatu(),fineLine,true)); repaint();
            new KrawatWisielca(); listaFigur.add(new Figury(Color.GRAY,KrawatWisielca.getTrapezKrawat(),fineLine,true)); repaint();
        }
        if(typFigury == 17){
            new KapeluszWisielca(); listaFigur.add(new Figury(Color.PINK,KapeluszWisielca.getRondoKapelusza(),fineLine,false)); repaint();
            new KapeluszWisielca(); listaFigur.add(new Figury(Color.MAGENTA,KapeluszWisielca.getPudloKapelusza(),thickLine,true)); repaint();
        }

        //Warunek rysuj¹cy szubienice po pierwszym wywo³aniu metody paintComponent()
        if(jednorazoweMalowanieSzubienicy){rysuj();}
    }

    public void rysuj(){
        repaint();
    }
}

class Figury{
    private Color color;
    private Shape typFigury;
    private BasicStroke basicStroke;
    private boolean fillColor;

    public Figury(Color color, Shape typFigury, BasicStroke basicStroke,boolean fillColor) {
        this.color = color;
        this.typFigury = typFigury;
        this.basicStroke = basicStroke;
        this.fillColor = fillColor;
    }

    public Color getColor() { return color; }
    public Shape getTypFigury() { return typFigury; }
    public BasicStroke getBasicStroke(){ return basicStroke; }
    public boolean getFillColor(){ return fillColor; }
}

class Szubienica{

    private static double insets = 25;
    private static double promienKolaPetelki = 50;
    private static Line2D.Double podlogaSzubienicy = new Line2D.Double();
    private static Line2D.Double masztSzubienicy = new Line2D.Double();
    private static Line2D.Double belkaSzubienicy = new Line2D.Double();
    private static Line2D.Double sznurekSzubienicy = new Line2D.Double();
    private static Ellipse2D.Double petelkaSzubienicy = new Ellipse2D.Double();
    private static Point2D increasePointXY; // obiekt pozwalaj¹cy skalowaæ wspó³rzêdne wedlug rozmiaru okna na którym malujemy
    private static Rectangle2D sizeDrawComponent; //aktualny rozmiar okna na którym malujemy
    private static Dimension sizePetelki;

    public Szubienica(Point2D increasePointXY,Rectangle2D sizeDrawComponent) {
        Szubienica.increasePointXY = increasePointXY;
        Szubienica.sizeDrawComponent = sizeDrawComponent;

        Szubienica.setPodlogaSzubienicy();
        Szubienica.setMasztSzubienicy();
        Szubienica.setBelkaSzubienicy();
        Szubienica.setSznurekSzubienicy();
        Szubienica.setPetelkaSzubienicy();
    }

    private static void setPodlogaSzubienicy(){
        Point2D p1 = new Point2D.Double((sizeDrawComponent.getMinX()) + insets,(sizeDrawComponent.getMaxY()) - insets);
        Point2D p2 = new Point2D.Double((sizeDrawComponent.getMaxX()) - insets,(sizeDrawComponent.getMaxY())-insets);
        podlogaSzubienicy.setLine(p1,p2);
    }

    private static void setMasztSzubienicy(){
        Point2D p1 = new Point2D.Double(podlogaSzubienicy.getP1().getX(),podlogaSzubienicy.getP1().getY());
        Point2D p2 = new Point2D.Double(p1.getX(),sizeDrawComponent.getMinY()+insets);
        masztSzubienicy.setLine(p1,p2);
    }

    private static void setBelkaSzubienicy(){
        Point2D p1 = new Point2D.Double(masztSzubienicy.getP2().getX(),masztSzubienicy.getP2().getY());
        Point2D p2 = new Point2D.Double(sizeDrawComponent.getCenterX(),insets);
        belkaSzubienicy.setLine(p1,p2);
    }

    private static void setSznurekSzubienicy(){
        Point2D p1 = new Point2D.Double(belkaSzubienicy.getP2().getX(),belkaSzubienicy.getP2().getY());
        Point2D p2 = new Point2D.Double(belkaSzubienicy.getP2().getX(),3*insets);
        sznurekSzubienicy.setLine(p1,p2);
    }

    private static void setPetelkaSzubienicy(){
        Point2D p1 = new Point2D.Double(sznurekSzubienicy.getP2().getX()-((promienKolaPetelki + increasePointXY.getX())/2),sznurekSzubienicy.getP2().getY());
        int width = (int)promienKolaPetelki+((int)increasePointXY.getX());
        int height = (int)promienKolaPetelki+((int)increasePointXY.getY());
        sizePetelki = new Dimension(width,height);
        petelkaSzubienicy.setFrame(p1,sizePetelki);
    }

    public static Line2D.Double getPodlogaSzubienicy(){ return podlogaSzubienicy; }
    public static Line2D.Double getMasztSzubienicy(){ return masztSzubienicy; }
    public static Line2D.Double getBelkaSzubienicy(){ return belkaSzubienicy; }
    public static Line2D.Double getSznurekSzubienicy(){ return sznurekSzubienicy; }
    public static Ellipse2D.Double getPetelkaSzubienicy(){ return petelkaSzubienicy; }

    public static void ustawSizeSzubienicy(Point2D increasePointXY,Rectangle2D sizeDrawComponent){
        Szubienica.increasePointXY = increasePointXY;
        Szubienica.sizeDrawComponent = sizeDrawComponent;

        Szubienica.setPodlogaSzubienicy();
        Szubienica.setMasztSzubienicy();
        Szubienica.setBelkaSzubienicy();
        Szubienica.setSznurekSzubienicy();
        Szubienica.setPetelkaSzubienicy();
    }
}

class GlowaWisielca{

    private static Ellipse2D.Double glowaWisielca = new Ellipse2D.Double();

    public GlowaWisielca() {
        GlowaWisielca.setGlowaWisielca();
    }

    private static void setGlowaWisielca(){
        double wyrownanieNaLinkePodSzyja = 5;
        double width = Szubienica.getPetelkaSzubienicy().width - wyrownanieNaLinkePodSzyja;
        double heigh = Szubienica.getPetelkaSzubienicy().height - wyrownanieNaLinkePodSzyja;
        Point2D p1 = new Point2D.Double(Szubienica.getPetelkaSzubienicy().getCenterX() - width/2,Szubienica.getPetelkaSzubienicy().y + wyrownanieNaLinkePodSzyja);
        glowaWisielca.setFrame(p1.getX(),p1.getY(),width,heigh);
    }

    public static Ellipse2D.Double getGlowaWisielca(){ return  glowaWisielca; }

    public static void ustawSizeGlowy(){
        GlowaWisielca.setGlowaWisielca();
    }
}

class TulwWisielca{

    private static Ellipse2D.Double tulwWisielca = new Ellipse2D.Double();

    public TulwWisielca() {
        TulwWisielca.setTulwWisielca();
    }

    private static void setTulwWisielca(){
        double widthTulw = (GlowaWisielca.getGlowaWisielca().width)*1.5;
        double heightTulw = (GlowaWisielca.getGlowaWisielca().height)*3;
        Point2D p1 = new Point2D.Double(GlowaWisielca.getGlowaWisielca().getCenterX() - (widthTulw/2),GlowaWisielca.getGlowaWisielca().getMaxY());
        tulwWisielca.setFrame(p1,new Dimension((int)widthTulw,(int)heightTulw));
    }

    public static Ellipse2D.Double getTulwWisielca(){ return tulwWisielca; }

    public static void ustawSizeTulw(){
        TulwWisielca.setTulwWisielca();
    }
}

class ReceWisielca{
    private static Line2D.Double lewaReka = new Line2D.Double();
    private static Line2D.Double prawaReka = new Line2D.Double();

    public ReceWisielca() {
        ReceWisielca.setReceWisielca();
    }

    private static void setReceWisielca(){
        double rekaWidth = GlowaWisielca.getGlowaWisielca().width;
        double rekaHeight = TulwWisielca.getTulwWisielca().height;

        Point2D lewaReka_P1 = new Point2D.Double(TulwWisielca.getTulwWisielca().getMinX(),TulwWisielca.getTulwWisielca().getMinY()+(rekaHeight/3));
        lewaReka.setLine(lewaReka_P1,new Point2D.Double(TulwWisielca.getTulwWisielca().getMinX() - rekaWidth,rekaHeight));

        Point2D prawaReka_P1 = new Point2D.Double(TulwWisielca.getTulwWisielca().getMaxX(),TulwWisielca.getTulwWisielca().getMinY()+(rekaHeight/3));
        prawaReka.setLine(prawaReka_P1,new Point2D.Double(TulwWisielca.getTulwWisielca().getMaxX() + rekaWidth,rekaHeight));
    }

    public static Line2D.Double getLewaReka(){ return lewaReka; }
    public static Line2D.Double getPrawaReka(){ return prawaReka; }

    public static void ustawSizeLewaReka(){ ReceWisielca.setReceWisielca(); }
    public static void ustawSizePrawaReka(){ ReceWisielca.setReceWisielca(); }
}

class NogiWisielca{
    private static Line2D.Double lewaNoga = new Line2D.Double();
    private static Line2D.Double prawaNoga = new Line2D.Double();

    public NogiWisielca() {
        NogiWisielca.setNogiWisielca();
    }

    private static void setNogiWisielca(){
        double glowaWidth = GlowaWisielca.getGlowaWisielca().width;
        double tulwWidth = TulwWisielca.getTulwWisielca().width;

        Point2D lewaNoga_P1 = new Point2D.Double(TulwWisielca.getTulwWisielca().getMinX()+(tulwWidth/3),TulwWisielca.getTulwWisielca().getMaxY());
        lewaNoga.setLine(lewaNoga_P1,new Point2D.Double(TulwWisielca.getTulwWisielca().getMinX(),TulwWisielca.getTulwWisielca().getMaxY()+glowaWidth));

        Point2D prawaNoga_P1 = new Point2D.Double(TulwWisielca.getTulwWisielca().getMaxX()-(tulwWidth/3),TulwWisielca.getTulwWisielca().getMaxY());
        prawaNoga.setLine(prawaNoga_P1,new Point2D.Double(TulwWisielca.getTulwWisielca().getMaxX(),TulwWisielca.getTulwWisielca().getMaxY()+glowaWidth));
    }

    public static Line2D.Double getLewaNoga(){ return lewaNoga; }
    public static Line2D.Double getPrawaNoga(){ return prawaNoga; }

    public static void ustawSizeLewaNoga(){ NogiWisielca.setNogiWisielca(); }
    public static void ustawSizePrawaNoga(){ NogiWisielca.setNogiWisielca(); }
}

class StopyWisielca{
    private static Line2D.Double lewaStopa = new Line2D.Double();
    private static Line2D.Double prawaStopa = new Line2D.Double();

    public StopyWisielca() {
        StopyWisielca.setStopyWisielca();
    }

    private static void setStopyWisielca(){
        double sizeStopa = (GlowaWisielca.getGlowaWisielca().width)/3;

        Point2D lewaStopa_P1 = new Point2D.Double(NogiWisielca.getLewaNoga().getP2().getX(),NogiWisielca.getLewaNoga().getP2().getY());
        lewaStopa.setLine(lewaStopa_P1,new Point2D.Double(NogiWisielca.getLewaNoga().getP2().getX() - sizeStopa,lewaStopa_P1.getY()));

        Point2D prawaStopa_P1 = new Point2D.Double(NogiWisielca.getPrawaNoga().getP2().getX(),NogiWisielca.getPrawaNoga().getP2().getY());
        prawaStopa.setLine(prawaStopa_P1,new Point2D.Double(NogiWisielca.getPrawaNoga().getP2().getX() + sizeStopa,prawaStopa_P1.getY()));
    }

    public static Line2D.Double getLewaStopa(){ return lewaStopa; }
    public static Line2D.Double getPrawaStopa(){ return prawaStopa; }


    public static void ustawSizeLewaStopa(){ StopyWisielca.setStopyWisielca(); }
    public static void ustawSizePrawaStopa(){ StopyWisielca.setStopyWisielca(); }
}

class PepekWisielca{
    private static Ellipse2D.Double pepekWisielca = new Ellipse2D.Double();

    public PepekWisielca() {
        PepekWisielca.setPepekWisielca();
    }

    private static void setPepekWisielca(){
        double heightTulw = TulwWisielca.getTulwWisielca().height;
        double x = TulwWisielca.getTulwWisielca().getCenterX();
        double y = (TulwWisielca.getTulwWisielca().getMaxY()) - ((heightTulw)/3);

        pepekWisielca.setFrame(x,y,1,1);
    }

    public static Ellipse2D.Double getPepekWisielca(){ return pepekWisielca; }

    public static void setLocationPepekWisielca(){ PepekWisielca.setPepekWisielca(); }
}

class OczyWisielca{
    private static Ellipse2D.Double okoLewe = new Ellipse2D.Double();
    private static Ellipse2D.Double okoPrawe = new Ellipse2D.Double();

    public OczyWisielca() {
        OczyWisielca.setOczyWisielca();
    }

    private static void setOczyWisielca(){
        double glowaWidth = GlowaWisielca.getGlowaWisielca().width;
        double glowaHeight = GlowaWisielca.getGlowaWisielca().height;
        double minGlowa_X = GlowaWisielca.getGlowaWisielca().getMinX();
        double maxGlowa_X = GlowaWisielca.getGlowaWisielca().getMaxX();
        double minGlowa_Y = GlowaWisielca.getGlowaWisielca().getMinY();
        Dimension sizeEyes = new Dimension(10,5);

        okoLewe.setFrame(new Point2D.Double(minGlowa_X + (glowaWidth/4),minGlowa_Y + (glowaHeight/4)),sizeEyes);
        okoPrawe.setFrame(new Point2D.Double(maxGlowa_X - ((glowaWidth/4)+sizeEyes.width),minGlowa_Y + (glowaHeight/4)),sizeEyes);
    }

    public static Ellipse2D.Double getOkoLewe(){ return okoLewe; }
    public static Ellipse2D.Double getOkoPrawe(){ return okoPrawe; }

    public static void setLocationOkoLewe(){ setOczyWisielca(); }
    public static void setLocationOkoPrawe(){ setOczyWisielca(); }
}

class UszyWisielca{
    private static Ellipse2D.Double uchoLewe = new Ellipse2D.Double();
    private static Ellipse2D.Double uchoPrawe = new Ellipse2D.Double();

    public UszyWisielca() {
        UszyWisielca.setUszyWisielca();
    }

    private static void setUszyWisielca(){
        double minGlowa_X = GlowaWisielca.getGlowaWisielca().getMinX();
        double maxGlowa_X = GlowaWisielca.getGlowaWisielca().getMaxX();
        double centerGlowa_Y = GlowaWisielca.getGlowaWisielca().getCenterY();
        Dimension sizeEars = new Dimension(8,16);

        uchoLewe.setFrame(new Point2D.Double((minGlowa_X - (sizeEars.width)),centerGlowa_Y - (sizeEars.height/2)),sizeEars);
        uchoPrawe.setFrame(new Point2D.Double(maxGlowa_X,centerGlowa_Y - (sizeEars.height/2)),sizeEars);
    }

    public static Ellipse2D.Double getUchoLewe(){ return uchoLewe; }
    public static Ellipse2D.Double getUchoPrawe(){ return uchoPrawe; }

    public static void setLocationUchoLewe(){ UszyWisielca.setUszyWisielca(); }
    public static void setLocationUchoPrawe(){ UszyWisielca.setUszyWisielca(); }
}

class NosWisielca{
    private static Ellipse2D.Double dziurkaLewa = new Ellipse2D.Double();
    private static Ellipse2D.Double dziurkaPrawa = new Ellipse2D.Double();

    public NosWisielca() {
        NosWisielca.setElementyNosa();
    }

    private static void setElementyNosa(){
        double centerGlowa_X = GlowaWisielca.getGlowaWisielca().getCenterX();
        double centerGlowa_Y = GlowaWisielca.getGlowaWisielca().getCenterY();
        Dimension sizeDziurkiNosa = new Dimension(2,3);

        dziurkaLewa.setFrame(new Point2D.Double(centerGlowa_X - (sizeDziurkiNosa.width),centerGlowa_Y - (sizeDziurkiNosa.width)),sizeDziurkiNosa);
        dziurkaPrawa.setFrame(new Point2D.Double(centerGlowa_X + (sizeDziurkiNosa.width),centerGlowa_Y - (sizeDziurkiNosa.width)),sizeDziurkiNosa);
    }

    public static Ellipse2D.Double getDziurkaLewa(){ return dziurkaLewa; }
    public static Ellipse2D.Double getDziurkaPrawa(){ return dziurkaPrawa; }

    public static void setLocationNos(){ NosWisielca.setElementyNosa(); }
}

class UstaWisielca{
    private static Ellipse2D.Double ustaWisielca = new Ellipse2D.Double();

    public UstaWisielca() {
        UstaWisielca.setUstaWisielca();
    }

    private static void setUstaWisielca(){
        double heightGlowa = GlowaWisielca.getGlowaWisielca().height;
        double widthUsta = 20;
        double heightUsta = 5;
        Point2D p1 = new Point2D.Double(GlowaWisielca.getGlowaWisielca().getCenterX() - (widthUsta/2),GlowaWisielca.getGlowaWisielca().getMaxY() - (heightGlowa/4));
        ustaWisielca.setFrame(p1,new Dimension((int)widthUsta,(int)heightUsta));
    }

    public static Ellipse2D.Double getUstaWisielca(){ return ustaWisielca; }

    public static void setLocationUstaWisielca(){ UstaWisielca.setUstaWisielca(); }
}

class KrawatWisielca extends KlasaLokalizacjiPunktowZwisuDlaKrawata{
    private static Rectangle2D.Double wezelKrawatu = new Rectangle2D.Double();
    private static KlasaLokalizacjiPunktowZwisuDlaKrawata trapezKrawat = new KlasaLokalizacjiPunktowZwisuDlaKrawata();

    public KrawatWisielca() {
        KrawatWisielca.setWezelKrawatuWiesielca();
        KrawatWisielca.setZwisKrawatuWisielca();
    }

    private static void setWezelKrawatuWiesielca() {
        //rysowanie wez³a dla krawata
        double sizeWezla = 12;
        double centerGlowa = GlowaWisielca.getGlowaWisielca().getCenterX();
        double dolGlowy = GlowaWisielca.getGlowaWisielca().getMaxY();
        double gruboscLiniRysujacej = PaintWisielec.getGruboscLiniRysujacej(2);//1 szerokosc linie cienkiej,2 szerokosc lini grubej
        Point2D p1_Wezel = new Point2D.Double(centerGlowa - sizeWezla / 2, dolGlowy + gruboscLiniRysujacej);

        wezelKrawatu.setFrame(p1_Wezel, new Dimension((int) sizeWezla, (int) sizeWezla));
    }

    private static void setZwisKrawatuWisielca(){

        //Rysowanie zwisu w krawacie
        double trapezSzerokosc = (GlowaWisielca.getGlowaWisielca().width/10);
        double trazpezWysokosc = (GlowaWisielca.getGlowaWisielca().height/2);

        Point2D.Double p1_Trapez = new Point2D.Double(KrawatWisielca.getWezelKrawatu().x,KrawatWisielca.getWezelKrawatu().getMaxY());
        Point2D.Double p2_Trapez = new Point2D.Double(KrawatWisielca.getWezelKrawatu().x+KrawatWisielca.getWezelKrawatu().width,KrawatWisielca.getWezelKrawatu().getMaxY());
        Point2D.Double p3_Trapez = new Point2D.Double(KrawatWisielca.getWezelKrawatu().getMaxX()+trapezSzerokosc,KrawatWisielca.getWezelKrawatu().getMaxY()+(trazpezWysokosc/2));
        Point2D.Double p4_Trapez = new Point2D.Double(KrawatWisielca.getWezelKrawatu().getCenterX(),KrawatWisielca.getWezelKrawatu().getMaxY()+(trazpezWysokosc));
        Point2D.Double p5_Trapez = new Point2D.Double(KrawatWisielca.getWezelKrawatu().getMinX()-trapezSzerokosc,KrawatWisielca.getWezelKrawatu().getMaxY()+(trazpezWysokosc/2));

        int tabX[] = new int[]{(int)p1_Trapez.x,(int)p2_Trapez.x,(int)p3_Trapez.x,(int)p4_Trapez.x,(int)p5_Trapez.x};
        int tabY[] = new int[]{(int)p1_Trapez.y,(int)p2_Trapez.y,(int)p3_Trapez.y,(int)p4_Trapez.y,(int)p5_Trapez.y};
        int iloscBokow = 5;

        trapezKrawat.setPolygon(tabX,tabY,5);
    }

    public static Rectangle2D.Double getWezelKrawatu(){ return wezelKrawatu; }
    public static Polygon getTrapezKrawat(){ return trapezKrawat; }

    public static void setLocationWezelKrawatuWisielca(){ KrawatWisielca.setWezelKrawatuWiesielca(); }
    public static void setLocationZwisKrawatuWisielca(){ KrawatWisielca.setZwisKrawatuWisielca(); }
}

class KlasaLokalizacjiPunktowZwisuDlaKrawata extends Polygon{

    public void setPolygon(int tabX[],int tabY[],int iloscBokow){
        this.xpoints = tabX;
        this.ypoints = tabY;
        this.npoints = iloscBokow;
    }
}

class KapeluszWisielca{
    private static Line2D.Double rondoKapelusza = new Line2D.Double();
    private static Rectangle2D.Double pudloKapelusza = new Rectangle2D.Double();

    public KapeluszWisielca() {
        KapeluszWisielca.setKapeluszWisielca();
    }

    private static void setKapeluszWisielca(){
        //ustawienie ronda kapelusza
        double x1_k = GlowaWisielca.getGlowaWisielca().x;
        double y1_k = GlowaWisielca.getGlowaWisielca().y;
        double x2_k = GlowaWisielca.getGlowaWisielca().getMaxX();
        rondoKapelusza.setLine(new Point2D.Double(x1_k,y1_k),new Point2D.Double(x2_k,y1_k));

        //ustawienie pudla kapelusza
        double sizePudlo = GlowaWisielca.getGlowaWisielca().width/2;
        double srodekRonda = GlowaWisielca.getGlowaWisielca().getCenterX();
        double y_ronda = GlowaWisielca.getGlowaWisielca().y;

        pudloKapelusza.setFrame(new Point2D.Double(srodekRonda - sizePudlo/2,y_ronda - sizePudlo),new Dimension((int)sizePudlo,(int)sizePudlo));
    }

    public static Line2D.Double getRondoKapelusza(){ return rondoKapelusza; }
    public static Rectangle2D.Double getPudloKapelusza(){ return pudloKapelusza; }

    public static void setLocationKapeluszWisielca(){ KapeluszWisielca.setKapeluszWisielca();}
}







































class Melonik extends Polygon{

    private static Line2D.Double rondoKapelusza = null;
    private static Rectangle2D.Double pudloKapelusza = null;

    public static void rysujMelonik(Graphics g,Ellipse2D glowaWisielca, double increaseSizeKapelutka){
        Graphics2D g2 = (Graphics2D)g;
        rondoKapelusza = new Line2D.Double();
        pudloKapelusza = new Rectangle2D.Double();
        rondoKapelusza.setLine(glowaWisielca.getMinX(),glowaWisielca.getMinY(),glowaWisielca.getMinX() +(glowaWisielca.getMaxX() - glowaWisielca.getMinX()),glowaWisielca.getMinY());
        double sizeKapelusza_width = (glowaWisielca.getMaxX() - glowaWisielca.getMinX())/2;
        double sizePudlo_h = 10;
        pudloKapelusza.setFrame(glowaWisielca.getCenterX()-(sizeKapelusza_width/2),glowaWisielca.getMinY()-sizePudlo_h,sizeKapelusza_width,sizePudlo_h);
        g2.draw(rondoKapelusza);
        g2.draw(pudloKapelusza);

        g2.setColor(Color.MAGENTA);
        g2.fill(pudloKapelusza);
        g2.setColor(Color.BLACK);
    }
    public static Line2D.Double getRondoKapelusza(){
        return rondoKapelusza;
    }

    public  static Rectangle2D.Double getPudloKapelusza(){
        return pudloKapelusza;
    }
}

class Nos extends Polygon{

    private static Ellipse2D.Double lewaDziurkaNosa = null;
    private static Line2D.Double koscNosowa = null;
    private static Ellipse2D.Double prawaDziurkaNosa = null;

    public static void rysujNos(Graphics g,Ellipse2D glowaWiesielca,double increaseSizeNos){
        Graphics2D g2 = (Graphics2D)g;

        lewaDziurkaNosa = new Ellipse2D.Double();
        koscNosowa = new Line2D.Double();
        prawaDziurkaNosa = new Ellipse2D.Double();

        g2.setColor(Color.black);
        lewaDziurkaNosa.setFrame(glowaWiesielca.getCenterX()-increaseSizeNos,glowaWiesielca.getCenterY(),2,2);
        koscNosowa.setLine(glowaWiesielca.getCenterX(),glowaWiesielca.getCenterY()-(increaseSizeNos*2),glowaWiesielca.getCenterX(),glowaWiesielca.getCenterY());
        prawaDziurkaNosa.setFrame(glowaWiesielca.getCenterX()+increaseSizeNos,glowaWiesielca.getCenterY(),2,2);

        g2.draw(lewaDziurkaNosa);
        g2.draw(prawaDziurkaNosa);

        g2.setColor(Color.ORANGE);
        g2.fill(lewaDziurkaNosa);
        g2.fill(prawaDziurkaNosa);

        g2.setColor(Color.GRAY);
        g2.draw(koscNosowa);
    }

    public static Ellipse2D.Double getLewaDziurkaNosa(){
        return lewaDziurkaNosa;
    }

    public  static Ellipse2D.Double getPrawaDziurkaNosa(){
        return  prawaDziurkaNosa;
    }

    public static Line2D.Double getKoscNosowa(){
        return koscNosowa;
    }
}

class Uszy extends Polygon{

    private static Ellipse2D.Double uchoLewe = null;
    private static Ellipse2D.Double uchoPrawe = null;

    public static void rysujUcho(Graphics g,boolean ktoreucho,Ellipse2D glowaWisielca,double increaseSizeUcho){
        Graphics2D g2 = (Graphics2D)g;
        uchoLewe = new Ellipse2D.Double();
        uchoPrawe = new Ellipse2D.Double();

        if(ktoreucho){
            uchoLewe.setFrame(glowaWisielca.getMinX()-5,glowaWisielca.getCenterY() - (increaseSizeUcho/2),5,increaseSizeUcho);
            g2.draw(uchoLewe);
            g2.setColor(Color.yellow);
            g2.fill(uchoLewe);
            g2.setColor(Color.black);
        }
        if(!ktoreucho){
            uchoPrawe.setFrame(glowaWisielca.getMaxX(),glowaWisielca.getCenterY() - (increaseSizeUcho/2),5,increaseSizeUcho);
            g2.draw(uchoPrawe);
            g2.setColor(Color.yellow);
            g2.fill(uchoPrawe);
            g2.setColor(Color.black);
        }
    }

    public static Ellipse2D.Double getUchoLewe(){
        return uchoLewe;
    }

    public  static Ellipse2D.Double getUchoPrawe(){
        return uchoPrawe;
    }
}

class Krawacik extends Polygon{

    private static Rectangle2D.Double kwadracik = null;
    private static Polygon polygon = null;

    public static void rysujKrawacik(Graphics g, Ellipse2D tulw, double increaseKrawat){
        Graphics2D g2 = (Graphics2D)g;
        kwadracik = new Rectangle2D.Double();

        kwadracik.setFrame(tulw.getCenterX() - (increaseKrawat/2),tulw.getMinY(),increaseKrawat,increaseKrawat);
        g2.setColor(Color.black);
        g2.draw(kwadracik);

        Point2D.Double p1 = new Point2D.Double(kwadracik.x,kwadracik.y+kwadracik.height);
        Point2D.Double p2 = new Point2D.Double(kwadracik.getMaxX(),kwadracik.y+kwadracik.height);
        Point2D.Double p3 = new Point2D.Double(kwadracik.getMaxX()+kwadracik.width,kwadracik.getMaxY() + kwadracik.height*3);
        Point2D.Double p4 = new Point2D.Double(kwadracik.getMinX() + (kwadracik.width/2),kwadracik.getMaxY() + kwadracik.height*4);
        Point2D.Double p5 = new Point2D.Double(kwadracik.x - kwadracik.width,kwadracik.getMaxY() + kwadracik.height*3);

        polygon = new Polygon(new int[]{(int)p1.x,(int)p2.x,(int)p3.x,(int)p4.x,(int)p5.x},new int[]{(int)p1.y,(int)p2.y,(int)p3.y,(int)p4.y,(int)p5.y},5);
        g2.drawPolygon(polygon);
        g2.setColor(Color.green);
        g2.fillPolygon(new int[]{(int)p1.x,(int)p2.x,(int)p3.x,(int)p4.x,(int)p5.x},new int[]{(int)p1.y,(int)p2.y,(int)p3.y,(int)p4.y,(int)p5.y},5);
        g2.setColor(Color.BLACK);
    }
}