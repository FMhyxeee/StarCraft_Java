import org.junit.Test;

/**
* @author hyx
* @description testTemplate
*
**/

public class testTemplate {
   /**
   *-----------------------------------
   * @author hyx
   * @date 2020/5/20 16:48
   * @description
   *-----------------------------------
   **/
    public int templateTest(int a,int b){

        return 1;
    }

    @Test
    public void testFor(){
        for (int i = 0; i < 10; ++i){
            System.out.println(i);
        }
    }
}


