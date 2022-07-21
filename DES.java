package des;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DESEncryption_1827150 {

    static int[][] PC1 = {
        {57, 49, 41, 33, 25, 17, 9},
        {1, 58, 50, 42, 34, 26, 18},
        {10, 2, 59, 51, 43, 35, 27},
        {19, 11, 3, 60, 52, 44, 36},
        {63, 55, 47, 39, 31, 23, 15},
        {7, 62, 54, 46, 38, 30, 22},
        {14, 6, 61, 53, 45, 37, 29},
        {21, 13, 5, 28, 20, 12, 4}
    };
    static int[][] PC2 = {
        {14, 17, 11, 24, 1, 5, 3, 28},
        {15, 6, 21, 10, 23, 19, 12, 4},
        {26, 8, 16, 7, 27, 20, 13, 2},
        {41, 52, 31, 37, 47, 55, 30, 40},
        {51, 45, 33, 48, 44, 49, 39, 56},
        {34, 53, 46, 42, 50, 36, 29, 32}
    };
    static int[][] IP = {
        {58, 50, 42, 34, 26, 18, 10, 2},
        {60, 52, 44, 36, 28, 20, 12, 4},
        {62, 54, 46, 38, 30, 22, 14, 6},
        {64, 56, 48, 40, 32, 24, 16, 8},
        {57, 49, 41, 33, 25, 17, 9, 1},
        {59, 51, 43, 35, 27, 19, 11, 3},
        {61, 53, 45, 37, 29, 21, 13, 5},
        {63, 55, 47, 39, 31, 23, 15, 7}
    };
    static int[][] P = {
        {16, 7, 20, 21},
        {29, 12, 28, 17},
        {1, 15, 23, 26},
        {5, 18, 31, 10},
        {2, 8, 24, 14},
        {32, 27, 3, 9},
        {19, 13, 30, 6},
        {22, 11, 4, 25}};

    static int[][] inverseIP = {
        {40, 8, 48, 16, 56, 24, 64, 32},
        {39, 7, 47, 15, 55, 23, 63, 31},
        {38, 6, 46, 14, 54, 22, 62, 30},
        {37, 5, 45, 13, 53, 21, 61, 29},
        {36, 4, 44, 12, 52, 20, 60, 28},
        {35, 3, 43, 11, 51, 19, 59, 27},
        {34, 2, 42, 10, 50, 18, 58, 26},
        {33, 1, 41, 9, 49, 17, 57, 25}};

    static int[][] E = {
        {32, 1, 2, 3, 4, 5},
        {4, 5, 6, 7, 8, 9},
        {8, 9, 10, 11, 12, 13},
        {12, 13, 14, 15, 16, 17},
        {16, 17, 18, 19, 20, 21},
        {20, 21, 22, 23, 24, 25},
        {24, 25, 26, 27, 28, 29},
        {28, 29, 30, 31, 32, 1}};

    static int[][][] sbox = {
        {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},
        {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},
        {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},
        {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},
        {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},
        {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},
        {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},
        {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}
    };

    static int[][] S1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};

    public static void main(String[] args) throws IOException {
        /* Scanner key = new Scanner(System.in);
        System.out.print(" enter the key :");
        String inputKey = key.nextLine();
        Scanner plain = new Scanner(System.in);
        System.out.print(" enter the plain :");
        String inputplain = plain.nextLine();*/
        System.out.print(encryption("0123456789ABCDEF", "133457799BBCDFF1"));
       }

    static String binary(String key) {
        int k = 0;
        String pinarykey = "";
        String bin = "";
        for (int i = 0; i < key.length(); i++) {
            k = Integer.parseInt("" + key.charAt(i), 16);
            bin = Integer.toBinaryString(k);

            while (bin.length() < 4) {
                bin = "0" + bin;
            }
            pinarykey += bin;
        }
        return pinarykey;
    }

    static String fStep(String key) {
        String k = "";
        key = binary(key);
        for (int i = 0; i < PC1.length; i++) {
            for (int x = 0; x < PC1[i].length; x++) {
                for (int j = 0; j < key.length(); j++) {
                    if (PC1[i][x] == j) {
                        k += key.charAt(j - 1);
                    }
                }
            }
        }
        return k;
    }

    static String[] shifting(String key) {
        int[] shiftTable = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        String Lkey = "", Rkey = "";
        int x = 0;
        String[] totKey = new String[16];
        for (int i = 0; i < key.length() / 2; i++) {
            Lkey += key.charAt(i);
        }
        for (int i = key.length() / 2; i < key.length(); i++) {
            Rkey += key.charAt(i);
        }
        for (int i = 0; i < shiftTable.length; i++) {
            while (x < shiftTable[i]) {
                Rkey = Rkey.substring(1, Rkey.length()) + Rkey.charAt(0);
                x++;
            }
            x = 0;
            while (x < shiftTable[i]) {
                Lkey = Lkey.substring(1, Lkey.length()) + Lkey.charAt(0);
                x++;
            }
            x = 0;
            totKey[i] = Lkey + Rkey;

        }

        /* for(int z=0;z<totKey.length;z++){
          System.out.println(totKey[z]);
      }*/
        return totKey;
    }

    static String[] LStep(String key) {
        key = fStep(key);
        String[] fkey = shifting(key);
        String[] finalkey = new String[fkey.length];
        for (int p = 0; p < fkey.length; p++) {
            key = fkey[p];
            String k = "";
            for (int i = 0; i < PC2.length; i++) {
                for (int x = 0; x < PC2[i].length; x++) {
                    for (int j = 0; j < key.length() + 1; j++) {
                        if (PC2[i][x] == j) {
                            k += key.charAt(j - 1);
                        }
                    }
                }
            }
            finalkey[p] = k;
        }
        /*  for (int z = 0; z < finalkey.length; z++) {
            System.out.println(finalkey[z]);
        }*/
        return finalkey;
    }

    static String[] hexKey(String[] key) {
        String[] Hexkey = new String[key.length];
        String temp;
        int hex;

        for (int i = 0; i < key.length; i++) {
            Hexkey[i] = "";
            for (int j = 0; j < key[i].length(); j++) {
                int nextDiv = j + 4;
                temp = key[i].substring(j, nextDiv);
                hex = Integer.parseInt(temp, 2);
                Hexkey[i] += Integer.toHexString(hex);
                j += 3;
            }
        }
        /* for (int i = 0; i < Hexkey.length; i++) {
            System.out.println(Hexkey[i]);
        }*/

        return Hexkey;

    }

    static File keyfile(String key) throws IOException {

        // Scanner scanner = new Scanner(plainTextFile);
        File output = new File("keys.txt");
        PrintWriter pw = new PrintWriter(output);
        String[] keys = LStep(key);
        String[] hexkeys = hexKey(keys);

        for (int i = 0; i < hexkeys.length; i++) {
            pw.println(hexkeys[i]);
        }

        pw.flush();
        pw.close();
        return output;
    }

    static String fpStep(String plain) {
        String k = "";
        plain = binary(plain);
        for (int i = 0; i < IP.length; i++) {
            for (int x = 0; x < IP[i].length; x++) {
                for (int j = 0; j < plain.length(); j++) {
                    if (IP[i][x] == j) {
                        k += plain.charAt(j - 1);
                    }
                }
            }
        }
        return k;
    }

    static String encryption(String plain, String key) {
        String LE = "", RE = "";
        String function = "";
        String totalcipher = "";
        String cipherText="";
        String[] keys = LStep(key);
        String[] L = new String[17];
        String[] R = new String[17];
        plain = fpStep(plain);
        for (int i = 0; i <= plain.length() / 2; i++) {
            LE += plain.charAt(i);
        }
        for (int i = plain.length() / 2; i < plain.length(); i++) {
            RE += plain.charAt(i);
        }
        L[0] = LE;
        R[0] = RE;
        for (int i = 1; i < 17; i++) {
            L[i] = R[i - 1];
            function = F(R[i - 1], keys[i - 1]);

            R[i] = xor(function, L[i - 1]);

        }
        totalcipher = R[16] + L[16];
        totalcipher = finalpermutation(totalcipher);
        for (int j = 0; j < totalcipher.length(); j++) {
                int nextDiv = j + 4;
               String temp = totalcipher.substring(j, nextDiv);
               int hex = Integer.parseInt(temp, 2);
                cipherText += Integer.toHexString(hex);
                j += 3;
        }
        return cipherText;
    }

    static String F(String RE, String key) {
        String xorRE = "";
        String decRE = "";
        String bin = "";
        int counter = 0;
        int row = 0;
        int col = 0;

        RE = expantion(RE);
        xorRE = xor(RE, key);

        // System.out.println(xorRE);
        for (int s = 0; s < sbox.length; s++, counter += 6) {

            String k;
            k = "" + xorRE.charAt(counter) + xorRE.charAt(counter + 5);
            String l = "" + (xorRE.substring(counter + 1, counter + 5));
            row = Integer.parseInt(k, 2);
            col = Integer.parseInt(l, 2);
            bin = Integer.toBinaryString(sbox[s][row][col]);

            while (bin.length() < 4) {
                bin = "0" + bin;
            }
            decRE += bin;

        }
        //     System.out.println(decRE);

        decRE = lpermutation(decRE);
        return decRE;
    }

    static String expantion(String RE) {
        String k = "";
        for (int i = 0; i < E.length; i++) {
            for (int x = 0; x < E[i].length; x++) {
                for (int j = 0; j <= RE.length(); j++) {
                    if (E[i][x] == j) {
                        k += RE.charAt(j - 1);
                    }
                }
            }
        }
        // System.out.print(k);
        return k;
    }

    static String lpermutation(String RE) {
        String k = "";
        for (int i = 0; i < P.length; i++) {
            for (int x = 0; x < P[i].length; x++) {
                for (int j = 0; j <= RE.length(); j++) {
                    if (P[i][x] == j) {
                        k += RE.charAt(j - 1);
                    }
                }
            }
        }
        return k;
    }

    static String xor(String re, String key) {
        String xorRE = "";

        for (int j = 0; j < key.length(); j++) {
            if (re.charAt(j) == key.charAt(j)) {
                xorRE += '0';
            } else {
                xorRE += '1';
            }
        }
        return xorRE;
    }

    static String finalpermutation(String plain) {
        String k = "";
        for (int i = 0; i < inverseIP.length; i++) {
            for (int x = 0; x < inverseIP[i].length; x++) {
                for (int j = 0; j <= plain.length(); j++) {
                    if (inverseIP[i][x] == j) {
                        k += plain.charAt(j - 1);
                    }
                }
            }
        }
        return k;
    }
}