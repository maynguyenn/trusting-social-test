package trustingsocial;

import org.junit.Test;
import trustingsocial.generation.GeneratingTest;

import static trustingsocial.utils.TestUtils.assertReaders;

public class PhoneProcessingTest {

    @Test
    public void test_default() {
        new PhoneProcessing("data/sample.csv").processing();
        assertReaders("data/sample_result.csv", "output.csv");
    }

    @Test
    public void test_simple_single_entry() {
        new PhoneProcessing("data/simple_test_1.csv").processing();
        assertReaders("data/simple_test_1_result.csv", "output.csv");
    }

    @Test
    public void test_simple_entry_with_no_deactivation_date() {
        new PhoneProcessing("data/simple_test_2.csv").processing();
        assertReaders("data/simple_test_2_result.csv", "output.csv");
    }

    @Test
    public void test_simple_entry_when_deactivation_date_out_of_range() {
        new PhoneProcessing("data/simple_test_3.csv").processing();
        assertReaders("data/simple_test_3_result.csv", "output.csv");
    }

    @Test
    public void test_1() {
        new PhoneProcessing("data/test_1.csv").processing();
        assertReaders("data/test_1_result.csv", "output.csv");
    }

    @Test
    public void test_2() {
        new PhoneProcessing("data/test_2.csv").processing();
        assertReaders("data/test_2_result.csv", "output.csv");
    }

    @Test
    public void test_random_data() {
        new PhoneProcessing("data/random_1.csv").processing();
        assertReaders("data/random_1_result.csv", "output.csv");
    }

    @Test
    public void test_random_with_big_data_2() {
        new PhoneProcessing("data/random_2.csv").processing();
        assertReaders("data/random_2_result.csv", "output.csv");
    }

    @Test
    public void test_totally_random() {
        new GeneratingTest(10, "data/real_random.csv", "data/real_random_result.csv").generate();
        new PhoneProcessing("data/real_random.csv").processing();
        assertReaders("data/real_random_result.csv", "output.csv");
    }
}
