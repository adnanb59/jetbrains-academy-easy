import java.io.*;
import java.util.*;

public class FlashCards {
    private Map<String, String> flashcards;

    public FlashCards() {
        this.flashcards = new HashMap<String, String>();
    }

    public void addCard(String term, String defn) {
        this.flashcards.put(term, defn);
    }

    public boolean isDefinitionCorrect(String term, String defn) {
        return defn.equalsIgnoreCase(this.flashcards.get(term));
    }

    public boolean doesCardExist(String term) {
        return this.flashcards.containsKey(term);
    }

    public String getTermFromDefinition(String defn) {
        Optional<String> check = this.flashcards.keySet().stream().filter(e -> defn.equals(flashcards.get(e))).findFirst();
        return check.isPresent() ? check.get() : null;
    }

    public boolean doesDefinitionExist(String defn) {
        return this.flashcards.containsValue(defn);
    }

    public String getDefinition(String term) {
        return this.flashcards.getOrDefault(term, null);
    }

    public boolean removeCard(String term) {
        return this.flashcards.remove(term) != null;
    }

    public void exportCards(String fileName) {
        int numSaved = 0;
        try {
            FileWriter fw = new FileWriter("assets\\" + fileName);
            for (Map.Entry<String, String> e : flashcards.entrySet()) {
                fw.write(e.getKey() + "\n" + e.getValue() + "\n");
                numSaved++;
            }
            fw.close();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println(numSaved + " cards have been saved.\n");
        }
    }

    public void importCards(String fileName) {
        File file = new File("assets\\" + fileName);
        int numLoaded = 0;
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String term = in.nextLine();
                String defn = in.nextLine();
                numLoaded++;
                flashcards.put(term, defn);
            }
            System.out.println(numLoaded + " cards have been loaded.\n");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public List<String> getCards(int length) {
        List<String> ret = new ArrayList<String>(flashcards.keySet());
        return (length <= flashcards.size()) ? ret.subList(0, length) : ret;
    }
}