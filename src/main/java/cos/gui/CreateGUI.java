package cos.gui;

import bin.apply.mode.DebugMode;
import bin.exception.FileException;
import bin.token.KlassToken;
import bin.token.SepToken;
import work.CreateWork;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

class CreateGUI extends CreateWork<ComponentTool> implements GuiToken {
    private static boolean USE_GUI = true;

    public CreateGUI() {
        super(ComponentTool.class, KlassToken.STRING_VARIABLE);
    }

    @Override
    protected ComponentTool createItem(Object[] params) {
        if (CreateGUI.USE_GUI) {
            CreateGUI.USE_GUI = false;
            System.setProperty("java.awt.headless", "false");
            String iconPath = DebugMode.isDevelopment()
                    ? SepToken.getPath(SepToken.getResource("gui"), "icon.otlm")
                    : SepToken.getPath(SepToken.INSTALL_PATH, SepToken.MODULE, "gui", "icon.otlm");
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(iconPath))) {
                Taskbar.getTaskbar().setIconImage(((ImageIcon) inputStream.readObject()).getImage());
            } catch (IOException | ClassNotFoundException e) {
                throw FileException.CREATE_ICON_ERROR.getThrow(null);
            }
        }

        return switch (params[0].toString()) {
            case BUTTON -> new GUIItem.ButtonItem();
            case CHECK_BOX -> new GUIItem.CheckBoxItem();
            case RADIO_BUTTON -> new GUIItem.RadioButtonItem();
            case TEXT_FIELD -> new GUIItem.TextFieldItem();
            case PASSWORD_FILED -> new GUIItem.PasswordFiledItem();
            case TEXT_AREA -> new GUIItem.TextAreaItem();
            default -> throw GuiException.DO_NOT_SUPPORT_TYPE.getThrow(params[0]);
        };
    }
}
