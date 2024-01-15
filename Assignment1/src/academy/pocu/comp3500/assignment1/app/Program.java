package academy.pocu.comp3500.assignment1.app;

import academy.pocu.comp3500.assignment1.PocuBasketballAssociation;
import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

import java.util.Arrays;
import java.util.Random;

public class Program {

    public static void main(String[] args) {
        /*{
            GameStat[] gameStats = new GameStat[]{
                    new GameStat("Player 1", 1, 13, 5, 6, 10, 1),
                    new GameStat("Player 2", 2, 5, 2, 5, 0, 10),
                    new GameStat("Player 1", 3, 12, 6, 9, 8, 5),
                    new GameStat("Player 3", 1, 31, 15, 40, 5, 3),
                    new GameStat("Player 2", 1, 3, 1, 3, 12, 2),
                    new GameStat("Player 1", 2, 11, 6, 11, 9, 3),
                    new GameStat("Player 2", 3, 9, 3, 3, 1, 11),
                    new GameStat("Player 3", 4, 32, 15, 51, 4, 2),
                    new GameStat("Player 4", 3, 44, 24, 50, 1, 1),
                    new GameStat("Player 1", 4, 11, 5, 14, 8, 3),
                    new GameStat("Player 2", 4, 5, 1, 3, 1, 9),
            };
            Player[] players = new Player[]{
                    new Player(),
                    new Player(),
                    new Player(),
                    new Player()
            };

            PocuBasketballAssociation.processGameStats(gameStats, players);
            printPlayerStat(players);

        }*/
        /*{
            Player[] players = new Player[] {
                    new Player("Player 1", 1, 5, 1, 60),
                    new Player("Player 2", 5, 2, 11, 31),
                    new Player("Player 3", 7, 4, 7, 44),
                    new Player("Player 4", 10, 10, 15, 25),
                    new Player("Player 5", 11, 12, 6, 77),
                    new Player("Player 6", 15, 0, 12, 61),
                    new Player("Player 7", 16, 8, 2, 70)
            };

            Player player = PocuBasketballAssociation.findPlayerPointsPerGame(players, 10);
            System.out.println(player.getName());

            player = PocuBasketballAssociation.findPlayerPointsPerGame(players, 5);
            assert (player.getName().equals("Player 2"));

            player = PocuBasketballAssociation.findPlayerPointsPerGame(players, 13);
            assert (player.getName().equals("Player 6"));
        }*/
        /*{
            Player[] players = new Player[] {
                    new Player("Player 4", 10, 10, 15, 25),
                    new Player("Player 2", 5, 2, 11, 31),
                    new Player("Player 3", 7, 4, 7, 44),
                    new Player("Player 1", 1, 5, 1, 60),
                    new Player("Player 6", 15, 0, 12, 61),
                    new Player("Player 7", 16, 8, 2, 70),
                    new Player("Player 5", 11, 12, 6, 77)
            };

            Player player = PocuBasketballAssociation.findPlayerShootingPercentage(players,
                    25);
            System.out.println(player.getName());


        }*/

        {
            Player[] players = new Player[] {

                    new Player("Player 1", 5, 5, 7, 50),
                    new Player("Player 2", 15, 4, 9, 40),
                    new Player("Player 3", 11, 1, 6, 54),
                    new Player("Player 4", 10, 2, 0, 88),
                    new Player("Player 5", 16, 3, 6, 77),
                    new Player("Player 6", 1, 6, 2, 22),
                    new Player("Player 7", 7, 1, 1, 66),
                    //new Player("Player 8", 7, 5, 8, 66),
            };

            Player[] outPlayers = new Player[3];
            Player[] scratch = new Player[3];

            long ret = PocuBasketballAssociation.find3ManDreamTeam(players, outPlayers, scratch);
            System.out.println(ret);
        }

        /*{
            Player[] players = new Player[]{
                    new Player("Player 1", 5, 4, 5, 50),
                    new Player("Player 2", 10, 10, 11, 88),
                    new Player("Player 3", 15, 2, 7, 40),
                    new Player("Player 4", 11, 4, 9, 54),
                    new Player("Player 5", 16, 7, 0, 77),
                    new Player("Player 6", 1, 11, 2, 22),

            };

            final int TEAM_SIZE = 4;

            Player[] outPlayers = new Player[TEAM_SIZE];
            Player[] scratch = new Player[TEAM_SIZE];

            long maxTeamwork = PocuBasketballAssociation.findDreamTeam(players, TEAM_SIZE, outPlayers, scratch);
            System.out.println(maxTeamwork);

        }*/

        /*{

            Player[] players = new Player[] {
                    new Player("Player 1", 2, 6, 5, 78),
                    new Player("Player 2", 10, 3, 5, 66),
                    new Player("Player 3", 3, 6, 1, 22),
                    new Player("Player 4", 1, 4, 2, 12),
                    new Player("Player 5", 11, 1, 9, 26),
                    new Player("Player 6", 7, 6, 2, 15),

            };

            Player[] tempPlayers = new Player[players.length];

            int k = PocuBasketballAssociation.findDreamTeamSize(players, tempPlayers);
            System.out.println(k);

        }*/


        for (int i = 0; i < 300; ++i) {
            test_DreamTeam3();
        }

        /*for (int i = 0; i < 50; ++i) {
            test_DreamTeamSize();
        }*/



        /*test_findDreamTeamSize_1();
        test_findDreamTeamSize_2();
        test_findDreamTeamSize_3();*/
        //test_findDreamTeamSize();


        System.out.println("No Assert");
    }
    private static Player getPlayerOrNull(final Player[] players, final String id) {
        for (Player player : players) {
            if (player.getName().equals(id)) {
                return player;
            }
        }

        return null;
    }


