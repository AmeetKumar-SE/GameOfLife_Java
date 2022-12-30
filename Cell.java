import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Cell extends JButton implements ActionListener {

    boolean status;
    boolean nextStatus;

    public Cell() {
        status = false;
        nextStatus = false;
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        changeStatus();
    }

    public boolean getStatus() {
        return status;
    }

    public void changeStatus() {
        if (this.getStatus() == false) {
            this.status = true;
            this.setBackground(Color.RED);
        } else {
            this.status = false;
            this.setBackground(null);
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
        if (status) {
            this.setBackground(Color.RED);
        } else {
            this.setBackground(null);
        }
    }

    public void setNextStatus(boolean nextStatus) {
        this.nextStatus = nextStatus;
    }

    public boolean getNextStatus() {
        return nextStatus;
    }

}
