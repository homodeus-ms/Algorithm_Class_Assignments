package academy.pocu.comp3500.lab6.app;

import academy.pocu.comp3500.lab6.League;
import academy.pocu.comp3500.lab6.RedBlackTree;
import academy.pocu.comp3500.lab6.leagueofpocu.Player;

import java.util.*;

public class Program {

    public static void main(String[] args) {

        G02_test();
        complexScenarioTest_2();
        test_G_multiple_2();

        /*Player p1 = new Player(1, "a", 3);
        Player p2 = new Player(2, "b", 5);
        Player p3 = new Player(3, "b", 10);
        Player p4 = new Player(4, "b", 7);
        Player p5 = new Player(5, "b", 19);
        Player p6 = new Player(6, "b", 8);
        Player p7 = new Player(7, "b", 9);
        Player p8 = new Player(8, "b", 11);
        Player p9 = new Player(9, "b", 12);
        Player p10 = new Player(10, "b", 18);

        Player[] players = new Player[10];
        players[0] = p1;
        players[1] = p2;
        players[2] = p3;
        players[3] = p4;
        players[4] = p5;
        players[5] = p6;
        players[6] = p7;
        players[7] = p8;
        players[8] = p9;
        players[9] = p10;

        League league = new League(players);
        league.print();
        System.out.println();

        boolean b = league.leave(p8);
        System.out.println(b);

        Player[] topPlayers = league.getTop(5);
        printPlayers(topPlayers);*/

        //testDelete();

        System.out.println("No Assert");
    }