    public static void test_findDreamTeamSize_1() {
        Player[] players = new Player[] {
                new Player("Player 1", 0, 75, 61, 0),
                new Player("Player 2", 0, 0, 43, 0),
                new Player("Player 3", 0, 89, 9, 0),
                new Player("Player 4", 0, 29, 50, 0)
        };

        Player[] scratch = new Player[players.length];
        int k = PocuBasketballAssociation.findDreamTeamSize(players, scratch);

        System.out.println("최고의 팀워크 점수를 성취하려면 필요한 선수 수 : " + "2명 (Player 1, Player 3)");
        System.out.println("나의 답 : " + k + "명");
        System.out.println("============================ E N D ============================");
        System.out.println(System.lineSeparator());
        assert(k == 2);

    }

    public static void test_findDreamTeamSize_2() {
        Player[] players = new Player[] {
                new Player("Player 1", 0, 70, 43, 0),
                new Player("Player 2", 0, 16, 16, 0),
                new Player("Player 3", 0, 42, 22, 0),
                new Player("Player 4", 0, 14, 37, 0),
                new Player("Player 5", 0, 40, 16, 0),
                new Player("Player 6", 0, 72, 23, 0),
                new Player("Player 7", 0, 13, 49, 0),
                new Player("Player 8", 0, 5, 40, 0)
        };

        Player[] scratch = new Player[players.length];
        int k = PocuBasketballAssociation.findDreamTeamSize(players, scratch);

        System.out.println("최고의 팀워크 점수를 성취하려면 필요한 선수 수 : " + "2명 (Player 1, Player 6)");
        System.out.println("나의 답 : " + k + "명");
        System.out.println("============================ E N D ============================");
        System.out.println(System.lineSeparator());
        assert(k == 2);
    }

    public static void test_findDreamTeamSize_3() {
        Player[] players = new Player[] {
                new Player("Player 1", 0, 6, 20, 0),
                new Player("Player 2", 0, 50, 26, 0),
                new Player("Player 3", 0, 89, 25, 0),
                new Player("Player 4", 0, 45, 1, 0),
                new Player("Player 5", 0, 73, 27, 0),
                new Player("Player 6", 0, 84, 34, 0),
                new Player("Player 7", 0, 30, 57, 0),
                new Player("Player 8", 0, 40, 88, 0),
                new Player("Player 9", 0, 47, 19, 0),
                new Player("Player 10", 0, 61, 77, 0)
        };

        Player[] scratch = new Player[players.length];
        int k = PocuBasketballAssociation.findDreamTeamSize(players, scratch);

        System.out.println("최고의 팀워크 점수를 성취하려면 필요한 선수 수 : " + "8명 (Player 2, Player 3, Player 4, Player 5, Player 6, Player 8, Player 9, Player 10)");
        System.out.println("나의 답 : " + k + "명");
        System.out.println("============================ E N D ============================");
        System.out.println(System.lineSeparator());
        assert(k == 8);
    }

