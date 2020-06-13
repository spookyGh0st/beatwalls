package beatwalls

import kotlinx.coroutines.ExperimentalCoroutinesApi
import mu.KotlinLogging
import java.awt.*
import java.awt.TrayIcon.MessageType
import kotlin.system.exitProcess


internal val logger = KotlinLogging.logger("beatwalls")

@ExperimentalCoroutinesApi
suspend fun main(args: Array<String>) {
    GlobalConfig(args)
    update()
    run()
    runOnChange { run() }
}


fun errorExit(e: Exception? = null, msg: () -> Any): Nothing {
    if (e != null) {
        logger.info { "Exeption" }
        logger.error { e.message }
        logger.error { e.stackTrace.toList().toString() }
    }
    logger.error {
        """
            
  _____ ____  ____   ___  ____  
 | ____|  _ \|  _ \ / _ \|  _ \ 
 |  _| | |_) | |_) | | | | |_) |
 | |___|  _ <|  _ <| |_| |  _ < 
 |_____|_| \_\_| \_\\___/|_| \_\
        """.trimIndent()
    }
    logger.error { msg.invoke() }
    logger.info("PLEASE FIX THE ERROR AND RESTART THE PROGRAM")
    logger.info("if you think this is a bug, let me know on discord or github")
    displayTray("Build Failed", MessageType.ERROR)
    readLine()
    exitProcess(-1)
}

fun displayMessage(message: String, messageType: MessageType){
    try {
        displayTray(message, messageType)
        playSystemSound()
    }catch (e:Exception){
        logger.error { "failed to display message" }
    }
}

fun displayTray(message: String, messageType: MessageType) { //Obtain only one instance of the SystemTray object
    val tray = SystemTray.getSystemTray()
    val image: Image = Toolkit.getDefaultToolkit().createImage("icon.png")
    val trayIcon = TrayIcon(image, "Tray Demo")
    trayIcon.isImageAutoSize = true
    trayIcon.toolTip = "System tray icon demo"
    tray.add(trayIcon)
    trayIcon.displayMessage("Beatwalls", message, messageType)
}

fun playSystemSound(){
    Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
}