    private static void printPlayer(Player player) {
        if (player == null) {
            System.out.println("null");
            return;
        }
        System.out.printf("(%d, %s, %d)%s", player.getId(), player.getName(), player.getRating(), System.lineSeparator());
    }
    private static void printPlayers(Player[] players) {
        System.out.println("===== print player arr =====");
        if (players == null) {
            System.out.println("null");
            return;
        }
        for (Player player : players) {
            System.out.printf("%d ", player.getRating());
        }
        System.out.println();
        System.out.println("===========================");
    }
    private static void testMatch(League league, Player[] players) {
        System.out.println("===== Print numbers in arr =====");
        for (Player p : players) {
            printPlayer(p);
        }
        System.out.println("===== End Print numbers in arr =====");

        for (Player p : players)
        {
            printPlayer(league.findMatchOrNull(p));
        }
        System.out.println("===== input null players =====");
        printPlayer(league.findMatchOrNull(new Player(135, "a", 100)));
        printPlayer(league.findMatchOrNull(new Player(136, "b", 0)));
        printPlayer(league.findMatchOrNull(new Player(137, "c", 1000)));
        System.out.println();
    }
    private static void testInsert() {

        Random random = new Random();

        Player player1 = new Player(1, "player1", random.nextInt(50));
        Player player2 = new Player(2, "player2", random.nextInt(50));
        Player player3 = new Player(3, "player3", random.nextInt(50));
        Player player4 = new Player(4, "player4", random.nextInt(50));
        Player player5 = new Player(5, "player5", random.nextInt(50));
        Player player6 = new Player(6, "player6", random.nextInt(50));
        Player player7 = new Player(7, "player3", random.nextInt(50));
        Player player8 = new Player(8, "player4", random.nextInt(50));
        Player player9 = new Player(9, "player5", random.nextInt(50));
        Player player10 = new Player(10, "player6", random.nextInt(50));

        /*Player player1 = new Player(1, "player1", 1);
        Player player2 = new Player(2, "player2", 2);
        Player player3 = new Player(3, "player3", 3);
        Player player4 = new Player(4, "player4", 4);
        Player player5 = new Player(5, "player5", 5);*/


        Player[] players = new Player[10];
        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;
        players[4] = player5;
        players[5] = player6;
        players[6] = player7;
        players[7] = player8;
        players[8] = player9;
        players[9] = player10;

        League league = new League(players);

        sortRatingDesc(players, new Player[players.length], 0, players.length);

        System.out.println("======== Picked Players =========");
        for (Player p : players) {
            System.out.printf("%d ", p.getRating());
        }
        System.out.println();
        System.out.println("=================================");



        int rating1 = player3.getRating();
        int rating2 = player5.getRating();
        int rating3 = player10.getRating();

        Player newPlayer1 = new Player(3, "player1", rating1);
        assert (!league.join(newPlayer1));
        Player newPlayer2 = new Player(5, "player2", rating2);
        assert (!league.join(newPlayer2));
        Player newPlayer3 = new Player(10, "player3", rating3);
        assert (!league.join(newPlayer3));
        Player newPlayer4 = new Player(20, "player4", 200);
        assert (league.join(newPlayer4));
        Player newPlayer5 = new Player(22, "player5", 100);
        assert (league.join(newPlayer5));

        Player[] get = league.getTop(12);
        sortRatingDesc(get, new Player[get.length], 0, get.length);

        for (Player p : get) {
            System.out.printf("%d ", p.getRating());
        }
        System.out.println();



    }
    private static void testInsertOne() {
        Player[] players = new Player[]{new Player(5, "a", 10)};
        League league = new League(players);
        Player player1 = new Player(5, "b", 10);
        Player player2 = new Player(4, "b", 10);
        Player player3 = new Player(6, "b", 9);
        Player player4 = new Player(6, "b", 11);
        assert(!league.join(player1));
        assert(league.join(player2));
        assert(league.join(player3));
        assert(league.join(player4));
    }
    private static void testDelete() {

        Random random = new Random();
        boolean[] picked = new boolean[20];

        /*Player player1 = new Player(1, "player1", 19);
        Player player2 = new Player(2, "player2", 17);
        Player player3 = new Player(3, "player3", 10);
        Player player4 = new Player(4, "player4", 12);
        Player player5 = new Player(5, "player5", 2);
        Player player6 = new Player(6, "player5", 0);
        Player player7 = new Player(7, "player5", 3);*/

        int randNum = random.nextInt(20);
        picked[randNum] = true;
        Player player1 = new Player(1, "player1", randNum);

        randNum = getRandNum(picked);
        Player player2 = new Player(2, "player2", randNum);
        randNum = getRandNum(picked);
        Player player3 = new Player(3, "player3", randNum);
        randNum = getRandNum(picked);
        Player player4 = new Player(4, "player4", randNum);
        randNum = getRandNum(picked);
        Player player5 = new Player(5, "player5", randNum);
        randNum = getRandNum(picked);
        Player player6 = new Player(6, "player6", randNum);
        randNum = getRandNum(picked);
        Player player7 = new Player(7, "player6", randNum);

        /*Player player8 = new Player(8, "player6", random.nextInt(20));
        Player player9 = new Player(9, "Player9", random.nextInt(20));
        Player player10 = new Player(10, "Player10", random.nextInt(20));*/

        System.out.printf("player1 : %d\n", player1.getRating());
        System.out.printf("player2 : %d\n", player2.getRating());
        System.out.printf("player3 : %d\n", player3.getRating());
        System.out.printf("player4 : %d\n", player4.getRating());
        System.out.printf("player5 : %d\n", player5.getRating());
        System.out.printf("player6 : %d\n", player6.getRating());
        System.out.printf("player7 : %d\n", player7.getRating());

        //League league = new League(new Player[]{player1, player2, player3, player4, player5, player6});
        Player[] players = new Player[] {player1, player2, player3, player4, player5, player6, player7};//, player8, player9, player10};
        League league = new League(players);

        league.print();
        System.out.println();

        /*league.leave(player1);
        System.out.printf("deleted Node : %d\n", player1.getRating());
        league.leave(player2);
        System.out.printf("deleted Node : %d\n", player2.getRating());
        league.leave(player3);
        System.out.printf("deleted Node : %d\n", player3.getRating());
        league.leave(player4);
        System.out.printf("deleted Node : %d\n", player4.getRating());
        league.leave(player5);
        System.out.printf("deleted Node : %d\n", player5.getRating());
        league.leave(player6);
        System.out.printf("deleted Node : %d\n", player6.getRating());
        league.leave(player7);
        System.out.printf("deleted Node : %d\n", player7.getRating());*/

        //sortRatingDesc(players, new Player[players.length], 0, players.length);
        league.print();

        /*for (int i = 9; i >= 0; --i) {
            league.leave(players[i]);
        }*/
        //System.out.println(league.bstSize());


    }
    private static void sortRatingDesc(Player[] players, Player[] buffer, int left, int right) {
        if (right - left <= 1) {
            return;
        }
        int mid = (right + left) / 2;
        sortRatingDesc(players, buffer, left, mid);
        sortRatingDesc(players, buffer, mid, right);

        int bufIdx = 0;
        for (int i = left; i < mid; ++i) {
            buffer[bufIdx++] = players[i];
        }
        for (int i = right - 1; i >= mid; --i) {
            buffer[bufIdx++] = players[i];
        }

        int leftIdx = 0;
        int rightIdx = bufIdx - 1;
        for (int i = left; i < right; ++i) {
            if (buffer[leftIdx].getRating() > buffer[rightIdx].getRating()) {
                players[i] = buffer[leftIdx++];
            } else {
                players[i] = buffer[rightIdx--];
            }
        }
    }
    private static void sortRatingAsce(Player[] players, Player[] buffer, int left, int right) {
        if (right - left <= 1) {
            return;
        }
        int mid = (right + left) / 2;
        sortRatingAsce(players, buffer, left, mid);
        sortRatingAsce(players, buffer, mid, right);

        int bufIdx = 0;
        for (int i = left; i < mid; ++i) {
            buffer[bufIdx++] = players[i];
        }
        for (int i = right - 1; i >= mid; --i) {
            buffer[bufIdx++] = players[i];
        }

        int leftIdx = 0;
        int rightIdx = bufIdx - 1;
        for (int i = left; i < right; ++i) {
            if (buffer[leftIdx].getRating() < buffer[rightIdx].getRating()) {
                players[i] = buffer[leftIdx++];
            } else {
                players[i] = buffer[rightIdx--];
            }
        }
    }

