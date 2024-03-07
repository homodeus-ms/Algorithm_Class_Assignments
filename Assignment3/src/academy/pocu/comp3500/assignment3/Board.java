package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;

import java.util.ArrayList;


public final class Board {
    private static final int BOARD_SIZE = 8;
    private static final int MAX_PIECE_COUNT = 16;
    private final static char[] board = new char[BOARD_SIZE * BOARD_SIZE];
    private final Player player;
    private final boolean playerIsWhite;

    private int[] myPiecePosIndexes = new int[MAX_PIECE_COUNT];
    private char[] myPieceTypeInIndexes = new char[MAX_PIECE_COUNT];
    private int myPosSize = 0;
    private int[] opPiecePosIndexes = new int[MAX_PIECE_COUNT];
    private char[] opPieceTypeInIndexes = new char[MAX_PIECE_COUNT];
    private int opPosSize = 0;

    private int myKingPos = 0;
    private int opKingPos = 0;
    private boolean isMyKingInDanger = false;
    private boolean isOpKingInDanger = false;


    public Board(Player player, boolean playerIsWhite) {
        this.playerIsWhite = playerIsWhite;
        this.player = player;
        for (int i = 0; i < MAX_PIECE_COUNT; ++i) {
            myPiecePosIndexes[i] = -1;
            opPiecePosIndexes[i] = -1;
        }
    }

    public char[] getBoard() {
        return board;
    }
    public int[] getMyPiecePosIndexes() {
        return this.myPiecePosIndexes;
    }
    public char[] getMyPieceTypeInIndexes() {
        return this.myPieceTypeInIndexes;
    }
    public int[] getOpPiecePosIndexes() {
        return this.opPiecePosIndexes;
    }
    public char[] getOpPieceTypeInIndexes() {
        return this.opPieceTypeInIndexes;
    }
    public int getMyPosSize() {
        return this.myPosSize;
    }
    public int getOpPosSize() {
        return this.opPosSize;
    }
    public int getKingPos(boolean isMine) {
        return isMine ? myKingPos : opKingPos;
    }
    public boolean isKingInDanger(boolean isMine) {
        return isMine ? isMyKingInDanger : isOpKingInDanger;
    }
    public void setBoard(char[][] newBoard) {
        for (int y = 0; y < BOARD_SIZE; ++y) {
            for (int x = 0; x < BOARD_SIZE; ++x) {
                board[y * BOARD_SIZE + x] = newBoard[y][x];
            }
        }

        setPiecePositions();
    }
    public void insertMove(Move move, boolean isMine) {
        int from = move.fromY * BOARD_SIZE + move.fromX;
        int to = move.toY * BOARD_SIZE + move.toX;

        char c = board[from];
        board[from] = 0;
        board[to] = c;

        if (isMine) {
            updateMyPiecePos(from, to);
            deleteOpPiecePos(to);
        } else {
            updateOpPiecePos(from, to);
            deleteMyPiecePos(to);
        }

        //logPiecePos();
    }
    public void insertMove(int from, int to, boolean isMine) {
        char c = board[from];
        board[from] = 0;
        board[to] = c;

        if (isMine) {
            updateMyPiecePos(from, to);
            deleteOpPiecePos(to);
            if ((c | 0x20) == 'k') {
                myKingPos = to;
            }
        } else {
            updateOpPiecePos(from, to);
            deleteMyPiecePos(to);
            if ((c | 0x20) == 'k') {
                opKingPos = to;
            }
        }

    }
    public void insertMove(int from, int to, boolean isMine, char capturedPiece) {

        board[to] = board[from];
        board[from] = capturedPiece;

        if (isMine) {
            updateMyPiecePos(from, to);
            addPosToOppenent(from, capturedPiece);
        } else {
            updateOpPiecePos(from, to);
            addPosToMine(from, capturedPiece);
        }

    }


