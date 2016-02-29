package xyz.yhsj.videoparse;

import org.junit.Test;

import xyz.yhsj.videoparse.extractors.letv.Letv;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(733315218, Letv.getTkey(1456724440));
    }
}