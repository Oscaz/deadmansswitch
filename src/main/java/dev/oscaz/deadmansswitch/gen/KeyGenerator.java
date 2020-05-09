package dev.oscaz.deadmansswitch.gen;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KeyGenerator {

    public static String generateDeadKey() {
        return UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
    }

    private static final Set<Character> characters = Sets.newHashSet(
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f', '-'
    );

    public static boolean authenticateDeadKey(String s) {
        if (s.length() != 73) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!characters.contains(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
