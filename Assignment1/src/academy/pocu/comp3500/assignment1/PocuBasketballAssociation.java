package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

// static 금지, Collection<E>, List<E> 금지
// 기본 자료형, String, 기본 자료 구조만 사용할 것
// java.util.Array, java.util.stream.Stream 사용 금지
// 개체 인스턴스 생성 금지
// 시간 복잡도


public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    // gameStats = {이름, 경기번호, 득점, 슛 성공 수, 슛 시도 수, 어시스트 수, 패스 수}
    // outPlayers = {이름, 평균 득점, 평균 어시스트, 평균 패스, 슛 성공률}

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        if (gameStats == null || gameStats.length == 0) {
            return;
        }
        // 정렬 한 번 하고 시작. n*logN
        quickSortRecursive(gameStats, 0, gameStats.length - 1);

        int gameCount = 0;
        int playerIdx = 0;

        int currIdxplayerHash;

        // 0번 인덱스의 정보를 일단 outPlayer에 넣음
        int preIdxPlayerHash = gameStats[0].getPlayerName().hashCode();


        accumulatePlayerStats(gameStats, 0, outPlayers, 0);
        ++gameCount;

        int goals = gameStats[0].getGoals();
        int goalAttempts = gameStats[0].getGoalAttempts();

        for (int i = 1; i < gameStats.length; ++i) {

            currIdxplayerHash = gameStats[i].getPlayerName().hashCode();

            // currPlayer가 prePlayer랑 같으면 그냥 스탯을 계속 더함
            if (currIdxplayerHash == preIdxPlayerHash) {
                ++gameCount;
                accumulatePlayerStats(gameStats, i, outPlayers, playerIdx);
                goals += gameStats[i].getGoals();
                goalAttempts += gameStats[i].getGoalAttempts();

            } else {
                // 바로 전 인덱스까지 기록했던 stat들의 %를 계산해서 outPlayer에 넣음
                int shootPercentage = 100 * goals / goalAttempts;
                updatePlayerStatsToPecentages(outPlayers, playerIdx, gameCount, shootPercentage);

                accumulatePlayerStats(gameStats, i, outPlayers, ++playerIdx);
                goals = gameStats[i].getGoals();
                goalAttempts = gameStats[i].getGoalAttempts();

                gameCount = 1;
            }
            preIdxPlayerHash = currIdxplayerHash;
        }

        updatePlayerStatsToPecentages(outPlayers, playerIdx, gameCount,
                100 * goals / goalAttempts);

    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        if (players == null || players.length == 0) {
            return null;
        }
        if (players.length == 1) {
            return players[0];
        }
        int right = players.length;
        int midIdx = right / 2;
        int midDiff = Math.abs(players[midIdx].getPointsPerGame() - targetPoints);
        int resIdx = findPlayerPointsPerGameRecursive(players, targetPoints, 0, right,
                midIdx, midDiff);
        return players[resIdx];
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        if (players == null || players.length == 0) {
            return null;
        }
        if (players.length == 1) {
            return players[0];
        }
        int right = players.length;
        int midIdx = right / 2;
        int midDiff = Math.abs(players[midIdx].getShootingPercentage() - targetShootingPercentage);
        int resIdx = findPlayerShootingPercentagePerGameRecursive(players, targetShootingPercentage,
                0, right, midIdx, midDiff);
        return players[resIdx];
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers,
                                         final Player[] scratch) {

        sortByTeamWorkDescRecursive(players, 0, players.length - 1);
        sortByAssistDescRecursive(players, 3, players.length - 1);

        int minAssist = players[0].getAssistsPerGame();
        int minPass = players[0].getPassesPerGame();

        for (int i = 1; i < 3; ++i) {
            int thisAssist = players[i].getAssistsPerGame();
            int thisPass = players[i].getPassesPerGame();
            if (thisAssist < minAssist) {
                minAssist = thisAssist;
            } else if (thisPass < minPass) {
                minPass = thisPass;
            }
        }

        int lastIndex = 2;
        for (int i = 3; i < players.length; ++i) {
            int thisAssist = players[i].getAssistsPerGame();
            int thisPass = players[i].getPassesPerGame();
            if (thisAssist >= minAssist || thisPass >= minPass) {
                ++lastIndex;
            } else {
                break;
            }
        }
        //System.out.printf("lastIndex: %d%s", lastIndex, System.lineSeparator());

        outPlayers[0] = players[0];
        outPlayers[1] = players[1];
        outPlayers[2] = players[2];

        findDreamTeamRecursive(players, outPlayers, scratch, 0, 0, 3, lastIndex);

        long ret = 0;
        minAssist = outPlayers[0].getAssistsPerGame();

        for (int i = 0; i < 3; ++i) {
            ret += outPlayers[i].getPassesPerGame();
            int thisAssist = outPlayers[i].getAssistsPerGame();
            if (thisAssist < minAssist) {
                minAssist = thisAssist;
            }
        }
        ret *= minAssist;

        return ret;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        if (k == 0) {
            return 0;
        }
        if (k == 1) {
            int maxIdx = 0;
            long maxVal = players[0].getPassesPerGame() * players[0].getAssistsPerGame();
            for (int i = 1; i < players.length; ++i) {
                long thisVal = players[i].getPassesPerGame() * players[i].getAssistsPerGame();
                if (thisVal > maxVal) {
                    maxVal = thisVal;
                    maxIdx = i;
                }
            }
            outPlayers[0] = players[maxIdx];
            return maxVal;
        }

        sortByTeamWorkDescRecursive(players, 0, players.length - 1);
        sortByAssistDescRecursive(players, k, players.length - 1);

        long maxPoint = 0;
        int minAssist = players[0].getAssistsPerGame();
        int minPass = players[0].getPassesPerGame();

        for (int i = 0; i < k; ++i) {

            outPlayers[i] = players[i];

            int thisAssist = players[i].getAssistsPerGame();
            int thisPass = players[i].getPassesPerGame();

            if (thisAssist < minAssist) {
                minAssist = thisAssist;
            }
            if (thisPass < minPass) {
                minPass = thisPass;
            }

            maxPoint += players[i].getPassesPerGame();
        }
        maxPoint *= minAssist;

        long[] maxPointPointer = { maxPoint };

        int lastIndex = k - 1;
        for (int i = k; i < players.length; ++i) {
            int thisAssist = players[i].getAssistsPerGame();
            int thisPass = players[i].getPassesPerGame();
            if (thisAssist > minAssist || thisPass > minPass) {
                ++lastIndex;
            }
        }

        for (int i = 0; i < outPlayers.length; ++i) {
            scratch[0] = players[i];
            findDreamTeamRecursive2(players, outPlayers, scratch, 1,
                    i + 1, k - 1, lastIndex, maxPointPointer);
        }

        return maxPointPointer[0];
    }
    private static void findDreamTeamRecursive2(final Player[] players, final Player[] outPlayers,
                                                final Player[] scratch, int startIdx,
                                                int sourceIdx, int pickCount, int lastIdx,
                                                long[] maxPointPointer) {
        if (pickCount == 0) {
            long thisPoint = getTeamWorkPoint(scratch);
            if (thisPoint > maxPointPointer[0]) {
                for (int i = 0; i < outPlayers.length; ++i) {
                    outPlayers[i] = scratch[i];
                }
                maxPointPointer[0] = thisPoint;
            }
            return;

        } else if (sourceIdx == lastIdx + 1) {
            return;
        } else {
            scratch[startIdx] = players[sourceIdx];
            findDreamTeamRecursive2(players, outPlayers, scratch, startIdx + 1,
                    sourceIdx + 1, pickCount - 1, lastIdx, maxPointPointer);
            findDreamTeamRecursive2(players, outPlayers, scratch, startIdx,
                    sourceIdx + 1, pickCount, lastIdx, maxPointPointer);
        }
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        if (players == null || players.length == 0) {
            return 0;
        }
        if (players.length == 1) {
            return 1;
        }

        for (int i = 0; i < players.length; ++i) {
            scratch[i] = players[i];
        }
        sortByTeamWorkDesc(scratch);

        long maxTeamWorkPoint = getTeamWorkPoint(scratch, 0, 0);
        int keepCount;
        int count = 1;

        for (int i = 1; i < scratch.length; ++i) {
            long getNewPoint = getTeamWorkPoint(scratch, 0, i);
            if (getNewPoint > maxTeamWorkPoint) {
                count = i;
                maxTeamWorkPoint = getNewPoint;
            }
        }

        keepCount = count + 1;
        count = 1;

        stableSortByAssistDesc(scratch, 0);

        for (int i = 1; i < scratch.length; ++i) {
            long getNewPoint = getTeamWorkPoint(scratch, 0, i);
            if (getNewPoint > maxTeamWorkPoint) {
                count = i;
                maxTeamWorkPoint = getNewPoint;
            }
        }

        if (count != 1) {
            return ++count;
        }

        return keepCount;
    }
    private static void stableSortByAssistDesc(final Player[] scratch, int start) {
        int thisAssistCount;
        int nextAssistCount;
        for (int i = 0; i < scratch.length - 1; ++i) {
            for (int j = start; j < scratch.length - 1 - i; ++j) {
                thisAssistCount = scratch[j].getAssistsPerGame();
                nextAssistCount = scratch[j + 1].getAssistsPerGame();
                if (thisAssistCount < nextAssistCount) {
                    Player temp = scratch[j];
                    scratch[j] = scratch[j + 1];
                    scratch[j + 1] = temp;
                }
            }
        }
    }
    private static void stableSortByPassDesc(final Player[] players) {
        for (int i = 0; i < players.length - 1; ++i) {
            for (int j = 0; j < players.length - i - 1; ++j) {
                if (players[j].getPassesPerGame() < players[j].getPassesPerGame()) {
                    swap(players, j, j + 1);
                }
            }
        }
    }

    private static void sortByTeamWorkDesc(final Player[] scratch) {
        long thisTeamWorkPoint;
        long nextTeamWorkPoint;
        for (int i = 0; i < scratch.length - 1; ++i) {
            for (int j = 0; j < scratch.length - 1 - i; ++j) {
                thisTeamWorkPoint = getTeamWorkPoint(scratch, j, j);
                nextTeamWorkPoint = getTeamWorkPoint(scratch, j + 1, j + 1);
                if (thisTeamWorkPoint < nextTeamWorkPoint) {
                    Player temp = scratch[j];
                    scratch[j] = scratch[j + 1];
                    scratch[j + 1] = temp;
                }
            }
        }
    }

    private static void sortByAssistDescRecursive(final Player[] players,
                                                  int left, int right) {
        if (left >= right) {
            return;
        }

        int preLeft = left;

        int mid = (left + right) / 2;
        swap(players, mid, right);
        int rightValue = players[right].getAssistsPerGame();
        int thisValue;
        for (int i = left; i < right; ++i) {
            thisValue = players[i].getAssistsPerGame();
            if (thisValue > rightValue) {
                swap(players, i, left);
                ++left;
            }
        }
        swap(players, left, right);

        sortByAssistDescRecursive(players, preLeft, left - 1);
        sortByAssistDescRecursive(players, left + 1, right);
    }
    private static void sortByPassDescRecursive(final Player[] players,
                                                int left, int right) {
        if (left >= right) {
            return;
        }

        int preLeft = left;

        int mid = (left + right) / 2;
        swap(players, mid, right);
        int rightValue = players[right].getPassesPerGame();
        int thisValue;
        for (int i = left; i < right; ++i) {
            thisValue = players[i].getPassesPerGame();
            if (thisValue > rightValue) {
                swap(players, i, left);
                ++left;
            }
        }
        swap(players, left, right);

        sortByPassDescRecursive(players, preLeft, left - 1);
        sortByPassDescRecursive(players, left + 1, right);
    }


    private static void sortByTeamWorkDescRecursive(final Player[] players, int left, int right) {
        if (left >= right) {
            return;
        }

        int preLeft = left;

        int mid = (left + right) / 2;
        swap(players, mid, right);
        int rightTeamWorkPoint = players[right].getPassesPerGame() * players[right].getAssistsPerGame();
        int thisTeamWorkPoint;
        for (int i = left; i < right; ++i) {
            thisTeamWorkPoint = players[i].getPassesPerGame() * players[i].getAssistsPerGame();
            if (thisTeamWorkPoint > rightTeamWorkPoint) {
                swap(players, i, left);
                ++left;
            }
        }
        swap(players, left, right);

        sortByTeamWorkDescRecursive(players, preLeft, left - 1);
        sortByTeamWorkDescRecursive(players, left + 1, right);
    }


    private static void quickSortRecursive(final GameStat[] gameStats, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = partition(gameStats, left, right);
        quickSortRecursive(gameStats, left, pivot - 1);
        quickSortRecursive(gameStats, pivot + 1, right);
    }
    private static int partition(final GameStat[] gameStats, int left, int right) {

        int pivot = (left + right) / 2;
        swap(gameStats, pivot, right);

        int rightIdxHash = gameStats[right].getPlayerName().hashCode();
        int currIdxHash;

        for (int i = left; i < right; ++i) {
            currIdxHash = gameStats[i].getPlayerName().hashCode();

            if (currIdxHash < rightIdxHash) {
                swap(gameStats, i, left);
                left++;
            }
        }
        swap(gameStats, left, right);
        return left;
    }
    private static void swap(final GameStat[] gameStats, int i, int j) {
        GameStat temp = gameStats[i];
        gameStats[i] = gameStats[j];
        gameStats[j] = temp;
    }
    private static void swap(final Player[] players, int i, int j) {
        Player temp = players[i];
        players[i] = players[j];
        players[j] = temp;
    }
    private static void accumulatePlayerStats(final GameStat[] gameStats, int statIdx,
                                              final Player[] outPlayers, int playerIdx) {
        GameStat gameStat = gameStats[statIdx];
        Player player = outPlayers[playerIdx];

        outPlayers[playerIdx].setName(gameStat.getPlayerName());
        outPlayers[playerIdx].setPointsPerGame(player.getPointsPerGame() + gameStat.getPoints());
        outPlayers[playerIdx].setAssistsPerGame(player.getAssistsPerGame() + gameStat.getAssists());
        outPlayers[playerIdx].setPassesPerGame(player.getPassesPerGame() + gameStat.getNumPasses());
    }
    private static void updatePlayerStatsToPecentages(final Player[] outPlayers, int playerIdx, int gameCount,
                                                      int shootingPercentage) {
        outPlayers[playerIdx].setPointsPerGame(outPlayers[playerIdx].getPointsPerGame() / gameCount);
        outPlayers[playerIdx].setAssistsPerGame(outPlayers[playerIdx].getAssistsPerGame() / gameCount);
        outPlayers[playerIdx].setPassesPerGame(outPlayers[playerIdx].getPassesPerGame() / gameCount);
        outPlayers[playerIdx].setShootingPercentage(shootingPercentage);
    }

    private static int findPlayerPointsPerGameRecursive(final Player[] players, int targetPoint,
                                                        int left, int right, int minIdx, int minDiff) {
        if (right - left <= 1) {
            int leftDiff = Math.abs(players[left].getPointsPerGame() - targetPoint);
            if (leftDiff < minDiff) {
                minDiff = leftDiff;
                minIdx = left;
            }
            return minIdx;
        }
        int mid = (left + right) / 2;
        int midIdxDiff = Math.abs(players[mid].getPointsPerGame() - targetPoint);

        if (midIdxDiff == 0) {
            return mid;
        }

        if (midIdxDiff < minDiff) {
            minDiff = midIdxDiff;
            minIdx = mid;

        } else if (midIdxDiff == minDiff) {
            if (mid > minIdx) {
                minIdx = mid;
                minDiff = midIdxDiff;
            }
        }
        if (targetPoint < players[mid].getPointsPerGame()) {
            return findPlayerPointsPerGameRecursive(players, targetPoint, left, mid, minIdx, minDiff);
        } else {
            return findPlayerPointsPerGameRecursive(players, targetPoint, mid, right, minIdx, minDiff);
        }
    }

    private static int findPlayerShootingPercentagePerGameRecursive(final Player[] players,
                                                                    int targetShootingPercentage,
                                                                    int left,
                                                                    int right, int minIdx,
                                                                    int minDiff) {
        if (right - left <= 1) {
            int leftDiff = Math.abs(players[left].getShootingPercentage() - targetShootingPercentage);
            if (leftDiff < minDiff) {
                minDiff = leftDiff;
                minIdx = left;
            }
            return minIdx;
        }
        int mid = (left + right) / 2;
        int midIdxDiff = Math.abs(players[mid].getShootingPercentage() - targetShootingPercentage);

        if (midIdxDiff == 0) {
            return mid;
        }

        if (midIdxDiff < minDiff) {
            minDiff = midIdxDiff;
            minIdx = mid;

        } else if (midIdxDiff == minDiff) {
            if (mid > minIdx) {
                minIdx = mid;
                minDiff = midIdxDiff;
            }
        }
        if (targetShootingPercentage < players[mid].getShootingPercentage()) {
            return findPlayerShootingPercentagePerGameRecursive(players, targetShootingPercentage,
                    left, mid,
                    minIdx, minDiff);
        } else {
            return findPlayerShootingPercentagePerGameRecursive(players, targetShootingPercentage,
                    mid, right,
                    minIdx, minDiff);
        }
    }

    private static void findDreamTeamRecursive(final Player[] players, final Player[] outPlayers,
                                               final Player[] scratch, int index, int depth,
                                               int pickCount, int lastIndex) {
        if (pickCount == 0) {

            long thisTeamPoint = getTeamWorkPoint(scratch);
            long maxTeamPoint = getTeamWorkPoint(outPlayers);
            if (thisTeamPoint > maxTeamPoint) {
                for (int i = 0; i < outPlayers.length; ++i) {
                    outPlayers[i] = scratch[i];
                }
            }
            return;
        } else if (depth == lastIndex + 1) {
            return;
        } else {
            scratch[index] = players[depth];

            findDreamTeamRecursive(players, outPlayers, scratch, index + 1, depth + 1,
                    pickCount - 1, lastIndex);
            findDreamTeamRecursive(players, outPlayers, scratch, index, depth + 1,
                    pickCount, lastIndex);
        }
    }
    private static int getTeamWorkValue(final Player[] scratch) {
        int value = 0;
        int minAssist = 0x7FFFFFFF;
        for (int i = 0; i < scratch.length; ++i) {
            value += scratch[i].getPassesPerGame();
            minAssist = Math.min(minAssist, scratch[i].getAssistsPerGame());
        }
        return value * minAssist;
    }
    private static long getTeamWorkPoint(final Player[] outPlayers) {
        assert (outPlayers.length >= 1) : "outPlayers.length < 1";
        long sumPasses = 0;
        int minAssistes = outPlayers[0].getAssistsPerGame();
        for (int i = 0; i < outPlayers.length; ++i) {
            sumPasses += outPlayers[i].getPassesPerGame();
            int thisAssistes = outPlayers[i].getAssistsPerGame();
            if (thisAssistes < minAssistes) {
                minAssistes = thisAssistes;
            }
        }
        return sumPasses * minAssistes;
    }
    private static long getTeamWorkPoint(final Player[] players, int startIdx, int lastIdx) {

        long sumPasses = 0;
        int minAssistes = players[startIdx].getAssistsPerGame();
        for (int i = startIdx; i <= lastIdx; ++i) {
            sumPasses += players[i].getPassesPerGame();
            int thisAssistes = players[i].getAssistsPerGame();
            if (thisAssistes < minAssistes) {
                minAssistes = thisAssistes;
            }
        }
        return sumPasses * minAssistes;
    }

    private static void print(final Player[] players) {
        System.out.println("===== Players =====");
        for (Player p : players) {
            System.out.printf("%s - assist(%d), pass(%d), teamWork(%d)%s", p.getName(),
                    p.getAssistsPerGame(), p.getPassesPerGame(), p.getAssistsPerGame() * p.getPassesPerGame(),
                    System.lineSeparator());
        }
        System.out.println();
    }
}

