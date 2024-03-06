package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;


public final class Board {
    private static final int BOARD_SIZE = 8;
    private static final int MAX_PIECE_COUNT = 16;
    private final static char[] board = new char[BOARD_SIZE * BOARD_SIZE];
    private final boolean playerIsWhite;

    private int[] myPiecePosIndexes = new int[MAX_PIECE_COUNT];
    private char[] myPieceTypeInIndexes = new char[MAX_PIECE_COUNT];
    private int myPosSize = 0;
    private int[] opPiecePosIndexes = new int[MAX_PIECE_COUNT];
    private char[] opPieceTypeInIndexes = new char[MAX_PIECE_COUNT];
    private int opPosSize= 0;

    public Board(boolean playerIsWhite) {
        this.playerIsWhite = playerIsWhite;
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
            updateMyPiecePos(from , to);
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
            updateMyPiecePos(from , to);
            deleteOpPiecePos(to);
        } else {
            updateOpPiecePos(from, to);
            deleteMyPiecePos(to);
        }

    }
    public void insertMove(int from, int to, boolean isMine, char capturedPiece) {

        board[to] = board[from];
        board[from] = capturedPiece;

        if (isMine) {
            updateMyPiecePos(from , to);
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


    }


    public void updateMyPiecePos(int from, int to) {
        for (int i = 0; i < myPosSize; ++i) {
            if (myPiecePosIndexes[i] == from) {
                myPiecePosIndexes[i] = to;
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
    public void logPiecePos() {
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
    }
}
