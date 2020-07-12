package gui.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.*;

public class StarComboPopup extends BasicComboPopup {


    public StarComboPopup(JComboBox combo) {
        super(combo);
    }

    @Override
    protected void configurePopup() {
        super.configurePopup();
        setBorder(new RoundBorder(Color.red));
    }
}
