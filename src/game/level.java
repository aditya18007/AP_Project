package game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

class Level{

    Stage stage;

    Lawnmower[] mowers = new Lawnmower[5];

    ArrayList<Zombie>[] zombies = new ArrayList[5];
    ArrayList<Peashooter>[] peashooters = new ArrayList[5];
    ArrayList<WallNut>[] wallnuts = new ArrayList[5];
    ArrayList<Sunflower>[] sunflowers = new ArrayList[5];
    ArrayList<PotatoMine>[] potatomines = new ArrayList[5];
    ArrayList<Attacking> attackers = new ArrayList<>();
    ArrayList<Plant> attackDefense = new ArrayList<>();
    ArrayList<Zombie> attackAttack = new ArrayList<>();
    double totalZombies;
    double deadZombies;
    Label label;
    ProgressBar progress;
    Group Root;
    levelHandler handler;
    int level;

    Timeline ZombiesAndSimplePlants;
    Timeline ZombiesAndPotatoMines;
    Timeline plantShooting;
    Timeline LawnmowerAndZombies;
    Timeline GameProgress;
    Map map;
    Scene scene;
    Level(Map map,Stage stage , FXMLLoader loader , int qwerty) throws Exception{
        this.level = qwerty;
        Parent root = loader.load();
        this.map = map;
        this.handler = loader.getController();
        label = (Label) root.lookup("#sunCountLabel");
        this.handler.map = this.map;
        switch (this.level){
            case 1:
                this.totalZombies = 10;
                label.setText("0");
                break;
            case 2:
                this.totalZombies = 15;
                label.setText("0");
                break;
            case 3:
                this.totalZombies = 20;
                label.setText("0");
                break;
            case 4:
                this.totalZombies = 30;
                label.setText("750");
                break;
            case 5:
                this.totalZombies = 500;
                label.setText(Integer.toString(Integer.MAX_VALUE));
        }

        this.stage = stage;
        Root = new Group();
        Root.getChildren().add(root);
        this.handler.main = this;
        handler.label = label;
        handler.DashboardImageview = (ImageView) root.lookup("#DashboardImageview");
        progress = (ProgressBar) root.lookup("#ProgressBar");
        progress.setProgress(0);

        for (int i = 1; i <= 5; i++) {
            mowers[i-1] = new Lawnmower(i,Root);
            zombies[i - 1] = new ArrayList<>();
            peashooters[i - 1] = new ArrayList<>();
            potatomines[i - 1] = new ArrayList<>();
            wallnuts[i - 1] = new ArrayList<>();
            sunflowers[i - 1] = new ArrayList<>();
            mowers[i-1].enemies = zombies[i-1];
        }


        GameProgress = new Timeline(
                new KeyFrame(Duration.seconds(1) , e ->{
                    for (int i = 0 ; i < 5 ; i++) {

                        if (this.level == 5){
                            for (Lawnmower mower : mowers){

                                if (mower.isRunning){
                                    pause();
                                    ImageView l = new ImageView(new Image("media/loserMower.png"));
                                    l.setFitWidth(1000);
                                    l.setFitHeight(1000);
                                    l.setX(0);
                                    l.setY(0);
                                    this.Root.getChildren().add(l);
                                    PauseTransition p = new PauseTransition(Duration.millis(3500));
                                    p.setOnFinished(q ->{
                                        this.map.getMainMenu().startMenu();
                                    });
                                    p.play();
                                    this.map.getBackgroundAudio().setMute(false);
                                }
                            }
                        }

                        for (Zombie z : zombies[i]){
                            if ((z.isDead())&&(!z.isCounted())){
                                this.deadZombies += 1;
                                z.countDone();
                            }
                            if ((!z.isDead())&&(z.isActive)&&(z.canEatBrain())){
                                pause();
                                ImageView l = new ImageView(new Image("media/looser.png"));
                                l.setFitWidth(1000);
                                l.setFitHeight(1000);
                                l.setX(0);
                                l.setY(0);
                                this.Root.getChildren().add(l);
                                PauseTransition p = new PauseTransition(Duration.millis(3500));
                                p.setOnFinished(q ->{
                                    this.map.getMainMenu().startMenu();
                                });
                                p.play();
                                this.map.getBackgroundAudio().setMute(false);
                            }
                        }
                    }
                    progress.setProgress(this.deadZombies/totalZombies);
                    if (progress.getProgress() == 1.0){
                        pause();
                        ImageView l = new ImageView(new Image("media/winner.png"));
                        l.setX(0);
                        l.setY(0);
                        l.setFitHeight(1000);
                        l.setFitWidth(1000);
                        this.Root.getChildren().add(l);
                        PauseTransition p = new PauseTransition(Duration.millis(3500));
                        p.setOnFinished(q ->{
                            this.map.getMainMenu().startMenu();
                        });
                        p.play();
                        this.map.getBackgroundAudio().setMute(false);
                    }
                })
        );

        ZombiesAndSimplePlants = new Timeline(

                new KeyFrame(Duration.seconds(1) , e ->{

                    if (handler.currentPeashooterCounter < handler.peashooterSelection){

                        if (this.level >3 ){
                            handler.currentPeashooterCounter =  handler.peashooterSelection;
                        } else {
                            handler.currentPeashooterCounter++;
                        }
                    }

                    if (handler.currentSunflowerCounter < handler.sunflowerSelection){

                        if (this.level >3 ){
                            handler.currentSunflowerCounter =  handler.sunflowerSelection;
                        } else {
                            handler.currentSunflowerCounter++;
                        }
                    }

                    if (handler.currentWallnutCounter < handler.wallnutSelection){

                        if (this.level >3 ){
                            handler.currentWallnutCounter =  handler.wallnutSelection;
                        } else {
                            handler.currentWallnutCounter++;
                        }
                    }

                    if (handler.currentPotatomineCounter < handler.potatomineSelection){

                        if (this.level >3 ){
                            handler.currentPotatomineCounter =  handler.potatomineSelection;
                        } else {
                            handler.currentPotatomineCounter++;
                        }
                    }

                    for (Attacking a : attackers){
                        if ((!a.plant.isActive)&&(a.zombie.isActive)){
                            a.zombie.Play();
                        }
                    }

                    for (int i = 0 ; i < 5 ; i++){

                        for (Zombie z : zombies[i]){

                            for (WallNut w : wallnuts[i]){
                                if (w.isActive && z.isActive){
                                    if (w.getBoundsInParent().intersects(z.getBoundsInParent())) {
                                        z.Pause();
                                        w.takeHit(z.doesDamage);
                                        w.attacking.add(z);
                                        attackers.add(new Attacking(z,w));
                                        attackAttack.add(z);
                                        attackDefense.add(w);
                                    }
                                }
                            }

                            for(Peashooter p : peashooters[i]){
                                if (p.isActive && z.isActive){
                                    if (p.getBoundsInParent().intersects(z.getBoundsInParent())) {
                                        z.Pause();
                                        p.takeHit(z.doesDamage);
                                        p.attacking.add(z);
                                        attackers.add(new Attacking(z,p));
                                        attackAttack.add(z);
                                        attackDefense.add(p);
                                    }
                                }
                            }

                            for(Sunflower s : sunflowers[i]){
                                if (s.isActive && z.isActive){
                                    if (s.getBoundsInParent().intersects(z.getBoundsInParent())) {
                                        z.Pause();
                                        s.takeHit(z.doesDamage);
                                        s.attacking.add(z);
                                        attackers.add(new Attacking(z,s));
                                        attackAttack.add(z);
                                        attackDefense.add(s);
                                    }
                                }
                            }
                        }
                    }
                })
        );

        ZombiesAndPotatoMines = new Timeline(

                new KeyFrame(Duration.seconds(1) , e ->{
                    for (int i = 0 ; i < 5 ; i++){

                        for (Zombie z : zombies[i]){
                            for (PotatoMine p : potatomines[i]){
                                if (p.isActive && z.isActive){
                                    if (p.getBoundsInParent().intersects(z.getBoundsInParent())) {
                                        p.blast();
                                        z.takeHit(1000);//Just a random number
                                    }
                                }
                            }
                        }
                    }
                })
        );


        plantShooting = new Timeline(
                new KeyFrame(Duration.seconds(1) , e ->{
                    for (int i = 0 ; i < 5 ; i++){
                        int count = 0;
                        for (Zombie z : zombies[i]){
                            if (z.isActive){
                                count++;
                            }
                        }
                        if (count > 0){
                            for (Peashooter p : peashooters[i]){
                                if (p.isActive) {
                                    p.shoot();
                                }
                            }
                        }
                    }
                })
        );

        LawnmowerAndZombies = new Timeline(
                new KeyFrame(Duration.seconds(1) , e ->{

                    for (int i = 0 ; i < 5 ; i++){
                        System.out.println(mowers[i].isActive);
                        for (Zombie z : zombies[i]){
                            if (z.isActive && mowers[i].isActive) {
                                if (mowers[i].getBoundsInParent().intersects(z.getBoundsInParent())) {
                                    mowers[i].fire();
                                    System.out.println("Should Kill");
                                }
                            }
                        }
                    }
                })
        );

        plantShooting.setCycleCount(Timeline.INDEFINITE);
        LawnmowerAndZombies.setCycleCount(Timeline.INDEFINITE);
        ZombiesAndPotatoMines.setCycleCount(Timeline.INDEFINITE);
        ZombiesAndSimplePlants.setCycleCount(Timeline.INDEFINITE);
        GameProgress.setCycleCount(Timeline.INDEFINITE);
    }

