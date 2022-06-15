public class sampleCode {

    private String s = new String();
    private int i;
    // useless comment
    public sampleCode(){
        s = "ohne Eingabe";
        i = 0;
    };
    //useless
    public sampleCode(String s, int i){
        this.s = s;
        this.i = i;
    }
    /* this is gonna be hard
     * to find out
     * how
     * multilined comments work
     */
    int iwas = 10;
    /* aölsdf */ // this is the ultimate test
    /* alösjdf
     * aölsdjf
     * 
     * asdf
     * 
     */

    public void sampleMethode(){
        for(int j = 0; j < i; j++){
            if((i%2) == 0){
                System.out.println(s + i);
            }else{

            }
            if(i==2){System.out.println("zweiter durchlauf");}
        }

        for (int x = 12; x <= 13; x++)
            System.out.println(2);
        if(true);

        for (var i : new int[]{1,2,3}){
            System.out.println(i);
        }
    }
}