package org.cinchapi.concourse.util;

import java.util.Collection;

import com.google.common.collect.Iterables;

import junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@Runwith(Parameterized.class)
public class TCllectionsCompareParameterizedTest {
    private Collection<?> a;
    private Collection<?> b;
}
