package game;

import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class levelHandler extends clickable{


    static  boolean playOnce;
    static boolean isPaused = false;
    static Label label;
    final static Image peaShooterCard = new Image("media/peaShooterCard.png");
    final static Image sunflowerCard = new Image("media/sunFlowerCard.png");
    final static Image wallNutCard = new Image("media/wallnutCard.png");
    final static Image potatomineCard = new Image("media/potatomineCard.png");

    final static int sunflowerSelection = 5;
    static int currentSunflowerCounter = 0;

    final static int peashooterSelection = 10;
    static int currentPeashooterCounter = 0;

    final static int potatomineSelection = 15;
    static int currentPotatomineCounter = 0;

    final static int wallnutSelection = 20;
    static int currentWallnutCounter = 0;
    Level main;
    static int dashboard = -1;
    static ImageView DashboardImageview;
    static boolean enter = false;
    static boolean placedOnDashboard;
    Map map;
    pauseMenu pm ;

    public levelHandler(){
    }

    public void mouseEnter(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        if (!playOnce){
            super.play();
            playOnce = true;
        }
        glow.setLevel(0.3);
        i.setEffect(glow);
    }

    public void mouseExit(MouseEvent mouseEvent) {
        ImageView i = (ImageView) mouseEvent.getSource();
        Glow glow = new Glow();
        glow.setLevel(0.0);
        i.setEffect(glow);
        playOnce = false;
    }

    public void planting(MouseEvent mouseEvent) {
        if (!placedOnDashboard){
            return;
        }

        ImageView i = (ImageView) mouseEvent.getSource();
        String name = i.getId();
        int x = 1 ;
        int row = 1;

        switch(name){

            case "OneOne":
                row = 1;
                x = 1;
                break;
            case "OneTwo":
                row = 1;
                x = 2;
                break;
            case "OneThree":
                row = 1;
                x = 3;
                break;
            case "OneFour":
                row = 1;
                x = 4;
                break;
            case "OneFive":
                row = 1;
                x = 5;
                break;
            case "OneSix":
                row = 1;
                x = 6;
                break;
            case "OneSeven":
                row = 1;
                x = 7;
                break;
            case "OneEight":
                row = 1;
                x = 8;
                break;
            case "OneNine":
                row = 1;
                x = 9;
                break;

            case "TwoOne":
                row = 2;
                x = 1;
                break;
            case "TwoTwo":
                row = 2;
                x = 2;
                break;
            case "TwoThree":
                row = 2;
                x = 3;
                break;
            case "TwoFour":
                row = 2;
                x = 4;
                break;
            case "TwoFive":
                row = 2;
                x = 5;
                break;
            case "TwoSix":
                row = 2;
                x = 6;
                break;
            case "TwoSeven":
                row = 2;
                x = 7;
                break;
            case "TwoEight":
                row = 2;
                x = 8;
                break;
            case "TwoNine":
                row = 2;
                x = 9;
                break;

            case "ThreeOne":
                row = 3;
                x = 1;
                break;
            case "ThreeTwo":
                row = 3;
                x = 2;
                break;
            case "ThreeThree":
                row = 3;
                x = 3;
                break;
            case "ThreeFour":
                row = 3;
                x = 4;
                break;
            case "ThreeFive":
                row = 3;
                x = 5;
                break;
            case "ThreeSix":
                row = 3;
                x = 6;
                break;
            case "ThreeSeven":
                row = 3;
                x = 7;
                break;
            case "ThreeEight":
                row = 3;
                x = 8;
                break;
            case "ThreeNine":
                row = 3;
                x = 9;
                break;

            case "FourOne":
                row = 4;
                x = 1;
                break;
            case "FourTwo":
                row = 4;
                x = 2;
                break;
            case "FourThree":
                row = 4;
                x = 3;
                break;
            case "FourFour":
                row = 4;
                x = 4;
                break;
            case "FourFive":
                row = 4;
                x = 5;
                break;
            case "FourSix":
                row = 4;
                x = 6;
                break;
            case "FourSeven":
                row = 4;
                x = 7;
                break;
            case "FourEight":
                row = 4;
                x = 8;
                break;
            case "FourNine":
                row = 4;
                x = 9;
                break;

            case "FiveOne":
                row = 5;
                x = 1;
                break;
            case "FiveTwo":
                row = 5;
                x = 2;
                break;
            case "FiveThree":
                row = 5;
                x = 3;
                break;
            case "FiveFour":
                row = 5;
                x = 4;
                break;
            case "FiveFive":
                row = 5;
                x = 5;
                break;
            case "FiveSix":
                row = 5;
                x = 6;
                break;
            case "FiveSeven":
                row = 5;
                x = 7;
                break;
            case "FiveEight":
                row = 5;
                x = 8;
                break;
            case "FiveNine":
                row = 5;
                x = 9;
                break;
        }


        int money = Integer.valueOf(label.getText());
        switch (dashboard){

            //dashboard = 0 (Peashooter)
            case 0:
                main.addPeashooter(x,row);
                currentPeashooterCounter = 0;
                money -= 100;
                placedOnDashboard = false;

                DashboardImageview.setImage(null);
                break;

            //dashboard = 1 (Sunflower)
            case 1:
                main.addSunflower(x,row);
                currentSunflowerCounter = 0;
                money -= 50;
                placedOnDashboard = false;

                DashboardImageview.setImage(null);
                break;

            //dashboard = 2 (Potatomine)
            case 2:
                main.addPotatomine(x,row);
                currentPotatomineCounter = 0;
                money -= 25;
                placedOnDashboard = false;

                DashboardImageview.setImage(null);
                break;

            //dashboard = 3 (Wallnut)
            case 3:
                main.addWallnut(x,row);
                currentWallnutCounter = 0;
                placedOnDashboard = false;
                money -= 25;
                
                DashboardImageview.setImage(null);
                break;
        }
        label.setText(Integer.toString(money));
    }

    public void selectCard(MouseEvent mouseEvent) {
        //dashboard = 0 (Peashooter)
        //dashboard = 1 (Sunflower)
        //dashboard = 2 (Potatomine)
        //dashboard = 3 (Wallnut)
        ImageView img = (ImageView) mouseEvent.getSource();

        int money = Integer.valueOf(label.getText());

        switch (img.getId()){
            case "peashooterCard":
                if (money >= 100){
                    if (currentPeashooterCounter == peashooterSelection){
                        dashboard = 0;
                        DashboardImageview.setImage(peaShooterCard);
                        placedOnDashboard = true;
                    }
                }
                break;

            case "sunflowerCard":
                if (money >= 50){
                    if (currentSunflowerCounter == sunflowerSelection){
                        dashboard = 1;
                        DashboardImageview.setImage(sunflowerCard);
                        placedOnDashboard = true;
                    }
                }
                break;

            case "potatomineCard":
                if (money >= 25){
                    if (currentPotatomineCounter == potatomineSelection){
                        dashboard = 2;
                        DashboardImageview.setImage(potatomineCard);
                        placedOnDashboard = true;
                    }
                }
                break;

            case "wallnutCard":
                if (money >= 25){
                    if (currentWallnutCounter == wallnutSelection){
                        dashboard = 3;
                        DashboardImageview.setImage(wallNutCard);
                        placedOnDashboard = true;
                    }
                }
                break;
        }

    }

    public void mouseExitOnCard(MouseEvent mouseEvent) {
        if (enter){
            mouseExit(mouseEvent);
            enter = false;
        }
    }

    public void mouseEnterOnCard(MouseEvent mouseEvent) {
        ImageView img = (ImageView) mouseEvent.getSource();

        int money = Integer.valueOf(label.getText());

        switch (img.getId()){
            case "peashooterCard":
                if (money >= 100){
                    if (currentPeashooterCounter == peashooterSelection){
                        mouseEnter(mouseEvent);
                        enter = true;
                    }
                }
                break;

            case "sunflowerCard":
                if (money >= 50){
                    if (currentSunflowerCounter == sunflowerSelection){
                        mouseEnter(mouseEvent);
                        enter = true;
                    }
                }
                break;

            case "potatomineCard":
                if (money >= 25){
                    if (currentPotatomineCounter == potatomineSelection){
                        mouseEnter(mouseEvent);
                        enter = true;
                    }
                }
                break;

            case "wallnutCard":
                if (money >= 25){
                    if (currentWallnutCounter == wallnutSelection){
                        mouseEnter(mouseEvent);
                        enter = true;
                    }
                }
                break;
        }
    }

    public void toggle(MouseEvent mouseEvent) throws Exception {
        if (this.pm == null){
            this.pm = new pauseMenu(this.map,this.main);
            this.pm.setLevel(main);
        }

        BoxBlur blur = new BoxBlur();
        blur.setHeight(1080.0);
        blur.setWidth(1920.0);
        blur.setInput(new GaussianBlur(100));
        main.Root.setEffect(blur);
        main.pause();
        this.pm.show();
    }
}
