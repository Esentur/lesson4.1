package com.company;

import java.util.Random;

public class Main {

    public static int[] health = {1500, 220, 230, 240, 250, 600, 200, 225, 210};//0й элемент это босс
    public static int[] hits = {55, 20, 20, 20, 20, 5, 20, 20, 20};
    public static String[] hitTypes = {"Физический ", "физический ", "магический ", "ментальный", "медицинский",
            "танковый", "ловкач", "берсерк", "тор"};//

    public static void main(String[] args) {
        int i = 1;
        while (!isFinished()) {

            System.out.println("раунд " + i);
            changeBossDefence();
            round();
            System.out.println("ЖИВОЙ медик лечит каждого ЖИВОГО героя на 15единиц, кроме себя.");
            printStatistics();
            i++;

        }
    }

    public static void round() {
        for (int i = 1; i <= 8; i++) {
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
            for (int i = 1; i <= 8; i++) {
                if (hitTypes[0].equals(hitTypes[5])) {//случай супер-удара танка--->
                        int tanksHelp = 25;
                        if (health[i] == 5) {
                            health[5] = bossHit(5) - tanksHelp * 7;//танк еще забирает часть урона(25 из 55) по другим себе
                        } else {
                            health[i] = bossHit(i) + tanksHelp;// и тогда босс наносит остальным по 10ед. урона
                        }
                } else if (hitTypes[0].equals(hitTypes[6])) {//случай супер-удара ловкача--->
                        if (health[i] <= 0) {
                            health[i] = 0;
                        }
                        if (health[i] == health[6]) {//ловкач не получает урон от босса
                            health[i] = health[i];
                        } else {
                            health[i] = bossHit(i);
                        }
                } else if (hitTypes[0].equals(hitTypes[7])) {//случай супер-удара берсерка--->
                        if (health[i] <= 0) {
                            health[i] = 0;
                        }
                        if (health[i] == health[7]) {//случай берсерка блокирует часть  урона от босса
                            health[i] = bossHit(i) + 15;
                        } else {
                            health[i] = bossHit(i);
                        }
                } else if (hitTypes[0].equals(hitTypes[8])) {//случай удара тора--->
                        if (health[i] <= 0) {
                            health[i] = 0;
                        }
                        health[i] = bossHit(i);

                } else { //Если критикал  хит наносит  ни танк, ни ловкач, ни берсерк, ни тор, то он как обычно бьет всех ЖИВЫХ по 50ед. урона
                        if (health[i] <= 0) {
                            health[i] = 0;
                        } else {
                            health[i] = bossHit(i);
                        }
                }
                if (health[i] > 0) { // если герой жив--->
                        if (health[i] == health[4]) {// если он сам не медик
                            health[i] = health[i];
                        } else if (health[4] <= 0) { //если еще жив медик
                            health[i] = health[i];
                        } else {
                            health[i] = health[i] + 15;//--->выполни лечение на 15ед.
                        }
                }
            }
        }

    }

    public static void printStatistics() {
        System.out.println("__________________________________");
        System.out.println("Boss health: " + health[0]);
        System.out.println("----------------------------------");
        System.out.println("Warrior health: " + health[1]);
        System.out.println("Magic health: " + health[2]);
        System.out.println("Kinetic health: " + health[3]);
        System.out.println("Doctor health: " + health[4]);
        System.out.println("Tank health: " + health[5]);
        System.out.println("Lovkach health: " + health[6]);
        System.out.println("Berserk health: " + health[7]);
        System.out.println("Tor health: " + health[8]);
        System.out.println("__________________________________");
    }

    public static boolean isFinished() {

        if (health[0] <= 0) {
            System.out.println("Победили Герои!!!");
            return true;
        }
        if (health[1] <= 0 && health[2] <= 0 && health[3] <= 0 && health[4] <= 0 && health[5] <= 0 && health[6] <= 0 && health[7] <= 0 && health[8] <= 0) {
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
                System.out.println("После супер-удара Танк забирает 25/55ед урона босса по игрокам себе, слабеет.");
            }
            if (hitTypes[playerIndex].equals(hitTypes[6])) {
                System.out.println("После супер-удара Ловкач уворачивается от урона босса.");
            }
            if (hitTypes[playerIndex].equals(hitTypes[7])) {
                System.out.println("После супер-удара Берсерк блокирует часть удара босса(15/55) и +15 к своему удару.");
                health[0] = health[0] - 15;
            }
            if (hitTypes[playerIndex].equals(hitTypes[8])) {
                System.out.println("После супер-удара Тор оглушает босса.Он не бьет раунд");
            }
            return health[0] - hits[playerIndex] * randomNumber;


        } else {
            return health[0] - hits[playerIndex];
        }
    }

    public static int bossHit(int playerIndex) {
        if (hitTypes[0].equals(hitTypes[8])) {//если у Тора супер-удар, босс не может атаковать раунд
            return health[playerIndex];
        } else
            return health[playerIndex] - hits[0];
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomNum = r.nextInt(8) + 1;
        hitTypes[0] = hitTypes[randomNum];
    }

}