    public static void test_findDreamTeamSize() {
        Random random = new Random();

        int length = random.nextInt(15) + 1;
        Player[] players = new Player[length];

        for (int i = 0; i < length; ++i) {
            players[i] = new Player("P" + Integer.toString(i + 1), random.nextInt(100), random.nextInt(100), random.nextInt(100), 0);
        }

        System.out.println("============================ START ============================");

        StringBuilder sb = new StringBuilder();
        sb.append("이름ㅡㅡ | ");
        for (Player p: players) {
            if (p.getName().length() < 4) {
                sb.append(p.getName() + " ");
            } else {
                sb.append(p.getName() + " ");
            }

        }
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        sb = new StringBuilder();
        sb.append("어시스트 | ");
        for (Player p: players) {
            if (p.getAssistsPerGame() < 10) {
                sb.append(p.getAssistsPerGame() + " ");
            } else {
                sb.append(p.getAssistsPerGame() + " ");
            }

        }
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        sb = new StringBuilder();
        sb.append("패스ㅡㅡ | ");
        for (Player p: players) {
            if (p.getPassesPerGame() < 10) {
                sb.append(p.getPassesPerGame() + " ");
            } else {
                sb.append(p.getPassesPerGame() + " ");
            }
        }
        sb.append(System.lineSeparator());
        System.out.println(sb.toString());

        long maxTeamWork = Integer.MIN_VALUE;
        long crtTeamWork;
        int dreamTeamSize = 0;
        for (int i = 1; i <= length; ++i) {
            crtTeamWork = PocuBasketballAssociation.findDreamTeam(players, i, new Player[i], new Player[i]);
            if (crtTeamWork >= maxTeamWork) {
                maxTeamWork = crtTeamWork;
                dreamTeamSize = i;
            }
        }

        Player[] scratch = new Player[players.length];
        int k = PocuBasketballAssociation.findDreamTeamSize(players, scratch);

        System.out.println("최고의 팀워크 점수를 성취하려면 필요한 선수 수 : " + dreamTeamSize + "명");
        System.out.println("나의 답 : " + k + "명");
        System.out.println("============================ E N D ============================");
        System.out.println(System.lineSeparator());
        assert(k == dreamTeamSize);
    }


    public static void test_DreamTeam3() {
        final int PLAYER_SIZE = 15;
        Random rand = new Random();
        Player[] players = new Player[PLAYER_SIZE];

        for (int i = 0; i < PLAYER_SIZE; ++i) {
            int pass = rand.nextInt(20);
            int assist = rand.nextInt(20);
            players[i] = new Player(String.valueOf(i + 1), 0, assist, pass, 0);
        }
        Player[] outPlayers = new Player[3];
        //Player[] myOutPlayers = new Player[3];
        Player[] answers = new Player[3];
        Player[] scratch = new Player[3];
        long answer = get3DreamTeamPerfectly(players, outPlayers, scratch);
        for (int i = 0; i < 3; ++i) {
            answers[i] = outPlayers[i];
        }

        long myRet = PocuBasketballAssociation.find3ManDreamTeam(players, outPlayers, scratch);
        //long myRet = PocuBasketballAssociation.findDreamTeam(players, 3, outPlayers, scratch);

        boolean outPlayerIsSame = false;

        for (int i = 0; i < 3; ++i) {
            outPlayerIsSame = false;
            for (int j = 0; j < 3; ++j) {
                if (answers[i] == outPlayers[j]) {
                    outPlayerIsSame = true;
                    break;
                }
            }
            if (!outPlayerIsSame) {
                break;
            }
        }


        if (answer != myRet) {
            System.out.println("Players");
            for (int i = 0; i < players.length; ++i) {
                System.out.printf("%s - assist(%d), pass(%d), point(%d)%s", players[i].getName(),
                    players[i].getAssistsPerGame(), players[i].getPassesPerGame(),
                    players[i].getAssistsPerGame() * players[i].getPassesPerGame(),
                    System.lineSeparator());
            }
            System.out.println();

            System.out.println("answers");
            for (int i = 0; i < 3; ++i) {
                System.out.printf("%s - assist(%d), pass(%d), point(%d)%s", answers[i].getName(),
                        answers[i].getAssistsPerGame(), answers[i].getPassesPerGame(),
                        answers[i].getAssistsPerGame() * answers[i].getPassesPerGame(),
                        System.lineSeparator());
            }
            System.out.println();

            System.out.println("mine");
            for (int i = 0; i < 3; ++i) {
                System.out.printf("%s - assist(%d), pass(%d), point(%d)%s", outPlayers[i].getName(),
                        outPlayers[i].getAssistsPerGame(), outPlayers[i].getPassesPerGame(),
                        outPlayers[i].getAssistsPerGame() * outPlayers[i].getPassesPerGame(),
                        System.lineSeparator());
            }
        } else {
            //System.out.println("True");
        }
    }

