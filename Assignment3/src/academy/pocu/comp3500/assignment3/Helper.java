package academy.pocu.comp3500.assignment3;

public class Helper {
    private static final byte[][] remainCountsToEdge = new byte[8 * 8][8];

    public Helper() {
        for (byte i = 0; i < 64; ++i) {
            byte north = (byte) (i / 8);
            byte south = (byte) (7 - i / 8);
            byte west = (byte) (i & 0b111);
            byte east = (byte) (7 - (i & 0b111));
            byte northWest = (byte) Math.min(north, west);
            byte southEast = (byte) Math.min(south, east);
            byte northEast = (byte) Math.min(north, east);
            byte southWest = (byte) Math.min(south, west);

            byte[] remainsToEdge = {
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
    public static byte[][] getRemainCountsToEdge() {
        return remainCountsToEdge;
    }
}
