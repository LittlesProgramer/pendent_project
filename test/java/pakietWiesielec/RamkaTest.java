package pakietWiesielec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import pakietWisielec.AllLeters;
import pakietWisielec.PaintWisielec;
import pakietWisielec.Ramka;

public class RamkaTest{

    @Nested
    @DisplayName("TestsForClassRamka")
    class TestRamkaClass {
        String daneP;
        Ramka ramka = new Ramka();

        @Test
        @DisplayName("TestThrowExceptionAndEqualsValue")
        void insertWordTest() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                ramka.getTextField(3);
            });

            Assertions.assertAll(

                    () -> {
                        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                            ramka.getTextField(3);
                        });
                    },
                    () -> {
                        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                            ramka.getTextField(3);
                        }, daneP = "Niew³asciwe argumenty dla funkcji dopuszczalne to 1 i 2");
                    },
                    () -> {
                        Assertions.assertEquals("Niew³asciwe argumenty dla funkcji dopuszczalne to 1 i 2", daneP);
                    }
            );
        }

        //sprawdzenie czy komponent JTextField zawiera jakiœ znak
        @Test
        @DisplayName("CorrectWordTextValue")
        void correctWordTesting() {
            ramka.getTextField(1).setText("a");
            Assertions.assertTrue(ramka.checkFilledFields(ramka.getTextField(1)));
        }

        //sprawdzenie czy komponent JTextField nie zawiera ¿adnego znaku
        @Test
        @DisplayName("IncorrectWordTextValue")
        void incorrectWordTesting() {
            ramka.getTextField(1).setText("");
            Assertions.assertFalse(ramka.checkFilledFields(ramka.getTextField(1)));
        }
    }

    @Nested
    @DisplayName("TestForClassAllLeters")
    class TestClassTheWindowsFromPasswordCategory{
        @Test
        @DisplayName("TestFunctionGiveTheButtonChar")
        void giveTheButtonChar(){
            AllLeters all = new AllLeters(null);
            all.helpFunctionAllLeters(all);
            //Sprawdzenie czt do panelu zosta³ dodany JButton z literk¹ 'A'
            Assertions.assertEquals("A", all.giveTheButtonChar());
        }
    }

    @Nested
    @DisplayName("TestForClassPaintWisielec")
    class TestClassPaintWisielec{
        //PaintWisielec paintWisielec = new PaintWisielec(true);
        @Test
        @DisplayName("Testing the getGruboscLiniRysujacej() method")
        void getGruboscLiniRysujacej(){
            Assertions.assertEquals(1,PaintWisielec.getGruboscLiniRysujacej(3));
        }
    }
}
