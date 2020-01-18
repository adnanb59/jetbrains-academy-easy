package cards;

import java.io.*;
import java.util.*;

public class FlashCards {
    private Map<String, String> flashcards;
    private Map<String, Integer> attempts;
    private Set<String> hardest;
    private Integer hard_count;

    public FlashCards() {
        this.flashcards = new HashMap<String, String>();
        this.attempts = new HashMap<String, Integer>();
        this.hard_count = 0;
        this.hardest = new HashSet<String>();
    }

    public void addCard(String term, String defn) {
        this.flashcards.put(term, defn);
        this.attempts.put(term, 0);
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
        boolean ret = this.flashcards.containsKey(term);
        this.flashcards.remove(term);
        this.attempts.remove(term);
        this.hardest.remove(term);
        if (hardest.size() == 0) hard_count = 0;
        return ret;
    }

    public String exportCards(String fileName) {
        int numSaved = 0;
        StringBuilder sb = new StringBuilder();
        try {
            FileWriter fw = new FileWriter(fileName);
            for (Map.Entry<String, String> e : flashcards.entrySet()) {
                fw.write(e.getKey() + "\n" + e.getValue() + "\n" + this.attempts.get(e.getKey()) + "\n");
                numSaved++;
            }
            fw.close();
        } catch(IOException e) {
            System.err.println(e.getMessage());
            sb.append(e.getMessage() + "\n");
        } finally {
            System.out.println(numSaved + " cards have been saved.\n");
            sb.append(numSaved + " cards have been saved.\n");
        }
        return sb.toString();
    }

    public String importCards(String fileName) {
        File file = new File(fileName);
        int numLoaded = 0;
        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String term = in.nextLine();
                String defn = in.nextLine();
                Integer count = in.nextInt();
                in.nextLine();
                flashcards.put(term, defn);
                attempts.put(term, count);
                if (count >= this.hard_count) {
                    this.hardest.add(term);
                    if (count > this.hard_count) this.hardest.retainAll(Set.of(term));
                    this.hard_count = count;
                }
                numLoaded++;
            }
            System.out.println(numLoaded + " cards have been loaded.\n");
            sb.append(numLoaded + " cards have been loaded.\n");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.\n");
            sb.append("File not found.\n");
        }
        return sb.toString();
    }

    public Set<String> getHardest() {
        return this.hardest;
    }

    public Integer getHardCount() {
        return this.hard_count;
    }

    public void resetCards() {
        for (String k :  flashcards.keySet()) {
            attempts.put(k, 0);
        }
        hardest.clear();
        hard_count = 0;
    }

    public List<String> getCards(int length) {
        List<String> ret = new ArrayList<String>(flashcards.keySet());
        return (length <= flashcards.size()) ? ret.subList(0, length) : ret;
    }

    public void updateTermStats(String term, boolean isCorrect) {
        if (isCorrect) {
            hardest.remove(term);
            attempts.put(term, 0);
        } else {
            //hardest.add(term);
            int t = attempts.get(term) + 1;
            attempts.put(term, t);
            if (t >= hard_count) {
                this.hardest.add(term);
                if (t > hard_count) this.hardest.retainAll(Set.of(term));
                hard_count = t;
            }
        }
    }
}