package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        assertThat(unit.hasSquare()).isEqualTo(false);
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        assertThat(unit.hasSquare()).isEqualTo(false);
        Square square = new BasicSquare();
        unit.occupy(square);
        assertThat(unit.hasSquare()).isEqualTo(true);
        assertThat(unit.getSquare()).isEqualTo(square);
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        assertThat(unit.hasSquare()).isEqualTo(false);
        Square square = new BasicSquare();
        unit.occupy(square);
        unit.occupy(square);
        assertThat(unit.hasSquare()).isEqualTo(true);
        assertThat(unit.getSquare()).isEqualTo(square);
    }
}
