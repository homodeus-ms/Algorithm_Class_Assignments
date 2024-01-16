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
        sortByAssistPassDescRecursive(players, 0, players.length - 1);

        long maxPoint = 0;
        long passSum = 0;
        int minPassIdx = 0;
        int minPassCount = players[minPassIdx].getPassesPerGame();

        for (int i = 0; i < 3; ++i) {
            scratch[i] = players[i];
            outPlayers[i] = players[i];
            int thisPassCount = players[i].getPassesPerGame();
            if (minPassCount > thisPassCount) {
                minPassCount = thisPassCount;
                minPassIdx = i;
            }
            passSum += thisPassCount;
        }
        maxPoint = passSum * players[2].getAssistsPerGame();

        int thisPassCount;
        int thisAssistCount;
        long thisTeamPoint = 0;

        for (int i = 3; i < players.length; ++i) {
            thisPassCount = players[i].getPassesPerGame();
            thisAssistCount = players[i].getAssistsPerGame();

            passSum += thisPassCount - minPassCount;
            thisTeamPoint = passSum * thisAssistCount;
            scratch[minPassIdx] = players[i];

            if (maxPoint < thisTeamPoint) {
                outPlayers[0] = scratch[0];
                outPlayers[1] = scratch[1];
                outPlayers[2] = scratch[2];
                maxPoint = thisTeamPoint;
            }

            minPassIdx = 0;
            for (int j = 0; j < 3; ++j) {
                if (scratch[minPassIdx].getPassesPerGame() > scratch[j].getPassesPerGame()) {
                    minPassIdx = j;
                }
            }
            minPassCount = scratch[minPassIdx].getPassesPerGame();
        }

        return maxPoint;
    }


    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        // 바로 리턴 할 수 있는 조건들
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
        if (k == players.length) {
            for (int i = 0; i < players.length; ++i) {
                outPlayers[i] = players[i];
            }
            return getTeamWorkPoint(players, 0, k - 1);
        }

        // 1. 어시스트 순으로 정렬, k명의 포인트를 구해 놓음
        // 2. k명 안에서 패스수가 가장 적은 녀석을 k밖에 있는 녀석과 교환하면서 최대값을 계산해 봄
        sortByAssistPassDescRecursive(players, 0, players.length - 1);

        long[] maxPointPointer = {getTeamWorkPoint(players, 0, k - 1)};
        for (int i = 0; i < k; ++i) {
            outPlayers[i] = players[i];
        }

        for (int i = k; i < players.length; ++i) {
            int minPassIdx = getMinPassIndex(players, 0, k - 1);
            swap(players, minPassIdx, i);

            long thisPoint = getTeamWorkPoint(players, 0, k - 1);
            if (thisPoint > maxPointPointer[0]) {
                maxPointPointer[0] = thisPoint;
                for (int j = 0; j < k; ++j) {
                    outPlayers[j] = players[j];
                }
            }
        }

        return maxPointPointer[0];
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        if (players == null || players.length == 0) {
            return 0;
        }
        if (players.length == 1) {
            return 1;
        }

        sortByAssistDescRecursive(players, 0, players.length - 1);

        long maxPoint = 0;
        long sum = 0;
        int lastIdx = players.length - 1;

        for (int i = 0; i < players.length; ++i) {
            sum += players[i].getPassesPerGame();
        }
        maxPoint = sum * players[lastIdx].getAssistsPerGame();

        int maxIndex = players.length;

        for (int i = players.length - 1; i > 0; --i) {
            sum -= players[i].getPassesPerGame();
            --lastIdx;
            if (players[i].getAssistsPerGame() == players[i - 1].getAssistsPerGame()) {
                continue;
            }

            long thisPoint = sum * players[lastIdx].getAssistsPerGame();

            if (thisPoint > maxPoint) {
                maxPoint = thisPoint;
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    private static void sortByAssistPassDescRecursive(final Player[] players, int left, int right) {
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
            if (thisValue == rightValue) {
                if (players[i].getPassesPerGame() > players[right].getPassesPerGame()) {
                    swap(players, i, left);
                    ++left;
                }
            } else if (thisValue > rightValue) {
                swap(players, i, left);
                ++left;
            }
        }
        swap(players, left, right);

        sortByAssistPassDescRecursive(players, preLeft, left - 1);
        sortByAssistPassDescRecursive(players, left + 1, right);

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

    private static int getMinPassIndex(Player[] players, int startIdx, int endIdx) {
        int minPass = 0x7FFFFFFF;
        int minIdx = -1;
        for (int i = startIdx; i <= endIdx; ++i) {
            int thisPass = players[i].getPassesPerGame();
            if (thisPass < minPass) {
                minPass = thisPass;
                minIdx = i;
            }
        }
        return minIdx;
    }

    /*private static void print(final Player[] players) {
        System.out.println("===== Players =====");
        for (Player p : players) {
            System.out.printf("%s - assist(%d), pass(%d), teamWork(%d)%s", p.getName(),
                    p.getAssistsPerGame(), p.getPassesPerGame(), p.getAssistsPerGame() * p.getPassesPerGame(),
                    System.lineSeparator());
        }
        System.out.println();
    }

    private static void sortByPassAssistDescRecursive(final Player[] players, int left, int right) {
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
            if (thisValue == rightValue) {
                if (players[i].getAssistsPerGame() > players[right].getAssistsPerGame()) {
                    swap(players, i, left);
                    ++left;
                }
            } else if (thisValue > rightValue) {
                swap(players, i, left);
                ++left;
            }
        }
        swap(players, left, right);

        sortByPassAssistDescRecursive(players, preLeft, left - 1);
        sortByPassAssistDescRecursive(players, left + 1, right);

    }

    private static void sortByTeamWorkAssistDescRecursive(final Player[] players, int left, int right) {
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
            if (thisTeamWorkPoint == rightTeamWorkPoint) {
                if (players[i].getAssistsPerGame() > players[right].getAssistsPerGame()) {
                    swap(players, i, left);
                    ++left;
                }
            } else if (thisTeamWorkPoint > rightTeamWorkPoint) {
                swap(players, i, left);
                ++left;
            }
        }
        swap(players, left, right);

        sortByTeamWorkAssistDescRecursive(players, preLeft, left - 1);
        sortByTeamWorkAssistDescRecursive(players, left + 1, right);
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

    private static void get3ManDreamTeamRecursive(final Player[] players, final Player[] outPlayers,
                                                  long[] outMaxPoint, int index, int minPassIdx,
                                                  long sum) {
        if (index == players.length) {
            return;
        }

        sum -= players[minPassIdx].getPassesPerGame();
        sum += players[index].getPassesPerGame();
        swap(players, minPassIdx, index);

        long thisTeamWorkPoint = sum * players[minPassIdx].getAssistsPerGame();

        minPassIdx = getMinPassIndex(players, 0, 2);

        if (thisTeamWorkPoint > outMaxPoint[0]) {
            outMaxPoint[0] = thisTeamWorkPoint;
            outPlayers[0] = players[0];
            outPlayers[1] = players[1];
            outPlayers[2] = players[2];
        }

        get3ManDreamTeamRecursive(players, outPlayers, outMaxPoint, index + 1, minPassIdx, sum);
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

    private static void findDreamTeamRecursive2(final Player[] players, final Player[] outPlayers,
                                                final Player[] scratch, int startIdx,
                                                int sourceIdx, int pickCount, int searchLength,
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

        } else if (sourceIdx == searchLength) {
            return;
        } else {
            scratch[startIdx] = players[sourceIdx];
            findDreamTeamRecursive2(players, outPlayers, scratch, startIdx + 1,
                    sourceIdx + 1, pickCount - 1, searchLength, maxPointPointer);
            findDreamTeamRecursive2(players, outPlayers, scratch, startIdx,
                    sourceIdx + 1, pickCount, searchLength, maxPointPointer);
        }
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

    private static int getTeamWorkValue(final Player[] scratch) {
        int value = 0;
        int minAssist = 0x7FFFFFFF;
        for (int i = 0; i < scratch.length; ++i) {
            value += scratch[i].getPassesPerGame();
            minAssist = Math.min(minAssist, scratch[i].getAssistsPerGame());
        }
        return value * minAssist;
    }

    private static int getMinAssistCount(Player[] players, int startIdx, int endIdx) {
        int minAssist = 0x7FFFFFFF;
        for (int i = startIdx; i <= endIdx; ++i) {
            int thisAssist = players[i].getAssistsPerGame();
            if (thisAssist < minAssist) {
                minAssist = thisAssist;
            }
        }
        return minAssist;
    }
    private static int getMinPassCount(Player[] players, int startIdx, int endIdx) {
        int minPass = 0x7FFFFFFF;
        for (int i = startIdx; i <= endIdx; ++i) {
            int thisPass = players[i].getPassesPerGame();
            if (thisPass < minPass) {
                minPass = thisPass;
            }
        }
        return minPass;
    }

    private static int getPassesSum(Player[] players, int startIdx, int endIdx) {
        int sum = 0;
        for (int i = startIdx; i <= endIdx; ++i) {
            sum += players[i].getPassesPerGame();
        }
        return sum;
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
    */
}

