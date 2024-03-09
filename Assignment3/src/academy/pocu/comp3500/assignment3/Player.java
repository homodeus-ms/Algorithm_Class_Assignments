package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
import java.util.LinkedList;

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

    private static final int N_IDX = 0;
    private static final int S_IDX = 1;
    private static final int W_IDX = 2;
    private static final int E_IDX = 3;
    private static final int NW_IDX = 4;
    private static final int SE_IDX = 5;
    private static final int NE_IDX = 6;
    private static final int SW_IDX = 7;
    private static final int IDX_END = 8;

    // consts

    private static final int BOARD_SIZE = 8;

    public int preWhiteScore = 1400;
    public int preBlackScore = 1400;
    public int whiteScore = 1400;
    public int blackScore = 1400;


    private static Board gameBoard = null;
    private final int[][] remainCountsToEdge;
    private char[] board;

    // piece 관련
    private int[] myPiecePosIndexes;
    private char[] myPieceTypeInIndexes;
    private int[] opPiecePosIndexes;
    private char[] opPieceTypeInIndexes;


    // move 관련
    private char capturedPiece = 0;
    private Move maxResult = new Move(0, 0, 0, 0);
    private Move result = new Move(0, 0, 0, 0);

    // result, score 관련
    private int[] bestMove = new int[2];


    // [0] = b (30 point)  |  [9] = k (1000 point)  |  [12] = n (30 point)
    // [14] = p (10 point)  |  [15] = q (100 point)  |  [16] = r (50 point)
    private static final int[] SCORE_TABLE = {
            30, -1, -1, -1, -1, -1, -1, -1, -1, 10000,
            -1, -1, 30, -1, 10, 100, 50,
    };


    // etc
    private static final int[] NIGHT_MOVE_OFFSET = {-10, -17, -15, -6, 10, 17, 15, 6};
    // { DIR_EASE|WEST, DIR_NORTH|SOUTH, needSpace1, needSpace2 }
    private static final int[] NIGHT_MOVE_HELPER = {2, 0, 2, 1, 2, 0, 1, 2, 3, 0, 1, 2, 3, 0, 2, 1, 3, 1, 2, 1, 3, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 1};

    private boolean timeOut = false;
    private long startTime;
    private boolean firstTurn = true;

    private int count = 0;


    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);

        gameBoard = new Board(this, isWhite);
        this.board = gameBoard.getBoard();
        myPiecePosIndexes = gameBoard.getMyPiecePosIndexes();
        myPieceTypeInIndexes = gameBoard.getMyPieceTypeInIndexes();
        opPiecePosIndexes = gameBoard.getOpPiecePosIndexes();
        opPieceTypeInIndexes = gameBoard.getOpPieceTypeInIndexes();
        Helper helper = new Helper();
        this.remainCountsToEdge = Helper.getRemainCountsToEdge();
    }



    public Move getNextMove(char[][] board) {

        boolean isStartBoard = true;

        gameBoard.setBoard(board);

        for (int i = 0; i < 64; ++i) {
            if (this.board[i] != START_BOARD[i]) {
                isStartBoard = false;
                break;
            }
        }

        firstTurn = false;
        Move move;

        if (this.isWhite() && isStartBoard) {
            move = new Move(3, 6, 3, 4);
            gameBoard.insertMove(move, true);
        } else {
            move = getNextMove(board, null);
        }

        return move;
    }

    public Move getNextMove(char[][] board, Move opponentMove) {

        if (firstTurn) {
            gameBoard.setBoard(board);
            firstTurn = false;
        } else if (opponentMove != null) {
            gameBoard.insertMove(opponentMove, false);
        }

        int point;
        int depth = 1;
        int startDepth = depth;
        this.startTime = System.currentTimeMillis();



        while (true) {

            maxResult.fromX = result.fromX;
            maxResult.fromY = result.fromY;
            maxResult.toX = result.toX;
            maxResult.toY = result.toY;


            whiteScore = preWhiteScore;
            blackScore = preBlackScore;
            point = minimax(depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE,
                    true, this.isWhite(), result);

            /*System.out.printf("depth : %d\n", depth);
            System.out.printf("Point : %d\n", point);*/

            if (depth == startDepth) {
                maxResult.fromX = result.fromX;
                maxResult.fromY = result.fromY;
                maxResult.toX = result.toX;
                maxResult.toY = result.toY;
            }

            // for debug
            /*if (depth == 5 || point >= 9900) { //timeOut || point >= 9900) {

                gameBoard.insertMove(maxResult, true);
                timeOut = false;
                System.out.printf("Count : %d\n", count);
                count = 0;
                return maxResult;
            }*/
            if (timeOut || point >= 9900) {
                //insertMoveToBoard(maxResult);
                gameBoard.insertMove(maxResult, true);
                timeOut = false;
                //System.out.printf("Count : %d\n", count);
                return maxResult;
            }

            ++depth;
        }
    }

    private int minimax(int depth, int startDepth, int alpha, int beta,
                        boolean isMyTurn, boolean playerIsWhite, Move result) {

        if (System.currentTimeMillis() - startTime > getMaxMoveTimeMilliseconds() - 50) {
            timeOut = true;
            return alpha;
        }

        //count++;

        LinkedList<Integer> moves = getAvailableMoves(isMyTurn);


        if (moves.isEmpty()) {
            return evaluate(this.isWhite());
        }

        if (isMyTurn) {
            int maxValue = Integer.MIN_VALUE;
            int currValue;
            int movesSize = moves.size();
            int tempEarnScore = 0;

            for (int i = 0; i < movesSize; ++i) {

                int from = moves.get(i++);
                int to = moves.get(i);
                char existingPiece = board[to];

                tempEarnScore = move(true, from, to, existingPiece);

                if (depth > 1) {
                    currValue = minimax(depth - 1, startDepth, alpha, beta,
                            false, !playerIsWhite, result);
                } else {
                    currValue = evaluate(this.isWhite());
                }

                cancelMove(true, to, from, existingPiece, tempEarnScore);


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

            for (int i = 0; i < movesSize; ++i) {
                int from = moves.get(i++);
                int to = moves.get(i);

                char existingPiece = board[to];

                tempEarnScore = move(false, from, to, existingPiece);

                if (depth > 1) {
                    currValue = minimax(depth - 1, startDepth, alpha, beta,
                            true, !playerIsWhite, result);
                } else {
                    currValue = evaluate(this.isWhite());
                }

                cancelMove(false, to, from, existingPiece, tempEarnScore);


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



    private LinkedList<Integer> getAvailableMoves(boolean isMyTurn) {

        LinkedList<Integer> moves = new LinkedList<>();

        if (isMyTurn) {
            int size = gameBoard.getMyPosSize();
            for (int i = 0; i < size; ++i) {
                getAvailableMoves(moves, this.isWhite(), myPiecePosIndexes[i]);
            }
        } else {
            int size = gameBoard.getOpPosSize();
            for (int i = 0; i < size; ++i) {
                getAvailableMoves(moves, !this.isWhite(), opPiecePosIndexes[i]);
            }
        }

        return moves;
    }

    private int evaluate(boolean playerIsWhite) {
        return playerIsWhite ? whiteScore - blackScore : blackScore - whiteScore;
    }


    public void getAvailableMoves(LinkedList<Integer> moves, boolean playerIsWhite, int idx) {

        char piece;
        piece = playerIsWhite ? board[idx] : ((char) (board[idx] ^ 0x20));

        // dir = { N, S, W, E, NW, SE, NE, SW }
        int[] remainsToEdge = remainCountsToEdge[idx];
        int to;
        int dirValue;

        switch (piece) {
            case 'p':
                if (playerIsWhite) {
                    to = idx + Dir.NW;
                    if (remainsToEdge[NW_IDX] != 0 && board[to] != 0 && board[to] <= 'R') {
                        moves.addFirst(to);
                        moves.addFirst(idx);
                    }
                    to = idx + Dir.NE;
                    if (remainsToEdge[NE_IDX] != 0 && board[to] != 0 && board[to] <= 'R') {
                        moves.addFirst(to);
                        moves.addFirst(idx);
                    }
                    to = idx + Dir.N;
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
                    to = idx + Dir.SW;
                    if (remainsToEdge[SW_IDX] != 0 && isEnemyPiece(playerIsWhite, to)) {
                        moves.addFirst(to);
                        moves.addFirst(idx);
                    }
                    to = idx + Dir.SE;
                    if (remainsToEdge[SE_IDX] != 0 && isEnemyPiece(playerIsWhite, to)) {
                        moves.addFirst(to);
                        moves.addFirst(idx);
                    }
                    to = idx + Dir.S;
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

                int helperIdx = 0;
                for (int i = 0; i < 8; ++i) {

                    int dir1 = NIGHT_MOVE_HELPER[helperIdx++];
                    int dir2 = NIGHT_MOVE_HELPER[helperIdx++];
                    int needSpaceForDir1 = NIGHT_MOVE_HELPER[helperIdx++];
                    int needSpaceForDir2 = NIGHT_MOVE_HELPER[helperIdx++];

                    // 움직일 여유 공간이 없는 경우 continue;
                    if (remainsToEdge[dir1] < needSpaceForDir1 || remainsToEdge[dir2] < needSpaceForDir2) {
                        continue;
                    }

                    to = idx + NIGHT_MOVE_OFFSET[i];
                    if (isEnemyPiece(playerIsWhite, to)) {
                        moves.addFirst(to);
                        moves.addFirst(idx);

                    } else if (isMoveablePlace(idx, to)) {
                        moves.add(idx);
                        moves.add(to);
                    }
                }

                break;

            case 'b':
                for (int dir = NW_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = idx + j * Dir.offset[dir];
                        if (isEnemyPiece(playerIsWhite, dirValue)) {
                            moves.addFirst(dirValue);
                            moves.addFirst(idx);
                            break;
                        } else if (isMoveablePlace(idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (this.board[dirValue] != 0) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                break;

            case 'r':
                for (int dir = N_IDX; dir <= E_IDX; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = idx + j * Dir.offset[dir];
                        if (isEnemyPiece(playerIsWhite, dirValue)) {
                            moves.addFirst(dirValue);
                            moves.addFirst(idx);
                            break;
                        } else if (isMoveablePlace(idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (this.board[dirValue] != 0) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                break;

            case 'q':
                for (int dir = N_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    for (int j = 1; j <= to; ++j) {
                        dirValue = idx + j * Dir.offset[dir];
                        if (isEnemyPiece(playerIsWhite, dirValue)) {
                            moves.addFirst(dirValue);
                            moves.addFirst(idx);
                            break;
                        } else if (isMoveablePlace(idx, dirValue)) {
                            moves.add(idx);
                            moves.add(dirValue);
                            if (this.board[dirValue] != 0) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }

                break;
            case 'k':
                for (int dir = N_IDX; dir < IDX_END; ++dir) {
                    to = remainsToEdge[dir];
                    if (to == 0) {
                        continue;
                    }
                    dirValue = idx + Dir.offset[dir];
                    if (isEnemyPiece(playerIsWhite, dirValue)) {
                        moves.addFirst(dirValue);
                        moves.addFirst(idx);
                    } else if (isMoveablePlace(idx, dirValue)) {
                        moves.add(idx);
                        moves.add(dirValue);
                    }
                }
                break;
            default:
                break;
        }
    }





    private int calculatePoint(char movingPiece, char capturedPiece, int to, boolean playerIsWhite) {
        int colorValue = playerIsWhite ? 1 : -1;
        int score = 0;

        if (capturedPiece != 0) {
            capturedPiece |= 0x20;
            score = SCORE_TABLE[capturedPiece - 'b'];
        }

        score += Positions.getPositionPoints(movingPiece, to);

        if (gameBoard.isCheckMate(this.isWhite() == playerIsWhite)) {
            score -= 2000;
        }

        whiteScore += score * colorValue;
        blackScore -= score * colorValue;

        return score;
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

    private int move(boolean isMyTurn, int from, int to, char capturedPiece) {
        int earnScore = 0;

        gameBoard.insertMove(from, to, isMyTurn);

        earnScore = calculatePoint(board[to], capturedPiece, to, isMyTurn == this.isWhite());


        return earnScore;
    }
    private void cancelMove(boolean isMyTurn, int from, int to, char existingPiece, int earnScoreInPreTurn) {
        if (earnScoreInPreTurn != 0) {
            /*int colorValue = this.isWhite() ? -1 : 1;
            whiteScore += earnScoreInPreTurn * colorValue;
            blackScore -= earnScoreInPreTurn * colorValue;*/

            if (isMyTurn == this.isWhite()) {
                whiteScore -= earnScoreInPreTurn;
                blackScore += earnScoreInPreTurn;
            } else {
                whiteScore += earnScoreInPreTurn;
                blackScore -= earnScoreInPreTurn;
            }
        }
        if (existingPiece == 0) {
            gameBoard.insertMove(from, to, isMyTurn);
        } else {
            gameBoard.insertMove(from, to, isMyTurn, existingPiece);
        }
    }

    public boolean isNightCheckMate(int idx, int kingPos) {

        int[] remainsToEdge = remainCountsToEdge[idx];
        int to = 0;
        int helperIdx = 0;
        for (int i = 0; i < 8; ++i) {

            int dir1 = NIGHT_MOVE_HELPER[helperIdx++];
            int dir2 = NIGHT_MOVE_HELPER[helperIdx++];
            int needSpaceForDir1 = NIGHT_MOVE_HELPER[helperIdx++];
            int needSpaceForDir2 = NIGHT_MOVE_HELPER[helperIdx++];

            // 움직일 여유 공간이 없는 경우 continue;
            if (remainsToEdge[dir1] < needSpaceForDir1 || remainsToEdge[dir2] < needSpaceForDir2) {
                continue;
            }

            to = idx + NIGHT_MOVE_OFFSET[i];
            if (to == kingPos) {
                return true;
            }
        }
        return false;
    }
}
