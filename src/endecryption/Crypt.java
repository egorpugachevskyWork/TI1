package endecryption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Crypt {

    protected boolean isText = true;
    protected boolean isEncrypting = true;
    protected String plainAlphabet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    protected String cipherAlphabet = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    protected String keyAlphabet = "0123456789";
    protected String plainText = "";
    protected String cipherText = "";
    protected boolean isBadKey = false;
    protected boolean isBadText = false;

    //---------------------------------------------------abstract methods-----------------------------------------------------------------

    public abstract String encrypt();
    public abstract String decrypt();
    public abstract void setKey(String key);

    //---------------------------------------------------public methods-----------------------------------------------------------------

    public void setPlainText(String inputString){
        isEncrypting = true;
        this.plainText = (isText)? readText(inputString) : readFile(inputString);
        isBadText = (this.plainText.length() > 0? false : true);
    }

    public void setCipherText(String inputString){
        isEncrypting = false;
        this.cipherText = (isText)? readText(inputString) : readFile(inputString);
        isBadText = (this.cipherText.length() > 0? false : true);
    }

    public void setText(boolean value){
        this.isText = value;
    }

    public void setEncrypting(boolean value) { this.isEncrypting = value; }

    //---------------------------------------------------inner methods-----------------------------------------------------------------
    protected String getCheckedString(String text, String alphabet){
        String result = "";

        for (int i = 0; i < text.length(); ++i){
            var tmpString = String.format("%c", text.charAt(i));
            if (alphabet.contains(tmpString)) {
                result = result.concat(tmpString);
            }
        }

        return result;
    }

    private String readText(String text){
        return getCheckedString(text, (isEncrypting)? plainAlphabet : cipherAlphabet);
    }

    private String readFile(String path){
        String text = "";

        try{
            var pathObject = Path.of(path);
            if (Files.exists(pathObject)){
                text = getCheckedString(Files.readString(pathObject), (isEncrypting)? plainAlphabet : cipherAlphabet);
            }
            else {
                return "cannot open this file";//переделать потом
            }
        }
        catch (IOException ex){

            System.out.println("cannot open");
        }

        return text;
    }
}
