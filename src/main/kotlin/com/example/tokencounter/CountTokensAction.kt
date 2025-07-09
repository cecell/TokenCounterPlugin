
package com.example.tokencounter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
//import com.intellij.openapi.editor.Editor
//import com.intellij.psi.PsiFile
//import com.jetbrains.python.psi.PyFile



class CountTokensAction : AnAction(
    { TokenCounterBundle.message("count.tokens.action.text") },
    { TokenCounterBundle.message("count.tokens.action.description") }
) {
    override fun update(e: AnActionEvent) {
        // Enable the action if there is selected text
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor?.selectionModel?.hasSelection() == true
    }

    override fun actionPerformed(e: AnActionEvent) {
        // Get the editor and selected text
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val sel = editor.selectionModel.selectedText ?: ""

        if (sel.isBlank()) {
            Messages.showInfoMessage(
                e.project,
                TokenCounterBundle.message("no.text.selected"),
                TokenCounterBundle.message("token.count.title")
            )
            return
        }

        // Get the tokenizer for the selected model
        val tokenizer = TokenCounterSettings.instance.selectedModel.tokenizer()
        val tokenCount = tokenizer.countTokens(sel)

        // Show the token count in a popup
        Messages.showInfoMessage(
            e.project,
            TokenCounterBundle.message(
                "token.count.result",
                tokenCount,
                TokenCounterSettings.instance.selectedModel.displayName
            ),
            TokenCounterBundle.message("token.count.title")
        )
    }
}