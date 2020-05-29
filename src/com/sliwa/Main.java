package com.sliwa;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean gameOn = true;
        int choice = 0;
        boolean valid;

        System.out.println("Welcome to EasyBridge!");
        while (gameOn) {
            printInstructions();
            /*
            validate the choice
             */
            do {
                System.out.println("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    valid = true;
                } else {
                    scanner.nextLine();
                    valid = false;
                    System.out.println("Enter a valid number!");
                    printInstructions();
                }
            } while (!valid);

            /*
            choice ok, proceed
             */

            switch (choice) {
                case 1:
                    gameOn = false;
                    System.out.println("Bye! Bye!");
                    break;
                case 2:
                    /*
                    Initialize deck and players
                     */
                    Deck deck = new Deck();

                    System.out.println("Enter player North name:");
                    String name = scanner.nextLine();
                    Player player1 = new Player(name, deck, "N");

                    System.out.println("Enter player East name:");
                    name = scanner.nextLine();
                    Player player2 = new Player(name, deck, "E");

                    System.out.println("Enter player South name:");
                    name = scanner.nextLine();
                    Player player3 = new Player(name, deck, "S");

                    System.out.println("Enter player West name:");
                    name = scanner.nextLine();
                    Player player4 = new Player(name, deck, "W");
                    /*
                    Bidding Phase
                     */
                    biddingPhase(player1,player2,player3,player4,random,scanner);
                    break;
                default:
                    System.out.println("Enter a valid number!");
            }
        }
    }


    public static void printInstructions(){
        System.out.println("1. Quit");
        System.out.println("2. Start Game");
    }

    public static void biddingPhase(Player player1, Player player2, Player player3, Player player4, Random random, Scanner scanner){
        int chooseStartingPlayer = random.nextInt(4);
        Contract contract = new Contract();
        System.out.println("Bidding starts now!");
        while (true) {
            playBid(player1, chooseStartingPlayer, contract, scanner,0);
            if (player3.isPass() && player4.isPass() && player1.isPass()){
                System.out.println("Current contact: " + contract.getCurrentContract());
                break;
            }

            playBid(player2, chooseStartingPlayer, contract, scanner,1);
            if (player4.isPass() && player1.isPass() && player2.isPass()){
                System.out.println("Current contact: " + contract.getCurrentContract());
                break;
            }


            playBid(player3, chooseStartingPlayer, contract, scanner,2);
            if (player1.isPass() && player2.isPass() && player3.isPass()){
                System.out.println("Current contact: " + contract.getCurrentContract());
                break;
            }

            playBid(player4, chooseStartingPlayer, contract, scanner,3);
            if (player2.isPass() && player3.isPass() && player4.isPass()){
                System.out.println("Current contact: " + contract.getCurrentContract());
                break;
            }
        }
    }

    public static void playBid(Player player, int chooseStartingPlayer, Contract contract, Scanner scanner, int number){
        if (chooseStartingPlayer == number || contract.isContractStarted()) {
            System.out.println("Your Card's: " + player.getPlayersDeck());
            System.out.println(player.getName()  + " " + player.getDirection() + " choose your action: ");
            contract.showBiddingBoxMenu();
            int bidChoice = 0;
            boolean valid;

            /*
            Choice validation
             */
            do {
                if (scanner.hasNextInt()) {
                    bidChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (bidChoice >= 1 && bidChoice <= contract.getBiddingBox().size()){
                        valid = true;
                    } else {
                        valid = false;
                        System.out.println("Enter a valid number!");
                    }

                } else {
                    scanner.nextLine();
                    valid = false;
                    System.out.println("Enter a valid number!");
                }
            } while (!valid);
            /*
            Choice okay, proceed
             */
            if (bidChoice>3 && bidChoice<=contract.getBiddingBox().size()){
                contract.setCurrentContract(bidChoice,player);
                contract.removeAllBidChoicesBeforeChosenOne(bidChoice);
                player.setPass(false);
            }else if (bidChoice==1){
                player.setPass(true);
            } else if (bidChoice==2){
                player.setVersus(true);
            } else if (bidChoice==3){
                player.setDoubleVersus(true);
            }
            if (!contract.isContractStarted()){
                contract.setContractStarted(true);
            }
        }

    }

}
