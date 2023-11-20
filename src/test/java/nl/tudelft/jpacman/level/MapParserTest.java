package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The MapParserTest class houses unit tests for the MapParser class, which
 * is responsible for parsing textual representations of game maps into
 * playable Level objects. These tests ensure that maps are correctly
 * interpreted, and the corresponding game elements such as walls, spaces,
 * and NPCs are accurately instantiated.
 *
 * The MockitoExtension is utilized to enable mockito annotations and to
 * provide a mock environment for isolated testing of the MapParser functionality.
 */
@ExtendWith(MockitoExtension.class)
public class MapParserTest {
    /**
     * Mock instance of BoardFactory used to create Board-related objects
     * like walls and ground spaces. BoardFactory is responsible for
     * instantiating the squares that make up the game board.
     */
    @Mock
    private BoardFactory boardFactory;

    /**
     * Mock instance of LevelFactory used to create Level-related objects
     * and NPCs (Non-Playable Characters). LevelFactory is responsible for
     * providing a new Level object with its required components, such as
     * ghosts and pellets.
     */
    @Mock
    private LevelFactory levelFactory;

    /**
     * Mock instance of Blinky, a specific type of Ghost NPC in the game.
     * Blinky is used here as a representative ghost for testing the
     * creation and placement of ghosts on the map.
     */
    @Mock
    private Blinky blinky;

    /**
     * Test for the parseMap method (good map).
     */
    @Test
    public void testParseMapGood() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(boardFactory);
        assertNotNull(levelFactory);
        Mockito.when(levelFactory.createGhost()).thenReturn(blinky);
        MapParser mapParser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>();
        map.add("############");
        map.add("#P        G#");
        map.add("############");
        mapParser.parseMap(map);

        final int expectedCreatedWall = 26;
        final int expectedCreateGround = 10;

        Mockito.verify(levelFactory, Mockito.times(1)).createGhost();
        Mockito.verify(boardFactory, Mockito.times(expectedCreatedWall)).createWall();
        Mockito.verify(boardFactory, Mockito.times(expectedCreateGround)).createGround();
    }

    /**
     * Test for the parseMap method with an incorrectly formatted map.
     */
    @Test
    public void testParseMapWrong1() {
        MockitoAnnotations.initMocks(this);
        assertNotNull(boardFactory);
        assertNotNull(levelFactory);
        MapParser mapParser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>();
        map.add("########");
        map.add("#P    G#");
        map.add("####"); // Inconsistent length
        map.add("#P @ G#"); // Invalid character '@'

        PacmanConfigurationException thrown = Assertions.assertThrows(
            PacmanConfigurationException.class, () -> mapParser.parseMap(map)
        );

        String expectedErrorMessage = "Input text lines are not of equal width.";
        Assertions.assertEquals(expectedErrorMessage, thrown.getMessage());
    }

}
