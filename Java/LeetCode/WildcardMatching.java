package Java.LeetCode;

/**
 * Author: Nitin Gupta(nitin.gupta@walmart.com)
 * Date: 2019-06-23
 * Description:
 * https://www.geeksforgeeks.org/wildcard-pattern-matching/
 * https://www.geeksforgeeks.org/dynamic-programming-wildcard-pattern-matching-linear-time-constant-space/
 */
public class WildcardMatching {


    public static void main(String args[]) {
        System.out.println(isMatch("aa", "*"));
        System.out.println(isMatch("cb", "?a"));
        System.out.println(isMatch("adceb", "*a*b"));
        System.out.println(isMatch("acdcb", "a*c?b"));
    }

    public static boolean isMatch(String s, String p) {
        return isMatchLiner(s, p);
    }

    /**
     * https://www.geeksforgeeks.org/wildcard-pattern-matching/
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isMatchPolynomial(String str, String pattern) {

        if ((null == str && null == pattern) || (str.isEmpty() && pattern.isEmpty()))
            return true;

        if (pattern.length() == 0)
            return str.length() == 0;

        int m = pattern.length();
        int n = str.length();
        // empty pattern can only match with
        // empty string
        if (m == 0)
            return (n == 0);

        // lookup table for storing results of
        // subproblems
        boolean[][] lookup = new boolean[n + 1][m + 1];


        // empty pattern can match with empty string
        lookup[0][0] = true;

        // Only '*' can match with empty string
        for (int j = 1; j <= m; j++)
            if (pattern.charAt(j - 1) == '*')
                lookup[0][j] = lookup[0][j - 1];

        // fill the table in bottom-up fashion
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Two cases if we see a '*'
                // a) We ignore '*'' character and move
                //    to next  character in the pattern,
                //     i.e., '*' indicates an empty sequence.
                // b) '*' character matches with ith
                //     character in input
                if (pattern.charAt(j - 1) == '*')
                    lookup[i][j] = lookup[i][j - 1] ||
                            lookup[i - 1][j];

                    // Current characters are considered as
                    // matching in two cases
                    // (a) current character of pattern is '?'
                    // (b) characters actually match
                else if (pattern.charAt(j - 1) == '?' ||
                        str.charAt(i - 1) == pattern.charAt(j - 1))
                    lookup[i][j] = lookup[i - 1][j - 1];

                    // If characters don't match
                else lookup[i][j] = false;
            }
        }

        return lookup[n][m];
    }

    /**
     * https://www.geeksforgeeks.org/dynamic-programming-wildcard-pattern-matching-linear-time-constant-space/
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatchLiner(String s, String p) {

        if ((null == s && null == p) || (s.isEmpty() && p.isEmpty()))
            return true;

        if (p.length() == 0)
            return s.length() == 0;

        char str[] = s.toCharArray();
        char pat[] = p.toCharArray();

        int txtL = s.length();
        int patL = p.length();

        int textIndex = 0, patIndex = 0;

        int textPreviousStarIndex = -1, patPreviousStarIndex = -1;  // * index


        while (textIndex < txtL) {
            if (patIndex < patL && pat[patIndex] == '*') {
                textPreviousStarIndex = textIndex;
                patPreviousStarIndex = patIndex;
                patIndex++;
            } else if (patIndex < patL && ((str[textIndex] == pat[patIndex]) || (pat[patIndex] == '?'))) {
                textIndex++;
                patIndex++;
            } else if (patPreviousStarIndex != -1) {
                // means current char is not * or ? but a char which is not matching,
                // then we have to back track to previous * of pattern and text and
                // assume the text (at previous * index) is now matched to this, and move forward in text from that index
                textIndex = textPreviousStarIndex + 1;
                patIndex = patPreviousStarIndex + 1;
                textPreviousStarIndex++;

            } else
                return false;
        }

        //if pattern is bigger then text, then only permissible char is *
        while (patIndex < patL && pat[patIndex] == '*') {
            patIndex++;
        }

        return patIndex == patL;

    }
}
