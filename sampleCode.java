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
    public void sampleMethode(){
        for(int j = 0; j < i; j++){
            if((i%2) == 0){
                System.out.println(s + i);
            }else{

            }
            if(i==2){
                System.out.println("zweiter durchlauf");
            }
        }
    }
}
