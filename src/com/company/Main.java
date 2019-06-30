package com.company;
import java.util.Random;

public class Main {
    public static int[] health = {1100, 250, 250, 250, 250, 500};//0й элемент это босс
    public static int[] hits = {55, 20, 20, 20, 20, 5};
    public static String[] hitTypes = {"Физический ", "физический ", "магический ", "ментальный", "медицинский", "танковый"};//

    public static void main(String[] args) {
        int i = 1;
        while (!isFinished()) {

            System.out.println("раунд " + i);
            changeBossDefence();
            round();
            System.out.println("Живой медик лечит каждого ЖИВОГО героя на 15ед. , кроме себя");
            printStatistics();
            i++;
        }
    }

    public static void round() {
        for (int i = 1; i <= 5; i++) {
            if (health[0] > 0) {
                if (health[i] > 0) {//если герой жив--->
                    int damagedHealthOfBoss = playerHit(i);//нанеси урон боссу
                    if (damagedHealthOfBoss < 0) {//если после удара босс умер, посчитай что у него 0хп
                        health[0] = 0;
                    } else {                      //если босс Не умер, то жизнь=поврежденная жизнь
                        health[0] = damagedHealthOfBoss;
                    }
                } else { //если герой умер--->
                    health[0] = health[0];//у босса жизнь не изменяется
                }
            }
        }
        if (health[0] > 0) { // если босс жив после атаки--->
            for (int i = 1; i <= 5; i++) {
                if (hitTypes[0].equals(hitTypes[5])) {//--->
                    int tanksHelp = 25;
                    if (health[i] == 5) {
                        health[5] = bossHit(5) - tanksHelp * 4;//танк еще забирает часть урона(25 из 55) по другим себе
                    } else {
                        health[i] = bossHit(i) + tanksHelp;// и тогда босс наносит остальным по 10ед. урона
                    }
                } else { //Если критикал  хит не наносит  танк, то он как обычно бьет всех ЖИВЫХ по 50ед. урона
                    if (health[i] <= 0) {
                        health[i] = 0;
                    } else {
                        health[i] = bossHit(i);
                    }
                }
                if (health[i] > 0) { // если герой жив--->
                    if (health[i] == health[4]) {// если не он сам не медик
                        health[i] = health[i];
                    } else if(health[4]<=0){ //если еще жив медик
                        health[i]=health[i];
                    }
                    else{
                        health[i] = health[i] + 15;//--->выполни лечение на 15ед.
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("__________________________________");
        System.out.println("Boss health: " + health[0]);
        System.out.println("Heroes health: "+(health[1]+health[2]+health[3]+health[4]+health[5]));
        System.out.println("Warrior health: " + health[1]);
        System.out.println("Magic health: " + health[2]);
        System.out.println("Kinetic health: " + health[3]);
        System.out.println("Doctor health: " + health[4]);
        System.out.println("Tank health: " + health[5]);
        System.out.println("__________________________________");
    }

    public static boolean isFinished() {

        if (health[0] <= 0) {
            System.out.println("Победили Герои!!!");
            return true;
        }
        if (health[1] <= 0 && health[2] <= 0 && health[3] <= 0 && health[4] <= 0 && health[5] <= 0) {
            System.out.println("Победил Босс!!!");
            return true;
        }
        return false;
    }

    public static int playerHit(int playerIndex) {
        Random r = new Random();
        int randomNumber = r.nextInt(8) + 2;
        if (hitTypes[0].equals(hitTypes[playerIndex])) {
            System.out.println(hitTypes[playerIndex] + " супер-удар " + " х" + randomNumber + " --->" + hits[playerIndex] * randomNumber);
            if (hitTypes[playerIndex].equals(hitTypes[5])) {
                System.out.println("Танк забирает 25/55ед урона босса по игрокам себе.");
            }
            return health[0] - hits[playerIndex] * randomNumber;
        } else {
            return health[0] - hits[playerIndex];
        }
    }

    public static int bossHit(int playerIndex) {
        return health[playerIndex] - hits[0];
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomNum = r.nextInt(5) + 1;
        hitTypes[0] = hitTypes[randomNum];
    }
}
