import java.util.Arrays;

public class MyThread {


    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    private static float[] arr = new float[SIZE];
    private static float[] a1 = new float[HALF];
    private static float[] a2 = new float[HALF];;
    public static void main(String[] args) {
        initArr();

        fillArr();
        initArr();
        fillArrWithThreads();

    }

    private static void fillArr() {
        long a = System.currentTimeMillis();
        for (int i =0; i< arr.length;i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println((System.currentTimeMillis()- a)/1000+" сек");
    }

    private static void fillArrWithThreads() {
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread x1 = new Thread(()-> {
            try{
                for (int i =0; i< a1.length;i++){
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }

            }
            catch(Exception e){
                System.out.println(e.getStackTrace());
            };
        });

        Thread x2 = new Thread(()-> {
            try{
                for (int i =0; i< a2.length;i++){
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i+HALF) / 5) * Math.cos(0.2f + (i+HALF) / 5) * Math.cos(0.4f + (i+HALF) / 2));
                }

            }
            catch(Exception e){
                System.out.println(e.getStackTrace());
            };
        });

        x1.start();
        x2.start();

        try{
            x1.join();
            x2.join();
        }catch (InterruptedException e){}

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println((System.currentTimeMillis()- a)/1000+" сек");
    }

    private static void initArr() {
        Arrays.fill(arr, 1);
    }
}
