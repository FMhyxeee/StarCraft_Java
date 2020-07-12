package gui.ui.combobox;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class StarComboPopup extends BasicComboPopup {

    public StarComboPopup(JComboBox comboBox) {
        super(comboBox);
    }

    @Override
    protected void configurePopup(){
        super.configurePopup();
        setBorder(new RoundBorder(Color.red));
    }
}
