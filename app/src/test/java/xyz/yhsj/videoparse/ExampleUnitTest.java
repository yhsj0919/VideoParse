package xyz.yhsj.videoparse;

import org.junit.Test;

import xyz.yhsj.videoparse.extractors.letv.Letv;
import xyz.yhsj.videoparse.utils.DateUtils;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals("21832160", Letv.getVideoId("http://www.letv.com/ptv/vplay/21832160.html"));
    }
}