package com.morphium.intellij.highlight;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.Icon;
import java.util.Map;
public class MorphColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
        new AttributesDescriptor("Keyword", MorphSyntaxHighlighter.KEYWORD),
        new AttributesDescriptor("String", MorphSyntaxHighlighter.STRING),
        new AttributesDescriptor("Number", MorphSyntaxHighlighter.NUMBER),
        new AttributesDescriptor("Comment", MorphSyntaxHighlighter.COMMENT),
        new AttributesDescriptor("Operator", MorphSyntaxHighlighter.OPERATOR),
        new AttributesDescriptor("Identifier", MorphSyntaxHighlighter.IDENTIFIER)
    };
    @Nullable @Override public Icon getIcon() { return null; }
    @NotNull @Override public SyntaxHighlighter getHighlighter() { return new MorphSyntaxHighlighter(); }
    @NotNull @Override public String getDemoText() {
        return "// Morphium file\nimport \"math.morph\" as math;\nfunction add(x, y) {\n    return x + y;\n}\nlet result = add(10, 20);";
    }
    @Nullable @Override public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() { return null; }
    @NotNull @Override public AttributesDescriptor[] getAttributeDescriptors() { return DESCRIPTORS; }
    @NotNull @Override public ColorDescriptor[] getColorDescriptors() { return ColorDescriptor.EMPTY_ARRAY; }
    @NotNull @Override public String getDisplayName() { return "Morphium"; }
}
