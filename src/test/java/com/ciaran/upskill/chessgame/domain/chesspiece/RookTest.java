package com.ciaran.upskill.chessgame.domain.chesspiece;

import com.ciaran.upskill.chessgame.domain.ChessBoard;
import com.ciaran.upskill.chessgame.exceptions.IllegalMoveException;
import com.ciaran.upskill.chessgame.domain.BoardCell;
import org.junit.Before;
import org.junit.Test;

import static com.ciaran.upskill.chessgame.Colour.BLACK;
import static com.ciaran.upskill.chessgame.Colour.WHITE;
import static com.ciaran.upskill.chessgame.domain.chesspiece.Pawn.Direction.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RookTest {

    private ChessBoard chessBoard;
    private ChessPiece chessPiece;

    @Before
    public void setup(){
        chessBoard = new ChessBoard();
        chessPiece = new Rook(new BoardCell(1,4),BLACK);
        chessBoard.addPiece(chessPiece);
    }


    @Test
    public void test_validate_move_detects_a_legal_move(){
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(3,4)), is(true));
    }

    @Test
    public void test_validate_move_detects_an_illegal_move(){
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(2,6)), is(false));
    }

    @Test
    public void test_validate_move_detects_a_move_passing_through_another_piece(){
        chessBoard.addPiece(new Pawn(new BoardCell(2,4), BLACK, UP));
        assertThat(chessPiece.validateMove(chessBoard, new BoardCell(3,4)), is(false));
    }

    @Test
    public void test_move_piece_updates_piece_and_board(){
        BoardCell finish = new BoardCell(4,4);
        try {
            chessPiece.move(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertThat(chessBoard.getPieceByLocation(finish), is(equalTo(chessPiece)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(1,4)), is(equalTo(null)));
    }

    @Test
    public void test_move_piece_takes_other_piece_from_board(){
        BoardCell finish = new BoardCell(4,4);
        ChessPiece victim = new Rook(finish, WHITE);
        chessBoard.addPiece(victim);
        ChessPiece removedPiece = null;
        try {
            removedPiece = chessPiece.move(chessBoard, finish);
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        assertThat(chessBoard.getPieceByLocation(finish), is(equalTo(chessPiece)));
        assertThat(chessBoard.getPieceByLocation(new BoardCell(1,4)), is(equalTo(null)));
        assertThat(chessBoard.contains(victim), is(false));
        assertThat(removedPiece, is(equalTo(victim)));
    }
}