    public static void G02_test() {
        Player player5 = new Player(5, "player5", 11);
        Player player6 = new Player(6, "player6", 12);

        League league = new League(new Player[] {
                player5,
                player6
        });

        boolean leaveSuccess = league.leave(player5);
        assert(leaveSuccess);

        leaveSuccess = league.leave(player5);
        assert(!leaveSuccess);

        league = new League(new Player[] {
                player6,
                player5
        });

        leaveSuccess = league.leave(player6);
        assert(leaveSuccess);

        leaveSuccess = league.leave(player6);
        assert(!leaveSuccess);

        league = new League(new Player[] {
                player6,
                player5
        });

        leaveSuccess = league.leave(player5);
        assert(leaveSuccess);

        leaveSuccess = league.leave(player5);
        assert(!leaveSuccess);

        league = new League(new Player[] {
                player6,
                player5
        });

        leaveSuccess = league.leave(player5);
        assert(leaveSuccess);

        leaveSuccess = league.leave(player5);
        assert(!leaveSuccess);

    }
    private static void Test_C() {
        int length = 10;
        Player[] players = new Player[length];

        Random random = new Random();
        HashSet< Integer > usedRanks = new HashSet < > ();

        for (int i = 0; i < length; ++i) {
            int rank;
            do {
                rank = random.nextInt(100);
            } while (usedRanks.contains(rank));
            usedRanks.add(rank);
            players[i] = (new Player(i, "player" + i, rank));
        }

        League league = new League(players);
        Player cmpPlayer = players[length / 2];
        Player result = league.findMatchOrNull(cmpPlayer);

        Player matchPlayer = null;
        int offset = Integer.MAX_VALUE;
        for (int i = 0; i < length; ++i) {
            if (cmpPlayer.equals(players[i])) {
                continue;
            }

            int cmpOffset = Math.abs(cmpPlayer.getRating() - players[i].getRating());
            if (offset == cmpOffset) {
                if (players[i].getRating() > matchPlayer.getRating()) {
                    matchPlayer = players[i];
                    offset = cmpOffset;
                }
            } else if (offset > cmpOffset) {
                matchPlayer = players[i];
                offset = cmpOffset;
            }
        }

        if (result.equals(matchPlayer) == false) {
            if (result.getRating() != matchPlayer.getRating()) {
                System.out.println("이곳에 디버그 포인트를 찍어서 디버깅을 하시면 더 편합니다.");
            }
        }
        assert(result.getRating() == matchPlayer.getRating());
    }
    private static void Test_G() {
        int length = 5;
        Player[] players = new Player[length];
        boolean[] isAffiliation = new boolean[length];
        int[] orders = new int[length];
        int order = 0;

        Random random = new Random();
        HashSet < Integer > usedRanks = new HashSet < > ();

        for (int i = 0; i < length; ++i) {
            isAffiliation[i] = true;
            int rank;
            do {
                rank = random.nextInt(100);
            } while (usedRanks.contains(rank));
            usedRanks.add(rank);
            players[i] = (new Player(i, "player" + i, rank));
        }
        League raw = new League(players);
        League league = new League(players);

        for (int i = 0; i < length * 30; ++i) {
            int index = random.nextInt(players.length);
            boolean leave = league.leave(players[index]);
            if (leave) {
                orders[index] = ++order;
            }
            if (isAffiliation[index] != leave) {
                System.out.println("이곳에 디버그 포인트를 찍어서 디버깅을 하시면 더 편합니다.");
            }
            assert(isAffiliation[index] == leave);
            isAffiliation[index] = false;
        }
    }

