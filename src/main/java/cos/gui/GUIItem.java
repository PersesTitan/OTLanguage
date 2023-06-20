package cos.gui;

import bin.apply.item.Item;
import bin.apply.OSConsumer;
import lombok.RequiredArgsConstructor;
import work.LoopWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface GUIItem {
    final class ButtonItem extends JButton implements ComponentTool, Item {
        @Override
        public String toString() {
            return itemToString(GuiToken.GUI, GuiToken.BUTTON);
        }
    }

    final class CheckBoxItem extends JCheckBox implements ComponentTool, Item {
        @Override
        public String toString() {
            return itemToString(GuiToken.GUI, GuiToken.CHECK_BOX);
        }
    }

    final class PasswordFiledItem extends JPasswordField implements Item, ComponentTool {
        @Override
        public String toString() {
            return itemToString(GuiToken.GUI, GuiToken.PASSWORD_FILED);
        }
    }

    final class RadioButtonItem extends JRadioButton implements Item, ComponentTool {
        @Override
        public String toString() {
            return itemToString(GuiToken.GUI, GuiToken.RADIO_BUTTON);
        }
    }

    final class TextFieldItem extends JTextField implements Item, ComponentTool {
        @Override
        public String toString() {
            return itemToString(GuiToken.GUI, GuiToken.TEXT_FIELD);
        }
    }

    final class TextAreaItem extends TextArea implements ComponentTool, Item {
        @Override
        public Component add(Component component) {
            throw GuiException.DO_NOT_USE_TYPE.getThrow(GuiToken.ADD);
        }
        @Override
        public void addActionListener(ActionListener l) {
            throw GuiException.DO_NOT_USE_TYPE.getThrow(GuiToken.ADD_EVENT);
        }

        @Override
        public String toString() {
            return itemToString(GuiToken.GUI, GuiToken.TEXT_AREA);
        }
    }

    // create
    final class AddEvent extends LoopWork {
        public AddEvent() {
            super(GuiToken.GUI, false, 1, new String[] {GuiToken.EVENT});
        }

        @Override
        protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
            ((ComponentTool) klass).addActionListener(consumer::accept);
        }
    }

    @RequiredArgsConstructor
    final class ActionEventItem implements Item {
        private final ActionEvent event;

        public Object getSource() {
            return event.getSource();
        }

        public String getActionCommand() {
            return event.getActionCommand();
        }

        @Override
        public String toString() {
            return this.itemToString(GuiToken.EVENT);
        }
    }
}
