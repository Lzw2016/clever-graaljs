AssertUtils.isTrue(2 > 1, "isTrue");

AssertUtils.isFalse(2 < 1, "isFalse");

AssertUtils.isNull(null, "isNull");

AssertUtils.isNull(undefined, "isNull-undefined");

AssertUtils.notNull("", "notNull");

AssertUtils.notNull(false, "notNull-false");

AssertUtils.hasLength(" ", "hasLength");

AssertUtils.hasLength("\n", "hasLength-AAA");

AssertUtils.hasText("1", "hasText");

AssertUtils.doesNotContain("textToSearch", "ea1", "doesNotContain");

AssertUtils.notEmpty([1, "3"], "notEmpty");

AssertUtils.noNullElements([1, "3"], "noNullElements");

