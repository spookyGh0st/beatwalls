package beatwalls

import org.junit.Test

import org.junit.Assert.*
import java.awt.TrayIcon

class MainKtTest {

    @Test
    fun displayTray() {
        beatwalls.displayTray("Build Failed",TrayIcon.MessageType.ERROR)
    }

    @Test
    fun displayMessage() {
        displayMessage("SOME INFO",TrayIcon.MessageType.INFO)
    }
}