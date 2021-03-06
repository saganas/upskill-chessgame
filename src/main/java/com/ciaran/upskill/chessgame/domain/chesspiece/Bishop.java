package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.Colour;
import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;
import com.ciaran.upskill.chessgame.domain.BoardCell;

import static com.ciaran.upskill.chessgame.UtilClass.modulo;
import static com.ciaran.upskill.chessgame.domain.chesspiece.ChessPieceType.BISHOP;

public class Bishop extends ChessPiece {

    public Bishop(BoardCell boardCell, Colour colour){
        this.type = BISHOP;
        this.boardCell = boardCell;
        this.colour = colour;
    }

    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()- boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()- boardCell.getYaxis();
        if (modulo(xAxisDiff)!= modulo(yAxisDiff)){
            System.out.println("Bishop must move in a diagonal direction");
            return false;
        }
        BoardCell roamingBoardCell = new BoardCell('A', '1');
        roamingBoardCell.setXaxis(boardCell.getXaxis());
        roamingBoardCell.setYaxis(boardCell.getYaxis());
        while(!roamingBoardCell.equals(finishPosition)){
            if(!roamingBoardCell.equals(boardCell)){
                if (chessBoard.getPieceByLocation(roamingBoardCell)!=null){
                    return false;
                }
            }
           roamingBoardCell.increment(xAxisDiff/modulo(xAxisDiff), yAxisDiff/modulo(yAxisDiff));
        }
        return true;
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
        return removedPiece;
    }
}
