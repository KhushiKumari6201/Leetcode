import java.util.*;

class Solution {
    private String s;
    private int pos;

    public List<String> braceExpansionII(String expression) {
        this.s = expression;
        this.pos = 0;
        TreeSet<String> result = parseExpr();
        return new ArrayList<>(result);
    }

    // Parses a concatenation of terms (letters or groups) until ',', '}', or end of string
    private TreeSet<String> parseExpr() {
        List<TreeSet<String>> terms = new ArrayList<>();
        while (pos < s.length() && s.charAt(pos) != ',' && s.charAt(pos) != '}') {
            terms.add(parseTerm());
        }
        return concatAll(terms);
    }

    // Parses a single term: either a run of lowercase letters, or a {...} group
    private TreeSet<String> parseTerm() {
        if (s.charAt(pos) == '{') {
            return parseGroup();
        } else {
            // consume consecutive lowercase letters as one literal word
            int start = pos;
            while (pos < s.length() && Character.isLowerCase(s.charAt(pos))) {
                pos++;
            }
            String word = s.substring(start, pos);
            TreeSet<String> set = new TreeSet<>();
            set.add(word);
            return set;
        }
    }

    // Parses "{ expr (, expr)* }" and unions all the expr results
    private TreeSet<String> parseGroup() {
        // consume '{'
        pos++; // skip '{'
        TreeSet<String> union = new TreeSet<>();
        union.addAll(parseExpr());
        while (pos < s.length() && s.charAt(pos) == ',') {
            pos++; // skip ','
            union.addAll(parseExpr());
        }
        // consume '}'
        if (pos < s.length() && s.charAt(pos) == '}') {
            pos++;
        }
        return union;
    }

    // Cartesian-product concatenation of a list of sets, in order
    private TreeSet<String> concatAll(List<TreeSet<String>> terms) {
        TreeSet<String> current = new TreeSet<>();
        current.add("");
        for (TreeSet<String> term : terms) {
            TreeSet<String> next = new TreeSet<>();
            for (String prefix : current) {
                for (String suffix : term) {
                    next.add(prefix + suffix);
                }
            }
            current = next;
        }
        return current;
    }
}