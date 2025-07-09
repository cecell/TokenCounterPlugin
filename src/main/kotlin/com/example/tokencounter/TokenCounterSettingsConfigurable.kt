
package com.example.tokencounter

import com.intellij.openapi.options.Configurable
import javax.swing.*
import com.intellij.openapi.ui.ComboBox
//import com.example.tokencounter.TokenizerModel
//import com.example.tokencounter.TokenCounterSettings

class TokenCounterSettingsConfigurable : Configurable {
    private val comboBox = ComboBox<String>().apply {
        TokenizerModel.entries.forEach { addItem(it.displayName) }
    }

    override fun createComponent(): JComponent {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.add(JLabel(TokenCounterBundle.message("token.counter.settings.model.label")))
        panel.add(comboBox)
        comboBox.selectedItem = TokenCounterSettings.instance.selectedModel.displayName
        return panel
    }

    override fun isModified(): Boolean {
        return comboBox.selectedItem != TokenCounterSettings.instance.selectedModel.displayName
    }

    override fun apply() {
        val selectedName = comboBox.selectedItem as String
        TokenCounterSettings.instance.selectedModel =
            TokenizerModel.entries.first { it.displayName == selectedName }
    }

    override fun reset() {
        comboBox.selectedItem = TokenCounterSettings.instance.selectedModel.displayName
    }

    override fun getDisplayName(): String = TokenCounterBundle.message("token.counter.settings.displayName")
}