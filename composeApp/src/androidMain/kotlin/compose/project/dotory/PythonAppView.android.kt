package compose.project.dotory

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform


@Composable
actual fun PythonLauncher(
    content: @Composable () -> Unit
) {
    if (!Python.isStarted()) {
        val platform = AndroidPlatform(LocalContext.current)
        platform.redirectStdioToLogcat()
        Python.start(platform)
        println("PythonLauncher: Python is started...")
    }
    //val runtime = Python.getInstance().getModule("pythonx.compose.runtime")
    //val composable = runtime["Composable"]
    /*if (composable != null) {
        composable.callAttr("register_composer", currentComposer)
        println("PythonLauncher: Composer is registered.")
    } else {
        throw RuntimeException("PythonLauncher: Failed to register Composer. Cannot find Composable class in python runtime.")
    } */

    content()
}

private fun getPyModule(name: String): PyObject {
    if (!Python.isStarted()) throw IllegalStateException("Python is not started. Please run PythonLauncher Composable first.")
    return Python.getInstance().getModule(name)
}

val sys: PyObject
    get() = getPyModule("sys")
val version: String
    get() = sys["version"].toString()
val os: PyObject
    get() = getPyModule("os")

@Composable
actual fun PythonWidget(
    composableName: String,
    modifier: Modifier,
    shape: Shape,
    color: Color,
    contentColor: Color,
    tonalElevation: Dp,
    shadowElevation: Dp,
    border: BorderStroke?,
    moduleName: String,
    content: @Composable (args: Array<Any>) -> Unit
) {
    val module = getPyModule(moduleName)
    Surface(modifier, shape, color, contentColor, tonalElevation, shadowElevation, border) {
        module.callAttr(composableName, content)
    }
}

@Composable
actual fun PythonAppView(
    modifier: Modifier,
    shape: Shape,
    color: Color,
    contentColor: Color,
    tonalElevation: Dp,
    shadowElevation: Dp,
    border: BorderStroke?
) {
    val module = getPyModule(moduleNamePreset)
    Surface(modifier, shape, color, contentColor, tonalElevation, shadowElevation, border) {
        module.callAttr("App")
    }
}

fun runPy(functionName: String, moduleName: String = moduleNamePreset, vararg args: Any): PyObject? {
    val module = getPyModule(moduleName)
    return when {
        args.isEmpty() -> module.callAttr(functionName)
        else -> module.callAttr(functionName, *(args.toList().toTypedArray()))
    }
}
