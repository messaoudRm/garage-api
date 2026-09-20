package library;

/** Redimensionne une étagère. Cette signature ne change pas. */
public final class Sizes {

    private Sizes() {
    }

    public static int resizeTo5By4(Rectangle shelf) {
        shelf.setWidth(5);
        shelf.setHeight(4);
        return shelf.area();
    }
}
