package endecryption;

public class Vizhener extends Crypt{

    private String[] cipherTable;
    private String key = "";
    {
        cipherTable = new String[33];
        generateCipherTable();
        plainAlphabet = "аАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯ";
        cipherAlphabet = "аАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯ";
        keyAlphabet = "аАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯ";
    }

//---------------------------------------------------public methods-----------------------------------------------------------------
    public String encrypt(){
        if (isBadKey || isBadText) {
            return "Cannot perform encryption";
        }
        String encString = "";
        for (int i = 0; i < plainText.length(); ++i){
            encString+= cipherTable[keyAlphabet.indexOf(Character.toLowerCase(key.charAt(i))) / 2].charAt(plainAlphabet.indexOf(Character.toLowerCase(plainText.charAt(i))) / 2);
        }

        this.cipherText = encString;
        return encString;
    }

    public String decrypt(){
        if (isBadKey || isBadText) {
            return "Cannot perform encryption";
        }

        String decString = "";
        for (int i = 0; i < cipherText.length(); ++i){
            decString+= cipherTable[0].charAt(cipherTable[keyAlphabet.indexOf(Character.toLowerCase(key.charAt(i))) / 2].indexOf(Character.toLowerCase(cipherText.charAt(i)))) ;
        }

        this.plainText = decString;
        return decString;
    }

    public void setKey(String key){
        this.key = getCheckedString(key, keyAlphabet);
        if (this.key.length() == 0) {
            isBadKey = true;
            return;
        }

        var textLength = (isEncrypting)? plainText.length() : cipherText.length();
        if ( textLength > this.key.length()) {
            this.key = cesarCipher(this.key, textLength);
        }
    }

//---------------------------------------------------public methods-----------------------------------------------------------------
    private String cesarCipher(String text, int neededLength){
        int shift = 1;
        var tmpString = text;
        while (tmpString.length() < neededLength) {
            for (int i = 0; i < text.length(); ++i) {
                tmpString+= cipherTable[0].charAt( (cipherTable[0].indexOf(Character.toLowerCase(text.charAt(i))) + shift) % cipherTable[0].length());
            }
            shift++;
        }

        return tmpString;
    }

    private void generateCipherTable(){
        cipherTable[0] = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        var amountOfSymbols = cipherTable[0].length();
        var shift = 1;
        for (int i = 1; i < amountOfSymbols; ++i){
            var tmpString = "";
            for (int j = 0; j < amountOfSymbols; ++j){
                tmpString+= cipherTable[0].charAt((j + shift) % amountOfSymbols);
            }
            shift++;
            cipherTable[i] = tmpString;
        }
    }

}
