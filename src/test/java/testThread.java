import net.udp.Broadcaster;
import org.junit.Test;

public class testThread {
    @Test
    public void testThread(){
        Broadcaster broadcaster = new Broadcaster(null);
        broadcaster.start(null);
    }
}
