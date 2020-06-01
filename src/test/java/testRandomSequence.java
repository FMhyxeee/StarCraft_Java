import org.junit.Test;
import static org.junit.Assert.assertEquals;

import util.RandomSequence;

import java.util.List;
import java.util.stream.Collectors;

public class testRandomSequence {

    @Test
    public void showrandom(){
        List<Integer> generate = RandomSequence.generate(5);
        int count = 0;
        List<Integer> collect = generate.stream().distinct().collect(Collectors.toList());
        assertEquals(generate,collect);
    }
}
