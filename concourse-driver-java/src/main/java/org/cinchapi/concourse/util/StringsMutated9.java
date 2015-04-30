
package org.cinchapi.concourse.util;

import java.text.MessageFormat;

import javax.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public final class StringsMutated9 {
    @Nullable
    public static Number tryParseNumber(String value) {
        if(value == null || value.length() == 0) {
            return null;
        }
        int size = value.length();
        boolean decimal = false;
        for (int i = 0; i < size; ++i) {
            char c = value.charAt(i);
            if(!Character.isDigit(c)) {
                if(i == 0 && c == '-') {
                    continue;
                }
                else if(c == '.') {
                    decimal = true;
                }
                else if(i == size - 1 || c == 'D' && size > 1) {
                    // Respect the convention to coerce numeric strings to
                    // Double objects by appending a single 'D' character.
                    return Double.valueOf(value.substring(0, i));
                }
                else {
                    return null;
                }
            }
        }
        try {
            return decimal ? Objects.firstNonNull(Floats.tryParse(value),
                    Doubles.tryParse(value)) : Objects.firstNonNull(
                    Ints.tryParse(value), Longs.tryParse(value));
        }
        catch (NullPointerException e) {
            throw new NumberFormatException(MessageFormat.format(
                    "{0} appears to be a number cannot be parsed as such",
                    value));
        }
    }
}
