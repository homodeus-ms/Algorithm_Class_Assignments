package academy.pocu.comp3500.assignment3;

public class Helper {
    private static final int[][] remainCountsToEdge = new int[8 * 8][8];

    public Helper() {
        for (int i = 0; i < 64; ++i) {
            int north = i / 8;
            int south = 7 - i / 8;
            int west = i & 0b111;
            int east = 7 - (i & 0b111);
            int northWest = Math.min(north, west);
            int southEast = Math.min(south, east);
            int northEast = Math.min(north, east);
            int southWest = Math.min(south, west);

            int[] remainsToEdge = {
                    north,
                    south,
                    west,
                    east,
                    northWest,
                    southEast,
                    northEast,
                    southWest,
            };

            remainCountsToEdge[i] = remainsToEdge;
        }
    }
    public static int[][] getRemainCountsToEdge() {
        return remainCountsToEdge;
    }
}
