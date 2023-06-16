package cos.poison.type;

import bin.parser.param.ParserParamItem;
import cos.poison.PoisonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum ResponseType {
    JS      ("application/x-javascript"),
    JSON    ("application/json"),
    PDF     ("application/pdf"),
    XML     ("application/xml"),
    CSS     ("text/css"),
    HTML    ("text/html"),
    IMAGE   ("image/jpeg"),
    SVG     ("image/svg+xml"),
    TEXT    ("text/plain")
    ;

    private final String mime;

    public static ResponseType getType(String type) {
        try {
            return ResponseType.valueOf(type.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw PoisonException.RESPONSE_TYPE_ERROR.getThrow(type);
        }
    }

    public byte[] getBody(String value) {
        return switch (this) {
            case TEXT -> value.getBytes(StandardCharsets.UTF_16);
            default -> value.getBytes(StandardCharsets.UTF_8);
        };
    }

    public static ResponseType getFileType(String fileName) {
        fileName = fileName.toLowerCase(Locale.ROOT);
        if (fileName.endsWith(".js")) return JS;
        else if (fileName.endsWith(".json")) return JSON;
        else if (fileName.endsWith(".pdf")) return PDF;
        else if (fileName.endsWith(".css")) return CSS;
        else if (fileName.endsWith(".xml")) return XML;
        else if (fileName.endsWith(".svg")
                || fileName.endsWith(".svgz")) return SVG;
        else if (fileName.endsWith(".html")
                || fileName.endsWith(".htm")) return HTML;
        else if (fileName.endsWith(".png")
                || fileName.endsWith(".jfif")
                || fileName.endsWith(".jfif-tbnl")
                || fileName.endsWith(".jpe")
                || fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")) return IMAGE;
        else return TEXT;
    }
}
