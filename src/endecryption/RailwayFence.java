package endecryption;


public class RailwayFence extends Crypt {

    private int key = -1;

//---------------------------------------------------public methods-----------------------------------------------------------------
    public String encrypt(){
        if (isBadKey || isBadText) {
            return " ";
        }

        String encString = "";
        char[][] symbols = new char[key][];
        for (int z = 0; z < key; ++z){
            symbols[z] = new char[plainText.length()];
        }

        int row = 0;
        int clm = 0;
        for (int i = 0; i < plainText.length(); ++i){

            symbols[row][clm] = plainText.charAt(i);
            clm++;
            if ((i / (key - 1)) % 2 == 0){
                row = (row == key - 1)? key - 2: row + 1;
            }
            else {
                row = (row == 0)? 1 : row - 1;
            }

        }

        int downStep = (key - 1) * 2;
        int upStep = 0;
        row = 0;
        clm = 0;

        int amount = 0;
        while (amount < plainText.length()){
            encString+= symbols[row][clm];
            amount++;
            int index = 0;
            int sum = ( (downStep != 0 && index % 2 == 0) || (index % 2 != 0 && upStep == 0) )? downStep : upStep;
            while (clm + sum < plainText.length()){
                encString+= symbols[row][clm + sum];
                amount++;
                index++;
                sum += ( (downStep != 0 && index % 2 == 0) || (index % 2 != 0 && upStep == 0) )? downStep : upStep;
            }
            clm++;
            if ((row / key) % 2 ==0){
                row++;
                downStep-=2;
                upStep+=2;
            }
            else {
                row--;
                downStep+=2;
                upStep-=2;
            }
        }

        this.cipherText = encString;
        return encString;
    }

    public String decrypt(){
        if (isBadKey || isBadText){
            return " ";
        }

        String decString = "";

        char[][] symbols = new char[key][];
        for (int z = 0; z < key; ++z){
            symbols[z] = new char[cipherText.length()];
        }

        int downStep = (key - 1) * 2;
        int upStep = 0;
        int row = 0;
        int clm = 0;

        int amount = 0;
        while (amount < cipherText.length()){
            symbols[row][clm] = cipherText.charAt(amount);
            amount++;
            int index = 0;
            int sum = ( (downStep != 0 && index % 2 == 0) || (index % 2 != 0 && upStep == 0) )? downStep : upStep;
            while (clm + sum < cipherText.length()){
                symbols[row][clm + sum] = cipherText.charAt(amount);
                amount++;
                index++;
                sum += ( (downStep != 0 && index % 2 == 0) || (index % 2 != 0 && upStep == 0) )? downStep : upStep;
            }
            clm++;
            if ((row / key) % 2 ==0){
                row++;
                downStep-=2;
                upStep+=2;
            }
            else {
                row--;
                downStep+=2;
                upStep-=2;
            }
        }

        row = 0;
        clm = 0;
        for (int i = 0; i < cipherText.length(); ++i){

            decString+= symbols[row][clm];
            clm++;
            if ((i / (key - 1)) % 2 == 0){
                row = (row == key - 1)? key - 2: row + 1;
            }
            else {
                row = (row == 0)? 1 : row - 1;
            }

        }

        this.plainText = decString;
        return decString;
    }

    public void setKey(String key) {
        try{
            var keyString = getCheckedString(key, keyAlphabet);
            if (keyString.length() != 0) {
                this.key = Integer.parseInt(keyString);
            }
            isBadKey = (this.key > 1)? false : true;
        }
        catch (NumberFormatException ex){
            System.out.println("Bad number");
        }
    }

}
