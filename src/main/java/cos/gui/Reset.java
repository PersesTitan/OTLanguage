package cos.gui;

import bin.apply.Repository;
import bin.exception.VariableException;
import bin.token.KlassToken;
import work.ResetWork;

public class Reset implements ResetWork, Repository, GuiToken {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        checkModuleError("color");

        createWorks.put(GUI, new CreateGUI());
        create(EVENT, GUIItem.ActionEventItem.class,
                v -> {throw VariableException.DO_NOT_CREATE_KLASS.getThrow(EVENT);});

        loopWorks.put(GUI, ADD_EVENT, new GUIItem.AddEvent());

        methodWorks.add(EVENT, GET_SOURCE, GUIItem.ActionEventItem::getSource, KlassToken.SYSTEM);
        methodWorks.add(EVENT, GET_TEXT, GUIItem.ActionEventItem::getActionCommand, s);

        methodWorks.add(GUI, ADD, GUI, ComponentTool::add);
        methodWorks.add(GUI, GET_LOCATION, ComponentTool::getLocationList, li);

        methodWorks.add(GUI, GET_VISIBLE, ComponentTool::isVisible, b);
        methodWorks.add(GUI, GET_ENABLE, ComponentTool::isEnabled, b);
        methodWorks.add(GUI, GET_BG, ComponentTool::getBG, COLOR);
        methodWorks.add(GUI, GET_SIZE, ComponentTool::getSizeList, li);
        methodWorks.add(GUI, GET_WIDTH, ComponentTool::getWidth, i);
        methodWorks.add(GUI, GET_HEIGHT, ComponentTool::getHeight, i);
        methodWorks.add(GUI, GET_X, ComponentTool::getX, i);
        methodWorks.add(GUI, GET_Y, ComponentTool::getY, i);
        methodWorks.add(GUI, GET_TEXT, ComponentTool::getText, s);
        methodWorks.add(GUI, GET_NAME, ComponentTool::getName, s);

        methodWorks.add(GUI, SET_VISIBLE, b, ComponentTool::setVisible);
        methodWorks.add(GUI, SET_ENABLE, b, ComponentTool::setEnabled);
        methodWorks.add(GUI, SET_BG, COLOR, ComponentTool::setBG);
        methodWorks.add(GUI, SET_SIZE, i, i, ComponentTool::setSize);
        methodWorks.add(GUI, SET_WIDTH, i, ComponentTool::setWidth);
        methodWorks.add(GUI, SET_HEIGHT, i, ComponentTool::setHeight);
        methodWorks.add(GUI, SET_X, i, ComponentTool::setX);
        methodWorks.add(GUI, SET_Y, i, ComponentTool::setY);
        methodWorks.add(GUI, SET_TEXT, i, ComponentTool::setText);
        methodWorks.add(GUI, SET_NAME, i, ComponentTool::setName);
    }
}
