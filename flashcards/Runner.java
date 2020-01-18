import java.io.*;
import java.util.*;
import cards.*;

public class Runner {
    static final String MENU_PROMPT = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        FlashCards fc = new FlashCards();
        List<String> log = new ArrayList<>();
        StringBuilder action = new StringBuilder();
        boolean exit = false;
        String prompt, importFile = null, exportFile = null;
        int flag = 0;

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0 && (args[i].equals("-export") || args[i].equals("-import"))) {
                int temp = args[i].equals("-export") ? 1 : -1;
                flag = (temp == flag) ? 0 : temp;
            }
            else if (flag == 1) exportFile = args[i];
            else if (flag == -1) importFile = args[i];
        }

        if (importFile != null) action.append(fc.importCards(importFile));

        while (!exit) {
            System.out.println(MENU_PROMPT);
            action.append(MENU_PROMPT + "\n");
            switch(in.nextLine()) {
                case "add":
                    System.out.println("The card:");
                    prompt = in.nextLine();
                    action.append("add\nThe card:\n" + prompt + "\n");
                    if (!fc.doesCardExist(prompt)) {
                        System.out.println("The definition of the card:");
                        String defn = in.nextLine();
                        action.append("The definition of the card:\n" + defn + "\n");
                        if (!fc.doesDefinitionExist(defn)) {
                            fc.addCard(prompt, defn);
                            System.out.println("The pair (\"" + prompt + "\":\"" + defn + "\") has been added.\n");
                            action.append("The pair (\"" + prompt + "\":\"" + defn + "\") has been added.\n\n");
                        } else {
                            System.out.println("The definition \"" + defn + "\" already exists.\n");
                            action.append("The definition \"" + defn + "\" already exists.\n\n");
                        }
                    } else {
                        System.out.println("The card \"" + prompt + "\" already exists.\n");
                        action.append("The card \"" + prompt + "\" already exists.\n\n");
                    }
                    break;
                case "remove":
                    System.out.println("The card:");
                    prompt = in.nextLine();
                    String result = fc.removeCard(prompt) ?
                            "The card has been removed.\n" : "Can't remove \"" + prompt + "\": there is no such card.\n";
                    System.out.println(result);
                    action.append("remove\nThe card:\n" + prompt + "\n" + result + "\n");
                    break;
                case "import":
                    System.out.println("File name:");
                    prompt = in.nextLine();
                    action.append("import\nFile name:\n" + prompt + "\n" + fc.importCards(prompt));
                    break;
                case "export":
                    System.out.println("File name:");
                    prompt = in.nextLine();
                    action.append("export\nFile name:\n" + prompt + "\n" + fc.exportCards(prompt));
                    break;
                case "ask":
                    System.out.println("How many times to ask?");
                    prompt = in.next();
                    in.nextLine();
                    action.append("ask\nHow many times to ask?\n" + prompt + "\n");
                    try {
                        Integer n = Integer.parseInt(prompt);
                        if (n <= 0) {
                            System.out.println("Enter a number above one.");
                            action.append("Enter a number above one.\n");
                        } else {
                            List<String> cards = fc.getCards(n);
                            if (cards.size() > 0) {
                                for (int i = 0; i < n; i++) {
                                    System.out.println("Print the definition of \"" + cards.get(i % cards.size()) + "\":");
                                    prompt = in.nextLine();
                                    action.append("Print the definition of \"" + cards.get(i % cards.size()) + "\":\n" + prompt + "\n");
                                    if (fc.isDefinitionCorrect(cards.get(i % cards.size()), prompt)) {
                                        fc.updateTermStats(cards.get(i % cards.size()), true);
                                        System.out.println("Correct answer\n");
                                        action.append("Correct answer\n\n");
                                    } else {
                                        fc.updateTermStats(cards.get(i % cards.size()), false);
                                        System.out.print("Wrong answer. (The correct one is \"" + fc.getDefinition(cards.get(i % cards.size())) + "\"");
                                        action.append("Wrong answer. (The correct one is \"" + fc.getDefinition(cards.get(i % cards.size())) + "\"");
                                        prompt = fc.getTermFromDefinition(prompt);
                                        if (prompt != null) {
                                            System.out.print(", you've just written the definition of \"" + prompt + "\"");
                                            action.append(", you've just written the definition of \"" + prompt + "\"");
                                        }
                                        System.out.println(").\n");
                                        action.append(").\n");
                                    }
                                }
                            } else {
                                System.out.println("No cards.\n");
                                action.append("No cards.\n\n");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Need to enter a valid number\n");
                        action.append("Need to enter a valid number\n\n");
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                case "log":
                    System.out.println("File name:");
                    prompt = in.nextLine();
                    action.append("log\nFile name:\n" + prompt + "\n");
                    try {
                        FileWriter fw = new FileWriter(prompt);
                        int tmp = 0;
                        for (String l : log) {
                            fw.write(tmp == log.size()-1 ? l.trim() : l);
                            tmp++;
                        }
                        System.out.println("The log has been saved.\n");
                        action.append("The log has been saved.\n\n");
                        fw.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        action.append(e.getMessage() + "\n");
                    }
                    break;
                case "hardest card":
                    Set<String> hardCards = fc.getHardest();
                    if (hardCards.size() == 0) {
                        System.out.println("There are no cards with errors.\n");
                        action.append("hardest card\nThere are no cards with errors.\n");
                    } else {
                        System.out.print("The hardest card" + (hardCards.size() > 1 ? "s are " : " is "));
                        action.append("The hardest card" + (hardCards.size() > 1 ? "s are " : " is "));
                        int temp = 0;
                        for (String card : hardCards) {
                            System.out.print("\"" + card + "\"");
                            action.append("\"" + card + "\"");
                            if (temp < hardCards.size() - 1) {
                                System.out.print(", ");
                                action.append(", ");
                            }
                            temp++;
                        }
                        System.out.println(". You have " + fc.getHardCount() + " error" + (hardCards.size() > 1 ? "s" : "") + " answering it.\n");
                        action.append(". You have " + fc.getHardCount() + " error" + (hardCards.size() > 1 ? "s" : "") + " answering it.\n\n");
                    }
                    break;
                case "reset stats":
                    fc.resetCards();
                    System.out.println("Card statistics has been reset.\n");
                    action.append("reset stats\nCard statistics has been reset.\n\n");
                    break;
                default:
                    System.out.println("Incorrect input. Try again.\n");
                    action.append("Incorrect input. Try again.\n\n");
                    break;
            }
            log.add(action.toString());
            action.setLength(0);
        }
        System.out.print("Bye bye!");
        if (exportFile != null) {
            System.out.println();
            fc.exportCards(exportFile);
        }
    }
}