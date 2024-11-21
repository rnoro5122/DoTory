package io.rnoro.dotory.presentation.screens.fairyTale

import io.rnoro.dotory.runPy

actual fun initLlama() {
    runPy(
        functionName = "init"
    )
}

actual fun runLlama(prompt: String, reset: Boolean, printer: (String) -> Unit) {
    runPy(
        functionName = "run_llama3",
        args = arrayOf(prompt, printer, reset)
    )
}