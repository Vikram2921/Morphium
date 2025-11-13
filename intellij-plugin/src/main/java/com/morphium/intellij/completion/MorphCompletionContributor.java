package com.morphium.intellij.completion;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.morphium.intellij.MorphLanguage;
import org.jetbrains.annotations.NotNull;
public class MorphCompletionContributor extends CompletionContributor {
    public MorphCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withLanguage(MorphLanguage.INSTANCE),
            new CompletionProvider<CompletionParameters>() {
                @Override protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                    String[] keywords = {"function", "let", "global", "if", "else", "for", "while", "return", "break", "continue", "import", "as", "export"};
                    for (String kw : keywords) result.addElement(LookupElementBuilder.create(kw).bold());
                }
            });
    }
}
