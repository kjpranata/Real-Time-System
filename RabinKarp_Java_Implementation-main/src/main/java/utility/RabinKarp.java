package utility;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    private static int d = 256; // number of possible characters
    public static boolean run(String text, String pattern, int q) {
        List<Integer> ans = new ArrayList<>();
        if (text == null || pattern == null || text.length() < pattern.length()) {
            return false;
        }

        int M = pattern.length();   // Pattern length
        int N = text.length();  // text length
        int p = 0;  // hash value for pattern
        int t = 0;  // hash value for text
        int h = 1;

        // Calculate h = pow(d, M-1) % q
        for (int i = 0; i < M - 1; i++) {
            h = (h * d) % q;
        }

        // Calculate hash value for the first M characters in text,
        //also the hash value for pattern string
        for (int i = 0; i < M; i ++) {
            p = (p * d + pattern.charAt(i)) % q;
            t = (t * d + text.charAt(i)) % q;
        }
        // Now start comparing
        for (int i = 0; i <= N - M; i++) {
            // check if it is possible to match

            if (p == t) {
                boolean match = true;
                for (int j = 0; j < M; j++) {
                    if (pattern.charAt(j) != text.charAt(i + j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    ans.add(i);
                }
            }

            // update hash value for text
            if (i < N - M) {
                t = ((t - text.charAt(i) * h) * d + text.charAt(i + M)) % q;
                t = t < 0 ? t + q : t; // avoid negative case
            }
        }
        
        if(ans.size() == 0){
            return false;
        }else{
            return true;
        }
    }
}
