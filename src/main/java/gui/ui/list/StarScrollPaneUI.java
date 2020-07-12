package gui.ui.list;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class StarScrollPaneUI extends BasicScrollBarUI {

    protected void installDefaults(JScrollPane scrollPane){

        LookAndFeel.installProperty(scrollPane, "opaque", Boolean.FALSE);

    }

    public static ComponentUI craeteUI(JComponent c){
        return new StarScrollPaneUI();
    }
}
