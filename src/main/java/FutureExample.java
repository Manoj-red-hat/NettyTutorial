import com.sun.corba.se.impl.orbutil.closure.*;

import java.util.concurrent.*;
import java.util.concurrent.Future;

/**
 * Created by Manoj Kumar on 11/6/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */

public class FutureExample {
    class asyncTaskEx implements Callable<Long>{
        int[] data ;

        asyncTaskEx(int data[]){
            this.data=data;
        }
        @Override
        public Long call() throws Exception {
            long sum=0;
            for (int x:data){
                sum+=x;
            }
            return sum;
        }
    }

    ExecutorService es=Executors.newFixedThreadPool(10);
    asyncTaskEx a=null;
    Long result;
    Future f1;
    FutureExample(int data[]){
        a=new asyncTaskEx(data);
        f1=es.submit(a);
    }

    void printResult(){

        try {
            result= (Long) f1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}

