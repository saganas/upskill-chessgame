package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.IllegalMoveException;
import com.ciaran.upskill.chessgame.UtilClass;
import com.ciaran.upskill.chessgame.domain.BoardCell;

public class Knight extends ChessPiece {

    public Knight(BoardCell boardCell, String colour){
        this.type = "knight";
        this.boardCell = boardCell;
        this.colour = colour;
    }


    public boolean validateMove(ChessBoard chessBoard, BoardCell finishPosition) {
        int xAxisDiff = finishPosition.getXaxis()- boardCell.getXaxis();
        int yAxisDiff = finishPosition.getYaxis()- boardCell.getYaxis();
        if (UtilClass.modulo(xAxisDiff)==2){
            if(UtilClass.modulo(yAxisDiff)==1){
                return true;
            }
        }
        if (UtilClass.modulo(xAxisDiff)==1){
            if(UtilClass.modulo(yAxisDiff)==2){
                return true;
            }
        }
        return false;
    }


    public ChessPiece movePiece(ChessBoard chessBoard, BoardCell finishPosition) throws IllegalMoveException {
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
