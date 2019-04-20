package matillion.test1;

import java.util.stream.IntStream;

public class StringComparisonHelper
{
    /**
     * Accepts two strings of equal length and returns the number of differences in characters between the two
     *
     * @param str1 The first string
     * @param str2 The second string
     * @return The number of character differences
     */
    public static long getCharacterDifferences( String str1, String str2 ) throws IllegalArgumentException
    {
        if ( str1 == null || str2 == null )
        {
            throw new IllegalArgumentException( "Arguments must be non-null" );
        }

        if ( str1.length() != str2.length() )
        {
            throw new IllegalArgumentException( "String arguments must be of equal length" );
        }

        return IntStream.range( 0, str1.length() )
                .mapToObj( i -> str1.charAt( i ) == str2.charAt( i ) )
                .filter( equalChars -> !equalChars )
                .count();
    }
}
