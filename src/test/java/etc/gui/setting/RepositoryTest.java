package etc.gui.setting;

import bin.exception.MatchException;
import bin.exception.VariableException;
import bin.token.MergeToken;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.STRING_VARIABLE;
import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public interface RepositoryTest extends MergeToken {
    String GUI = "ㄱㄹㄱ";
    String BUTTON = "ㅂㅌㅂ";
    String LABEL = "ㄹㅂㄹ";
    String PASSWORD_FIELD = "ㅌㅂㅌ";
    String TEXT_FIELD = "ㅌㅍㅌ";
    String PANEL = "ㅍㄴㅍ";
    String FRAME = "ㅍㄹㅍ";

    String ADD = "<<";
    String SET_MOVE = "ㅇㄷㅇ";
    String SET_SIZE = "ㅅㅇㅅ";
    String SET_TEXT = "ㅌㄱㅌ";
    String REMOVE = "ㅅㅈㅅ";
    String SET_VISIBLE = "ㅂㅈㅂ";
    String ACTIONS = "ㄷㅈㄷ";
    String SET_COLUMNS = "ㅋ<ㅋ";

    String GET_CONTAINS = "??";
    String GET_POSITION = "ㅇㅊㅇ";
    String GET_HEIGHT = "ㄴㅇㄴ";
    String GET_WIDTH = "ㄴㅂㄴ";
    String GET_TEXT = "ㅌㅅㅌ";
    String GET_X = "ㄱㄹㄱ";
    String GET_Y = "ㅅㄹㅅ";
    String GET_COLUMNS = "ㅋ>ㅋ";

    Map<String, Object> item = new HashMap<>();

    default void add(Object ob1, Object ob2) {
        if (ob1 instanceof JFrame frame) {
            if (ob2 instanceof Component com) frame.add(com);
            else if (ob2 instanceof PopupMenu pop) frame.add(pop);
        } else if (ob1 instanceof JPanel panel) {
            if (ob2 instanceof Component com) panel.add(com);
            else if (ob2 instanceof PopupMenu pop) panel.add(pop);
        }
    }

    default void setText(Object ob, String text) {
        if (ob instanceof JTextComponent component) component.setText(text);
        else if (ob instanceof JButton button) button.setText(text);
        else if (ob instanceof JLabel label) label.setText(text);
        else if (ob instanceof JFrame frame) frame.setTitle(text);
    }

    default String getText(Object ob) {
        if (ob instanceof JPasswordField pf) return String.valueOf(pf.getPassword());
        else if (ob instanceof JTextField textField) return textField.getText();
        else if (ob instanceof JButton button) return button.getText();
        else if (ob instanceof JLabel label) return label.getText();
        else if (ob instanceof JFrame frame) return frame.getTitle();
        else return "";
    }

    default void setSize(Object ob, int a, int b) {
        if (ob instanceof Component component) component.setSize(a, b);
    }

    default String getHeight(Object ob) {
        return ob instanceof JComponent component
                ? Integer.toString(component.getHeight())
                : null;
    }

    default String getWidth(Object ob) {
        return ob instanceof JComponent component
                ? Integer.toString(component.getWidth())
                : null;
    }

    default String getContains(Object ob, int a, int b) {
        return ob instanceof JComponent component
                ? (component.contains(a, b) ? TRUE : FALSE)
                : null;
    }

    default String getPosition(Object ob) {
        return ob instanceof JComponent component
                ? String.format("[%d, %d]", component.getX(), component.getY())
                : null;
    }

    default String getX(Object ob) {
        return ob instanceof JComponent component
                ? Integer.toString(component.getX())
                : null;
    }

    default String getY(Object ob) {
        return ob instanceof JComponent component
                ? Integer.toString(component.getY())
                : null;
    }

    default void setMove(Object ob, int a, int b) {
        if (ob instanceof JComponent component)
            component.setLocation(a, b);
    }

    default void remove(Object ob1, Object ob2) {
        if (ob1 instanceof Container container && ob2 instanceof Component component)
            container.remove(component);
    }

    default void setVisible(Object ob, boolean bool) {
        if (ob instanceof Component component) component.setVisible(bool);
    }

    default void setColumns(Object ob, int column) {
        if (ob instanceof JTextField field) field.setColumns(column);
    }

    default String getColumns(Object ob) {
        return ob instanceof JTextField field
                ? Integer.toString(field.getColumns())
                : null;
    }

    // loop : (test,1,15)   // name : 버튼1
    default void setAction(String loop, String name, LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String[] loops = getLoop(loop);
        // RETURN 값
        if (loops.length == 4) throw new MatchException().grammarError();
        else if (loops.length == 5) {
            if (!loops[3].equals(STRING_VARIABLE)) throw new VariableException().typeMatch();
        }

        if (item.get(name) instanceof JButton button) button.addActionListener(e -> {
            String variableName = loops.length == 5 ? loops[4] : null;
            try {
                if (variableName != null) {
                    variableDefineError(variableName, repositoryArray.get(0));
                    repositoryArray.get(0).get(STRING_VARIABLE).put(variableName, name);
                }
                start(loops[0], loops[1], loops[2], repositoryArray);
            } finally {
                if (variableName != null) repositoryArray.get(0).get(STRING_VARIABLE).remove(variableName);
            }
        });
    }
}
