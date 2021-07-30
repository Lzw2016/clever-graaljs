interface StringUtils {
    // Delete
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .deleteWhitespace(null)         = null
     *  .deleteWhitespace("")           = ""
     *  .deleteWhitespace("abc")        = "abc"
     *  .deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     */
    deleteWhitespace(str: JString): JString;

    // Remove
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .removeStart(null, *)      = null
     *  .removeStart("", *)        = ""
     *  .removeStart(*, null)      = *
     *  .removeStart("www.domain.com", "www.")   = "domain.com"
     *  .removeStart("domain.com", "www.")       = "domain.com"
     *  .removeStart("www.domain.com", "domain") = "www.domain.com"
     *  .removeStart("abc", "")    = "abc"
     * </pre>
     */
    removeStart(str: JString, remove: JString): JString;

    /**
     * <pre>
     *  .removeStartIgnoreCase(null, *)      = null
     *  .removeStartIgnoreCase("", *)        = ""
     *  .removeStartIgnoreCase(*, null)      = *
     *  .removeStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
     *  .removeStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
     *  .removeStartIgnoreCase("domain.com", "www.")       = "domain.com"
     *  .removeStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     *  .removeStartIgnoreCase("abc", "")    = "abc"
     * </pre>
     */
    removeStartIgnoreCase(str: JString, remove: JString): JString;

    /**
     * <pre>
     *  .removeEnd(null, *)      = null
     *  .removeEnd("", *)        = ""
     *  .removeEnd(*, null)      = *
     *  .removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     *  .removeEnd("www.domain.com", ".com")   = "www.domain"
     *  .removeEnd("www.domain.com", "domain") = "www.domain.com"
     *  .removeEnd("abc", "")    = "abc"
     * </pre>
     */
    removeEnd(str: JString, remove: JString): JString;

    /**
     * <pre>
     *  .removeEndIgnoreCase(null, *)      = null
     *  .removeEndIgnoreCase("", *)        = ""
     *  .removeEndIgnoreCase(*, null)      = *
     *  .removeEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
     *  .removeEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
     *  .removeEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     *  .removeEndIgnoreCase("abc", "")    = "abc"
     *  .removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
     *  .removeEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
     * </pre>
     */
    removeEndIgnoreCase(str: JString, remove: JString): JString;

    /**
     * <pre>
     *  .remove(null, *)        = null
     *  .remove("", *)          = ""
     *  .remove(*, null)        = *
     *  .remove(*, "")          = *
     *  .remove("queued", "ue") = "qd"
     *  .remove("queued", "zz") = "queued"
     * </pre>
     */
    remove(str: JString, remove: JString): JString;

    /**
     * <pre>
     *  .removeIgnoreCase(null, *)        = null
     *  .removeIgnoreCase("", *)          = ""
     *  .removeIgnoreCase(*, null)        = *
     *  .removeIgnoreCase(*, "")          = *
     *  .removeIgnoreCase("queued", "ue") = "qd"
     *  .removeIgnoreCase("queued", "zz") = "queued"
     *  .removeIgnoreCase("quEUed", "UE") = "qd"
     *  .removeIgnoreCase("queued", "zZ") = "queued"
     * </pre>
     */
    removeIgnoreCase(str: JString, remove: JString): JString;

    /**
     * <pre>
     *  .remove(null, *)       = null
     *  .remove("", *)         = ""
     *  .remove("queued", 'u') = "qeed"
     *  .remove("queued", 'z') = "queued"
     * </pre>
     */
    remove(str: JString, remove: JChar): JString;

    // Replacing
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .replaceOnce(null, *, *)        = null
     *  .replaceOnce("", *, *)          = ""
     *  .replaceOnce("any", null, *)    = "any"
     *  .replaceOnce("any", *, null)    = "any"
     *  .replaceOnce("any", "", *)      = "any"
     *  .replaceOnce("aba", "a", null)  = "aba"
     *  .replaceOnce("aba", "a", "")    = "ba"
     *  .replaceOnce("aba", "a", "z")   = "zba"
     * </pre>
     */
    replaceOnce(text: JString, searchString: JString, replacement: JString): JString;

    /**
     * <pre>
     *  .replaceOnceIgnoreCase(null, *, *)        = null
     *  .replaceOnceIgnoreCase("", *, *)          = ""
     *  .replaceOnceIgnoreCase("any", null, *)    = "any"
     *  .replaceOnceIgnoreCase("any", *, null)    = "any"
     *  .replaceOnceIgnoreCase("any", "", *)      = "any"
     *  .replaceOnceIgnoreCase("aba", "a", null)  = "aba"
     *  .replaceOnceIgnoreCase("aba", "a", "")    = "ba"
     *  .replaceOnceIgnoreCase("aba", "a", "z")   = "zba"
     *  .replaceOnceIgnoreCase("FoOFoofoo", "foo", "") = "Foofoo"
     * </pre>
     */
    replaceOnceIgnoreCase(text: JString, searchString: JString, replacement: JString): JString;

    /**
     * <pre>
     *  .replace(null, *, *)        = null
     *  .replace("", *, *)          = ""
     *  .replace("any", null, *)    = "any"
     *  .replace("any", *, null)    = "any"
     *  .replace("any", "", *)      = "any"
     *  .replace("aba", "a", null)  = "aba"
     *  .replace("aba", "a", "")    = "b"
     *  .replace("aba", "a", "z")   = "zbz"
     * </pre>
     */
    replace(text: JString, searchString: JString, replacement: JString): JString;

    /**
     * <pre>
     *  .replaceIgnoreCase(null, *, *)        = null
     *  .replaceIgnoreCase("", *, *)          = ""
     *  .replaceIgnoreCase("any", null, *)    = "any"
     *  .replaceIgnoreCase("any", *, null)    = "any"
     *  .replaceIgnoreCase("any", "", *)      = "any"
     *  .replaceIgnoreCase("aba", "a", null)  = "aba"
     *  .replaceIgnoreCase("abA", "A", "")    = "b"
     *  .replaceIgnoreCase("aba", "A", "z")   = "zbz"
     * </pre>
     */
    replaceIgnoreCase(text: JString, searchString: JString, replacement: JString): JString;

    /**
     * <pre>
     *  .replace(null, *, *, *)         = null
     *  .replace("", *, *, *)           = ""
     *  .replace("any", null, *, *)     = "any"
     *  .replace("any", *, null, *)     = "any"
     *  .replace("any", "", *, *)       = "any"
     *  .replace("any", *, *, 0)        = "any"
     *  .replace("abaa", "a", null, -1) = "abaa"
     *  .replace("abaa", "a", "", -1)   = "b"
     *  .replace("abaa", "a", "z", 0)   = "abaa"
     *  .replace("abaa", "a", "z", 1)   = "zbaa"
     *  .replace("abaa", "a", "z", 2)   = "zbza"
     *  .replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     */
    replace(text: JString, searchString: JString, replacement: JString, max: JInt): JString;

    /**
     * <pre>
     *  .replaceIgnoreCase(null, *, *, *)         = null
     *  .replaceIgnoreCase("", *, *, *)           = ""
     *  .replaceIgnoreCase("any", null, *, *)     = "any"
     *  .replaceIgnoreCase("any", *, null, *)     = "any"
     *  .replaceIgnoreCase("any", "", *, *)       = "any"
     *  .replaceIgnoreCase("any", *, *, 0)        = "any"
     *  .replaceIgnoreCase("abaa", "a", null, -1) = "abaa"
     *  .replaceIgnoreCase("abaa", "a", "", -1)   = "b"
     *  .replaceIgnoreCase("abaa", "a", "z", 0)   = "abaa"
     *  .replaceIgnoreCase("abaa", "A", "z", 1)   = "zbaa"
     *  .replaceIgnoreCase("abAa", "a", "z", 2)   = "zbza"
     *  .replaceIgnoreCase("abAa", "a", "z", -1)  = "zbzz"
     * </pre>
     */
    replaceIgnoreCase(text: JString, searchString: JString, replacement: JString, max: JInt): JString;

    /**
     * <pre>
     *   .replaceEach(null, *, *)        = null
     *   .replaceEach("", *, *)          = ""
     *   .replaceEach("aba", null, null) = "aba"
     *   .replaceEach("aba", new String[0], null) = "aba"
     *   .replaceEach("aba", null, new String[0]) = "aba"
     *   .replaceEach("aba", new String[]{"a"}, null)  = "aba"
     *   .replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
     *   .replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
     *   .replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
     *   (example of how it does not repeat)
     *   .replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
     * </pre>
     */
    replaceEach(text: JString, searchList: JString[], replacementList: JString[]): JString;

    /**
     * <pre>
     *  .replaceEachRepeatedly(null, *, *) = null
     *  .replaceEachRepeatedly("", *, *) = ""
     *  .replaceEachRepeatedly("aba", null, null) = "aba"
     *  .replaceEachRepeatedly("aba", new String[0], null) = "aba"
     *  .replaceEachRepeatedly("aba", null, new String[0]) = "aba"
     *  .replaceEachRepeatedly("aba", new String[]{"a"}, null) = "aba"
     *  .replaceEachRepeatedly("aba", new String[]{"a"}, new String[]{""}) = "b"
     *  .replaceEachRepeatedly("aba", new String[]{null}, new String[]{"a"}) = "aba"
     *  .replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}) = "wcte"
     *  (example of how it repeats)
     *  .replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}) = "tcte"
     *  .replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}) = IllegalStateException
     * </pre>
     */
    replaceEachRepeatedly(text: JString, searchList: JString[], replacementList: JString[]): JString;

    // Replace, character based
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .replaceChars(null, *, *)        = null
     *  .replaceChars("", *, *)          = ""
     *  .replaceChars("abcba", 'b', 'y') = "aycya"
     *  .replaceChars("abcba", 'z', 'y') = "abcba"
     * </pre>
     */
    replaceChars(str: JString, searchChar: JChar, replaceChar: JChar): JString;

    /**
     * <pre>
     *  .replaceChars(null, *, *)           = null
     *  .replaceChars("", *, *)             = ""
     *  .replaceChars("abc", null, *)       = "abc"
     *  .replaceChars("abc", "", *)         = "abc"
     *  .replaceChars("abc", "b", null)     = "ac"
     *  .replaceChars("abc", "b", "")       = "ac"
     *  .replaceChars("abcba", "bc", "yz")  = "ayzya"
     *  .replaceChars("abcba", "bc", "y")   = "ayya"
     *  .replaceChars("abcba", "bc", "yzx") = "ayzya"
     * </pre>
     */
    replaceChars(str: JString, searchChars: JString, replaceChars: JString): JString;

    // Overlay
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .overlay(null, *, *, *)            = null
     *  .overlay("", "abc", 0, 0)          = "abc"
     *  .overlay("abcdef", null, 2, 4)     = "abef"
     *  .overlay("abcdef", "", 2, 4)       = "abef"
     *  .overlay("abcdef", "", 4, 2)       = "abef"
     *  .overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     *  .overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     *  .overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     *  .overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     *  .overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     *  .overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
     * </pre>
     */
    overlay(str: JString, overlay: JString, start: JInt, end: JInt): JString;

    // Chomping
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .chomp(null)          = null
     *  .chomp("")            = ""
     *  .chomp("abc \r")      = "abc "
     *  .chomp("abc\n")       = "abc"
     *  .chomp("abc\r\n")     = "abc"
     *  .chomp("abc\r\n\r\n") = "abc\r\n"
     *  .chomp("abc\n\r")     = "abc\n"
     *  .chomp("abc\n\rabc")  = "abc\n\rabc"
     *  .chomp("\r")          = ""
     *  .chomp("\n")          = ""
     *  .chomp("\r\n")        = ""
     * </pre>
     */
    chomp(str: JString): JString;

    // Chopping
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .chop(null)          = null
     *  .chop("")            = ""
     *  .chop("abc \r")      = "abc "
     *  .chop("abc\n")       = "abc"
     *  .chop("abc\r\n")     = "abc"
     *  .chop("abc")         = "ab"
     *  .chop("abc\nabc")    = "abc\nab"
     *  .chop("a")           = ""
     *  .chop("\r")          = ""
     *  .chop("\n")          = ""
     *  .chop("\r\n")        = ""
     * </pre>
     */
    chop(str: JString): JString;

    // Conversion
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // Padding
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .repeat(null, 2) = null
     *  .repeat("", 0)   = ""
     *  .repeat("", 2)   = ""
     *  .repeat("a", 3)  = "aaa"
     *  .repeat("ab", 2) = "abab"
     *  .repeat("a", -2) = ""
     * </pre>
     */
    repeat(str: JString, repeat: JInt): JString;

    /**
     * <pre>
     *  .repeat(null, null, 2) = null
     *  .repeat(null, "x", 2)  = null
     *  .repeat("", null, 0)   = ""
     *  .repeat("", "", 2)     = ""
     *  .repeat("", "x", 3)    = "xxx"
     *  .repeat("?", ", ", 3)  = "?, ?, ?"
     * </pre>
     */
    repeat(str: JString, separator: JString, repeat: JInt): JString;

    /**
     * <pre>
     *  .repeat('e', 0)  = ""
     *  .repeat('e', 3)  = "eee"
     *  .repeat('e', -2) = ""
     * </pre>
     */
    repeat(ch: JChar, repeat: JInt): JString;

    /**
     * <pre>
     *  .rightPad(null, *)   = null
     *  .rightPad("", 3)     = "   "
     *  .rightPad("bat", 3)  = "bat"
     *  .rightPad("bat", 5)  = "bat  "
     *  .rightPad("bat", 1)  = "bat"
     *  .rightPad("bat", -1) = "bat"
     * </pre>
     */
    rightPad(str: JString, size: JInt): JString;

    /**
     * <pre>
     *  .rightPad(null, *, *)     = null
     *  .rightPad("", 3, 'z')     = "zzz"
     *  .rightPad("bat", 3, 'z')  = "bat"
     *  .rightPad("bat", 5, 'z')  = "batzz"
     *  .rightPad("bat", 1, 'z')  = "bat"
     *  .rightPad("bat", -1, 'z') = "bat"
     * </pre>
     */
    rightPad(str: JString, size: JInt, padChar: JChar): JString;

    /**
     * <pre>
     *  .rightPad(null, *, *)      = null
     *  .rightPad("", 3, "z")      = "zzz"
     *  .rightPad("bat", 3, "yz")  = "bat"
     *  .rightPad("bat", 5, "yz")  = "batyz"
     *  .rightPad("bat", 8, "yz")  = "batyzyzy"
     *  .rightPad("bat", 1, "yz")  = "bat"
     *  .rightPad("bat", -1, "yz") = "bat"
     *  .rightPad("bat", 5, null)  = "bat  "
     *  .rightPad("bat", 5, "")    = "bat  "
     * </pre>
     */
    rightPad(str: JString, size: JInt, padStr: JString): JString;

    /**
     * <pre>
     *  .leftPad(null, *)   = null
     *  .leftPad("", 3)     = "   "
     *  .leftPad("bat", 3)  = "bat"
     *  .leftPad("bat", 5)  = "  bat"
     *  .leftPad("bat", 1)  = "bat"
     *  .leftPad("bat", -1) = "bat"
     * </pre>
     */
    leftPad(str: JString, size: JInt): JString;

    /**
     * <pre>
     *  .leftPad(null, *, *)     = null
     *  .leftPad("", 3, 'z')     = "zzz"
     *  .leftPad("bat", 3, 'z')  = "bat"
     *  .leftPad("bat", 5, 'z')  = "zzbat"
     *  .leftPad("bat", 1, 'z')  = "bat"
     *  .leftPad("bat", -1, 'z') = "bat"
     * </pre>
     */
    leftPad(str: JString, size: JInt, padChar: JChar): JString;

    /**
     * <pre>
     *  .leftPad(null, *, *)      = null
     *  .leftPad("", 3, "z")      = "zzz"
     *  .leftPad("bat", 3, "yz")  = "bat"
     *  .leftPad("bat", 5, "yz")  = "yzbat"
     *  .leftPad("bat", 8, "yz")  = "yzyzybat"
     *  .leftPad("bat", 1, "yz")  = "bat"
     *  .leftPad("bat", -1, "yz") = "bat"
     *  .leftPad("bat", 5, null)  = "  bat"
     *  .leftPad("bat", 5, "")    = "  bat"
     * </pre>
     */
    leftPad(str: JString, size: JInt, padStr: JString): JString;

    /**
     * 获取CharSequence长度，如果CharSequence为null，则获取0
     */
    length(cs: JString): JInt;

    // Centering
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .center(null, *)   = null
     *  .center("", 4)     = "    "
     *  .center("ab", -1)  = "ab"
     *  .center("ab", 4)   = " ab "
     *  .center("abcd", 2) = "abcd"
     *  .center("a", 4)    = " a  "
     * </pre>
     */
    center(str: JString, size: JInt): JString;

    /**
     * <pre>
     *  .center(null, *, *)     = null
     *  .center("", 4, ' ')     = "    "
     *  .center("ab", -1, ' ')  = "ab"
     *  .center("ab", 4, ' ')   = " ab "
     *  .center("abcd", 2, ' ') = "abcd"
     *  .center("a", 4, ' ')    = " a  "
     *  .center("a", 4, 'y')    = "yayy"
     * </pre>
     */
    center(str: JString, size: JInt, padChar: JChar): JString;

    /**
     * <pre>
     *  .center(null, *, *)     = null
     *  .center("", 4, " ")     = "    "
     *  .center("ab", -1, " ")  = "ab"
     *  .center("ab", 4, " ")   = " ab "
     *  .center("abcd", 2, " ") = "abcd"
     *  .center("a", 4, " ")    = " a  "
     *  .center("a", 4, "yz")   = "yayz"
     *  .center("abc", 7, null) = "  abc  "
     *  .center("abc", 7, "")   = "  abc  "
     * </pre>
     */
    center(str: JString, size: JInt, padStr: JString): JString;

    // Case conversion
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .upperCase(null)  = null
     *  .upperCase("")    = ""
     *  .upperCase("aBc") = "ABC"
     * </pre>
     */
    upperCase(str: JString): JString;

    /**
     * <pre>
     *  .upperCase(null, Locale.ENGLISH)  = null
     *  .upperCase("", Locale.ENGLISH)    = ""
     *  .upperCase("aBc", Locale.ENGLISH) = "ABC"
     * </pre>
     */
    upperCase(str: JString, locale: Locale): JString;

    /**
     * <pre>
     *  .lowerCase(null)  = null
     *  .lowerCase("")    = ""
     *  .lowerCase("aBc") = "abc"
     * </pre>
     */
    lowerCase(str: JString): JString;

    /**
     * <pre>
     *  .lowerCase(null, Locale.ENGLISH)  = null
     *  .lowerCase("", Locale.ENGLISH)    = ""
     *  .lowerCase("aBc", Locale.ENGLISH) = "abc"
     * </pre>
     */
    lowerCase(str: JString, locale: Locale): JString;

    /**
     * <pre>
     *  .capitalize(null)  = null
     *  .capitalize("")    = ""
     *  .capitalize("cat") = "Cat"
     *  .capitalize("cAt") = "CAt"
     *  .capitalize("'cat'") = "'cat'"
     * </pre>
     */
    capitalize(str: JString): JString;

    /**
     * <pre>
     *  .uncapitalize(null)  = null
     *  .uncapitalize("")    = ""
     *  .uncapitalize("cat") = "cat"
     *  .uncapitalize("Cat") = "cat"
     *  .uncapitalize("CAT") = "cAT"
     * </pre>
     */
    uncapitalize(str: JString): JString;

    /**
     * <pre>
     *  .swapCase(null)                 = null
     *  .swapCase("")                   = ""
     *  .swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
     * </pre>
     */
    swapCase(str: JString): JString;

    // Count matches
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .countMatches(null, *)       = 0
     *  .countMatches("", *)         = 0
     *  .countMatches("abba", null)  = 0
     *  .countMatches("abba", "")    = 0
     *  .countMatches("abba", "a")   = 2
     *  .countMatches("abba", "ab")  = 1
     *  .countMatches("abba", "xxx") = 0
     * </pre>
     */
    countMatches(str: JString, sub: JString): JInt;

    /**
     * <pre>
     *  .countMatches(null, *)       = 0
     *  .countMatches("", *)         = 0
     *  .countMatches("abba", 0)     = 0
     *  .countMatches("abba", 'a')   = 2
     *  .countMatches("abba", 'b')   = 2
     *  .countMatches("abba", 'x')   = 0
     * </pre>
     */
    countMatches(str: JString, ch: JChar): JInt;

    // Character Tests
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .isAlpha(null)   = false
     *  .isAlpha("")     = false
     *  .isAlpha("  ")   = false
     *  .isAlpha("abc")  = true
     *  .isAlpha("ab2c") = false
     *  .isAlpha("ab-c") = false
     * </pre>
     */
    isAlpha(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isAlphaSpace(null)   = false
     *  .isAlphaSpace("")     = true
     *  .isAlphaSpace("  ")   = true
     *  .isAlphaSpace("abc")  = true
     *  .isAlphaSpace("ab c") = true
     *  .isAlphaSpace("ab2c") = false
     *  .isAlphaSpace("ab-c") = false
     * </pre>
     */
    isAlphaSpace(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isAlphanumeric(null)   = false
     *  .isAlphanumeric("")     = false
     *  .isAlphanumeric("  ")   = false
     *  .isAlphanumeric("abc")  = true
     *  .isAlphanumeric("ab c") = false
     *  .isAlphanumeric("ab2c") = true
     *  .isAlphanumeric("ab-c") = false
     * </pre>
     */
    isAlphanumeric(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isAlphanumericSpace(null)   = false
     *  .isAlphanumericSpace("")     = true
     *  .isAlphanumericSpace("  ")   = true
     *  .isAlphanumericSpace("abc")  = true
     *  .isAlphanumericSpace("ab c") = true
     *  .isAlphanumericSpace("ab2c") = true
     *  .isAlphanumericSpace("ab-c") = false
     * </pre>
     */
    isAlphanumericSpace(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isAsciiPrintable(null)     = false
     *  .isAsciiPrintable("")       = true
     *  .isAsciiPrintable(" ")      = true
     *  .isAsciiPrintable("Ceki")   = true
     *  .isAsciiPrintable("ab2c")   = true
     *  .isAsciiPrintable("!ab-c~") = true
     *  .isAsciiPrintable(" ") = true
     *  .isAsciiPrintable("!") = true
     *  .isAsciiPrintable("~") = true
     *  .isAsciiPrintable("") = false
     *  .isAsciiPrintable("Ceki Gülcü") = false
     * </pre>
     */
    isAsciiPrintable(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isNumeric(null)   = false
     *  .isNumeric("")     = false
     *  .isNumeric("  ")   = false
     *  .isNumeric("123")  = true
     *  .isNumeric("१२३")  = true
     *  .isNumeric("12 3") = false
     *  .isNumeric("ab2c") = false
     *  .isNumeric("12-3") = false
     *  .isNumeric("12.3") = false
     *  .isNumeric("-123") = false
     *  .isNumeric("+123") = false
     * </pre>
     */
    isNumeric(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isNumericSpace(null)   = false
     *  .isNumericSpace("")     = true
     *  .isNumericSpace("  ")   = true
     *  .isNumericSpace("123")  = true
     *  .isNumericSpace("12 3") = true
     *  .isNumeric("१२३")  = true
     *  .isNumeric("१२ ३")  = true
     *  .isNumericSpace("ab2c") = false
     *  .isNumericSpace("12-3") = false
     *  .isNumericSpace("12.3") = false
     * </pre>
     */
    isNumericSpace(cs: JString): JBoolean;

    /**
     * <pre>
     *  .getDigits(null)  = null
     *  .getDigits("")    = ""
     *  .getDigits("abc") = ""
     *  .getDigits("1000$") = "1000"
     *  .getDigits("1123~45") = "112345"
     *  .getDigits("(541) 754-3010") = "5417543010"
     *  .getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     */
    getDigits(str: JString): JString;

    /**
     * <pre>
     *  .isWhitespace(null)   = false
     *  .isWhitespace("")     = true
     *  .isWhitespace("  ")   = true
     *  .isWhitespace("abc")  = false
     *  .isWhitespace("ab2c") = false
     *  .isWhitespace("ab-c") = false
     * </pre>
     */
    isWhitespace(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isAllLowerCase(null)   = false
     *  .isAllLowerCase("")     = false
     *  .isAllLowerCase("  ")   = false
     *  .isAllLowerCase("abc")  = true
     *  .isAllLowerCase("abC")  = false
     *  .isAllLowerCase("ab c") = false
     *  .isAllLowerCase("ab1c") = false
     *  .isAllLowerCase("ab/c") = false
     * </pre>
     */
    isAllLowerCase(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isAllUpperCase(null)   = false
     *  .isAllUpperCase("")     = false
     *  .isAllUpperCase("  ")   = false
     *  .isAllUpperCase("ABC")  = true
     *  .isAllUpperCase("aBC")  = false
     *  .isAllUpperCase("A C")  = false
     *  .isAllUpperCase("A1C")  = false
     *  .isAllUpperCase("A/C")  = false
     * </pre>
     */
    isAllUpperCase(cs: JString): JBoolean;

    /**
     * <pre>
     *  .isMixedCase(null)    = false
     *  .isMixedCase("")      = false
     *  .isMixedCase("ABC")   = false
     *  .isMixedCase("abc")   = false
     *  .isMixedCase("aBc")   = true
     *  .isMixedCase("A c")   = true
     *  .isMixedCase("A1c")   = true
     *  .isMixedCase("a/C")   = true
     *  .isMixedCase("aC\t")  = true
     * </pre>
     */
    isMixedCase(cs: JString): JBoolean;

    // Defaults
    //-----------------------------------------------------------------------
    /**
     * <pre>
     *  .defaultString(null)  = ""
     *  .defaultString("")    = ""
     *  .defaultString("bat") = "bat"
     * </pre>
     */
    defaultString(str: JString): JString;

    /**
     * <pre>
     *  .defaultString(null, "NULL")  = "NULL"
     *  .defaultString("", "NULL")    = ""
     *  .defaultString("bat", "NULL") = "bat"
     * </pre>
     */
    defaultString(str: JString, defaultStr: JString): JString;

    // Rotating (circular shift)
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .rotate(null, *)        = null
     *  .rotate("", *)          = ""
     *  .rotate("abcdefg", 0)   = "abcdefg"
     *  .rotate("abcdefg", 2)   = "fgabcde"
     *  .rotate("abcdefg", -2)  = "cdefgab"
     *  .rotate("abcdefg", 7)   = "abcdefg"
     *  .rotate("abcdefg", -7)  = "abcdefg"
     *  .rotate("abcdefg", 9)   = "fgabcde"
     *  .rotate("abcdefg", -9)  = "cdefgab"
     * </pre>
     */
    rotate(str: JString, shift: JInt): JString;

    // Reversing
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .reverse(null)  = null
     *  .reverse("")    = ""
     *  .reverse("bat") = "tab"
     * </pre>
     */
    reverse(str: JString): JString;

    /**
     * <pre>
     *  .reverseDelimited(null, *)      = null
     *  .reverseDelimited("", *)        = ""
     *  .reverseDelimited("a.b.c", 'x') = "a.b.c"
     *  .reverseDelimited("a.b.c", ".") = "c.b.a"
     * </pre>
     */
    reverseDelimited(str: JString, separatorChar: JChar): JString;

    // Abbreviating
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .abbreviate(null, *)      = null
     *  .abbreviate("", 4)        = ""
     *  .abbreviate("abcdefg", 6) = "abc..."
     *  .abbreviate("abcdefg", 7) = "abcdefg"
     *  .abbreviate("abcdefg", 8) = "abcdefg"
     *  .abbreviate("abcdefg", 4) = "a..."
     *  .abbreviate("abcdefg", 3) = IllegalArgumentException
     * </pre>
     */
    abbreviate(str: JString, maxWidth: JInt): JString;

    /**
     * <pre>
     *  .abbreviate(null, *, *)                = null
     *  .abbreviate("", 0, 4)                  = ""
     *  .abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
     *  .abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
     *  .abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
     *  .abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
     *  .abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
     *  .abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
     *  .abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
     *  .abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
     * </pre>
     */
    abbreviate(str: JString, offset: JInt, maxWidth: JInt): JString;

    /**
     * <pre>
     *  .abbreviate(null, "...", *)      = null
     *  .abbreviate("abcdefg", null, *)  = "abcdefg"
     *  .abbreviate("", "...", 4)        = ""
     *  .abbreviate("abcdefg", ".", 5)   = "abcd."
     *  .abbreviate("abcdefg", ".", 7)   = "abcdefg"
     *  .abbreviate("abcdefg", ".", 8)   = "abcdefg"
     *  .abbreviate("abcdefg", "..", 4)  = "ab.."
     *  .abbreviate("abcdefg", "..", 3)  = "a.."
     *  .abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     *  .abbreviate("abcdefg", "...", 3) = IllegalArgumentException
     * </pre>
     */
    abbreviate(str: JString, abbrevMarker: JString, maxWidth: JInt): JString;

    /**
     * <pre>
     *  .abbreviate(null, "...", *)      = null
     *  .abbreviate("abcdefg", null, *)  = "abcdefg"
     *  .abbreviate("", "...", 4)        = ""
     *  .abbreviate("abcdefg", ".", 5)   = "abcd."
     *  .abbreviate("abcdefg", ".", 7)   = "abcdefg"
     *  .abbreviate("abcdefg", ".", 8)   = "abcdefg"
     *  .abbreviate("abcdefg", "..", 4)  = "ab.."
     *  .abbreviate("abcdefg", "..", 3)  = "a.."
     *  .abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
     *  .abbreviate("abcdefg", "...", 3) = IllegalArgumentException
     * </pre>
     */
    abbreviate(str: JString, abbrevMarker: JString, offset: JInt, maxWidth: JInt): JString;

    /**
     * <pre>
     *  .abbreviateMiddle(null, null, 0)        = null
     *  .abbreviateMiddle("abc", null, 0)       = "abc"
     *  .abbreviateMiddle("abc", ".", 0)        = "abc"
     *  .abbreviateMiddle("abc", ".", 3)        = "abc"
     *  .abbreviateMiddle("abcdef", ".", 4)     = "ab.f"
     * </pre>
     */
    abbreviateMiddle(str: JString, middle: JString, length: JInt): JString;

    // Difference
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .difference(null, null)          = null
     *  .difference("", "")              = ""
     *  .difference("", "abc")           = "abc"
     *  .difference("abc", "")           = ""
     *  .difference("abc", "abc")        = ""
     *  .difference("abc", "ab")         = ""
     *  .difference("ab", "abxyz")       = "xyz"
     *  .difference("abcde", "abxyz")    = "xyz"
     *  .difference("abcde", "xyz")      = "xyz"
     * </pre>
     */
    difference(str1: JString, str2: JString): JString;

    /**
     * <pre>
     *  .indexOfDifference(null, null)       = -1
     *  .indexOfDifference("", "")           = -1
     *  .indexOfDifference("", "abc")        = 0
     *  .indexOfDifference("abc", "")        = 0
     *  .indexOfDifference("abc", "abc")     = -1
     *  .indexOfDifference("ab", "abxyz")    = 2
     *  .indexOfDifference("abcde", "abxyz") = 2
     *  .indexOfDifference("abcde", "xyz")   = 0
     * </pre>
     */
    indexOfDifference(cs1: JString, cs2: JString): JInt;

    /**
     * <pre>
     *  .indexOfDifference(null)                                            = -1
     *  .indexOfDifference(new String[] {})                                 = -1
     *  .indexOfDifference(new String[] {"abc"})                            = -1
     *  .indexOfDifference(new String[] {null, null})                       = -1
     *  .indexOfDifference(new String[] {"", ""})                           = -1
     *  .indexOfDifference(new String[] {"", null})                         = 0
     *  .indexOfDifference(new String[] {"abc", null, null})                = 0
     *  .indexOfDifference(new String[] {null, null, "abc"})                = 0
     *  .indexOfDifference(new String[] {"", "abc"})                        = 0
     *  .indexOfDifference(new String[] {"abc", ""})                        = 0
     *  .indexOfDifference(new String[] {"abc", "abc"})                     = -1
     *  .indexOfDifference(new String[] {"abc", "a"})                       = 1
     *  .indexOfDifference(new String[] {"ab", "abxyz"})                    = 2
     *  .indexOfDifference(new String[] {"abcde", "abxyz"})                 = 2
     *  .indexOfDifference(new String[] {"abcde", "xyz"})                   = 0
     *  .indexOfDifference(new String[] {"xyz", "abcde"})                   = 0
     *  .indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
     * </pre>
     */
    indexOfDifference(...css: JString[]): JInt;

    /**
     * <pre>
     *  .getCommonPrefix(null)                                              = ""
     *  .getCommonPrefix(new String[] {})                                   = ""
     *  .getCommonPrefix(new String[] {"abc"})                              = "abc"
     *  .getCommonPrefix(new String[] {null, null})                         = ""
     *  .getCommonPrefix(new String[] {"", ""})                             = ""
     *  .getCommonPrefix(new String[] {"", null})                           = ""
     *  .getCommonPrefix(new String[] {"abc", null, null})                  = ""
     *  .getCommonPrefix(new String[] {null, null, "abc"})                  = ""
     *  .getCommonPrefix(new String[] {"", "abc"})                          = ""
     *  .getCommonPrefix(new String[] {"abc", ""})                          = ""
     *  .getCommonPrefix(new String[] {"abc", "abc"})                       = "abc"
     *  .getCommonPrefix(new String[] {"abc", "a"})                         = "a"
     *  .getCommonPrefix(new String[] {"ab", "abxyz"})                      = "ab"
     *  .getCommonPrefix(new String[] {"abcde", "abxyz"})                   = "ab"
     *  .getCommonPrefix(new String[] {"abcde", "xyz"})                     = ""
     *  .getCommonPrefix(new String[] {"xyz", "abcde"})                     = ""
     *  .getCommonPrefix(new String[] {"i am a machine", "i am a robot"})   = "i am a "
     * </pre>
     */
    getCommonPrefix(...strs: JString[]): JString;

    // Misc
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // startsWith
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .startsWith(null, null)      = true
     *  .startsWith(null, "abc")     = false
     *  .startsWith("abcdef", null)  = false
     *  .startsWith("abcdef", "abc") = true
     *  .startsWith("ABCDEF", "abc") = false
     * </pre>
     */
    startsWith(str: JString, prefix: JString): JBoolean;

    /**
     * <pre>
     *  .startsWithIgnoreCase(null, null)      = true
     *  .startsWithIgnoreCase(null, "abc")     = false
     *  .startsWithIgnoreCase("abcdef", null)  = false
     *  .startsWithIgnoreCase("abcdef", "abc") = true
     *  .startsWithIgnoreCase("ABCDEF", "abc") = true
     * </pre>
     */
    startsWithIgnoreCase(str: JString, prefix: JString): JBoolean;

    /**
     * <pre>
     *  .startsWithAny(null, null)                                   = false
     *  .startsWithAny(null, new String[] {"abc"})                   = false
     *  .startsWithAny("abcxyz", null)                               = false
     *  .startsWithAny("abcxyz", new String[] {""})                  = true
     *  .startsWithAny("abcxyz", new String[] {"abc"})               = true
     *  .startsWithAny("abcxyz", new String[] {null, "xyz", "abc"})  = true
     *  .startsWithAny("abcxyz", null, "xyz", "ABCX")                = false
     *  .startsWithAny("ABCXYZ", null, "xyz", "abc")                 = false
     * </pre>
     */
    startsWithAny(sequence: JString, ...searchStrings: JString[]): JBoolean;

    // endsWith
    //----------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * <pre>
     *  .endsWith(null, null)      = true
     *  .endsWith(null, "def")     = false
     *  .endsWith("abcdef", null)  = false
     *  .endsWith("abcdef", "def") = true
     *  .endsWith("ABCDEF", "def") = false
     *  .endsWith("ABCDEF", "cde") = false
     *  .endsWith("ABCDEF", "")    = true
     * </pre>
     */
    endsWith(str: JString, suffix: JString): JBoolean;

    /**
     * <pre>
     *  .endsWithIgnoreCase(null, null)      = true
     *  .endsWithIgnoreCase(null, "def")     = false
     *  .endsWithIgnoreCase("abcdef", null)  = false
     *  .endsWithIgnoreCase("abcdef", "def") = true
     *  .endsWithIgnoreCase("ABCDEF", "def") = true
     *  .endsWithIgnoreCase("ABCDEF", "cde") = false
     * </pre>
     */
    endsWithIgnoreCase(str: JString, suffix: JString): JBoolean;

    /**
     *
     */
    normalizeSpace(str: JString): JString;

    /**
     * <pre>
     *  .endsWithAny(null, null)                                    = false
     *  .endsWithAny(null, new String[] {"abc"})                    = false
     *  .endsWithAny("abcxyz", null)                                = false
     *  .endsWithAny("abcxyz", new String[] {""})                   = true
     *  .endsWithAny("abcxyz", new String[] {"xyz"})                = true
     *  .endsWithAny("abcxyz", new String[] {null, "xyz", "abc"})   = true
     *  .endsWithAny("abcXYZ", "def", "XYZ")                        = true
     *  .endsWithAny("abcXYZ", "def", "xyz")                        = false
     * </pre>
     */
    endsWithAny(sequence: JString, ...searchStrings: JString[]): JBoolean;

    /**
     * <pre>
     *  .appendIfMissing(null, null)        = null
     *  .appendIfMissing("abc", null)       = "abc"
     *  .appendIfMissing("", "xyz")         = "xyz"
     *  .appendIfMissing("abc", "xyz")      = "abcxyz"
     *  .appendIfMissing("abcxyz", "xyz")   = "abcxyz"
     *  .appendIfMissing("abcXYZ", "xyz")   = "abcXYZxyz"
     * </pre>
     */
    appendIfMissing(str: JString, suffix: JString, ...suffixes: JString[]): JString;

    /**
     * <pre>
     *  .appendIfMissingIgnoreCase(null, null)      = null
     *  .appendIfMissingIgnoreCase("abc", null)     = "abc"
     *  .appendIfMissingIgnoreCase("", "xyz")       = "xyz"
     *  .appendIfMissingIgnoreCase("abc", "xyz")    = "abcxyz"
     *  .appendIfMissingIgnoreCase("abcxyz", "xyz") = "abcxyz"
     *  .appendIfMissingIgnoreCase("abcXYZ", "xyz") = "abcXYZ"
     * </pre>
     */
    appendIfMissingIgnoreCase(str: JString, suffix: JString, ...suffixes: JString[]): JString;

    /**
     * <pre>
     *  .prependIfMissing(null, null)      = null
     *  .prependIfMissing("abc", null)     = "abc"
     *  .prependIfMissing("", "xyz")       = "xyz"
     *  .prependIfMissing("abc", "xyz")    = "xyzabc"
     *  .prependIfMissing("xyzabc", "xyz") = "xyzabc"
     *  .prependIfMissing("XYZabc", "xyz") = "xyzXYZabc"
     * </pre>
     */
    prependIfMissing(str: JString, prefix: JString, ...prefixes: JString[]): JString;

    /**
     * <pre>
     *  .prependIfMissingIgnoreCase(null, null)      = null
     *  .prependIfMissingIgnoreCase("abc", null)     = "abc"
     *  .prependIfMissingIgnoreCase("", "xyz")       = "xyz"
     *  .prependIfMissingIgnoreCase("abc", "xyz")    = "xyzabc"
     *  .prependIfMissingIgnoreCase("xyzabc", "xyz") = "xyzabc"
     *  .prependIfMissingIgnoreCase("XYZabc", "xyz") = "XYZabc"
     * </pre>
     */
    prependIfMissingIgnoreCase(str: JString, prefix: JString, ...prefixes: JString[]): JString;

    /**
     * 使用指定的字符编码将byte[]转换为字符串
     */
    toEncodedString(bytes: JByte[], charset: Charset): JString;

    /**
     * <pre>
     *  .wrap(null, *)        = null
     *  .wrap("", *)          = ""
     *  .wrap("ab", '\0')     = "ab"
     *  .wrap("ab", 'x')      = "xabx"
     *  .wrap("ab", '\'')     = "'ab'"
     *  .wrap("\"ab\"", '\"') = "\"\"ab\"\""
     * </pre>
     */
    wrap(str: JString, wrapWith: JChar): JString;

    /**
     * <pre>
     *  .wrap(null, *)         = null
     *  .wrap("", *)           = ""
     *  .wrap("ab", null)      = "ab"
     *  .wrap("ab", "x")       = "xabx"
     *  .wrap("ab", "\"")      = "\"ab\""
     *  .wrap("\"ab\"", "\"")  = "\"\"ab\"\""
     *  .wrap("ab", "'")       = "'ab'"
     *  .wrap("'abcd'", "'")   = "''abcd''"
     *  .wrap("\"abcd\"", "'") = "'\"abcd\"'"
     *  .wrap("'abcd'", "\"")  = "\"'abcd'\""
     * </pre>
     */
    wrap(str: JString, wrapWith: JString): JString;

    /**
     * <pre>
     *  .wrap(null, *)        = null
     *  .wrap("", *)          = ""
     *  .wrap("ab", '\0')     = "ab"
     *  .wrap("ab", 'x')      = "xabx"
     *  .wrap("ab", '\'')     = "'ab'"
     *  .wrap("\"ab\"", '\"') = "\"ab\""
     *  .wrap("/", '/')  = "/"
     *  .wrap("a/b/c", '/')  = "/a/b/c/"
     *  .wrap("/a/b/c", '/')  = "/a/b/c/"
     *  .wrap("a/b/c/", '/')  = "/a/b/c/"
     * </pre>
     */
    wrapIfMissing(str: JString, wrapWith: JChar): JString;

    /**
     * <pre>
     *  .wrap(null, *)         = null
     *  .wrap("", *)           = ""
     *  .wrap("ab", null)      = "ab"
     *  .wrap("ab", "x")       = "xabx"
     *  .wrap("ab", "\"")      = "\"ab\""
     *  .wrap("\"ab\"", "\"")  = "\"ab\""
     *  .wrap("ab", "'")       = "'ab'"
     *  .wrap("'abcd'", "'")   = "'abcd'"
     *  .wrap("\"abcd\"", "'") = "'\"abcd\"'"
     *  .wrap("'abcd'", "\"")  = "\"'abcd'\""
     *  .wrap("/", "/")  = "/"
     *  .wrap("a/b/c", "/")  = "/a/b/c/"
     *  .wrap("/a/b/c", "/")  = "/a/b/c/"
     *  .wrap("a/b/c/", "/")  = "/a/b/c/"
     * </pre>
     */
    wrapIfMissing(str: JString, wrapWith: JString): JString;

    /**
     * <pre>
     *  .unwrap(null, null)         = null
     *  .unwrap(null, "")           = null
     *  .unwrap(null, "1")          = null
     *  .unwrap("\'abc\'", "\'")    = "abc"
     *  .unwrap("\"abc\"", "\"")    = "abc"
     *  .unwrap("AABabcBAA", "AA")  = "BabcB"
     *  .unwrap("A", "#")           = "A"
     *  .unwrap("#A", "#")          = "#A"
     *  .unwrap("A#", "#")          = "A#"
     * </pre>
     */
    unwrap(str: JString, wrapToken: JString): JString;

    /**
     * <pre>
     *  .unwrap(null, null)         = null
     *  .unwrap(null, '\0')         = null
     *  .unwrap(null, '1')          = null
     *  .unwrap("\'abc\'", '\'')    = "abc"
     *  .unwrap("AABabcBAA", 'A')   = "ABabcBA"
     *  .unwrap("A", '#')           = "A"
     *  .unwrap("#A", '#')          = "#A"
     *  .unwrap("A#", '#')          = "A#"
     * </pre>
     */
    unwrap(str: JString, wrapChar: JChar): JString;

    /**
     * <pre>
     *  .toCodePoints(null)   =  null
     *  .toCodePoints("")     =  []  // empty array
     * </pre>
     */
    toCodePoints(str: JString): JInt[];
}

declare const StringUtils: StringUtils;
