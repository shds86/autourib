package Interface;

import java.io.Serializable;

public class main implements Serializable{
static MainFrame Frame;
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        Frame = new MainFrame();
        Frame.setVisible(true);
    }
}
