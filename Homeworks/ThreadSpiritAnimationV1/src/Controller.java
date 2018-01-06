import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Write a description of class Controller here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Controller {
	private View viewer;
	private Spirit spirit;
	private ArrayList<Spirit> roles = new ArrayList<>();

	public Controller(View v) {
		viewer = v;
	}

	public void notify(State state) {
		viewer.refresh(state);
	}

	public void move(Spirit sp, ActionType act, DIR4 dir) {
		sp.move(act, dir);// let sp walk
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

	static void createAndShowGUI() {

		final View view = new View(); // for used in anonymous class
		Controller ctl = new Controller(view);
		view.setController(ctl);

		JFrame win = new JFrame("Control Spirit");

		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				view.requestFocusInWindow(); // it is necessary to keep the keyboard event can be accept
			}

			public void windowOpened(WindowEvent e) {
				view.requestFocusInWindow(); // it is necessary to keep the keyboard event can be accept
			}
		});
		win.setContentPane(view);
		win.setSize(1000, 1000);
		win.validate();

		win.setVisible(true);
		view.requestFocusInWindow();

		ctl.prepare();
	}

	private void prepare() {
		// prepare the roles
		roles.add(new Spirit(this)); // add the player
		for (int i = 0; i < 20; i++) {
			// <------- Add Red Spirits here
			roles.add(new FoodSpirit(this));
		}
	}

	

}
