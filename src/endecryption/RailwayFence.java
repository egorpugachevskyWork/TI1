package endecryption;


public class RailwayFence extends Crypt {

    private int key = -1;

//---------------------------------------------------public methods-----------------------------------------------------------------
    public String encrypt(){
        if (isBadKey || isBadText) {
            return " ";
        }

        String encString = "";
        int downStep = (key * 2) - 2;
        int upStep = 0;
        int firstIndex = 0;
        
        while (downStep >= 0 && encString.length() != plainText.length()) {
            int index = firstIndex;
            encString+=this.plainText.charAt(index);
            while (index + downStep < plainText.length() && index + upStep < plainText.length()) {
                if (downStep > 0 && index + downStep < plainText.length()){
                    index+=downStep;
                    encString+=this.plainText.charAt(index);
                }

                if (upStep > 0 && index + upStep < plainText.length()) {
                    index+=upStep;
                    encString+=this.plainText.charAt(index);
                }
            }

            upStep+=2;
            downStep-=2;
            firstIndex++;
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

//        for (int z1 = 0 ; z1 < key; ++z1){
//            for (int z2 = 0; z2 < cipherText.length(); ++z2){
//                symbols[z1][z2] = '%';
//            }
//        }

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
            this.key = Integer.parseInt(getCheckedString(key, keyAlphabet));
            isBadKey = (this.key > 1)? false : true;
        }
        catch (NumberFormatException ex){
            System.out.println("Bad number");
        }
    }

}
