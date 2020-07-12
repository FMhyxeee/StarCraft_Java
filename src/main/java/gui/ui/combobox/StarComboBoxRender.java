package gui.ui.combobox;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class StarComboBoxRender extends BasicComboBoxRenderer {

    Color bg = new Color(0, 0, 50);
    Color text = new Color(76, 196, 40);
    Color heighTx = new Color(175, 250, 110);

    public StarComboBoxRender(){
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        setBackground(bg);
        if (isSelected){
            setForeground(heighTx);
        }else {
            setForeground(text);
        }

        setText((value == null) ? "" : value.toString());
        return this;
    }

}