    void startTimelines(){
        plantShooting.play();
        LawnmowerAndZombies.play();
        ZombiesAndPotatoMines.play();
        ZombiesAndSimplePlants.play();
        GameProgress.play();
    }

    void add(){


        int delay = 15;
        int normalZombies = 0;
        int footballzombies = 0;
        int delayBetweenZombies = 0;
        int delayBetweenWaves = 0;
        int zombieDefense = 0;
        int zombieAttack = 0;
        switch(this.level){

            case 1:
                normalZombies = 5;
                footballzombies = 5;
                delayBetweenWaves = 5;
                delayBetweenZombies = 2;
                zombieAttack += 0;
                zombieDefense += 0;
                break;
            case 2:
                normalZombies = 8;
                footballzombies = 7;
                delayBetweenWaves = 5;
                delayBetweenZombies = 2;
                zombieAttack += 10;
                zombieDefense += 10;
                break;
            case 3:
                normalZombies = 8;
                footballzombies = 12;
                delayBetweenWaves = 5;
                delayBetweenZombies = 2;
                zombieAttack += 20;
                zombieDefense += 20;
                break;
            case 4:
                normalZombies = 15;
                footballzombies = 15;
                delayBetweenWaves = 8;
                delayBetweenZombies = 2;
                zombieAttack += 30;
                zombieDefense += 30;
                break;
            case 5:
                normalZombies = 250;
                footballzombies = 250;
                delayBetweenWaves = 10;
                delayBetweenZombies = 2;
                zombieAttack += 40;
                zombieDefense += 40;
                break;
        }
        Random r = new Random();
        for (int i = 0 ; i < normalZombies ; i++){
            int y = r.nextInt(5);
            if (y == 0) y = 5;
            delay+=delayBetweenZombies;
            addNormalzombie(y,delay);
        }
        delay += delayBetweenWaves;
        for (int i = 0 ; i < footballzombies ; i++){
            int y = r.nextInt(5);
            if (y == 0) y = 5;
            delay+=delayBetweenZombies;
            addFootballzombie(y,delay);
        }

        String name = "media/Level_"+this.level+".png";
        Image i = new Image(name);
        ImageView l = new ImageView(i);
        this.Root.getChildren().add(l);
        PauseTransition p = new PauseTransition(Duration.millis(3500));
        p.setOnFinished(q ->{
            this.Root.setEffect(null);
            this.Root.getChildren().remove(l);
            this.map.getBackgroundAudio().setMute(true);
            if (this.level < 4){
                Sun sun = new Sun(label, Root);
                sun.move();
            }

            startTimelines();
        });
        p.play();
        this.map.getBackgroundAudio().setMute(false);
        scene = new Scene(Root);
        stage.setScene(scene);
        stage.show();
    }

