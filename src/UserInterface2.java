import java.io.*;
import java.util.StringTokenizer;

public class UserInterface2 {
    public static void main(String[] args) {
        // Initialize OrderedDictionary to store data from files
        OrderedDictionary dictionary = new OrderedDictionary();
        // We try to open file and return an IO error if we cannot open
        try {
            FileInputStream inputFile = new FileInputStream(args[0]);
            BufferedReader textReader = new BufferedReader(new InputStreamReader(inputFile));
            String wordInput = textReader.readLine().toLowerCase();
            String definition, type;
            while (wordInput != null) {
                try {
                    definition = textReader.readLine();
                    if (definition.endsWith(".gif") || definition.endsWith(".jpg"))
                        type = "image";
                    else if (definition.endsWith(".mid") || definition.endsWith(".wav"))
                        type = "audio";
                    else
                        type = "text";
                    dictionary.put(new Record(new Pair(wordInput,type),definition));
                    wordInput = textReader.readLine();
                    if (wordInput!=null)
                        wordInput = wordInput.toLowerCase();
                }
                catch (Exception e) {
                    System.out.println("Could not insert record, please try again!");
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot open file.");
        }

        StringReader userReader = new StringReader();
        String line = userReader.read("Enter command: ");
        String command = "", wordInput, type, data, prefix;
        Record recordOne, audioRec, imageRec, textRec;
        SoundPlayer player = new SoundPlayer();
        PictureViewer viewer = new PictureViewer();

        while (!command.equals("finish")) {
            StringTokenizer token = new StringTokenizer(line," ");
            command = token.nextToken();
            switch(command) {
                case "get":
                    wordInput = token.nextToken().toLowerCase();
                    textRec = dictionary.get(new Pair(wordInput,"text"));
                    imageRec = dictionary.get(new Pair(wordInput,"image"));
                    audioRec = dictionary.get(new Pair(wordInput,"audio"));
                    if (imageRec != null) {
                        try {
                            viewer.show(imageRec.getData());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                    if (audioRec != null) {
                        try {
                            player.play(audioRec.getData());
                        }catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                    if (textRec != null) {
                        System.out.println(textRec.getData());
                    }

                    if (audioRec == null && imageRec == null && textRec == null) {
                        System.out.println("File is not in dictionary: "+wordInput);
                        String pred, succ;
                        if (dictionary.predecessor(new Pair(wordInput,"audio")) == null) {
                            pred = " ";
                        } else {
                            pred = dictionary.predecessor(new Pair(wordInput,"audio")).getKey().getWord();
                        }
                        if (dictionary.successor(new Pair(wordInput,"audio")) == null) {
                            succ = " ";
                        }
                        else {
                            succ = dictionary.successor(new Pair(wordInput,"audio")).getKey().getWord();
                        }
                        System.out.println("Preceding: "+pred);
                        System.out.println("Following: "+succ);
                    }
                case "delete":
                    wordInput = token.nextToken().toLowerCase();
                    type = token.nextToken();
                    try {
                        dictionary.remove(new Pair(wordInput,type));
                    } catch (Exception e) {
                        System.out.println("No record in the dictionary has that key.");
                    }
                case "put":
                    wordInput = token.nextToken();
                    type = token.nextToken();
                    data = token.nextToken();
                    recordOne = new Record(new Pair(wordInput,type), data);
                    try {
                        dictionary.put(recordOne);
                    } catch (Exception e) {
                        System.out.println("Record with key ("+wordInput+","+type+") is already in dictionary.");
                    }
                case "list":
                    prefix = token.nextToken();
                    recordOne = dictionary.get(new Pair(prefix,"audio"));
                    if (recordOne != null) {
                        System.out.println(recordOne.getKey().getWord());
                    }
                    recordOne = dictionary.successor(new Pair(prefix,"audio"));
                    if (recordOne != null && recordOne.getKey().getWord().startsWith(prefix))
                        System.out.println(recordOne.getKey().getWord());
                    do {
                        recordOne = dictionary.successor(recordOne.getKey());
                        if (recordOne != null && recordOne.getKey().getWord().startsWith(prefix))
                            System.out.println(recordOne.getKey().getWord());
                    } while (recordOne != null);


                case "finish":
                    System.out.println("Ending Application");
                    break;

                case "largest":
                    recordOne = dictionary.largest();
                    System.out.println(recordOne.getKey().getWord()+","+recordOne.getKey().getType()+","+recordOne.getData());

                case "smallest":
                    recordOne = dictionary.smallest();
                    System.out.println(recordOne.getKey().getWord()+","+recordOne.getKey().getType()+","+recordOne.getData());
                default:
                    System.out.println("Incorrect command");
                    line = userReader.read("Enter next command: ");
            }

        }
    }
}