    public void setPiecePositions() {

        if (playerIsWhite) {
            for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; ++i) {
                char c = board[i];
                if (c == 0) {
                    continue;
                }

                if (c >= 'b') {
                    myPiecePosIndexes[myPosSize] = i;
                    myPieceTypeInIndexes[myPosSize++] = c;
                } else {
                    opPiecePosIndexes[opPosSize] = i;
                    opPieceTypeInIndexes[opPosSize++] = c;
                }
            }

        } else {
            for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; ++i) {
                char c = board[i];
                if (c == 0) {
                    continue;
                }

                if (c >= 'b') {
                    opPiecePosIndexes[opPosSize] = i;
                    opPieceTypeInIndexes[opPosSize++] = c;
                } else {
                    myPiecePosIndexes[myPosSize] = i;
                    myPieceTypeInIndexes[myPosSize++] = c;
                }
            }
        }

        updateKingPos(true);
        updateKingPos(false);
    }


    public void updateMyPiecePos(int from, int to) {

        for (int i = 0; i < myPosSize; ++i) {
            if (myPiecePosIndexes[i] == from) {
                myPiecePosIndexes[i] = to;

                if ((myPieceTypeInIndexes[i] | 0x20) == 'k') {
                    myKingPos = to;
                }

                break;
            }
        }
    }
    public void deleteMyPiecePos(int pos) {
        for (int i = 0; i < myPosSize; ++i) {
            assert (myPiecePosIndexes[i] != -1);

            if (myPiecePosIndexes[i] == pos) {
                --myPosSize;
                myPiecePosIndexes[i] = myPiecePosIndexes[myPosSize];
                myPiecePosIndexes[myPosSize] = -1;
                myPieceTypeInIndexes[i] = myPieceTypeInIndexes[myPosSize];
                myPieceTypeInIndexes[myPosSize] = 0;
                break;
            }
        }
    }
    public void updateOpPiecePos(int from, int to) {
        for (int i = 0; i < opPosSize; ++i) {
            if (opPiecePosIndexes[i] == from) {
                opPiecePosIndexes[i] = to;

                if ((opPieceTypeInIndexes[i] | 0x20) == 'k') {
                    opKingPos = to;
                }
                break;
            }

        }
    }
    public void deleteOpPiecePos(int pos) {
        for (int i = 0; i < opPosSize; ++i) {
            assert (opPiecePosIndexes[i] != -1);

            if (opPiecePosIndexes[i] == pos) {
                --opPosSize;
                opPiecePosIndexes[i] = opPiecePosIndexes[opPosSize];
                opPiecePosIndexes[opPosSize] = -1;
                opPieceTypeInIndexes[i] = opPieceTypeInIndexes[opPosSize];
                opPieceTypeInIndexes[opPosSize] = 0;
                break;
            }
        }
    }
    private void addPosToMine(int pos, char piece) {
        myPiecePosIndexes[myPosSize] = pos;
        myPieceTypeInIndexes[myPosSize++] = piece;
    }
    private void addPosToOppenent(int pos, char piece) {
        opPiecePosIndexes[opPosSize] = pos;
        opPieceTypeInIndexes[opPosSize++] = piece;
    }

    private void updateKingPos(boolean isMine) {
        if (isMine) {
            for (int i = 0; i < myPosSize; ++i) {
                int c = myPieceTypeInIndexes[i] | 0x20;
                if (c == 'k') {
                    myKingPos = myPiecePosIndexes[i];
                    break;
                }
            }
        } else {
            for (int i = 0; i < opPosSize; ++i) {
                int c = opPieceTypeInIndexes[i] | 0x20;
                if (c == 'k') {
                    opKingPos = opPiecePosIndexes[i];
                    break;
                }
            }
        }

    }
    public boolean isCheckMate(boolean isMine) {

        if (isMine) {
            isMyKingInDanger = false;
            if (checkKingSafety(player.isWhite())) {
                isMyKingInDanger = true;
                return true;
            }
        } else {
            isOpKingInDanger = false;
            if (checkKingSafety(!player.isWhite())) {
                isOpKingInDanger = true;
                return true;
            }
        }

        return false;
    }

    private boolean checkKingSafety(boolean playerisWhite) {

        final char opK = playerisWhite ? 'K' : 'k';
        final char opQ = playerisWhite ? 'Q' : 'q';
        final char opR = playerisWhite ? 'R' : 'r';
        final char opB = playerisWhite ? 'B' : 'b';
        final char opN = playerisWhite ? 'N' : 'n';

        int kingPos = player.isWhite() == playerisWhite ? myKingPos : opKingPos;

        int pos = kingPos - 8;
        if (pos >= 0 && board[pos] == opK) {
            return true;
        }
        while (pos >= 0) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            }
            if (c == opR || c == opQ) {
                return true;
            }
            pos -= 8;
        }
        // South
        pos = kingPos + 8;
        if (pos <= 63 && board[pos] == opK) {
            return true;
        }
        while (pos <= 63) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opR || c == opQ) {
                return true;
            }
            pos += 8;
        }
        // East
        pos = kingPos + 1;
        int row = kingPos / BOARD_SIZE;
        if (pos < 8 * (row + 1) && board[pos] == opK) {
            return true;
        }

        while (pos < 8 * (row + 1)) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opR || c == opQ) {
                return true;
            }
            ++pos;
        }
        // West
        pos = kingPos - 1;
        if (pos >= 8 * row && board[pos] == opK) {
            return true;
        }
        while (pos >= 8 * row) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opR || c == opQ) {
                return true;
            }
            --pos;
        }

        // North-West
        pos = kingPos - 9;
        int col = kingPos % BOARD_SIZE;
        int count = Math.min(col, row);
        if ((col > 0 && row > 0) && (board[pos] == opK || (playerisWhite && board[pos] == 'P'))) {
            return true;
        }
        while (count > 0) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opB || c == opQ) {
                return true;
            }
            --count;
            pos -= 9;
        }

        // South-West
        pos = kingPos + 7;
        count = Math.min(col, 7 - row);
        if ((col > 0 && row < 7) && (board[pos] == opK || (!playerisWhite && board[pos] == 'p'))) {
            return true;
        }
        while (count > 0) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opB || c == opQ) {
                return true;
            }
            --count;
            pos += 7;
        }

        // North-Eest
        pos = kingPos - 7;
        count = Math.min(7 - col, row);
        if ((col < 7 && row > 0) && (board[pos] == 'K' || (playerisWhite && board[pos] == 'P'))) {
            return true;
        }
        while (count > 0) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opB || c == opQ) {
                return true;
            }
            --count;
            pos -= 7;
        }

        // South-Eest
        pos = kingPos + 9;
        count = Math.min(7 - col, 7 - row);
        if ((col < 7 && row < 7) && (board[pos] == 'K' || (!playerisWhite && board[pos] == 'p'))) {
            return true;
        }
        while (count > 0) {
            char c = board[pos];
            if (isMyPiece(c, playerIsWhite)) {
                break;
            } else if (c == opB || c == opQ) {
                return true;
            }
            --count;
            pos += 9;
        }

        boolean isNightCheck = false;
        if (player.isWhite() == playerisWhite) {
            for (int i = 0; i < opPosSize; ++i) {
                if (opPieceTypeInIndexes[i] == opN) {
                    isNightCheck = player.isNightCheckMate(opPiecePosIndexes[i], kingPos);
                }
                if (isNightCheck) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < myPosSize; ++i) {
                if (myPieceTypeInIndexes[i] == opN) {
                    isNightCheck = player.isNightCheckMate(myPiecePosIndexes[i], kingPos);
                }
                if (isNightCheck) {
                    return true;
                }
            }
        }


        return false;
    }
    private boolean isMyPiece(char c, boolean playerIsWhite) {
        return playerIsWhite ? c >= 'b' : (c != 0 && c <= 'R');
    }

    /*public void logPiecePos() {
        System.out.println("===== myPieces =====");
        for (int i = 0; i < myPosSize; ++i) {
            System.out.printf("%c(%d) ", myPieceTypeInIndexes[i], myPiecePosIndexes[i]);
        }
        System.out.println();
        System.out.println("===== opPieces =====");
        for (int i = 0; i < opPosSize; ++i) {
            System.out.printf("%c(%d) ", opPieceTypeInIndexes[i], opPiecePosIndexes[i]);
        }
        System.out.println();
    }*/
}
