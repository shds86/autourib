/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import java.io.Serializable;

/**
 *
 * @author support
 */
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
