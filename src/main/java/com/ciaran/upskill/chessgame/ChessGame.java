package com.ciaran.upskill.chessgame;

import com.ciaran.upskill.chessgame.domain.Coordinate;

import java.util.Scanner;


public class ChessGame {

    private static ChessBoard chessBoard = new ChessBoard();
    private static Scanner scanner = new Scanner(System.in);

    public static void Main(String[] args){
        newGame();
    }

    private static void newGame() {
        chessBoard.newGame();
        boolean checkMate = false;
        String colour = "white";
        while (!checkMate) {
            takeTurn(colour);
            colour = switchColour(colour);
            checkMate = isInCheckMate(colour);
        }
        colour = switchColour(colour);
        System.out.println("Checkmate, "+colour+" wins the game!");
    }

    private static boolean isInCheckMate(String colour) {
        //is King in check
        //can King move out of check
        //can A Piece block check
        return false;
    }

    private static String switchColour(String colour) {
        if (colour.matches("black")) {
            colour = "white";
        } else {
            colour = "black";
        }
        return colour;
    }

    private static void takeTurn(String colour) {
        System.out.println("It is "+colour+"'s turn.");
        boolean valid = false;
        while(!valid){
            Coordinate startPosition = null;
            while(startPosition==null){
                System.out.print("Please input your piece's starting co-ordinate: ");
                startPosition = validateCoordinateInput(scanner.next().toUpperCase());
            }
            Coordinate endPosition = null;
            while(endPosition==null){
                System.out.print("Please input your piece's ending co-ordinate: ");
                endPosition = validateCoordinateInput(scanner.next().toUpperCase());
            }
            valid=chessBoard.movePiece(startPosition, endPosition, colour);
        }
    }

    public static Coordinate validateCoordinateInput(String coordinate) {
        if(coordinate.matches("[A-H][1-8]")){
            return new Coordinate(coordinate.charAt(0),coordinate.charAt(1));
        }
        System.out.println("The co-ordinate you have input is invalid, please try again. Remember a co-ordinate is comprised of a letter from A-H followed by a number from 1-8.");
        return null;
    };
}
