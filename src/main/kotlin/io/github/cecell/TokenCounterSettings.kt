package io.github.cecell

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service

enum class TokenizerModel(val displayName: String) {
    GPT35("GPT-3.5") {
        override fun tokenizer() = DefaultTokenizer()
    },
    GPT4("GPT-4") {
        override fun tokenizer() = DefaultTokenizer()
    },
    CLAUDE("Claude") {
        override fun tokenizer() = DefaultTokenizer()
    };

    abstract fun tokenizer(): Tokenizer
}

interface Tokenizer {
    fun countTokens(text: String): Int
}

class DefaultTokenizer : Tokenizer {
    override fun countTokens(text: String): Int {
        return text.trim()
            .split(Regex("""\s+|(?=\p{Punct})|(?<=\p{Punct})""").toPattern().toString())
            .filter { it.isNotBlank() }
            .size
    }
}

@Service
@State(
    name = "TokenCounterSettings",
    storages = [Storage("tokenCounterSettings.xml")]
)
class TokenCounterSettings : PersistentStateComponent<TokenCounterSettings.State> {
    data class State(
        var selectedModelName: String = TokenizerModel.GPT35.name
    )

    private var myState = State()

    var selectedModel: TokenizerModel
        get() = TokenizerModel.valueOf(myState.selectedModelName)
        set(value) {
            myState.selectedModelName = value.name
        }

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    companion object {
        val instance: TokenCounterSettings
            get() = service()
    }
}