import java.lang.String;

class Node {
    Node[]  children;
    String definition;
    boolean wordExists;

    Node() {
        children = new Node[26]; //Only care about lowercase letters
        wordExists = false; //Check if we reached the end of a word
        definition = null;
    }
}


/**
 * Dictionary class that stores words and associates them with their definitions
 */
public class Dictionary {
    Node dict;
    /**
     * Constructor to initialize the Dictionary
     */
    public Dictionary() {
        dict = new Node();
    }

    /**
     * A method to add a new word to the dictionary
     * If the word is already in the dictionary, this method will change its
     * definition
     *
     * @param word       The word we want to add to our dictionary
     * @param definition The definition we want to associate with the word
     */
    public void add(String word, String definition) {
        Node curr = dict;

        for (char ch : word.toCharArray()) {
            int pos = ch - 'a'; //Maps all character from 0-25

            if (curr.children[pos] == null) {
                curr.children[pos] = new Node(); //Position tells us character
            }

            curr = curr.children[pos];
        }

        curr.definition = definition;
        curr.wordExists = true;
    }

    // Recursive method for remove
    private boolean remove_recursive(String word, Node curr, int count) {
        //Count represents next character to look at in word
        if (count == word.length()) { //Checks last character to remove word
            curr.wordExists = false;
            curr.definition = null;
            return curr.children.length == 0; //Checks if node can be deleted
        }

        char ch = word.charAt(count);
        int next = ch - 'a';
        Node next_curr = curr.children[next];
        count = count + 1;

        //Recursively removes any other characters not used by other words
        if (remove_recursive(word, next_curr, count)) {
            curr.children[next] = null;
            return (curr.children.length == 0) && (!curr.wordExists);
        }

        return false;
    }

    /**
     * A method to remove a word from the dictionary
     *
     * @param word The word we want to remove from our dictionary
     */
    public void remove(String word) {
        //From class ONLY REMOVE UP UNTIL FIRST NON-TRIVIAL NODE
        remove_recursive(word, dict, 0);
    }

    /**
     * A method to get the definition associated with a word from the dictionary
     * Returns null if the word is not in the dictionary
     *
     * @param word The word we want to get the definition for
     * @return The definition of the word, or null if not found
     */
    public String getDefinition(String word) {
        Node curr = dict;

        for (char ch : word.toCharArray()) {
            int pos = ch - 'a';

            if (curr.children[pos] == null) {
                return null;
            }

            curr = curr.children[pos];
        }

        if (curr.wordExists) {
            return curr.definition;
        }

        return null;
    }

    /**
     * A method to get a string representation of the sequence of nodes which would
     * store the word
     * in a compressed trie consisting of all words in the dictionary
     * Returns null if the word is not in the dictionary
     *
     * @param word The word we want the sequence for
     * @return The sequence representation, or null if word not found
     */
    public String getSequence(String word) {
        Node curr = dict;
        StringBuilder sb = new StringBuilder();

        for (char ch : word.toCharArray()) {
            int pos = ch - 'a';

            if (curr == null) {
                return null;
            } else if (curr.children[pos] == null) {
                return null;
            }

            if (sb.length() > 0) {
                if (curr.wordExists) {
                    sb.append("-");
                }
            }

            sb.append(ch);
            curr = curr.children[pos];
        }

        if (curr.wordExists) {
            return sb.toString();
        } else {
            return null;
        }
    }

    private int recursive_count(Node curr) {
        if (curr == null) {
            return 0;
        }

        int count = 0;

        if (curr.wordExists) {
            count = count + 1;
        }

        for (Node next : curr.children) {
            count += recursive_count(next);
        }
        return count;
    }

    /**
     * Gives the number of words in the dictionary with the given prefix
     *
     * @param prefix The prefix we want to count words for
     * @return The number of words that start with the prefix
     */
    public int countPrefix(String prefix) {
        Node curr = dict;

        for (char ch : prefix.toCharArray()) {
            int pos = ch - 'a';

            if (curr.children[pos] == null) {
                return 0;
            }
            curr = curr.children[pos];
        }

        return recursive_count(curr);
    }

    private Node recursive_compress(Node curr) {
        if (curr == null) {
            return null;
        }

        int count = 0; //Counts # of children
        Node next = null;

        for (int i = 0; i < curr.children.length; i++) {
            curr.children[i] = recursive_compress(curr.children[i]);

            if (curr.children[i] != null) {
                count = count + 1;
                next = curr.children[i];
            }
        }

        if (count == 1) {
            if (!curr.wordExists) {
                return next;
            }
        }

        return curr;
    }

    /**
     * Compresses the trie by combining nodes with single children
     * This operation should not change the behavior of any other methods
     */
    public void compress() {
        dict = recursive_compress(dict);
    }
}