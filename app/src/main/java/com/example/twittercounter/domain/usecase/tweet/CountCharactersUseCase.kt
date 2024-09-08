package com.example.twittercounter.domain.usecase.tweet

import com.example.twittercounter.persentaion.common.Constants.URL_LENGTH_COUNT
import java.util.regex.Pattern

/*according to this link https://tweetdelete.net/resources/twitter-character-counter-an-analysis-of-xs-tweet-limits/#How_Does_the_Twitter_Character_Counter_Work_on_X
*
All alphabets, punctuations, numbers, and symbols are all a single character. For example, @ takes up one character on X. Even alphabets from Western European languages, like Ç or Ü, also carry the same weight. A space is also a single character. So, how many characters are there in this sentence? Counting every alphabet, the spaces between each word, the question, and the comma, it is 51. As there are eight words, there are eight spaces. So, if you remove the spaces, it is 43.
There are also characters that account for two units instead of one. Emojis, which people tend to use instead of words, fall into this category. If you look at certain emojis, you’ll notice they combine two or more emojis like 👩‍👧‍👦. Even in this case, using 👩‍👧‍👦 counts for two characters, not three.
The platform assigns this value to Chinese, Japanese, and Korean languages. This reduces the maximum character limit from 280 to 140 and 25,000 to 12,500 for subscribers.
You may include other things in a post that don’t fit these categories. A link, regardless of the length, will always be 23 characters. Photos, GIFs, and videos don’t take up any characters. If you mention a user, the platform counts the number of characters in the username, along with the @ symbol. As a handle cannot exceed 15 characters, it won’t take more than 16 characters.*
*
* so as a counclution :
* normal characters = 1
* emoji =2
* Chinese, Japanese, and Korean languages = 2
* links = 23
* */
class CountCharactersUseCase {
    val urlPattern: Pattern = Pattern.compile(
        "(https?|ftp)://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]+"
    )
    val spectialLangsRange =
        "\\p{InCJKUnifiedIdeographs}|\\p{InHangulSyllables}|\\p{InHiragana}|\\p{InKatakana}".toRegex()

    fun getTextLength(text: String): Int {
        var length = 0
        val urlMatcher = urlPattern.matcher(text)
        var modifiedText = text
        while (urlMatcher.find()) {
            modifiedText =
                urlMatcher.replaceFirst(" ".repeat(URL_LENGTH_COUNT))
        }

        for (char in modifiedText) {
            val charAsString = char.toString()

            if (spectialLangsRange.matches(charAsString)) {
                length += 2 //spectial langs
            } else if (Character.isSupplementaryCodePoint(char.code) || Character.charCount(char.code) == 2) {
                length += 2 // Emojis
            } else {
                length += 1 // Regular
            }
        }

        return length
    }
}