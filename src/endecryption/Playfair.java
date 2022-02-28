package endecryption;

import java.util.Locale;

public class Playfair extends Crypt{

    private String[] cipherTable;
    private int sideLength = 5;
    private String key = "";
    {
        generateCipherTable();
        keyAlphabet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    }

    public String encrypt(){
        if (isBadKey || isBadText){
            return "Cannot perform encryption";
        }

        var encString = "";
        for (int i = 0; i < plainText.length(); i+=2){
            int[] firstIndexes = findIndexes(plainText.charAt(i));
            int[] secondIndexes = findIndexes(plainText.charAt(i + 1));

            if (firstIndexes[0] == secondIndexes[0]) {
                encString+= cipherTable[firstIndexes[0]].charAt((firstIndexes[1] + 1) % sideLength);
                encString+= cipherTable[secondIndexes[0]].charAt((secondIndexes[1] + 1) % sideLength);
            }
            else if (firstIndexes[1] == secondIndexes[1]){
                encString+= cipherTable[(firstIndexes[0] + 1) % sideLength].charAt(firstIndexes[1]);
                encString+= cipherTable[(secondIndexes[0] + 1) % sideLength].charAt(secondIndexes[1]);
            }
            else{
                int shiftFirstY = firstIndexes[1] - secondIndexes[1];
                int shiftSecondY = -shiftFirstY;

                encString += cipherTable[firstIndexes[0]].charAt(firstIndexes[1] - shiftFirstY);
                encString += cipherTable[secondIndexes[0]].charAt(secondIndexes[1] - shiftSecondY);
            }
        }

        this.cipherText = encString;
        return encString;
    }

    public String decrypt(){
        if (isBadKey || isBadText) {
            return "Cannot perform decryption";
        }

        var decString = "";
        for (int i = 0; i < cipherText.length(); i+=2){
            int[] firstIndexes = findIndexes(cipherText.charAt(i));
            int[] secondIndexes = findIndexes(cipherText.charAt(i + 1));

            if (firstIndexes[0] == secondIndexes[0]) {
                decString+= cipherTable[firstIndexes[0]].charAt((firstIndexes[1] - 1 + sideLength) % sideLength);
                decString+= cipherTable[secondIndexes[0]].charAt((secondIndexes[1] - 1 + sideLength) % sideLength);
            }
            else if (firstIndexes[1] == secondIndexes[1]){
                decString+= cipherTable[(firstIndexes[0] - 1 + sideLength) % sideLength].charAt(firstIndexes[1]);
                decString+= cipherTable[(secondIndexes[0] - 1 + sideLength) % sideLength].charAt(secondIndexes[1]);
            }
            else{
                int shiftFirstY = firstIndexes[1] - secondIndexes[1];
                int shiftSecondY = -shiftFirstY;

                decString += cipherTable[firstIndexes[0]].charAt(firstIndexes[1] - shiftFirstY);
                decString += cipherTable[secondIndexes[0]].charAt(secondIndexes[1] - shiftSecondY);
            }
        }

        this.plainText = decString;
        return decString;
    }

    public void setKey(String key){
        this.key = key;
        if (isEncrypting) {
            plainText = prepareText();
        }
        else {
            cipherText = prepareText();
        }
    }

    private String prepareText(){
        var text = (isEncrypting)? plainText : cipherText;
        var alphabet = (isEncrypting)? plainAlphabet : cipherAlphabet;
        text = text.toLowerCase(Locale.ROOT);
        text = text.replaceAll("j", "i");
        for (int i = 0; i < alphabet.length() / 2; ++i){
            var tmpString = String.format("%c%c", alphabet.charAt(i * 2), alphabet.charAt(i * 2));
            int position = -1;
            while ((position = text.indexOf(tmpString)) != -1){
                if (text.charAt(position) != 'x') {
                    text = text.substring(0, position + 1) + "x" + text.substring(position + 1);
                }
                else {
                    text = text.substring(0, position + 1) + "q" + text.substring(position + 1);
                }

            }
        }
        if (text.length() % 2 != 0) {
            text+= (text.charAt(text.length() - 1) == 'x')? "q" : "x";
        }

        return text;
    }

    private void generateCipherTable(){
        cipherTable = new String[] {"crypt", "ogahb", "defik", "lmnqs", "uvwxz"};
    }

    private int[] findIndexes(char ch){
        for (int i = 0; i < cipherTable.length; ++i){
            if (cipherTable[i].contains(String.valueOf(ch))) {
                return new int[] { i, cipherTable[i].indexOf(ch) };
            }
        }

        return new int[] { -1, -1 };
    }
}