    private static void Test_G_DebugDetail() {
        Player player1 = new Player(1, "player1", 28);
        Player player2 = new Player(2, "player2", 6);
        Player player3 = new Player(3, "player3", 75);
        Player player4 = new Player(4, "player4", 70);
        Player player5 = new Player(5, "player5", 72);

        League league = new League(new Player[] {
                player1,
                player2,
                player3,
                player4,
                player5
        });

        league.leave(player1);
    }

    private static void complexScenarioTest_2() {
        List< Player > players = new ArrayList< >();
        Random random = new Random();
        HashSet < Integer > usedRanks = new HashSet < > ();

        for (int i = 1; i <= 50; i++) {
            int id = random.nextInt(1000);
            int rank;
            do {
                rank = random.nextInt(100);
            } while (usedRanks.contains(rank));
            usedRanks.add(rank);
            players.add(new Player(id, "player" + i, rank));
        }

        Collections.shuffle(players);

        for (Player player: players) {
            System.out.println(player);
        }

        League league = new League(players.toArray(new Player[0]));

        Player playerToTest = players.get(0);

        System.out.println("playerToTest : " + playerToTest);

        Player closestPlayer = null;
        for (Player player: players) {
            if (player == playerToTest) {
                continue;
            }
            if (closestPlayer == null || Math.abs(player.getRating() - playerToTest.getRating()) < Math.abs(closestPlayer.getRating() - playerToTest.getRating())) {
                closestPlayer = player;
            } else if (Math.abs(player.getRating() - playerToTest.getRating()) == Math.abs(closestPlayer.getRating() - playerToTest.getRating()) && player.getRating() > closestPlayer.getRating()) {
                closestPlayer = player;
            }
        }

        Player match = league.findMatchOrNull(playerToTest);

        System.out.println("match : " + match);
        System.out.println("result : " + closestPlayer);

        assert(match.getId() == closestPlayer.getId());
    }