    public static void test_DreamTeamK() {
        final int PLAYER_SIZE = 7;
        Random rand = new Random();
        int k = 3;  //Math.max(0, rand.nextInt(11));

        Player[] players = new Player[PLAYER_SIZE];
        for (int i = 0; i < PLAYER_SIZE; ++i) {
            players[i] = new Player(String.valueOf(i + 1), 0, rand.nextInt(12), rand.nextInt(12), 0);
        }
        Player[] outPlayers = new Player[k];
        Player[] answers = new Player[k];
        Player[] scratch = new Player[k];

        long answer = getKDreamTeamPerfectly(players, k, outPlayers, scratch);

        for (int i = 0; i < k; ++i) {
            answers[i] = outPlayers[i];
        }

        long myRet = PocuBasketballAssociation.findDreamTeam(players, k, outPlayers, scratch);

        if (answer != myRet) {
            System.out.printf("answer : %d%s", answer, System.lineSeparator());
            System.out.printf("answer : %d%s", myRet, System.lineSeparator());
            System.out.println();

            System.out.println("Players");
            for (int i = 0; i < players.length; ++i) {
                System.out.printf("%s - assist(%d), pass(%d), point(%d)%s", players[i].getName(),
                        players[i].getAssistsPerGame(), players[i].getPassesPerGame(),
                        players[i].getAssistsPerGame() * players[i].getPassesPerGame(),
                        System.lineSeparator());
            }
            System.out.println();

            System.out.println("answers");
            for (int i = 0; i < k; ++i) {
                System.out.printf("%s - assist(%d), pass(%d), point(%d)%s", answers[i].getName(),
                        answers[i].getAssistsPerGame(), answers[i].getPassesPerGame(),
                        answers[i].getAssistsPerGame() * answers[i].getPassesPerGame(),
                        System.lineSeparator());
            }
            System.out.println();

            System.out.println("mine");
            for (int i = 0; i < k; ++i) {
                System.out.printf("%s - assist(%d), pass(%d), point(%d)%s", outPlayers[i].getName(),
                        outPlayers[i].getAssistsPerGame(), outPlayers[i].getPassesPerGame(),
                        outPlayers[i].getAssistsPerGame() * outPlayers[i].getPassesPerGame(),
                        System.lineSeparator());
            }
            System.out.println();
        } else {
            boolean isSame;
            for (int i = 0; i < k; ++i) {
                isSame = false;
                for (int j = 0; j < k; ++j) {
                    if (answers[i] == outPlayers[j]) {
                        isSame = true;
                    }
                }
                if (!isSame) {
                    System.out.println("FALSE");
                    System.out.println("--------------------------------------------------");
            System.out.printf("%s %s %s %s%s", answers[0].getName(), answers[1].getName(),
                    answers[2].getName(), answers[3].getName(), System.lineSeparator());
            System.out.printf("%s %s %s %s%s", outPlayers[0].getName(), outPlayers[1].getName(),
                    outPlayers[2].getName(), outPlayers[3].getName(), System.lineSeparator());
            System.out.println("--------------------------------------------------");
            System.out.println();

                for (int l = 0; l < players.length; ++l) {
                    System.out.printf("%s - %d, %d%s", players[l].getName(), players[l].getAssistsPerGame(),
                            players[l].getPassesPerGame(), System.lineSeparator());
                }
                }
            }
        }
    }

    public static void printGameStat(GameStat[] gameStats) {

        for (int i = 0; i < gameStats.length; ++i) {
            System.out.printf("%s %d%s", gameStats[i].getPlayerName(), gameStats[i].getGame()
                , System.lineSeparator());
        }
    }
    public static void printPlayerStat(Player[] players) {
        Player player;
        for (int i = 0; i < players.length; ++i) {
            player = players[i];
            System.out.printf("%s %d %d %d %d%s", player.getName(), player.getPointsPerGame(),
                    player.getAssistsPerGame(), player.getPassesPerGame(), player.getShootingPercentage()
                    , System.lineSeparator());
        }
    }
    public static void test_D() {
        int length = 10;
        Player[] players = new Player[length];
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            players[i] = new Player("P" + Integer.toString(random.nextInt(100)), random.nextInt(100), random.nextInt(100), random.nextInt(100), 0);
        }

