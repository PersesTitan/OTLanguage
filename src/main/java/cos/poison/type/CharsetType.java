package cos.poison.type;

import cos.poison.PoisonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public enum CharsetType {
    US_ASCII(StandardCharsets.US_ASCII),
    ISO_8859_1(StandardCharsets.ISO_8859_1),
    UTF_8(StandardCharsets.UTF_8),
    UTF_16BE(StandardCharsets.UTF_16BE),
    UTF_16LE(StandardCharsets.UTF_16LE),
    UTF_16(StandardCharsets.UTF_16);

    public final Charset charset;

    public static CharsetType getType(String type) {
        try {
            return CharsetType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw PoisonException.CHARSET_TYPE_ERROR.getThrow(type);
        }
    }
}
