package academy.pocu.comp3500.assignment3;

public class Positions {

    public static int[] pointsForWhiteP = {
            -3, -3, -3, -3, -3, -3, -3, -3,
            -2, -2, -2, -2, -2, -2, -2, -2,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 2, 2, 2, 2, 1, 1,
            2, 2, 3, 3, 3, 3, 2, 2,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
    };
    public static int[] pointsForBlackP = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            2, 2, 3, 3, 3, 3, 2, 2,
            1, 1, 2, 2, 2, 2, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            -2, -2, -2, -2, -2, -2, -2, -2,
            -3, -3, -3, -3, -3, -3, -3, -3
    };
    public static int[] pointsForNight = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1, 1, 0,
            0, 2, 3, 3, 3, 3, 2, 0,
            0, 2, 3, 3, 3, 3, 2, 0,
            0, 2, 3, 3, 3, 3, 2, 0,
            0, 2, 3, 3, 3, 3, 2, 0,
            0, 1, 1, 1, 1, 1, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
    };
    public static int[] pointsForBandQ = {
            -2, 0, 0, 0, 0, 0, 0, -2,
            0, 1, 1, 1, 1, 1, 1, 0,
            1, 2, 3, 4, 4, 4, 2, 1,
            1, 2, 3, 5, 5, 4, 2, 1,
            1, 2, 3, 5, 5, 4, 2, 1,
            1, 2, 3, 4, 4, 4, 2, 1,
            0, 1, 1, 1, 1, 1, 1, 0,
            -2, 0, 0, 0, 0, 0, 0, -2,
    };
    public static int[] pointsForR = {
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 2, 3, 3, 3, 3, 2, 1,
            1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
    };



    public static int getPositionPoints(char piece, int to) {
        if (piece == 'k' || piece == 'K') {
            return 0;
        } else if (piece != 'p' && piece != 'P') {
            piece |= 0x20;
        }

        switch (piece) {
            case 'p':
                return pointsForWhiteP[to];
            case 'P':
                return pointsForBlackP[to];
            case 'n':
                return pointsForNight[to];
            case 'b':
                // intentional fall through
            case 'q':
                return pointsForBandQ[to];
            case 'r':
                return pointsForR[to];

            default:

                assert (false);
                return 0;
        }
    }
}