        Arrays.sort(players, ((o1, o2) -> {
            return o1.getPointsPerGame() - o2.getPointsPerGame();
        }));

        int data;
        Player player;
        StringBuilder sb;

        for (int i = 0; i < 100; i++) {
            System.out.println("============================ START ============================");
            data = random.nextInt(100);
            player = PocuBasketballAssociation.findPlayerPointsPerGame(players, data);

            System.out.println(System.lineSeparator());

            sb = new StringBuilder();
            sb.append("나의 답 | ");
            for (Player p: players) {
                if (player.getName() == p.getName()) {
                    sb.append("✅ ");
                } else {
                    sb.append(" ");
                }

            }
            sb.append(System.lineSeparator());
            System.out.println(sb.toString());

            sb = new StringBuilder();
            sb.append("이름 | ");
            for (Player p: players) {
                if (p.getName().length() < 3) {
                    sb.append(p.getName() + " ");
                } else {
                    sb.append(p.getName() + " ");
                }

            }
            sb.append(System.lineSeparator());
            System.out.println(sb.toString());

            sb = new StringBuilder();
            sb.append("점수 | ");
            for (Player p: players) {
                if (p.getPointsPerGame() < 10) {
                    sb.append(p.getPointsPerGame() + " ");
                } else {
                    sb.append(p.getPointsPerGame() + " ");
                }

            }
            sb.append(System.lineSeparator());

            System.out.println(sb.toString());

            System.out.println("타겟 숫자 : " + data);
            System.out.println("나의 답ㅡ : " + player.getName() + ", " + player.getPointsPerGame());
            System.out.println(System.lineSeparator());
            System.out.println("============================ E N D ============================");
        }
    }
    public static void test_DreamTeamSize() {
        final int PLAYER_SIZE = 10;
        Random rand = new Random();
        Player[] players = new Player[PLAYER_SIZE];
        for (int i = 0; i < PLAYER_SIZE; ++i) {
            players[i] = new Player(String.valueOf(i + 1), 0, rand.nextInt(20), rand.nextInt(20), 0);
        }

        Player[] scratch = new Player[PLAYER_SIZE];
        Player[] myScratch = new Player[PLAYER_SIZE];
        Player[] outPlayers =new Player[PLAYER_SIZE];
        // answer[0] = size, answer[1] = maxPoint, answer[2] = total subset Count
        long[] answer = new long[]{0, 0, 0};
        long[] myRet = new long[]{0, 0, 0};
        int count = 0;

        for (int i = 0; i < scratch.length; ++i) {
            getMaxPointTeamPerfectly(players, outPlayers, scratch, answer, 0, 0,
                    i + 1, i + 1);
        }
        myRet[0] = PocuBasketballAssociation.findDreamTeamSize(players, myScratch);

        if (answer[0] != myRet[0]) {
            System.out.println("=================== 문제 =====================");
            for (int i = 0; i < PLAYER_SIZE; ++i) {
                System.out.printf("%s - assist(%d), pass(%d)%s", players[i].getName(),
                        players[i].getAssistsPerGame(), players[i].getPassesPerGame(),
                        System.lineSeparator());
            }
            System.out.println();

            System.out.printf("정답 : %d명, %d점%s", answer[0], answer[1], System.lineSeparator());
            System.out.printf("내 대답 : %d명%s", myRet[0], System.lineSeparator());
            System.out.println();

            for (int i = 0; i < answer[0]; ++i) {
                System.out.printf("%s - assist(%d), pass(%d)%s", outPlayers[i].getName(),
                        outPlayers[i].getAssistsPerGame(), outPlayers[i].getPassesPerGame(),
                        System.lineSeparator());
            }
            System.out.println();
            System.out.println("============================================");
            System.out.println();
        } else {
            System.out.println("true");
        }

    }

    public static void getMaxPointTeamPerfectly(Player[] players, Player[] outPlayers,
                                                Player[] scratch, long[] result,
                                                int startIdx, int sourceIdx,
                                                int pickCount, int scratchLength) {
        if (pickCount == 0) {
            long thisPoint = 0;
            int minAssist = scratch[0].getAssistsPerGame();
            for (int i = 0; i < scratchLength; ++i) {
                thisPoint += scratch[i].getPassesPerGame();
                int thisAssist = scratch[i].getAssistsPerGame();
                if (thisAssist < minAssist) {
                    minAssist = thisAssist;
                }
            }
            thisPoint *= minAssist;

            if (thisPoint >= result[1]) {
                result[1] = thisPoint;
                result[0] = scratchLength;

                for (int i = 0; i < scratchLength; ++i) {
                    outPlayers[i] = scratch[i];
                }
            }

        } else if (sourceIdx == players.length) {
            return;
        } else {
            scratch[startIdx] = players[sourceIdx];
            getMaxPointTeamPerfectly(players, outPlayers, scratch, result, startIdx + 1,
                    sourceIdx + 1, pickCount - 1, scratchLength);
            getMaxPointTeamPerfectly(players, outPlayers, scratch, result, startIdx,
                    sourceIdx + 1, pickCount, scratchLength);
        }
    }

    public static long getKDreamTeamPerfectly(Player[] players, int k, Player[] outPlayers,
                                              Player[] scratch) {
        for (int i = 0; i < k; ++i) {
            outPlayers[i] = players[i];
        }
        getDreamTeamRecursive(players, outPlayers, scratch, 0, 0, k);
        return getTeamWorkPoint(outPlayers, 0, outPlayers.length - 1);

    }
    public static void getDreamTeamRecursive(Player[] players, Player[] outPlayers, Player[] scratch,
                                             int startIdx, int srcIdx, int pickCount) {
        if (pickCount == 0) {
            long thisPoint = getTeamWorkPoint(scratch, 0, scratch.length - 1);
            long maxPoint = getTeamWorkPoint(outPlayers, 0, outPlayers.length - 1);
            if (thisPoint > maxPoint) {
                for (int i = 0; i < scratch.length; ++i) {
                    outPlayers[i] = scratch[i];
                }
            }
            return;
        } else if (srcIdx == players.length) {
            return;
        } else {
            scratch[startIdx] = players[srcIdx];
            getDreamTeamRecursive(players, outPlayers, scratch, startIdx + 1, srcIdx + 1,
                    pickCount - 1);
            getDreamTeamRecursive(players, outPlayers, scratch, startIdx, srcIdx + 1,
                    pickCount);
        }
    }


    public static long getTeamWorkPoint(Player[] players, int start, int endIdx) {
        long ret = 0;
        int minAssist = players[start].getAssistsPerGame();
        for (int i = start; i <= endIdx; ++i) {
            ret += players[i].getPassesPerGame();
            int thisAssist = players[i].getAssistsPerGame();
            if (thisAssist < minAssist) {
                minAssist = thisAssist;
            }
        }
        return ret * minAssist;
    }

    public static long get3DreamTeamPerfectly(Player[] players, Player[] outPlayers, Player[] scratch) {
        final int maxMember = 3;

        long maxPoint = 0;
        long thisPoint = 0;
        int minAssist = players[0].getAssistsPerGame();
        for (int i = 0; i < maxMember; ++i) {
            outPlayers[i] = players[i];
            maxPoint += players[i].getPassesPerGame();
            int thisAssist = players[i].getAssistsPerGame();
            if (thisAssist < minAssist) {
                minAssist = thisAssist;
            }
        }
        maxPoint *= minAssist;

        for (int i = 0; i < players.length; ++i) {
            for (int j = i + 1; j < players.length; ++j) {
                for (int k = j + 1; k < players.length; ++k) {
                    thisPoint = 0;
                    scratch[0] = players[i];
                    scratch[1] = players[j];
                    scratch[2] = players[k];

                    minAssist = scratch[0].getAssistsPerGame();
                    for (int l = 0; l < maxMember; ++l) {
                        thisPoint += scratch[l].getPassesPerGame();
                        int thisAssist = scratch[l].getAssistsPerGame();
                        if (thisAssist < minAssist) {
                            minAssist = thisAssist;
                        }
                    }
                    thisPoint *= minAssist;

                    if (thisPoint > maxPoint) {
                        outPlayers[0] = scratch[0];
                        outPlayers[1] = scratch[1];
                        outPlayers[2] = scratch[2];
                        maxPoint = thisPoint;
                    }
                }
            }
        }

        return maxPoint;
    }

    public static void printOutPlayers(Player[] outPlayers) {
        System.out.println("===== out Players =====");
        for (Player p : outPlayers) {
            System.out.printf("%s - assist(%d), pass(%d), teamWork(%d)%s", p.getName(),
                    p.getAssistsPerGame(), p.getPassesPerGame(), p.getAssistsPerGame() * p.getPassesPerGame(),
                    System.lineSeparator());
        }
        System.out.println();
    }
}
