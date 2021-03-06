package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.Colour;
import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;

import static com.ciaran.upskill.chessgame.UtilClass.modulo;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.PAWN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.DOWN;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.UP;

public class Pawn extends ChessPiece {

    private final BoardCell startPosition;
    private Direction direction;
    private int moved;

    public enum Direction{
        UP,
        DOWN;
    }

    public Pawn(BoardCell boardCell, Colour colour, Direction direction){
        this.type = PAWN;
        this.boardCell = boardCell;
        this.startPosition = boardCell;
        this.colour = colour;
        this.direction = direction;
        moved = 0;
    }

    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis() - boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis() - boardCell.getYaxis();
        if(modulo(xAxisDiff)>1|| modulo(yAxisDiff)>2||(xAxisDiff!=0&&yAxisDiff!=1)||yAxisDiff==0){
            System.out.println("Illegal - Pawn move");
            return false;
        }
        if ((yAxisDiff > 0 && direction.equals(DOWN)) || (yAxisDiff < 0 && direction.equals(UP))) {
            System.out.println("Pawn must move forward");
            return false;
        }
        if (modulo(xAxisDiff)==1){
            if (chessBoard.getPieceByLocation(finishPosition)==null){
                int yAxis = finishPosition.getYaxis();
                finishPosition.setYaxis(boardCell.getYaxis());
                ChessPiece chessPiece = chessBoard.getPieceByLocation(finishPosition);
                finishPosition.setYaxis(yAxis);
                if(chessPiece!=null){
                    if (!chessPiece.getType().equals(PAWN)){
                        System.out.println("Illegal - Pawn move");
                        return false;
                    }
                    if (modulo(boardCell.getYaxis()-startPosition.getYaxis())!=3){
                        System.out.println("Illegal - Pawn move");
                        return false;
                    }
                } else {
                    System.out.println("Illegal - Pawn move");
                    return false;
                }
            }
        }
        if(modulo(xAxisDiff)==0 && chessBoard.getPieceByLocation(finishPosition)!=null){
            System.out.println("Illegal - Pawn move, you can only pieces diagonally");
            return false;
        }
        if (modulo(yAxisDiff)==2) {
            if (moved>0) {
                System.out.println("Pawn can't move 2 spaces if it has already moved");
                return false;
            }
            BoardCell roamingBoardCell = new BoardCell('A', '1');
            roamingBoardCell.setXaxis(boardCell.getXaxis());
            roamingBoardCell.setYaxis(boardCell.getYaxis());
            while (!roamingBoardCell.equals(finishPosition)) {
                if (!roamingBoardCell.equals(boardCell)) {
                    if (chessBoard.getPieceByLocation(roamingBoardCell) != null) {
                        return false;
                    }
                }
                roamingBoardCell.increment(0, yAxisDiff/modulo(yAxisDiff));
            }
        }
        return true;
    }

    public Direction getDirection() {
        return direction;
    }

    public ChessPiece move(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException {
        if (!validateMove(chessBoard,finishPosition)){
            throw new IllegalMoveException();
        };
        ChessPiece removedPiece = chessBoard.getPieceByLocation(finishPosition);
        if(removedPiece!=null){
            chessBoard.removePiece(removedPiece);
        }
        setBoardCell(finishPosition);
        //TODO Pawn Conversion
        return removedPiece;
    }
}
