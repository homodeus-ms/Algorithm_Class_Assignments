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
        // 정렬 한 번 하고 시작. 괜찮을지?
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

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {

        outPlayers[0] = players[0];
        outPlayers[1] = players[1];
        outPlayers[2] = players[2];
        findDreamTeamRecursive(players, outPlayers, scratch, 0, 0, 3);
        long teamWorkPoint = getTeamWorkPoint(outPlayers);
        return teamWorkPoint;
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

        for (int i = 0; i < k; ++i) {
            outPlayers[i] = players[i];
        }

        findDreamTeamRecursive(players, outPlayers, scratch, 0, 0, k);

        long teamWorkPoint = getTeamWorkPoint(outPlayers);
        return teamWorkPoint;
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

        sortByAssistDesc(scratch);

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

        /*long maxTeamWorkPoint = players[0].getPassesPerGame() * players[0].getAssistsPerGame();
        int maxTeamWorkIdx = 0;
        long thisTeamWorkPoint;
        boolean isThisTimeBigger = true;

        for (int i = 0; i < players.length; ++i) {
            thisTeamWorkPoint = getTeamWorkPoint(players, i, i);
            if (thisTeamWorkPoint > maxTeamWorkPoint) {
                maxTeamWorkIdx = i;
                maxTeamWorkPoint = thisTeamWorkPoint;
            }
        }
        scratch[0] = players[maxTeamWorkIdx];
        players[maxTeamWorkIdx] = null;
        int count = 1;

        while (isThisTimeBigger) {
            isThisTimeBigger = false;

            for (int i = 0; i < players.length; ++i) {
                if (players[i] != null) {
                    scratch[count] = players[i];
                    thisTeamWorkPoint = getTeamWorkPoint(scratch, 0, count);

                    if (thisTeamWorkPoint > maxTeamWorkPoint) {
                        maxTeamWorkIdx = i;
                        maxTeamWorkPoint = thisTeamWorkPoint;
                        isThisTimeBigger = true;
                    }
                }
            }
            if (isThisTimeBigger) {
                scratch[count] = players[maxTeamWorkIdx];
                players[maxTeamWorkIdx] = null;
                count++;
            } else {
                //scratch[count] = null;
            }
        }

        return count;*/

    }
    private static void sortByAssistDesc(final Player[] scratch) {
        int thisAssistCount;
        int nextAssistCount;
        for (int i = 0; i < scratch.length - 1; ++i) {
            for (int j = 0; j < scratch.length - 1 - i; ++j) {
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


    private static void quickSortRecursive(final GameStat[] gameStats, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = partition(gameStats, 0, right);
        quickSortRecursive(gameStats, 0, pivot - 1);
        quickSortRecursive(gameStats, pivot + 1, right);
    }
    private static int partition(final GameStat[] gameStats, int left, int right) {

        int rightIdxHash = gameStats[right].getPlayerName().hashCode();
        int rightIdxGameNum = gameStats[right].getGame();
        int currIdxHash;

        for (int i = left; i < right; ++i) {
            currIdxHash = gameStats[i].getPlayerName().hashCode();
            if (currIdxHash == rightIdxHash) {
                if (gameStats[i].getGame() < rightIdxGameNum) {
                    swap(gameStats, i, left);
                    left++;
                }
            } else if (currIdxHash < rightIdxHash) {
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
                                                   final Player[] scratch, int start, int depth, int teamSize) {

        if (depth == teamSize) {
            long outPlayersScore = getTeamWorkValue(outPlayers);
            long thisPlayersScore = getTeamWorkValue(scratch);
            if (thisPlayersScore > outPlayersScore) {
                for (int i = 0; i < scratch.length; ++i) {
                    outPlayers[i] = scratch[i];
                }
            }
            return;
        }

        for (int i = start; i < players.length; ++i) {
            scratch[depth] = players[i];
            findDreamTeamRecursive(players, outPlayers, scratch, i + 1,
                    depth + 1, teamSize);
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
}
