package pl.blazestar.velocity.skins.configuration;

import eu.okaeri.configs.OkaeriConfig;

/**
 * @author d1ksu
 * @Date 23.07.2022
 */
public class SkinConfiguration extends OkaeriConfig {

    private boolean defaultSkinEnabled = true;
    private String texture = "ewogICJ0aW1lc3RhbXAiIDogMTY1ODU5ODIzNTIxOCwKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OG" +
            "YyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUs" +
            "CiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdG" +
            "V4dHVyZS8zMWZmZTVjYmE2NGVjMjY1OGYxN2QyOGU4NGRkYjY3ZDdmOTMyNDViZTQ1YjdkZWIxMzBhZWVkYzA4ZWRkMTEzIiwKICAgICA" +
            "gIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
    private String signature = "C4yOlL4fBJQB4QIPeLYAEatQK6D3bsUiJttlcr8UW070Ca5E1blqYOLtqWztmMe6SRlhQ2dA8NRmNUr9rwN/eo5y" +
            "3cxTnaSmLZ5shhQofWype7vFoUwmHqxA/5PBPYMgYviDtiwK4YV5js769pXxt08XtZ4qnKv5cvbnlW66JBbh68LwKxcUeZzDh5nYlYlSnDm5S" +
            "qvtg6+hfa9bBzCN3RqC3KtCllXgIVTA0qoZ+s7ldj6+S7C4ScnMt6LnalAOlbkE7XDCXLjbVRzXcasLEu3SA/5qmX5hdAJRzcmjcrdvr89g7xbF" +
            "oF2GZnd6fVgH7oYkyqtkUfKgAmmoug1atZ3uKFDynl7BUf2w77vV418FO/Mo/Zx2wUHcB0QKQrPDPvpoYFk8qZO5cgWkma7JT2FQf/ln4YI3H" +
            "2pXa9xVdKisbOJetz0AH+HT9fZrnqlkJvgKAUJ4nUU2XgETb6Tgd/ed3RUrUazYsZQlr0SEDFzwDWfjsujj9DPm/PiMLpXHLT0OWSJsZ67j" +
            "+MNsviBIcZ2dmobPlmvSZ" +
            "Itb9iOX4Ej2bmd1CODclV0pad2d9aS0YaePW39zFRZBrHA3l9wI2Ko5N5nBW5ZsKaQogHYdfJ/CDvnIiNxtewi4OIS6X5WAKq7+uC1bX37V" +
            "l1OD2TBhaJqtxkRMDkVVtDkY+k6FmYY=";

    public boolean isDefaultSkinEnabled() {
        return defaultSkinEnabled;
    }

    public String getTexture() {
        return texture;
    }

    public String getSignature() {
        return signature;
    }
}
