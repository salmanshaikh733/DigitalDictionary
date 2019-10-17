public class Record {

    private Pair newPair;
    private String newData;


    public Record(Pair key, String data) {
        newPair = key;
        newData = data;
    }

    public Pair getKey() {
        return newPair;
    }

    public String getData() {
        return newData;
    }

}
