package matillion.test1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringComparisonHelperTest
{
    @Test
    public void test_nullArguments_throwsException()
    {
        assertThrows( IllegalArgumentException.class,
                () -> StringComparisonHelper.getCharacterDifferences( null, null ) );
    }

    @Test
    public void test_differentLengthArguments_throwsException()
    {
        String arg1 = "x".repeat( 5 );
        String arg2 = "x".repeat( 4 );
        assertThrows( IllegalArgumentException.class,
                () -> StringComparisonHelper.getCharacterDifferences( arg1, arg2 ) );
    }

    @Test
    public void test_blankArguments_returnsZero()
    {
        long diffs = StringComparisonHelper.getCharacterDifferences( "", "" );
        assertEquals( 0, diffs, "Blank strings have no differences" );
    }

    @ParameterizedTest
    @CsvSource( {       "aaaa,aaaa,0",                                  // A normal case
                        "692AQY:\",6~2AQY:\",1",                        // Special characters
                        "D23W8MCCIZQOP9,D236862CEZQOPS,5",              // The given example in the question
                        "0    00,0   000,1",                            // Whitespace characters
                        "\u00AC-\u2228\u00AC-,\u00AC-\u2229\u00AC-,1"   // Non-ascii characters
    } )
    public void test_validInputs_isCorrect( String input1, String input2, int diffsExpected )
    {
        long diffs = StringComparisonHelper.getCharacterDifferences( input1, input2 );
        assertEquals( diffsExpected, diffs, "Number of differences is as expected" );
    }

}
