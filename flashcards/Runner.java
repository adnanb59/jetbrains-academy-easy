import java.util.*;

public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        FlashCards fc = new FlashCards();
        boolean exit = false;
        String prompt;
        while (!exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            switch(in.nextLine()) {
                case "add":
                    System.out.println("The card:");
                    prompt = in.nextLine();
                    if (!fc.doesCardExist(prompt)) {
                        System.out.println("The definition of the card:");
                        String defn = in.nextLine();
                        if (!fc.doesDefinitionExist(defn)) {
                            fc.addCard(prompt, defn);
                            System.out.println("The pair (\"" + prompt + "\":\"" + defn + "\") has been added.\n");
                        } else {
                            System.out.println("The definition \"" + defn + "\" already exists.\n");
                        }
                    } else {
                        System.out.println("The card \"" + prompt + "\" already exists.\n");
                    }
                    break;
                case "remove":
                    System.out.println("The card:");
                    prompt = in.nextLine();
                    System.out.println(fc.removeCard(prompt) ?
                        "The card has been removed.\n" : "Can't remove \"" + prompt + "\": there is no such card.\n");
                    break;
                case "import":
                    System.out.println("File name:");
                    prompt = in.nextLine();
                    fc.importCards(prompt);
                    break;
                case "export":
                    System.out.println("File name:");
                    prompt = in.nextLine();
                    fc.exportCards(prompt);
                    break;
                case "ask":
                    System.out.println("How many times to ask?");
                    prompt = in.next();
                    in.nextLine();
                    try {
                        Integer n = Integer.parseInt(prompt);
                        if (n <= 0) System.out.println("Enter a number above one.");
                        else {
                            List<String> cards = fc.getCards(n);
                            if (cards.size() > 0) {
                                for (int i = 0; i < n; i++) {
                                    System.out.println("Print the definition of \"" + cards.get(i % cards.size()) + "\":");
                                    prompt = in.nextLine();
                                    if (fc.isDefinitionCorrect(cards.get(i % cards.size()), prompt)) {
                                        System.out.println("Correct answer");
                                    } else {
                                        System.out.print("Wrong answer. The correct one is \"" + fc.getDefinition(cards.get(i % cards.size())) + "\"");
                                        prompt = fc.getTermFromDefinition(prompt);
                                        if (prompt != null) {
                                            System.out.print(", you've just written the definition of \"" + prompt + "\"");
                                        }
                                        System.out.println(".");
                                    }
                                }
                            } else {
                                System.out.println("No cards.");
                            }
                            System.out.println("\n");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Need to enter a valid number\n");
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Incorrect input. Try again.\n");
                    break;
            }
        }
        System.out.println("Bye bye!");
    }
}