package cos.gui;

import bin.apply.item.Item;
import bin.apply.OSConsumer;
import lombok.RequiredArgsConstructor;
import work.LoopWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface GUIItem extends GuiToken {
    // create item button
    final class ButtonItem extends JButton implements ComponentTool, Item {
        @Override public String toString() {return itemToString(GUI, BUTTON);}
    }

    // create item checkBox
    final class CheckBoxItem extends JCheckBox implements ComponentTool, Item {
        @Override public String toString() {return itemToString(GUI, CHECK_BOX);}
    }

    // create item passwordFiled
    final class PasswordFiledItem extends JPasswordField implements ComponentTool, Item {
        @Override public String toString() {return itemToString(GUI, PASSWORD_FILED);}
    }

    // create item radioButton
    final class RadioButtonItem extends JRadioButton implements ComponentTool, Item {
        @Override public String toString() {return itemToString(GUI, RADIO_BUTTON);}
    }

    // create item textField
    final class TextFieldItem extends JTextField implements ComponentTool, Item {
        @Override public String toString() {return itemToString(GUI, TEXT_FIELD);}
    }

    // create item textArea
    final class TextAreaItem extends JTextArea implements ComponentTool, Item {
        @Override public void addActionListener(ActionListener l) {
            throw GuiException.DO_NOT_USE_TYPE.getThrow(ADD_EVENT);}
        @Override public String toString() {return itemToString(GUI, TEXT_AREA);}
    }

    // create item frame
    final class FrameItem extends JFrame implements ComponentTool, Item {
        public FrameItem() throws HeadlessException {super(); setDefaultCloseOperation(EXIT_ON_CLOSE);}
        @Override public String getText() {return super.getTitle();}
        @Override public void setText(String text) {super.setTitle(text);}
        @Override public void addActionListener(ActionListener l) {
            throw GuiException.DO_NOT_USE_TYPE.getThrow(FRAME);}
        @Override public String toString() {return itemToString(GUI, FRAME);}
    }

    // create
    final class AddEvent extends LoopWork {
        public AddEvent() {super(GUI, false, 1, new String[] {EVENT});}
        @Override protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
            ((ComponentTool) klass).addActionListener(consumer::accept);
        }
    }

    @RequiredArgsConstructor
    final class ActionEventItem implements Item {
        private final ActionEvent event;
        public Object getSource() {return event.getSource();}
        public String getActionCommand() {return event.getActionCommand();}
        @Override public String toString() {return this.itemToString(EVENT);}
    }
}
