package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
//import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
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

    public int preWhiteScore = 1400;
    public int preBlackScore = 1400;
    public int whiteScore = 1400;
    public int blackScore = 1400;

    // board 관련
    private final byte[][] remainCountsToEdge;
    private char[] board = null;
    /*private byte[] myPiecePositions = new byte[16];
    private byte myPiecePositionsSize;*/
    private HashSet<Byte> myPiecePositions = new HashSet<>(16);
    private HashSet<Byte> opPiecePositions = new HashSet<>(16);


    // move 관련
    private char capturedPiece = 0;
    private Move maxResult = new Move(0, 0, 0, 0);
    private Move result = new Move(0, 0, 0, 0);

    private int[] bestScores = new int[]{-1, -1, -1, -1, -1};
    private int[] bestScoresListIndexes = new int[5];
    private int bestLeastScore = bestScores[4];

    //private byte myKingPos;
    //private boolean kingPosChecked = false;

    // result, score 관련
    private byte[] bestMove = new byte[2];


    // [0] = b (30 point)  |  [9] = k (1000 point)  |  [12] = n (30 point)
    // [14] = p (10 point)  |  [15] = q (100 point)  |  [16] = r (50 point)
    private static final int[] SCORE_TABLE = {
            30, -1, -1, -1, -1, -1, -1, -1, -1, 1000,
            -1, -1, 30, -1, 10, 100, 50,
    };


    // etc
    private static final byte[] NIGHT_MOVE_OFFSET = {-10, -17, -15, -6, 10, 17, 15, 6};
    // { DIR_EASE|WEST, DIR_NORTH|SOUTH, needSpace1, needSpace2 }
    private static final byte[] NIGHT_MOVE_HELPER = {2, 0, 2, 1, 2, 0, 1, 2, 3, 0, 1, 2, 3, 0, 2, 1, 3, 1, 2, 1, 3, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 1};
    //Random random = new Random();

    private final double sqrtLimitTime;
    private boolean timeEnd = false;
    private long deltaTime;

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);

        sqrtLimitTime = Math.sqrt(maxMoveTimeMilliseconds);
        //myKingPos = isWhite ? (byte) 59 : (byte) 3;

        Helper helper = new Helper();
        this.remainCountsToEdge = Helper.getRemainCountsToEdge();
    }



    public Move getNextMove(char[][] board) {

        boolean isStartBoard = false;
        //byte myPosIdx = 0;

        this.board = new char[BOARD_SIZE * BOARD_SIZE];
        getPiecePositions(board);
        if (myPiecePositions.size() == 16 && opPiecePositions.size() == 16) {
            isStartBoard = true;
        }

        Move move;

        if (this.isWhite() && isStartBoard) {
            move = new Move(3, 6, 3, 4);
            insertMoveToBoard(move);
            updateMyPiecePositions(move.fromX, move.fromY, move.toX, move.toY);
        } else {
            move = getNextMove(board, new Move(0, 0, 0, 0));
        }

        return move;
    }

    public Move getNextMove(char[][] board, Move opponentMove) {

        if (this.board == null) {
            this.board = new char[BOARD_SIZE * BOARD_SIZE];
            getPiecePositions(board);
        } else {
            insertMoveToBoard(opponentMove);
            byte opponentMoveFromIdx = (byte) (opponentMove.fromY * BOARD_SIZE + opponentMove.fromX);
            byte opponentMoveToIdx = (byte) (opponentMove.toY * BOARD_SIZE + opponentMove.toX);
            opPiecePositions.remove(opponentMoveFromIdx);
            opPiecePositions.add(opponentMoveToIdx);
            myPiecePositions.remove(opponentMoveToIdx);
        }


        int maxPoint = Integer.MIN_VALUE;
        int point = 0;
        long start;
        long end;
        long limitTime = getMaxMoveTimeMilliseconds();
        int depth = limitTime >= 1000 ? 4 : 3;


        while (true) {

            //System.out.println(depth);

            start = System.nanoTime();
            whiteScore = preWhiteScore;
            blackScore = preBlackScore;
            point = minimax(depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE,
                    true, this.isWhite(), result);

            end = System.nanoTime();
            deltaTime = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
            /*System.out.printf("limit Time : %d\n", getMaxMoveTimeMilliseconds());
            System.out.printf("depth : %d\n", depth);
            System.out.printf("deltaTime : %d\n", deltaTime);*/


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


        insertMoveToBoard(maxResult);
        updateMyPiecePositions(maxResult.fromX, maxResult.fromY, maxResult.toX, maxResult.toY);
        opPiecePositions.remove((byte) (maxResult.toY * BOARD_SIZE + maxResult.toX));

        return maxResult;
    }
    private int minimax(int depth, int startDepth, int alpha, int beta,
                        boolean isMyTurn, boolean playerIsWhite, Move result) {
        /*if (depth == 0) {
            return evaluate(board, this.isWhite());
        }*/
        ArrayList<Byte> moves = isMyTurn ? getMyAvailableMoves() : getOpAvailableMoves();
        /*if (playerIsWhite) {
            moves = getWhiteMoves();
        } else {
            moves = getBlackMoves();
        }*/


        for (int i = 0; i < 5; ++i) {
            if (bestScores[i] == -1) {
                break;
            }
            byte tempFrom = moves.get(i * 2);
            byte tempTo = moves.get(i * 2 + 1);

            int targetIdx = bestScoresListIndexes[i];
            moves.set(i * 2, moves.get(targetIdx));
            moves.set(i * 2 + 1, moves.get(targetIdx + 1));

            moves.set(targetIdx, tempFrom);
            moves.set(targetIdx + 1, tempTo);
        }
        for (int j = 0; j < 5; ++j) {
            bestScores[j] = -1;
            bestLeastScore = bestScores[4];
        }

        if (moves.isEmpty()) {
            return evaluate(this.isWhite());
        }

        if (isMyTurn) {
            int maxValue = Integer.MIN_VALUE;
            int currValue;
            int movesSize = moves.size();
            int tempEarnScore = 0;

            for (int i = 0; i < movesSize; i += 2) {
                byte from = moves.get(i);
                byte to = moves.get(i + 1);
                char existingPiece = board[to];


                tempEarnScore = move(playerIsWhite, from, to);
                if (depth > 1) {
                    currValue = minimax(depth - 1, startDepth, alpha, beta,
                            false, !playerIsWhite, result);
                } else {
                    currValue = evaluate(this.isWhite());
                }

                cancelMove(playerIsWhite, to, from, existingPiece, tempEarnScore);


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
            int tempEarnScore = 0;

            for (int i = 0; i < movesSize; i += 2) {
                byte from = moves.get(i);
                byte to = moves.get(i + 1);
                char existingPiece = board[to];

                tempEarnScore = move(playerIsWhite, from, to);
                if (depth > 1) {
                    currValue = minimax(depth - 1, startDepth, alpha, beta,
                            true, !playerIsWhite, result);
                } else {
                    currValue = evaluate(this.isWhite());
                }

                cancelMove(playerIsWhite, to, from, existingPiece, tempEarnScore);

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

    private ArrayList<Byte> getMyAvailableMoves() {
        ArrayList<Byte> moves = new ArrayList<>(myPiecePositions.size() * 8);
        for (byte idx : myPiecePositions) {
            getAvailableMoves(moves, this.isWhite(), idx);
        }
        return moves;
    }
    private ArrayList<Byte> getOpAvailableMoves() {
        ArrayList<Byte> moves = new ArrayList<>(opPiecePositions.size() * 8);
        for (byte idx : opPiecePositions) {
            getAvailableMoves(moves, !this.isWhite(), idx);
        }
        return moves;
    }


    private ArrayList<Byte> getWhiteMoves() {
        ArrayList<Byte> moves = new ArrayList<>();
        moves.ensureCapacity(MAX_MOVE_IN_A_TURN * 2);


        for (byte i = BOARD_LAST_IDX; i >= 0; --i) {

            if (board[i] == 0) {
                continue;
            }
            getAvailableMoves(moves, true, i);

        }
        return moves;
    }


    private ArrayList<Byte> getBlackMoves() {
        ArrayList<Byte> moves = new ArrayList<>();
        moves.ensureCapacity(MAX_MOVE_IN_A_TURN * 2);


        for (byte i = 0; i <= BOARD_LAST_IDX; ++i) {

            if (board[i] == 0) {
                continue;
            }

            getAvailableMoves(moves, false, i);
        }
        return moves;
    }

    private int evaluate(boolean playerIsWhite) {
        return playerIsWhite ? whiteScore - blackScore : blackScore - whiteScore;
    }

    private boolean isGameOver(boolean playerIsWhite) {
        return playerIsWhite ? whiteScore <= 400 : blackScore <= 400;
    }

    public void getAvailableKingMoves(char[] board, ArrayList<Byte> moves, boolean playerIsWhite, byte idx) {

        // dir = { N, S, W, E, NW, SE, NE, SW }
        byte[] remainsToEdge = remainCountsToEdge[idx];
        byte to;
        byte dirValue;

        for (byte dir = N_IDX; dir < IDX_END; ++dir) {
            to = remainsToEdge[dir];
            if (to == 0) {
                continue;
            }
            dirValue = (byte) (idx + Dir.offset[dir]);
            if (isMoveablePlace(idx, dirValue)) {
                moves.add(idx);
                moves.add(dirValue);
            }
        }
    }
    public void getAvailableMoves(ArrayList<Byte> moves, boolean playerIsWhite, byte idx) {

        char piece;
        piece = playerIsWhite ? board[idx] : ((char) (board[idx] ^ 0x20));

        // dir = { N, S, W, E, NW, SE, NE, SW }
        byte[] remainsToEdge = remainCountsToEdge[idx];
        byte to;
        byte dirValue;
        int listIdx = moves.size();

        switch (piece) {
            case 'p':
                if (playerIsWhite) {
                    to = (byte) (idx + Dir.NW);
                    if (remainsToEdge[NW_IDX] != 0 && board[to] != 0 && board[to] <= 'R') {
                        moves.add(idx);
                        moves.add(to);

                        writeDownBestMoves(to, listIdx, playerIsWhite);

                        listIdx += 2;


                    }
                    to = (byte) (idx + Dir.NE);
                    if (remainsToEdge[NE_IDX] != 0 && board[to] != 0 && board[to] <= 'R') {
                        moves.add(idx);
                        moves.add(to);

                        writeDownBestMoves(to, listIdx, playerIsWhite);

                        listIdx += 2;
                    }
                    to = (byte) (idx + Dir.N);
                    if (remainsToEdge[N_IDX] != 0 && board[to] == 0) {
                        moves.add(idx);
                        moves.add(to);
                    }
                    to += Dir.N;
                    if (idx >= 48 && (board[idx + Dir.N] == 0) && (board[to] == 0)) {
                        moves.add(idx);
                        moves.add(to);
                    }

                } else {
                    to = (byte) (idx + Dir.SW);
                    if (remainsToEdge[SW_IDX] != 0 && isEnemyPiece(playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);

                        writeDownBestMoves(to, listIdx, playerIsWhite);

                        listIdx += 2;
                    }
                    to = (byte) (idx + Dir.SE);
                    if (remainsToEdge[SE_IDX] != 0 && isEnemyPiece(playerIsWhite, to)) {
                        moves.add(idx);
                        moves.add(to);

                        writeDownBestMoves(to, listIdx, playerIsWhite);

                        listIdx += 2;
                    }
                    to = (byte) (idx + Dir.S);
                    if (remainsToEdge[S_IDX] != 0 && board[to] == 0) {
                        moves.add(idx);
                        moves.add(to);
                    }

                    to += Dir.S;
                    if (idx <= 15 && (board[idx + Dir.S] == 0) && (board[to] == 0)) {
                        moves.add(idx);
                        moves.add(to);
                    }

                }

                break;

            case 'n':

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
                    if (isMoveablePlace(idx, to)) {
                        moves.add(idx);
                        moves.add(to);

                        writeDownBestMoves(to, listIdx, playerIsWhite);
                        listIdx += 2;
                    }
                }

                break;

            case 'b':
                for (byte dir = NW_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = (byte) (idx + j * Dir.offset[dir]);
                        if (isMoveablePlace(idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(playerIsWhite, dirValue)) {
                                writeDownBestMoves(dirValue, listIdx, playerIsWhite);
                                listIdx += 2;
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
                        if (isMoveablePlace(idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(playerIsWhite, dirValue)) {
                                writeDownBestMoves(dirValue, listIdx, playerIsWhite);
                                listIdx += 2;
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
                        if (isMoveablePlace(idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (isEnemyPiece(playerIsWhite, dirValue)) {
                                writeDownBestMoves(dirValue, listIdx, playerIsWhite);
                                listIdx += 2;
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
                    if (isMoveablePlace(idx, dirValue)) {
                        moves.add(idx);
                        moves.add(dirValue);

                        writeDownBestMoves(dirValue, listIdx, playerIsWhite);
                        listIdx += 2;
                    }
                }
                break;
            default:
                break;
        }
    }

    /*public void getLimitedMoves(char[] board, ArrayList<Byte> moves, boolean playerIsWhite, byte idx) {

        char piece;
        piece = playerIsWhite ? board[idx] : ((char) (board[idx] ^ 0x20));

        // dir = { N, S, W, E, NW, SE, NE, SW }
        byte[] remainsToEdge = remainCountsToEdge[idx];
        byte to;
        byte dirValue;

        switch (piece) {
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
    }*/


    private boolean writeDownBestMoves(int pos, int listIdx, boolean isWhite) {

        int score;
        if (this.board[pos] != 0) {
            score = SCORE_TABLE[(this.board[pos] | 0x20) - 'b'];

        } else {
            score = 0;
        }
        score = this.isWhite() == isWhite ? score : -score;
        if (score > bestLeastScore) {
            return insertTop5BestMoves(score, listIdx);
        }

        return false;
    }

    private int calculatePoint(char movingPiece, char capturedPiece, byte toPos, boolean playerIsWhite) {
        int colorValue = playerIsWhite ? 1 : -1;
        int score = 0;

        if (capturedPiece != 0) {
            capturedPiece |= 0x20;
            score = SCORE_TABLE[capturedPiece - 'b'];
        } else if (movingPiece == 'p') {
            if (toPos >= 34 && toPos <= 36) {
                score = 3;
            } else if (toPos >= 42 && toPos <= 44) {
                score = 3;
            }
        } else if (movingPiece == 'P') {
            if (toPos >= 26 && toPos <= 28) {
                score = 3;
            } else if (toPos >= 18 && toPos <= 20) {
                score = 3;
            }
        }

        whiteScore += score * colorValue;
        blackScore -= score * colorValue;

        return score;
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

    private boolean isEnemyPiece(boolean playerIsWhite, int idx) {
        return playerIsWhite ? (board[idx] != 0 && board[idx] <= 'R') : (board[idx] >= 'b');
    }
    private boolean isMoveablePlace(int from, int to) {
        if (board[to] == 0) {
            return true;
        }
        boolean isCaptured = board[from] <= 'R' ? board[to] >= 'b' : board[to] <= 'R';

        return isCaptured;
    }
    private void copyBoard(char[] dst, char[][] src) {

        for (int j = 0; j < BOARD_SIZE; ++j) {
            for (int i = 0; i < BOARD_SIZE; ++i) {
                dst[j * BOARD_SIZE + i] = src[j][i];
            }
        }
    }


    private int move(boolean playerIsWhite, byte from, byte to) {
        char capturedPiece = board[to];
        int earnScore = 0;

        if (capturedPiece != 0 || board[from] == 'p' || board[from] == 'P') {
            earnScore = calculatePoint(board[from], capturedPiece, to, playerIsWhite);
        }

        board[to] = board[from];
        board[from] = 0;
        return earnScore;
    }
    private void cancelMove(boolean playerIsWhite, int from, int to, char existingPiece, int earnScoreInPreTurn) {
        if (earnScoreInPreTurn != 0) {
            if (playerIsWhite) {
                whiteScore -= earnScoreInPreTurn;
                blackScore += earnScoreInPreTurn;
            } else {
                whiteScore += earnScoreInPreTurn;
                blackScore -= earnScoreInPreTurn;
            }
        }

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


    private void insertMoveToBoard(Move move) {
        char c = this.board[move.fromY * BOARD_SIZE + move.fromX];
        this.board[move.fromY * BOARD_SIZE + move.fromX] = 0;
        this.board[move.toY * BOARD_SIZE + move.toX] = c;
    }
    private void printBoard() {
        for (int y = 0; y < BOARD_SIZE; ++y) {
            for (int x = 0; x < BOARD_SIZE; ++x) {
                char c = this.board[y * BOARD_SIZE + x] == 0 ? '.' : this.board[y * BOARD_SIZE + x];
                System.out.printf("%c", c);
            }
            System.out.println();
        }
        System.out.println();
    }
    private boolean insertTop5BestMoves(int score, int idx) {
        for (int i = 0; i < bestScores.length; ++i) {
            if (bestScores[i] < score) {
                for (int j = bestScores.length - 2; j >= i; --j) {
                    bestScores[j + 1] = bestScores[j];
                    bestScoresListIndexes[j + 1] = bestScoresListIndexes[j];
                }
                bestScores[i] = score;
                bestScoresListIndexes[i] = idx;
                bestLeastScore = bestScores[4];

                return true;
            }
        }
        return false;
    }
    private void getPiecePositions(char[][] board) {
        if (this.isWhite()) {
            for (int y = 0; y < BOARD_SIZE; ++y) {
                for (int x = 0; x < BOARD_SIZE; ++x) {
                    char c = board[y][x];
                    byte idx = (byte) (y * BOARD_SIZE + x);
                    this.board[idx] = c;
                    if (c >= 'b') {
                        myPiecePositions.add(idx);
                    } else if (c != 0 && c <= 'R') {
                        opPiecePositions.add(idx);
                    }
                }
            }
        } else {
            for (int y = 0; y < BOARD_SIZE; ++y) {
                for (int x = 0; x < BOARD_SIZE; ++x) {
                    char c = board[y][x];
                    byte idx = (byte) (y * BOARD_SIZE + x);
                    this.board[idx] = c;
                    if (c != 0 && c <= 'R') {
                        myPiecePositions.add(idx);
                    } else if (c >= 'b') {
                        opPiecePositions.add(idx);
                    }
                }
            }
        }
    }
    private void updateMyPiecePositions(int fromX, int fromY, int toX, int toY) {
        byte from = (byte) (fromY * BOARD_SIZE + fromX);
        byte to = (byte) (toY * BOARD_SIZE + toX);
        myPiecePositions.remove(from);
        myPiecePositions.add(to);
    }
    private void updateOpPiecePositions(int fromX, int fromY, int toX, int toY) {
        byte from = (byte) (fromY * BOARD_SIZE + fromX);
        byte to = (byte) (toY * BOARD_SIZE + toX);
        opPiecePositions.remove(from);
        opPiecePositions.add(to);
    }


}
