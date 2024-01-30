package academy.pocu.comp3500.lab5;

public class ByteArrayWrapper {
    private byte[] bytes;

    public ByteArrayWrapper(byte[] src) {
        bytes = src;
    }
    public void setWrapper(byte[] src) {
        bytes = src;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ByteArrayWrapper) ||
                this.hashCode() != (obj.hashCode())) {
            return false;
        }
        byte[] otherBytes = ((ByteArrayWrapper) obj).bytes;

        for (int i = 0; i < otherBytes.length; ++i) {
            if (bytes[i] != otherBytes[i]) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        return bytes.length;
    }
}
