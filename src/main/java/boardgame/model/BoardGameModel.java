package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

import java.util.*;

public class BoardGameModel {

    public static int BOARD_SIZE_ROW= 6;
    public static int BOARD_SIZE_COLUMN = 8;

    private final Piece[] pieces;

    private boolean [][] visited;

    public enum Player{
        PLAYER1, PLAYER2;

        public Player next(){
            return switch (this){
                case PLAYER1 -> PLAYER2;
                case PLAYER2 -> PLAYER1;
            };
        }
    }

    private ReadOnlyObjectWrapper<Player> nextPlayer = new ReadOnlyObjectWrapper<Player>();



    public BoardGameModel() {
        this(new Piece(PieceType.WHITE, new Position(2, 0)),
                new Piece(PieceType.BLACK, new Position(3, 7)));
        visited = new boolean[BOARD_SIZE_ROW][BOARD_SIZE_COLUMN];
        visited [2][0] = true;
        visited [3][7] = true;
        nextPlayer.set(Player.PLAYER1);




    }


    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (! isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    public int getPieceCount() {
        return pieces.length;
    }

    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }


    public boolean isValidMove(int pieceNumber, PawnDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (! isOnBoard(newPosition)) {
            return false;
        }
        if (visited[newPosition.row()][newPosition.col()] == true)
        {
            return false;
        }
        for (var piece : pieces) {
            if (piece.getPosition().equals(newPosition)) {
                return false;
            }
        }
        return true;
    }

    public Set<PawnDirection> getValidMoves(int pieceNumber) {
        EnumSet<PawnDirection> validMoves = EnumSet.noneOf(PawnDirection.class);
        for (var direction : PawnDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public boolean getWhiteHasMoves(){
        if(getValidMoves(0).size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean getBlackHasMoves(){
        if(getValidMoves(1).size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isGameOver(){
        if(getWhiteHasMoves() == true && getBlackHasMoves() == true)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public void move(int pieceNumber, PawnDirection direction) {
        Position oldPos = getPiecePosition(pieceNumber);
        this.pieces[pieceNumber].moveTo(direction);
        visited[oldPos.row()][oldPos.col()] = true;
        if(isGameOver() == true)
        {
            Logger.info("A játéknak vége!");
        }
        else {
            nextPlayer.set(nextPlayer.get().next());
        }
    }

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_SIZE_ROW
                && 0 <= position.col() && position.col() < BOARD_SIZE_COLUMN;
    }

    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>(pieces.length);
        PieceType currentType = switch (nextPlayer.get()){
            case PLAYER1 -> PieceType.WHITE;
            case PLAYER2 -> PieceType.BLACK;
        };
        for (var piece : pieces) {
            if (piece.getType().equals(currentType)) {
                positions.add(piece.getPosition());
            }
        }
        return positions;
    }

    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        System.out.println(model);
    }

}
