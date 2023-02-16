package marvel.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * This class tests MarvelTests
 */
public class MarvelTests {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested
    private Graph<String, String> graph1;

    @Before
    public void buildGraph() {
        graph1 = MarvelPaths.graphCreator("nflPlayers.csv");
    }

    @Test (expected = IllegalArgumentException.class)
    public void char1NotInGraph() {
        MarvelPaths.findPath(graph1, "MARSHAWN-LYNCH", "TOM-BRADY");
    }

    @Test (expected = IllegalArgumentException.class)
    public void char2NotInGraph() throws IllegalArgumentException{
        MarvelPaths.findPath(graph1, "TOM-BRADY", "MARSHAWN-LYNCH");
    }

    @Test (expected = IllegalArgumentException.class)
    public void char1Null() {
        MarvelPaths.findPath(graph1, null, "GENO-SMITH");
    }

    @Test (expected = IllegalArgumentException.class)
    public void char2Null() {
        MarvelPaths.findPath(graph1, "GENO-SMITH", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void buildNullFile() {
        MarvelPaths.graphCreator(null);
    }

}
