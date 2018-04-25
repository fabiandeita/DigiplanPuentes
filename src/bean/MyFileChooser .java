package bean;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;



class MyFileChooser extends JFileChooser {
  public JDialog createDialog(Component parent) throws HeadlessException {
    return super.createDialog(parent);
  }
}