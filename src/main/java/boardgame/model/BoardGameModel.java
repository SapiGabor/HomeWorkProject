package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

import java.util.*;

public class BoardGameModel {
    /**
     * The row size of the board is given here.
     */
    public static int BOARD_SIZE_ROW= 6;
    /**
     * The col size of the board is given here.
     */
    public static int BOARD_SIZE_COLUMN = 8;
    /**
     * It is an array which records the pieces on the board.
     */
    private final Piece[] pieces;
    /**
     * This matrix stores which squares were visited.
     */
    private boolean [][] visited;
    /**
     * This is the enum which stores that which Players can be used.
     */
    public enum Player{
        PLAYER1, PLAYER2;

        public Player next(){
            return switch (this){
                case PLAYER1 -> PLAYER2;
                case PLAYER2 -> PLAYER1;
            };
        }
    }
    /**
     * It records which player turns.
     */
    private ReadOnlyObjectWrapper<Player> nextPlayer = new ReadOnlyObjectWrapper<Player>();
    /**
     * This it he begining of the board.
     * It places the white and black king to it's designated places.
     * This places should be visited at the start, so this two positions will store in
     * the visited matrix and will be set to true.
     * It records that PLAYER1 will start the game.
     */
    public BoardGameModel() {
        this(new Piece(PieceType.WHITE, new Position(2, 0)),
                new Piece(PieceType.BLACK, new Position(3, 7)));
        visited = new boolean[BOARD_SIZE_ROW][BOARD_SIZE_COLUMN];
        visited [2][0] = true;
        visited [3][7] = true;
        nextPlayer.set(Player.PLAYER1);
    }
    /**
     * It's a checker constructor.
     * It checks that the {@code Piece} {@code Position} is correct.
     * It will only add pieces if {@code Position} is correct.
     */
    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }
    /**
     * Checks if the {@code Piece} array is valid.
     * @param pieces given {@code Piece} array.
     */
    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (! isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }
    /**
     * It's the piece count on the board.
     * @return pieces.
     */
    public int getPieceCount() {
        return pieces.length;
    }
    /**
     * Gets type of {@code Piece}'s.
     * @param pieceNumber is the number of {@code Piece} in the array of the pieces.
     * @return the {@code PieceType} of the {@code Piece}.
     */
    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }
    /**
     * Gets the position of the {@code Piece} number.
     * @param pieceNumber is the number of {@code Piece} in the array of the pieces.
     * @return the {@code Position} of the {@code Piece}.
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }
    /**
     * Returns positionProperty of the Piece.
     * @param pieceNumber is the number of {@code Piece} in the array of the pieces.
     * @return the positionProperty of the {@code Piece}.
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }
    /**
     * It checks that {@code Piece} can move to a direction which is given by the user.
     * @param pieceNumber is the number of {@code Piece} in the array of the pieces.
     * @param direction is the direction of {@code KingDirection} where we would like to replace the {@code Piece}.
     * @return a boolean which can be true(move is valid) or false(move is invalid).
     */
    public boolean isValidMove(int pieceNumber, KingDirection direction) {
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
    /**
     * Gets all the moves that can made by the {@code Piece}.
     * @param pieceNumber is the number of {@code Piece} in the array of the pieces.
     * @return all possible {@code KingDirection} moves that Piece can make.
     */
    public Set<KingDirection> getValidMoves(int pieceNumber) {
        EnumSet<KingDirection> validMoves = EnumSet.noneOf(KingDirection.class);
        for (var direction : KingDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }
    /**
     * Returns that white will be able to move or not.
     * @return true(can move) or false(can't move)
     */
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
    /**
     * Returns that black will be able to move or not.
     * @return true(can move) or false(can't move)
     */
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
    /**
     * Returns that the game is over or not.
     * @return true(if yes) or false(not yet).
     */
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
    /**
     * Moves the {@code Piece} to the {@code KingDirection}.
     * It checks if the game is over or not. If it's over, it will log "A játéknak vége" message.
     * If game is not over, then it will gives the turn to the nextPlayer.
     * @param pieceNumber is the number of {@code Piece} in the array of the pieces.
     * @param direction is the direction of {@code KingDirection} where we would like to replace the {@code Piece}.
     */
    public void move(int pieceNumber, KingDirection direction) {
        Position oldPos = getPiecePosition(pieceNumber);
        this.pieces[pieceNumber].moveTo(direction);

        if(isGameOver() == true)
        {
            Logger.info("A játéknak vége!");
        }
        else {
            nextPlayer.set(nextPlayer.get().next());
        }
    }
    public void removeSquare(Position position){
        visited[position.row()][position.col()] = true;
    }
    public List<Position> removeableSquares(){
        List<Position> removables = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE_ROW; i++) {
            for (int j = 0; j < BOARD_SIZE_COLUMN; j++) {
                if(visited[i][j] == false)
                {
                    removables.add(new Position(i,j));
                }
            }
        }
        return removables;
    }
    /**
     * Checking {@code Position} if it is on the board.
     * @param position is the position we want to check.
     * @return {@code Position} is on the board or not.
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_SIZE_ROW
                && 0 <= position.col() && position.col() < BOARD_SIZE_COLUMN;
    }
    /**
     * Gets {@code Piece} positions belonging to the current player.
     * @return {@code Piece} positions as arraylist.
     */
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
    /**
     * Gets the {@code Position}'s {@code Piece}'s number.
     * @param position is the position we want to check.
     * @return {@code OptionalInt} empty or {@code Piece}'s number.
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }
    /**
     * Creates a {@code String} from the present version of the {@code Piece}' on the board.
     * @return a {@code String} which represents the version of the {@code Piece}'s on the board.
     */
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
