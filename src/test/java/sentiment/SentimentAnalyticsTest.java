package sentiment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentAnalyticsTest {

    private SentimentAnalytics sut;
    private InteractionGateway interactionGateway;

    @BeforeEach
    void setUp() {
        interactionGateway = new InteractionGatewayFake();
        sut = new SentimentAnalytics(interactionGateway);
    }

    @Test
    public void createSentimentAnalyticsTest() {
    }

    @Test
    public void emptyResultOnNonExistingInteractionTest() {

        Optional<AnalyticsResult> result = sut.analyze("NON-EXISTING-INTERACTION");

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void resultOfInteractionWithoutSegmentsTest() {
        Interaction interaction = new Interaction();
        String id = interactionGateway.create(interaction);

        AnalyticsResult result = sut.analyze(id).get();

        assertEquals(Optional.empty(), result.getSentiment());
    }

    @Test
    public void resultOnInteractionWithSingleSegmentTest() {
        Segment segment = segmentForSentiment(0.7, 0.1, 0.2);
        Interaction interaction = new Interaction();
        interaction.addSegment(segment);
        String id = interactionGateway.create(interaction);

        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();

        assertSentiment(sentiment, 0.7, 0.1, 0.2);
    }

    @Test
    public void resultOnInteractionWithMultipleSegmentTest() {
        Segment segment1 = segmentForSentiment(0.5, 0.2, 0.3);
        Segment segment2 = segmentForSentiment(0.7, 0.1, 0.2);
        Interaction interaction = new Interaction();
        interaction.addSegment(segment1);
        interaction.addSegment(segment2);
        String id = interactionGateway.create(interaction);

        AnalyticsResult result = sut.analyze(id).get();
        Sentiment sentiment = result.getSentiment().get();

        assertSentiment(sentiment, 0.6, 0.15, 0.25);
    }

    private void assertSentiment(Sentiment sentiment, double positive, double neutral, double negative) {
        assertEquals(positive, sentiment.getPositive(), "Positive");
        assertEquals(neutral, sentiment.getNeutral(), "Neutral");
        assertEquals(negative, sentiment.getNegative(), "Negative");
    }

    private Segment segmentForSentiment(double positive, double neutral, double negative) {
        List<String> text = new ArrayList<>();
        text.addAll(generateStringList(sizeOfScore(positive),"Positive"));
        text.addAll(generateStringList(sizeOfScore(neutral),"Neutral"));
        text.addAll(generateStringList(sizeOfScore(negative),"Negative"));

        return new Segment(text);
    }

    private int sizeOfScore(double score) {
        return (int)(score * 100);
    }

    private List<String> generateStringList(int size, String word) {

        List<String> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            result.add(word);
        }

        return result;
    }
}
