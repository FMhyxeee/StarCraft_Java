package gui.ui.list;

import javax.swing.*;
import java.awt.*;

public class StarListCellRender extends DefaultListCellRenderer {
    Color text  = new Color(76,196,40);
    Color heightTx  = new Color(175,250,110);
    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        setComponentOrientation(list.getComponentOrientation());

//		        Color bg = null;
//		        Color fg = null;

        setBackground(list.getBackground());

        if (isSelected) {
            //setBackground(bg == null ? list.getSelectionBackground() : bg);
            setForeground(heightTx);
        }
        else {
            //setBackground(list.getBackground());
            setForeground(text);
        }

        if (value instanceof Icon) {
            setIcon((Icon)value);
            setText("");
        }
        else {
            setIcon(null);
            setText((value == null) ? "" : value.toString());
        }

        setEnabled(list.isEnabled());
        setFont(list.getFont());



        return this;
    }
}
