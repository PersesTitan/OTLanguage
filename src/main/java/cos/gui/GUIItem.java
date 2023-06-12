package cos.gui;

import bin.apply.item.Item;
import bin.apply.repository.function.OSConsumer;
import lombok.RequiredArgsConstructor;
import work.LoopWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface GUIItem {
    class ButtonItem extends JButton implements ComponentTool, Item {
        @Override
        public String toString() {
            return toString(GuiToken.GUI, GuiToken.BUTTON);
        }
    }

    class CheckBoxItem extends JCheckBox implements ComponentTool, Item {
        @Override
        public String toString() {
            return toString(GuiToken.GUI, GuiToken.CHECK_BOX);
        }
    }

    class PasswordFiledItem extends JPasswordField implements Item, ComponentTool {
        @Override
        public String toString() {
            return toString(GuiToken.GUI, GuiToken.PASSWORD_FILED);
        }
    }

    class RadioButtonItem extends JRadioButton implements Item, ComponentTool {
        @Override
        public String toString() {
            return toString(GuiToken.GUI, GuiToken.RADIO_BUTTON);
        }
    }

    class TextFieldItem extends JTextField implements Item, ComponentTool {
        @Override
        public String toString() {
            return toString(GuiToken.GUI, GuiToken.TEXT_FIELD);
        }
    }

    class TextAreaItem extends TextArea implements ComponentTool, Item {
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
            return toString(GuiToken.GUI, GuiToken.TEXT_AREA);
        }
    }

    // create
    class AddEvent extends LoopWork {
        public AddEvent() {
            super(GuiToken.GUI, false, 1, new String[] {GuiToken.EVENT});
        }

        @Override
        protected void loopItem(Object klass, Object[] params, OSConsumer consumer) {
            ((ComponentTool) klass).addActionListener(consumer::accept);
        }
    }

    @RequiredArgsConstructor
    class ActionEventItem implements Item {
        private final ActionEvent event;

        public Object getSource() {
            return event.getSource();
        }

        public String getActionCommand() {
            return event.getActionCommand();
        }

        @Override
        public String toString() {
            return this.toString(GuiToken.EVENT);
        }
    }
}
