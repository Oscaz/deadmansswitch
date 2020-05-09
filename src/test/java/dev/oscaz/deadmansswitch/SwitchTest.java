package dev.oscaz.deadmansswitch;

import dev.oscaz.deadmansswitch.exception.InvalidContentTypeException;
import dev.oscaz.deadmansswitch.gen.KeyGenerator;
import dev.oscaz.deadmansswitch.io.FileHandler;
import dev.oscaz.deadmansswitch.pojo.LiveMansSwitch;
import dev.oscaz.deadmansswitch.pojo.WebSocketMessage;
import dev.oscaz.deadmansswitch.socket.MessageHandler;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class SwitchTest {

    @Test
    public void validateAuthentication() {
        Assert.assertFalse(KeyGenerator.authenticateDeadKey("ggggg"));
        Assert.assertFalse(KeyGenerator.authenticateDeadKey("xxxxx"));
        Assert.assertFalse(KeyGenerator.authenticateDeadKey("zzzzz"));
        Assert.assertTrue(KeyGenerator.authenticateDeadKey(KeyGenerator.generateDeadKey()));
    }

    @Test
    public void testContentException() {
        try {
            if (true) {
                throw new InvalidContentTypeException();
            }
        } catch (InvalidContentTypeException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testFileHandler() {
        Assert.assertTrue(FileHandler.LIVE_FILE.exists());
        Assert.assertTrue(FileHandler.DEAD_FILE.exists());
    }

    @Test
    public void testSwitchTimedeath() {
        Assert.assertTrue(
                new LiveMansSwitch(
                        null,
                        null,
                        Instant.now().minusSeconds(60),
                        Instant.now().minusSeconds(60),
                        Duration.ofSeconds(60)
                ).hasBecomeDead()
        );
    }

    @Test
    public void testSocketMessageHandlerAssignability() {
        for (MessageHandler handler : MessageHandler.values()) {
            if (handler.getMessageClass() != null) {
                Assert.assertTrue(WebSocketMessage.class.isAssignableFrom(handler.getMessageClass()));
            }
        }
    }

    @Test
    public void testTimeParsingSparkPost() {
        Assert.assertEquals(SparkPosts.parseTimeUnit("Minutes"), 60);
        Assert.assertEquals(SparkPosts.parseTimeUnit("Hours"), 60 * 60);
        Assert.assertEquals(SparkPosts.parseTimeUnit("Days"), 24 * 60 * 60);
        Assert.assertEquals(SparkPosts.parseTimeUnit("Weeks"), 7 * 24 * 60 * 60);
        Assert.assertEquals(SparkPosts.parseTimeUnit("Months"), 4 * 7 * 24 * 60 * 60 + (2 * 24 * 60 * 60));
        Assert.assertEquals(SparkPosts.parseTimeUnit("Years"), 365 * 24 * 60 * 60);
        Assert.assertEquals(SparkPosts.parseTimeUnit("a"), 1);
        Assert.assertEquals(SparkPosts.parseTimeUnit(UUID.randomUUID().toString()), 1);
    }

}
