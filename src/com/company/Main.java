package com.company;

import java.util.Random;

public class Main {
    public static int num = 0;
    public static int[] health = {1700, 220, 230, 240, 250, 600, 200, 225, 210};//0й элемент это босс
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
                if (health[i] > 0) {//если герой жив для атаки--->
                    int damagedHealthOfBoss = playerHit(i);
                    if (damagedHealthOfBoss < 0) {
                        health[0] = 0;
                    } else {
                        health[0] = damagedHealthOfBoss;
                    }
                } else { //если герой умер--->
                    health[0] = health[0];
                }
            }
        }

        if (health[0] > 0) { // если босс жив после атаки--->
            for (int i = 1; i <= 8; i++) {
                if (hitTypes[0].equals(hitTypes[5])) {//случай супер-удара танка--->
                    int tanksHelp = 25;
                    if (health[i] == health[5]) {
                        switch (num) {//танк еще забирает часть урона(25 из 55) по другим себе
                            case 0:
                                health[5]=bossHit(5);
                                break;
                            case 1:
                                health[5] = bossHit(5) - (tanksHelp *1);
                                break;
                            case 2:
                                health[5] = bossHit(5) - (tanksHelp *2);
                                break;
                            case 3:
                                health[5] = bossHit(5) - (tanksHelp *3);
                                break;
                            case 4:
                                health[5] = bossHit(5) - (tanksHelp *4);
                                break;
                            case 5:
                                health[5] = bossHit(5) - (tanksHelp * 5);//танк еще забирает часть урона(25 из 55) по другим себе
                                break;
                            case 6:
                                health[5] = bossHit(5) - (tanksHelp *6);
                                break;
                            case 7:
                                health[5] = bossHit(5) - (tanksHelp *7);
                                break;
                        }
                    } else {
                        health[i] = bossHit(i) + tanksHelp;// и тогда босс наносит остальным по 55-25ед. урона
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

                } else { //Если критикал  хит наносит  ни танк, ни ловкач, ни берсерк, ни тор, то он как обычно бьет всех ЖИВЫХ по 55ед. урона
                    if (health[i] <= 0) {
                        health[i] = 0;
                    } else {
                        health[i] = bossHit(i);
                    }
                }
                if (health[i] > 0&&health[4]>0&&health[i]!=health[4]) { // если герой жив--->
                    health[i] = health[i] + 15;//--->выполни лечение на 15ед.
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
        checkHeroes(health);
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
                System.out.println("После супер-удара Танк забирает 25/55ед урона босса по живым игрокам себе.");
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

    public static int checkHeroes(int[] health) {
        int num = 0;
        for (int i = 1; i < 8; i++) {
            if (health[i] > 0) {
                if(i==5)continue;
                num++;
            }

        }
        System.out.println("Живых героев: " + (num)+" и +танк");
        return num;
    }

}
