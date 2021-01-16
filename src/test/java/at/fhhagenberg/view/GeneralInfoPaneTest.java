package at.fhhagenberg.view;

import at.fhhagenberg.elevator.SystemStatus;
import at.fhhagenberg.elevator.view.GeneralInfoPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class GeneralInfoPaneTest {
    @Test
    void testSetSystemStatusConnected() {
        GeneralInfoPane generalInfoPane = new GeneralInfoPane();

        generalInfoPane.setSystemStatus(SystemStatus.CONNECTED);

        TextFlow textFlow = (TextFlow) generalInfoPane.getChildren().get(1);
        Text systemStatusText = (Text) textFlow.getChildren().get(0);
        assertEquals("connected", systemStatusText.getText());
        assertEquals(Color.GREEN, systemStatusText.getFill());
    }

    @Test
    void testSetSystemStatusConnecting() {
        GeneralInfoPane generalInfoPane = new GeneralInfoPane();

        generalInfoPane.setSystemStatus(SystemStatus.CONNECTING);

        TextFlow textFlow = (TextFlow) generalInfoPane.getChildren().get(1);
        Text systemStatusText = (Text) textFlow.getChildren().get(0);
        assertEquals("connecting", systemStatusText.getText());
        assertEquals(Color.RED, systemStatusText.getFill());
    }
}
