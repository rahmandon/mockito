/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.invocation;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.mockito.util.ExtraMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.TestBase;
import org.mockito.internal.matchers.Equals;
import org.mockito.internal.matchers.NotNull;

@SuppressWarnings("unchecked")
public class InvocationMatcherTest extends TestBase {

    public void shouldBuildEqualsMatchersWhenNullPassed() throws Exception {
        InvocationMatcher m = new InvocationMatcher(new InvocationBuilder().args("foo").toInvocation(), null);
        assertThat(m.getMatchers(), collectionHasExactlyInOrder(new Equals("foo")));
    }
    
    @Test
    public void shouldBeACitizenOfHashes() throws Exception {
        Invocation invocation = new InvocationBuilder().toInvocation();
        Invocation invocationTwo = new InvocationBuilder().args("blah").toInvocation();
        
        Map map = new HashMap();
        map.put(new InvocationMatcher(invocation), "one");
        map.put(new InvocationMatcher(invocationTwo), "two");
        
        assertEquals(2, map.size());
    }
    
    @Test
    public void shouldNotEqualIfNumberOfArgumentsDiffer() throws Exception {
        InvocationMatcher withOneArg = new InvocationMatcher(new InvocationBuilder().args("test").toInvocation());
        InvocationMatcher withTwoArgs = new InvocationMatcher(new InvocationBuilder().args("test", 100).toInvocation());

        assertFalse(withOneArg.equals(null));
        assertFalse(withOneArg.equals(withTwoArgs));
    }
    
    @Test
    public void shouldToStringWithMatchers() throws Exception {
        Matcher m = NotNull.NOT_NULL;
        InvocationMatcher notNull = new InvocationMatcher(new InvocationBuilder().toInvocation(), asList(m));
        Matcher mTwo = new Equals('x');
        InvocationMatcher equals = new InvocationMatcher(new InvocationBuilder().toInvocation(), asList(mTwo));

        assertEquals("Object.simpleMethod(notNull())", notNull.toString());
        assertEquals("Object.simpleMethod('x')", equals.toString());
    }
}