    public static void print_arr(Player[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].toString()).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
    }

    public static void test_G_multiple_2() {

        Player player1 = new Player(1, "player1", 9999);
        Player player2 = new Player(2, "player2", 1);
        Player player3 = new Player(3, "player3", 5555);
        Player player4 = new Player(4, "player4", 200);
        Player player5 = new Player(5, "player5", 100);
        Player player6 = new Player(6, "player6", 5);

        League league = new League(new Player[] {
                player1,
                player5
        });
        //              9999(p1)
        //  100(p5)

        league.leave(player1);
        print_arr(league.getTop(100));

        league.leave(player5);
        print_arr(league.getTop(100));

        league.leave(player2);
        print_arr(league.getTop(100));

        league.leave(player3);
        print_arr(league.getTop(100));

        league.leave(player4);
        print_arr(league.getTop(100));

        league.leave(player6);
        print_arr(league.getTop(100));

    }

    public static void testGetTop() {
        Player player1 = new Player(1, "a", 1);
        Player player2 = new Player(2, "b", 2);
        Player[] players = new Player[] {player1, player2};
        League league = new League(players);

        Player[] get = league.getTop(3);

        System.out.println();
    }

    public static void testFindMatch() {

        for (int repeat = 0; repeat < 10; ++repeat) {
            Random random = new Random();
            boolean[] checkDuplicateNumber = new boolean[20];
            int[] pickedNumber = new int[10];
            for (int i = 0; i < 10; ++i) {
                int randNum = random.nextInt(20);
                while (checkDuplicateNumber[randNum]) {
                    randNum = random.nextInt(20);
                }
                pickedNumber[i] = randNum;
                checkDuplicateNumber[randNum] = true;
            }

            Player p1 = new Player(1, "a", pickedNumber[0]);
            Player p2 = new Player(2, "b", pickedNumber[1]);
            Player p3 = new Player(3, "b", pickedNumber[2]);
            Player p4 = new Player(4, "b", pickedNumber[3]);
            Player p5 = new Player(5, "b", pickedNumber[4]);
            Player p6 = new Player(6, "b", pickedNumber[5]);
            Player p7 = new Player(7, "b", pickedNumber[6]);
            Player p8 = new Player(8, "b", pickedNumber[7]);
            Player p9 = new Player(9, "b", pickedNumber[8]);
            Player p10 = new Player(10, "b", pickedNumber[9]);

        /*Player p1 = new Player(1, "a", 16);
        Player p2 = new Player(2, "b", 1);
        Player p3 = new Player(3, "b", 9);
        Player p4 = new Player(4, "b", 13);
        Player p5 = new Player(5, "b", 8);
        Player p6 = new Player(6, "b", 4);
        Player p7 = new Player(7, "b", 14);
        Player p8 = new Player(8, "b", 11);
        Player p9 = new Player(9, "b", 10);
        Player p10 = new Player(10, "b", 18);*/

            Player[] players = new Player[10];
            players[0] = p1;
            players[1] = p2;
            players[2] = p3;
            players[3] = p4;
            players[4] = p5;
            players[5] = p6;
            players[6] = p7;
            players[7] = p8;
            players[8] = p9;
            players[9] = p10;

            Player[] copyPlayers = new Player[10];
            for (int i = 0; i < 10; ++i) {
                copyPlayers[i] = players[i];
            }
            sortRatingDesc(copyPlayers, new Player[10], 0, 10);

            League league = new League(players);


            //int ret = league.findMatchOrNull(p9).getRating();


            for (int i = 0; i < 10; ++i) {
                Player correctPlayer = findMatch(copyPlayers, players[i]);
                Player myPlayer = league.findMatchOrNull(players[i]);
                if (correctPlayer != myPlayer) {
                    printReason(players, players[i], correctPlayer, myPlayer);
                }
            }

        }
    }
    public static Player findMatch(Player[] players, Player player) {

        int playerIdx = 0;
        for (int i = 0; i < 10; ++i) {
            if (players[i] == player) {
                playerIdx = i;
                break;
            }
        }

        if (playerIdx == 0) {
            return players[1];
        } else if (playerIdx == 9) {
            return players[8];
        }

        int diff1 = Math.abs(players[playerIdx - 1].getRating() - players[playerIdx].getRating());
        int diff2 = Math.abs(players[playerIdx].getRating() - players[playerIdx + 1].getRating());

        if (diff2 < diff1) {
            return players[playerIdx + 1];
        } else {
            return players[playerIdx - 1];
        }
    }

    public static void printReason(Player[] players, Player input, Player correctPlayer, Player myPlayer) {
        System.out.println("======================================");
        for (Player p : players) {
            System.out.printf("%d ", p.getRating());
        }
        System.out.println();
        System.out.println("======================================");

        System.out.printf("Input : %d\n", input.getRating());
        System.out.printf("Correct : %d\n", correctPlayer.getRating());
        System.out.printf("MyAnswer : %d\n", myPlayer.getRating());

    }
    public static int getRandNum(boolean[] pickedNum) {
        Random random = new Random();
        int num =  random.nextInt(20);
        while (pickedNum[num]) {
            num = random.nextInt(20);
        }
        pickedNum[num] = true;
        return num;
    }
}
