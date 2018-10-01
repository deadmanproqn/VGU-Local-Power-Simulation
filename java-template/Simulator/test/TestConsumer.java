import interfaces.AbstractComponent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vgu.consumer.ConsumerFactory;
import vgu.control.Control;
import vgu.generator.GeneratorFactory;
import java.util.ArrayList;

class TestConsumer {
    @BeforeAll
    static void initialize() {
        ConsumerFactory.setRunBehaviour(new double[] { .5, .2, .15, .45, .75, .60, .55, .40, .45, .65, .95, .75 });
    }
    @Test
    void Test1() {
        Control control=new Control();

        ArrayList<AbstractComponent> consumer;
        consumer= ConsumerFactory.generate(100,100,0);
        System.out.println(consumer.size());

    }




}