    void addWallnut(int x , int y){
        wallnuts[y-1].add(new WallNut(x,y,Root));
    }

    void addWallnut(double x , double y , int countdown , int row){
        WallNut w = new WallNut(x,y,row,Root);
        w.countdown = countdown;
        w.row = row;
        if (w.countdown < 26){
            w.halfLife();
        }
        wallnuts[w.row-1].add(w);
    }

    void addPeashooter(int x , int y){
        Peashooter p = new Peashooter(x,y,Root);
        p.enemies = zombies[y-1];
        peashooters[y-1].add(p);
    }

    void addPeashooter(double x , double y,int countdown , int row){
        Peashooter p = new Peashooter(x,y,row,Root);
        p.countdown = countdown;
        p.enemies = zombies[p.row-1];
        p.row = row;
        peashooters[p.row-1].add(p);
    }


    void addPotatomine(int x , int y){
        potatomines[y-1].add(new PotatoMine(x,y,Root));
    }

    void addPotatomine(double x , double y , int row){
        PotatoMine p = new PotatoMine(x,y,row,Root);
        p.row = row;
        potatomines[p.row-1].add(p);
    }

    void addSunflower(int x , int y){
        sunflowers[y-1].add(new Sunflower(x,y,Root,label));
    }
    void addSunflower(double x , double y , int countdown , int row) {
        Sunflower s = new Sunflower(x,y,row,Root,label);
        s.row = row;
        s.countdown = countdown;
        sunflowers[s.row-1].add(s);
    }


