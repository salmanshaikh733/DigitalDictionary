//import java libraries
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

//declare the class
public class UserInterface {

    //declare the main method
    public static void main(String[] args) {
        //have a try and catch statement to catch all exceptions
        try {

            //get the new file
            FileInputStream newFile = new FileInputStream(args[0]);
            //reader to read the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(newFile));

            //fileLine will store each line read by the buffered reader
            String fileLine;
            //create the dictionary to store the values
            OrderedDictionary dictionary = new OrderedDictionary();

            //read the first line
            fileLine = reader.readLine();
            int counter = 0;
            String name;
            String data;
            String fileType;
            while (fileLine != null) {
                //store first line in name
                name = fileLine;
                //read the second line storing the type
                fileLine = reader.readLine();
                //store in data
                data = fileLine;
                //create the new pair from data
                if (data.endsWith(".mid") || data.endsWith(".wav")) {
                    fileType = "audio";
                } else if (data.endsWith(".jpg") || data.endsWith(".gif")) {
                    fileType = "image";
                } else {
                    fileType = "text";
                }
                //create the pair using what we read
                Pair newPair = new Pair(name, fileType);
                //create the new record using the pair and data type
                Record newRecord = new Record(newPair, data);
                //add to dictionary
                dictionary.put(newRecord);
                //read the next line and loop over
                fileLine = reader.readLine();
                counter++;
                //System.out.println(counter);

            }

            //command loop infinite loop will only stop when command says so
            while (true) {
                //commands start here
                StringReader keyboard = new StringReader();
                String line = keyboard.read("Enter next Command: ");

                Scanner command = new Scanner(line);
                //splits the command
                String[] arguments = command.nextLine().split(" ");

                //if the user enters finish then the program will exit
                if (arguments[0].equalsIgnoreCase("finish")) {
                    System.out.println("Exiting Dictionary Goodbye....");
                    System.exit(0);
                }

                //if the user enters smallest then the dictionary will return the smallest
                if (arguments[0].equalsIgnoreCase("smallest")) {
                    System.out.println(dictionary.smallest().getKey().getWord());
                    System.out.println(dictionary.smallest().getData());
                }
                //if the user enters largest then the dictionary will return the largest
                else if (arguments[0].equalsIgnoreCase("largest")) {
                    System.out.println(dictionary.largest().getKey().getWord());
                    System.out.println(dictionary.largest().getData());

                }
                //if the user enters put then the run put command to insert record into dictionary
                else if (arguments[0].equalsIgnoreCase("put")) {
                    //have a try and catch statement block to catch any exception if they are thrown
                    try {
                        //get the arguments
                        String newWord = arguments[1];
                        String newtype = arguments[2];
                        String newData=arguments[3];
                        for(int z=4; z<arguments.length;z++){
                            newData=newData+" "+arguments[z];
                        }

                        //creat the pair
                        Pair newPair = new Pair(newWord, newtype);
                        //create the record
                        Record newRecord = new Record(newPair, newData);
                        //insert the record
                        dictionary.put(newRecord);

                        //if value already in tree throw and catch exception
                    } catch (DictionaryException e) {
                        System.out.println("Value already in tree");
                    }

                    //if the user enters the get command
                } else if (arguments[0].equalsIgnoreCase("get")) {

                    //first get the largest value in the tree
                    Record largest = dictionary.largest();
                    //array to store values equal to the argument passed in
                    Record[] getVal = new Record[counter];
                    int i = 0;
                    String pred = "";
                    String suc = "";
                    boolean flag=false;
                    while (largest != null) {
                        if (arguments[1].equalsIgnoreCase(largest.getKey().getWord())) {
                            getVal[i] = largest;
                            i++;
                            flag=true;
                        }
                        largest = dictionary.predecessor(largest.getKey());
                    }

                    //if the flag is false then the value is not in the tre
                    if (flag==false) {
                        //find the predecessor and sucessor
                        Pair newPair = new Pair(arguments[1], "gif");
                        if (dictionary.predecessor(newPair) != null) {
                            pred = dictionary.predecessor(newPair).getKey().getWord();

                        }
                        if (dictionary.successor(newPair) != null) {
                            suc = dictionary.successor(newPair).getKey().getWord();
                        }
                        //output predessor and sucessor
                        System.out.println("The word "+arguments[1]+" is not in the ordered dictionary");
                        System.out.println("PRECEDING WORD:" + pred);
                        System.out.println("SUCEEDING WORD: " + suc);
                    }

                    //loop through getVal output each object
                    for (int j = 0; j < i; j++) {
                        PictureViewer image = new PictureViewer();
                        SoundPlayer sPlayer = new SoundPlayer();
                        String displayText = getVal[j].getData();

                        //try to catch the multimedia exception
                        try {
                            //determime which type
                            if (displayText.endsWith(".jpg") || displayText.endsWith(".gif")) {
                                System.out.println(displayText);
                                image.show(displayText);
                            } else if (displayText.endsWith(".wav") || displayText.endsWith(".mid")) {
                                sPlayer.play(displayText);
                            } else if (!displayText.endsWith(".jpg") || !displayText.endsWith(".gif") || !displayText.endsWith(".wav") || !displayText.endsWith(".mid")) {
                                System.out.println(displayText);
                            }

                        } catch (MultimediaException e) {
                            System.out.println("Multimedia Exception");
                        }

                    }
                }

                //list command
                else if (arguments[0].equalsIgnoreCase("list")) {

                    //get the argument store it in prefix
                    String prefix = arguments[1];
                    //get the largest record
                    Record largestRecord = dictionary.largest();
                    //nextrecord will store the previous to it
                    Record nextRecor = dictionary.predecessor(largestRecord.getKey());
                    //while we havent reached the end of the tree
                    while (nextRecor != null) {
                        //if the nextRecor starts with the prefix sent output it
                        if (nextRecor.getKey().getWord().startsWith(prefix)) {
                            System.out.println(nextRecor.getKey().getWord());
                            //get the next recor
                            nextRecor = dictionary.predecessor(nextRecor.getKey());
                        } else {
                            //else get the next recor
                            nextRecor = dictionary.predecessor(nextRecor.getKey());
                        }

                    }


                }
                //delete the specified record from the dictionary
                else if (arguments[0].equalsIgnoreCase("delete")) {
                    //try and catch to catch any exceptions
                    try {
                        //store the arguments
                        String newWord = arguments[1];
                        String newType = arguments[2];
                        //create a pair using the arguments
                        Pair removePair = new  Pair(newWord, newType);
                        //call the remove method sending in the remove pair as arguments
                        dictionary.remove(removePair);
                        //catch the dictionary exception to be thrown
                    } catch (DictionaryException e) {
                        System.out.println("No record in the ordered dictionary has key (" + arguments[1] + "," + arguments[2] + ")");
                    }

                }
                //if user enters a command not in the while loop
                else if (!command.hasNextLine()) {
                    System.out.println("Invalid Command");
                    continue;
                }

            }//end of command

            //catch these exceptions
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (java.io.IOException e) {
            System.out.println("IO Exception");
        } catch (DictionaryException e) {
            System.out.println("Dictionary Exception");
        } catch (NoSuchElementException e) {
            System.out.println("Empty Line!!");
            System.exit(0);
        } catch (NullPointerException e) {
            System.out.println("Trying to access value that does not exist");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Missing command arguments ");
        }
    }
}

