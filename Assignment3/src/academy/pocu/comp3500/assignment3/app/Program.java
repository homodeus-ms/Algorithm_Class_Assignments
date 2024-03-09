package academy.pocu.comp3500.assignment3.app;

import academy.pocu.comp3500.assignment3.Player;
import academy.pocu.comp3500.assignment3.ZobristHashing;
import academy.pocu.comp3500.assignment3.chess.Move;

import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Program {

    public static void main(String[] args) {
        {
            final boolean IS_AUTO_PLAY = false; // true 라면 주기적으로 자동으로 다음 턴이 진행됨; false 라면 Enter/Return 키를 누를 때 진행됨
            final boolean IS_WHITE_KEYBOARD_PLAYER = true; // true 라면 하얀색 플레이어의 수를 콘솔에 입력해야 함
            final boolean IS_BLACK_KEYBOARD_PLAYER = false; // true 라면 검은색 플레이어의 수를 콘솔에 입력해야 함

            final int MAX_MOVE_TIME_MILLISECONDS = 1000; // Player 가 턴마다 수를 결정하는 데에 주어진 시간
            final long AUTO_PLAY_TURN_DURATION_IN_MILLISECONDS = 1000; // Autoplay 중 턴마다 일시중지 되는 기간

            PlayerBase whitePlayer;
            PlayerBase blackPlayer;

            if (IS_WHITE_KEYBOARD_PLAYER) {
                whitePlayer = new KeyboardPlayer(true);
            } else {
                whitePlayer = new Player(true, MAX_MOVE_TIME_MILLISECONDS);
            }
            if (IS_BLACK_KEYBOARD_PLAYER) {
                blackPlayer = new KeyboardPlayer(false);
            } else {
                blackPlayer = new Player(false, MAX_MOVE_TIME_MILLISECONDS);
            }

            Game game = new Game(whitePlayer, blackPlayer, MAX_MOVE_TIME_MILLISECONDS);

            System.out.println("Let the game begin!");
            System.out.println(game.toString());

            for (int i = 0; i < 50; ++i) {
                if (game.isNextTurnWhite() && IS_BLACK_KEYBOARD_PLAYER
                        || !game.isNextTurnWhite() && IS_WHITE_KEYBOARD_PLAYER) {
                    if (IS_AUTO_PLAY) {
                        pause(AUTO_PLAY_TURN_DURATION_IN_MILLISECONDS);
                    } else {
                        continueOnEnter();
                    }
                }

                game.nextTurn();

                clearConsole();
                System.out.println(game.toString());

                if (game.isGameOver()) {
                    break;
                }
            }
        }

        /*ZobristHashing.init();
        char[] board = {
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 ,'P','K', 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 ,'k', 0 , 0 , 0 ,
                0 , 0 ,'R', 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
                0 , 0 , 0 , 0 , 0 , 0 ,'Q', 0 ,
                0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
        };
        long hash0 = ZobristHashing.getHash(board);
        System.out.println(hash0);*/



        /*{
            Player player = new Player(true, 1000);
            Player opponent = new Player(false, 1000);
        *//*char[][] board = {
                { 'R','N','B','Q','K','B','N','R'},
                { 'P', 0, 'P','P','P','P','P','P'},
                {  0 , 0 , 0 , 0 , 0 ,'p', 0 , 0 },
                {  0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                {  0 ,'q', 0 , 0 , 0 , 0 , 0 , 0 },
                {  0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                { 'p', 0 ,'p','p','p','p','p','p'},
                { 'r','n','b', 0 ,'k','b','n', 0 },
        };*//*

            char[][] board = {
                    // A   B   C   D   E   F   G   H
                    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 },
                    { 0 , 0 , 0 , 0 , 0 ,'p','p','p'},
                    { 0 , 0 , 0 ,'Q', 0 , 0 ,'k', 0 },
            };

            printBoard(board);

            long startTime = System.nanoTime();

            Move res = player.getNextMove(board, new Move(1, 4, 2, 4));

            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            long elapsedTimeMillis = TimeUnit.NANOSECONDS.toMillis(elapsedTime);

            System.out.println();
            System.out.println("경과 시간: " + elapsedTimeMillis + " 밀리초");
            System.out.println();
            insertMove(board, res);

            printBoard(board);
            System.out.printf("WhiteScore: %d\n", player.whiteScore);
            System.out.printf("BlackScore: %d\n", player.blackScore);


            System.out.println("No Assert");
        }*/
    }
    public static void insertMove(char[][] board, Move move) {
        board[move.toY][move.toX] = board[move.fromY][move.fromX];
        board[move.fromY][move.fromX] = 0;
    }
    public static void printBoard(char[][] board) {
        System.out.printf("  ");
        for (int i = 0; i < 8; ++i) {
            int c = 'A';
            System.out.printf("%c ", c + i);
        }
        System.out.println();

        for (int y = 0; y < 8; ++y) {
            System.out.printf("%d ", y);
            for (int x = 0; x < 8; ++x) {
                char c = board[y][x];
                System.out.printf("%c ", c == 0 ? '_' : c);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void continueOnEnter() {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Press enter to continue:");
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
