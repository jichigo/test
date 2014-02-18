public class Sample {

    /**
     * </p>
     * @param args aaa
     * @param hoge 
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String reserveNo = "123";
        reserveNo = ("0000000000" + reserveNo).substring(reserveNo.length());
        System.out.println(reserveNo);
    }

}
