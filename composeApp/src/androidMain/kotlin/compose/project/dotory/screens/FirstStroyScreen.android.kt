package compose.project.dotory.screens

import compose.project.dotory.runPy


actual fun initLlama() {
    runPy(
        functionName = "init"
    )
}

actual fun runLlama(prompt: String, printer: (String) -> Unit) {
    runPy(
        functionName = "run_llama3",
        args = arrayOf(prompt, printer)
    )
}
