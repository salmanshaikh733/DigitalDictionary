import java.lang.*;

public class Pair {

    //declare instance variables
    private String newWord;
    private String newType;

    public Pair(String word, String type) {
        newWord = word.toLowerCase();
        newType = type;
    }

    public String getWord() {
        return newWord;
    }

    public String getType() {
        return newType;
    }

    public int compareTo(Pair k) {
        int compareValue = newWord.compareTo(k.getWord());
        int returnVal = 0;
        //if the word value is the same check the type value
        if (compareValue == 0) {
            compareValue = newType.compareTo(k.getType());
        }
        //if equal to
        if (compareValue == 0) {
            returnVal = 0;
        }
        //less than
        else if (compareValue < 0) {
            returnVal = 1;

        }
        //greather than
        else if (compareValue > 0) {
            returnVal = -1;
        }
        return returnVal;

    }
}
