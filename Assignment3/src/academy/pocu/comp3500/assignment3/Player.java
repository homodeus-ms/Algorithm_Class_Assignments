package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
//import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Player extends PlayerBase {
    private static final char[] START_BOARD = {
            'R', 'N', 'B', 'K', 'Q', 'B', 'N', 'R',
            'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P',
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p',
            'r', 'n', 'b', 'k', 'q', 'b', 'n', 'r',
    };
    // DIR = { N, S, W, E, NW, SE, NE, SW }
    // consts
    private static final int N_IDX = 0;
    private static final int S_IDX = 1;
    private static final int W_IDX = 2;
    private static final int E_IDX = 3;
    private static final int NW_IDX = 4;
    private static final int SE_IDX = 5;
    private static final int NE_IDX = 6;
    private static final int SW_IDX = 7;
    private static final int IDX_END = 8;

    private static final int MAX_MOVE_IN_A_TURN = 138;
    private static final int BOARD_SIZE = 8;
    private static final int BOARD_LAST_IDX = 63;
    private static final long minReserveTime = 100;

    public int preWhiteScore = 1400;
    public int preBlackScore = 1400;
    public int whiteScore = 1400;
    public int blackScore = 1400;

    // board 관련
    private final byte[][] remainCountsToEdge;
    private char[] board = new char[BOARD_SIZE * BOARD_SIZE];
    private char[] copyBoard = new char[BOARD_SIZE * BOARD_SIZE];
    ArrayList<Byte> moves = new ArrayList<>();

    /*private Stack<Byte> myPiecesIdx = new Stack<>();
    private Stack<Byte> opponentPiecesIdx = new Stack<>();*/

    /*private byte[] myPiecesIdx = new byte[16];
    private byte myPiecesCount = 0;
    private byte[] opponentPiecesIdx = new byte[16];
    private byte opponentPiecesCount = 0;


    // moves = {fromIdx, toIdx}

    public byte[] myMoves = new byte[MAX_MOVE_IN_A_TURN * 2];
    public int myMovesCount = 0;
    public int[] myScores = new int[MAX_MOVE_IN_A_TURN];
    public int myScoresCount = 0;

    public int bestScore;
    public int[] bestScoreInfo = new int[3];
    public int worstScore;
    public int[] worstScoreInfo = new int[3];
    public int sum;


    // opponent 관련
    public Player opponent;
    public byte[] oppoMoves = new byte[MAX_MOVE_IN_A_TURN * 2];
    public int oppoMovesCount = 0;
    public int[] oppoScores = new int[MAX_MOVE_IN_A_TURN];
    public int oppoScoresCount = 0;

    public int whitePieceCheckRemainCount = 16;
    public int blackPieceCheckRemainCount = 16;*/

    // move 관련
    private char capturedPiece = 0;

    // result 관련
    private byte[] bestMove = new byte[2];


    // etc
    private static final byte[] NIGHT_MOVE_OFFSET = {-10, -17, -15, -6, 10, 17, 15, 6};
    // { DIR_EASE|WEST, DIR_NORTH|SOUTH, needSpace1, needSpace2 }
    private static final byte[] NIGHT_MOVE_HELPER = {2, 0, 2, 1, 2, 0, 1, 2, 3, 0, 1, 2, 3, 0, 2, 1, 3, 1, 2, 1, 3, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 1};
    //Random random = new Random();

    private final double sqrtLimitTime;

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);

        sqrtLimitTime = Math.sqrt(maxMoveTimeMilliseconds);

        Helper helper = new Helper();
        this.remainCountsToEdge = Helper.getRemainCountsToEdge();
    }



    public Move getNextMove(char[][] board) {
        boolean isStartBoard = true;
        for (int y = 0; y < BOARD_SIZE; ++y) {
            for (int x = 0; x < BOARD_SIZE; ++x) {
                if (board[y][x] != START_BOARD[y * BOARD_SIZE + x]) {
                    isStartBoard = false;
                    break;
                }
            }
        }

        Move move;

        if (this.isWhite() && isStartBoard) {
            move = new Move(3, 6, 3, 4);
        } else {
            move = getNextMove(board, new Move(0, 0, 0, 0));
        }

        return move;
    }

    public Move getNextMove(char[][] board, Move opponentMove) {
        copyBoard(this.board, board);
        int maxPoint = 0;
        int point = 0;
        Move maxResult = new Move(0, 0, 0, 0);
        Move result = new Move(0, 0, 0, 0);
        //int[] result = new int[5];

        long start;
        long end;
        long deltaTime = 0;
        int depth = 2;

        preWhiteScore = whiteScore;
        preBlackScore = blackScore;

        maxPoint = mimimax(this.board, depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, this.isWhite(), result);

        maxResult.fromX = result.fromX;
        maxResult.fromY = result.fromY;
        maxResult.toX = result.toX;
        maxResult.toY = result.toY;

        ++depth;

        while (true) {
            start = System.nanoTime();
            whiteScore = preWhiteScore;
            blackScore = preBlackScore;
            point = mimimax(this.board, depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, this.isWhite(), result);


            end = System.nanoTime();
            deltaTime = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
            //System.out.printf("limit Time : %d\n", getMaxMoveTimeMilliseconds());
            //System.out.printf("depth : %d\n", depth);
            //System.out.printf("deltaTime : %d\n", deltaTime);


            if (point > maxPoint) {
                maxPoint = point;
                maxResult.fromX = result.fromX;
                maxResult.fromY = result.fromY;
                maxResult.toX = result.toX;
                maxResult.toY = result.toY;
            }

            if (deltaTime >= sqrtLimitTime) {
                break;
            }

            ++depth;


        }



        return maxResult;
    }
    private int mimimax(char[] board, int depth, int startDepth, int alpha, int beta, boolean isMyTurn, boolean playerIsWhite, Move result) {
        if (depth == 0) {
            return evaluate(board, this.isWhite());
        }

        ArrayList<Byte> moves = getMoves(board, playerIsWhite);

        if (moves.isEmpty()) {
            return evaluate(board, this.isWhite());
        }

        if (isMyTurn) {
            int maxValue = Integer.MIN_VALUE;
            int currValue;
            int movesSize = moves.size();

            for (int i = 0; i < movesSize; i += 2) {
                byte from = moves.get(i);
                byte to = moves.get(i + 1);
                char existingPiece = board[to];


                move(board, playerIsWhite, from, to);
                currValue = mimimax(board, depth - 1, startDepth, alpha, beta, false, !playerIsWhite, result);
                cancelMove(board, playerIsWhite, to, from, existingPiece);


                if (currValue > maxValue) {
                    maxValue = currValue;
                    bestMove[0] = from;
                    bestMove[1] = to;

                    if (depth == startDepth) {
                        result.fromX = bestMove[0] % BOARD_SIZE;
                        result.fromY = bestMove[0] / BOARD_SIZE;
                        result.toX = bestMove[1] % BOARD_SIZE;
                        result.toY = bestMove[1] / BOARD_SIZE;

                    }

                    alpha = Math.max(alpha, maxValue);
                }

                if (alpha >= beta) {
                    break;
                }
            }

            return maxValue;

        } else {
            int minValue = Integer.MAX_VALUE;
            int currValue;
            int movesSize = moves.size();

            for (int i = 0; i < movesSize; i += 2) {
                byte from = moves.get(i);
                byte to = moves.get(i + 1);
                char existingPiece = board[to];

                move(board, playerIsWhite, from, to);
                currValue = mimimax(board, depth - 1, startDepth, alpha, beta, true, !playerIsWhite, result);
                cancelMove(board, playerIsWhite, to, from, existingPiece);

                if (currValue < minValue) {
                    minValue = currValue;
                    bestMove[0] = from;
                    bestMove[1] = to;
                    beta = Math.min(beta, minValue);
                }

                if (alpha >= beta) {
                    break;
                }
            }

            return minValue;
        }
    }
    private ArrayList<Byte> getMoves(char[] board, boolean playerIsWhite) {
        ArrayList<Byte> moves = new ArrayList<>();
        moves.ensureCapacity(MAX_MOVE_IN_A_TURN * 2);


        for (byte i = 0; i <= BOARD_LAST_IDX; ++i) {
            if (board[i] == 0) {
                continue;
            }

            if (playerIsWhite && board[i] >= 'b') {
                getAvailableMoves(board, moves, playerIsWhite, i);
                //getLimitedMoves(board, moves, playerIsWhite, i);
            } else if (!playerIsWhite && board[i] <= 'R') {
                getAvailableMoves(board, moves, playerIsWhite, i);
                //getLimitedMoves(board, moves, playerIsWhite, i);
            }
        }
        return moves;
    }

    private int evaluate(char[] board, boolean playerIsWhite) {
        return playerIsWhite ? whiteScore - blackScore : blackScore - whiteScore;
    }

    private boolean isGameOver(boolean playerIsWhite) {
        return playerIsWhite ? whiteScore <= 400 : blackScore <= 400;
    }

    public void getAvailableMoves(char[] board, ArrayList<Byte> moves, boolean playerIsWhite, byte idx) {

        char piece;
        piece = playerIsWhite ? board[idx] : ((char) (board[idx] ^ 0x20));

        // dir = { N, S, W, E, NW, SE, NE, SW }
        byte[] remainsToEdge = remainCountsToEdge[idx];
        byte to;
        byte dirValue;

        switch (piece){
            case 'p':
                if (playerIsWhite) {
                    to = (byte) (idx + Dir.NW);
                    if (remainsToEdge[NW_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.NE);
                    if (remainsToEdge[NE_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + 2 * Dir.N);
                    if ((idx >=48 && idx <= 55) && (board[idx + Dir.N] == 0) && (board[to] == 0)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.N);
                    if (remainsToEdge[N_IDX] != 0 && board[to] == 0) {
                        moves.add(idx);
                        moves.add(to);
                    }
                } else {
                    to = (byte) (idx + Dir.SW);
                    if (remainsToEdge[SW_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.SE);
                    if (remainsToEdge[SE_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }

                    to = (byte) (idx + 2 * Dir.S);
                    if ((idx >= 8 && idx <= 15) && (board[idx + Dir.S] == 0) && (board[to] == 0)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.S);
                    if (remainsToEdge[S_IDX] != 0 && board[to] == 0) {
                        moves.add(idx);
                        moves.add(to);
                    }
                }

                break;

            case 'n':
                assert (NIGHT_MOVE_HELPER.length == 32);

                byte helperIdx = 0;
                for (int i = 0; i < 8; ++i) {

                    byte dir1 = NIGHT_MOVE_HELPER[helperIdx++];
                    byte dir2 = NIGHT_MOVE_HELPER[helperIdx++];
                    byte needSpaceForDir1 = NIGHT_MOVE_HELPER[helperIdx++];
                    byte needSpaceForDir2 = NIGHT_MOVE_HELPER[helperIdx++];

                    // 움직일 여유 공간이 없는 경우 continue;
                    if (remainsToEdge[dir1] < needSpaceForDir1 || remainsToEdge[dir2] < needSpaceForDir2) {
                        continue;
                    }

                    to = (byte) (idx + NIGHT_MOVE_OFFSET[i]);
                    if (isMoveablePlace(board, idx, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                }

                break;

            case 'b':
                for (byte dir = NW_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(board, idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(board, playerIsWhite, dirValue)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                break;

            case 'r':
                for (byte dir = N_IDX; dir <= E_IDX; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(board, idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(board, playerIsWhite, dirValue)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                break;

            case 'q':
                for (byte dir = N_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(board, idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(board, playerIsWhite, dirValue)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                break;
            case 'k':
                for (byte dir = N_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    if (to == 0) {
                        continue;
                    }
                    dirValue = (byte) (idx + Dir.offset[dir]);
                    if (isMoveablePlace(board, idx, dirValue)) {
                        moves.add(idx);
                        moves.add(dirValue);
                    }
                }
                break;

            default:
                break;
        }
    }

    public void getLimitedMoves(char[] board, ArrayList<Byte> moves, boolean playerIsWhite, byte idx) {

        char piece;
        piece = playerIsWhite ? board[idx] : ((char) (board[idx] ^ 0x20));

        // dir = { N, S, W, E, NW, SE, NE, SW }
        byte[] remainsToEdge = remainCountsToEdge[idx];
        byte to;
        byte dirValue;

        switch (piece){
            case 'p':
                if (playerIsWhite) {
                    to = (byte) (idx + Dir.NW);
                    if (remainsToEdge[NW_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.NE);
                    if (remainsToEdge[NE_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.N);
                    if (remainsToEdge[N_IDX] != 0 && board[to] == 0) {
                        moves.add(idx);
                        moves.add(to);
                    }
                } else {
                    to = (byte) (idx + Dir.SW);
                    if (remainsToEdge[SW_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to = (byte) (idx + Dir.SE);
                    if (remainsToEdge[SE_IDX] != 0 && isEnemyPiece(board, playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }

                    to = (byte) (idx + Dir.S);
                    if (remainsToEdge[S_IDX] != 0 && board[to] == 0) {
                        moves.add(idx);
                        moves.add(to);
                    }
                }

                break;

            case 'n':
                assert (NIGHT_MOVE_HELPER.length == 32);

                byte helperIdx = 0;
                for (int i = 0; i < 2; ++i) {

                    byte dir1 = NIGHT_MOVE_HELPER[helperIdx++];
                    byte dir2 = NIGHT_MOVE_HELPER[helperIdx++];
                    byte needSpaceForDir1 = NIGHT_MOVE_HELPER[helperIdx++];
                    byte needSpaceForDir2 = NIGHT_MOVE_HELPER[helperIdx++];

                    // 움직일 여유 공간이 없는 경우 continue;
                    if (remainsToEdge[dir1] < needSpaceForDir1 || remainsToEdge[dir2] < needSpaceForDir2) {
                        continue;
                    }

                    to = (byte) (idx + NIGHT_MOVE_OFFSET[i]);
                    if (isMoveablePlace(board, idx, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                }

                break;

            case 'b':
                for (byte dir = NW_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= Math.min(to, 1); ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(board, idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(board, playerIsWhite, dirValue)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                break;

            case 'r':
                for (byte dir = N_IDX; dir <= E_IDX; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= Math.min(to, 2); ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(board, idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(board, playerIsWhite, dirValue)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                break;

            case 'q':
                for (byte dir = N_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= Math.min(to, 1); ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(board, idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(board, playerIsWhite, dirValue)) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                break;
            case 'k':
                for (byte dir = N_IDX; dir < 4; ++dir) {
                    to = remainsToEdge[dir];
                    if (to == 0) {
                        continue;
                    }
                    dirValue = (byte) (idx + Dir.offset[dir]);
                    if (isMoveablePlace(board, idx, dirValue)) {
                        moves.add(idx);
                        moves.add(dirValue);
                    }
                }
                break;

            default:
                break;
        }
    }

    private void calculatePoint(char capturedPiece, boolean playerIsWhite) {
        capturedPiece |= 0x20;
        int colorValue = playerIsWhite ? 1 : -1;

        switch (capturedPiece) {
            case 'p':
                whiteScore += 10 * colorValue;
                blackScore -= 10 * colorValue;
                return;
            case 'n':
                // intentional fall through
            case 'b':
                whiteScore += 30 * colorValue;
                blackScore -= 30 * colorValue;
                return;
            case 'r':
                whiteScore += 50 * colorValue;
                blackScore -= 50 * colorValue;
                return;
            case 'q':
                whiteScore += 100 * colorValue;
                blackScore -= 100 * colorValue;
                return;
            case 'k':
                whiteScore += 1000 * colorValue;
                blackScore -= 1000 * colorValue;
                return;
            default:
                assert (false);
                return;
        }
    }

    private void calculateStrategyPoint(char[] board, boolean playerIsWhite, byte from, byte to) {

        char movePiece = board[from];
        //movePiece |= 0x20;
        int colorValue = playerIsWhite ? 1 : -1;

        switch (movePiece) {
            case 'p':
                if (board[to - 9] == 'p' || board[to - 7] == 'p') {
                    whiteScore += 5 * colorValue;
                    blackScore -= 5 * colorValue;
                }
                return;

            case 'P':
                if (board[to + 9] == 'P' || board[to + 7] == 'P') {
                    whiteScore += 5 * colorValue;
                    blackScore -= 5 * colorValue;
                }
                return;
            default:
                return;
        }
    }

    private boolean isEnemyPiece(char[] board, boolean playerIsWhite, int idx) {
        return playerIsWhite ? (board[idx] != 0 && board[idx] <= 'R') : (board[idx] >= 'b');
    }
    private boolean isMoveablePlace(char[] board, int from, int to) {
        if (board[to] == 0) {
            return true;
        }
        return board[from] <= 'R' ? board[to] >= 'b' : board[to] <= 'R';
    }
    private void copyBoard(char[] dst, char[][] src) {
        for (int j = 0; j < BOARD_SIZE; ++j) {
            for (int i = 0; i < BOARD_SIZE; ++i) {
                dst[j * BOARD_SIZE + i] = src[j][i];
            }
        }
    }
    private void copyBoard(char[] dst, char[] src) {
        for (int i = 0; i < src.length; ++i) {
            dst[i] = src[i];
        }
    }
    private void insertMove(Move move) {
        this.board[move.toY * BOARD_SIZE + move.toX] = this.board[move.fromY * BOARD_SIZE + move.fromX];
        this.board[move.fromY * BOARD_SIZE + move.fromX] ^= this.board[move.fromY * BOARD_SIZE + move.fromX];
    }
    private void move(char[] board, boolean playerIsWhite, byte from, byte to) {
        char capturedPiece = board[to];

        //preWhiteScore = whiteScore;
        //preBlackScore = blackScore;

        if (capturedPiece != 0) {
            calculatePoint(capturedPiece, playerIsWhite);
        } /*else if (board[from] == 'p') {
            calculateStrategyPoint(board, playerIsWhite, from, to);
        }*/

        board[to] = board[from];
        board[from] = 0;
    }
    private void cancelMove(char[] board, boolean playerIsWhite, int from, int to, char existingPiece) {
        if (existingPiece != 0) {
            calculatePoint(existingPiece, !playerIsWhite);
        }

        //whiteScore = preWhiteScore;
        //blackScore = preBlackScore;

        board[to] = board[from];
        board[from] = existingPiece;
    }
    /*private void getRandomMove(ArrayList<Byte> moves, Move result) {
        int pick = random.nextInt(moves.size()) / 2;
        int from = moves.get(pick);
        int to = moves.get(pick + 1);
        result.fromX = from % BOARD_SIZE;
        result.fromY = from / BOARD_SIZE;
        result.toX = to % BOARD_SIZE;
        result.toY = to / BOARD_SIZE;
    }*/



}