    void addNormalzombie(int Row , int delay){
        Zombie z = new normalZombie(Row,delay,Root);
        zombies[Row-1].add(z);
    }

    void addFootballzombie(int Row , int Delay){
        Zombie z = new footballZombie(Row,Delay,Root);
        zombies[Row-1].add(z);
    }
    void addNormalzombie(double x , double y , Duration delay , int countdown , int row){
        Zombie z = new normalZombie(x,y,delay,Root,row);
        z.countdown = countdown;
        zombies[z.row-1].add(z);
    }

    void addFootballzombie(double x , double y , Duration delay , int countDown , int row){
        Zombie z = new footballZombie(x,y,delay,Root,row);
        z.countdown = countDown;
        zombies[z.row-1].add(z);
    }
    void serialize(){
        play();

        ArrayList<mortalObjectSerializable> list= new ArrayList<>();
        Wallet w = new Wallet(Integer.valueOf(label.getText()));
        Progress dead = new Progress(deadZombies);
        mortalObjectSerializable qwerty = new mortalObjectSerializable(w);
        mortalObjectSerializable wqe = new mortalObjectSerializable(dead);
        list.add(qwerty);
        list.add(wqe);
        for (Lawnmower m : mowers){
            if (m.isActive && (!m.isDead())){
                mortalObjectSerializable mS= new mortalObjectSerializable(m);
                list.add(mS);
            }
        }

        for (int i = 0 ; i < 5 ;i++){

            for (Zombie z : zombies[i]){
                if (!z.isDead()){

                    mortalObjectSerializable mS= new mortalObjectSerializable(z);
                    list.add(mS);
                }
            }

            for (Peashooter p : peashooters[i]){
                if (!p.isDead()){
                    System.out.println("PEASHOOTER");
                    mortalObjectSerializable mS= new mortalObjectSerializable(p);
                    list.add(mS);
                }
            }

            for (WallNut p : wallnuts[i]){
                if (!p.isDead()){
                    mortalObjectSerializable mS= new mortalObjectSerializable(p);
                    list.add(mS);
                }
            }

            for (PotatoMine p : potatomines[i]){
                if (!p.isDead()){
                    mortalObjectSerializable mS= new mortalObjectSerializable(p);
                    list.add(mS);
                }
            }

            for (Sunflower p : sunflowers[i]){

                if (!p.isDead()){
                    mortalObjectSerializable mS= new mortalObjectSerializable(p);
                    list.add(mS);
                }
            }
        }

        ObjectOutputStream o = null;
        try {
            String fileName = "Aditya";

            switch (this.level){
                case 1:
                    fileName = ("saveOne.txt");
                    break;
                case 2:
                    fileName = ("saveTwo.txt");
                    break;
                case 3:
                    fileName = ("saveThree.txt");
                    break;
                case 4:
                    fileName = ("saveFour.txt");
                    break;
                case 5:
                    fileName = ("saveFive.txt");
            }

            o = new ObjectOutputStream(new FileOutputStream(fileName));
            o.writeObject(list);
            System.out.println("Success");
        }
        catch(FileNotFoundException f){
            System.out.println("FNF");
        }
        catch(NullPointerException n){
            System.out.println("null");
        }
        catch(IOException i){
            System.out.println("IO");
        }
    }
    void deserialize() {
        ObjectInputStream i = null;
        ArrayList<mortalObjectSerializable> list = new ArrayList<>();

        try {
            String fileName = "Aditya";

            switch (this.level){
                case 1:
                    fileName = ("saveOne.txt");
                    break;
                case 2:
                    fileName = ("saveTwo.txt");
                    break;
                case 3:
                    fileName = ("saveThree.txt");
                    break;
                case 4:
                    fileName = ("saveFour.txt");
                    break;
                case 5:
                    fileName = ("saveFive.txt");
            }

            i = new ObjectInputStream(new FileInputStream(fileName));
            list = (ArrayList<mortalObjectSerializable>) i.readObject();
            System.out.println("Success");
        } catch (FileNotFoundException f) {
            System.out.println("FNF");
        } catch (ClassNotFoundException c) {
            System.out.println("CNFE");
        } catch (NullPointerException n) {
            System.out.println("Null");
        } catch (IOException ie) {
            System.out.println(ie);
        }
        for (int r = 0 ; r < 5 ; r++){
            mowers[r].setInactive();
            mowers[r].setVisible(false);
        }

        for (mortalObjectSerializable m : list) {

            switch (m.name){

                case "WALLET":
                    label.setText(Integer.toString(m.countDown));
                    break;

                case "FOOTBALLZOMBIE":

                    addFootballzombie(m.currentX,m.currentY,m.Delay,m.countDown,m.row);
                    break;

                case "NORMALZOMBIE":

                    addNormalzombie(m.currentX,m.currentY,m.Delay,m.countDown,m.row);
                    break;

                case "PEASHOOTER":

                    addPeashooter(m.currentX,m.currentY,m.countDown,m.row);
                    break;

                case "WALLNUT":

                    addWallnut(m.currentX,m.currentY,m.countDown,m.row);
                    break;

                case "POTATOMINE":

                    addPotatomine(m.currentX,m.currentY,m.row);
                    break;

                case "SUNFLOWER":

                    addSunflower(m.currentX,m.currentY,m.countDown,m.row);
                    break;

                case "DEAD":
                    deadZombies = m.currentX;
                    break;

                case "LAWNMOWER":
                    System.out.println(m.row);
                    mowers[m.row-1].setVisible(true);
                    mowers[m.row-1].isActive = true;
            }
        }
        for (int t = 0 ; t < 0 ;t++){
            if (!mowers[t].isActive){
                Root.getChildren().remove(mowers[t]);
            }
        }

        progress.setProgress(deadZombies/totalZombies);
        startTimelines();
        stage.setScene(new Scene(Root));
        stage.show();
        startTimelines();
    }

    void pause(){
        for (Lawnmower m : mowers){
            if (m.isActive)
            m.Pause();
        }
        for (int i = 0 ; i < 5 ; i++ ){
            for (Zombie z : zombies[i]){
                z.Pause();
            }
        }
        plantShooting.pause();
        LawnmowerAndZombies.pause();
        ZombiesAndPotatoMines.pause();
        ZombiesAndSimplePlants.pause();
        GameProgress.pause();
    }

    void play(){

        for (Lawnmower m : mowers){
            if (m.isActive)
                m.Play();
        }
        for (int i = 0 ; i < 5 ; i++ ){
            for (Zombie z : zombies[i]){
                z.Play();
            }
        }
        plantShooting.play();
        LawnmowerAndZombies.play();
        ZombiesAndPotatoMines.play();
        ZombiesAndSimplePlants.play();
        GameProgress.play();
    }
}
class Attacking {

    Zombie zombie ;
    Plant plant;
    Attacking(Zombie z , Plant p){
        this.zombie = z;
        this.plant = p;
    }
}