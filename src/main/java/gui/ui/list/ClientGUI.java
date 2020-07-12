package gui.ui.list;

import gui.ui.combobox.RoundBorder;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JPanel {

    Color text = new Color(76, 196, 40);
    Color heightTx = new Color(175, 250, 110);

    JList list = new JList();
    DefaultListModel model = new DefaultListModel();
    JTextField textField = new JTextField();
    JButton create = new JButton("Create");
    JButton exit = new JButton("Exit");

    public ClientGUI(){
        super();
        setLocation(10,10);
        setSize(400, 200);
        setLayout(new BorderLayout());

        for (int i = 0; i < 15; i++){
            model.addElement("sanyun" + i);
        }

        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new StarListCellRender());
        list.setVisibleRowCount(5);
        list.setSelectedIndex(0);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.getVerticalScrollBar().setUI((StarScrollBarUI)StarScrollBarUI.createUI(list));

        scrollPane.setBorder(new RoundBorder(Color.RED));
//		scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI((StarScrollBarUI)StarScrollBarUI.createUI(list));

        textField.setBorder(BorderFactory.createLineBorder(Color.red));
        textField.setForeground(text);
        textField.setCaretColor(heightTx);
        textField.setBackground(Color.BLACK);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(create);
        buttonPane.add(exit);

        add(textField, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);

    }

    public static void main(String[] args) throws Exception, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame();
        JPanel contentPanel = new JPanel();
        frame.setContentPane(contentPanel);
        contentPanel.add(new ClientGUI());
        contentPanel.setLayout(null);
//		contentPanel.setBackground(Color.BLACK);
        //contentPanel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setVisible(true);


    }

}
