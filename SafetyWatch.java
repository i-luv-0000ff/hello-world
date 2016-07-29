import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;


public class SafetyWatch {

	private JFrame frame;
	JLabel lblDanger;
	JToggleButton tglbtnPanic;
	static SafetyWatch window = new SafetyWatch();
	
	private final Action action = new PanicNow();
	private final static UpdateServer updateServer = new UpdateServer();
	private final Action action_1 = new MarkDangerZone();
	static UpdateDangerZone updateDangerZone = window.new UpdateDangerZone();
	private final Action action_2 = new Reset();
	private JLabel label;
	private JLabel label_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(updateServer, 0, 5000);
		timer.schedule(updateDangerZone, 0, 100);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public class UpdateDangerZone extends TimerTask {

		@Override
		public void run() {
			String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
			String timeStamp1 = new SimpleDateFormat("dd-MMMM-yyyy").format(new java.util.Date());
			label.setText(timeStamp);
			label_1.setText(timeStamp1);
			if(updateServer.isDanger())
				lblDanger.setText("Danger Zone!!!!!!!!!!!!!!!!!");
			else if(!"Danger zone updated!".equals(lblDanger.getText()))
				lblDanger.setText("");
		}
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public SafetyWatch() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tglbtnPanic = new JToggleButton("Panic");
		tglbtnPanic.setAction(action);
		tglbtnPanic.setBounds(75, 44, 121, 23);
		frame.getContentPane().add(tglbtnPanic);
		
		JButton tglbtnMarkSuspecious = new JButton("Mark Suspecious");
		tglbtnMarkSuspecious.setAction(action_1);
		tglbtnMarkSuspecious.setBounds(229, 44, 144, 23);
		frame.getContentPane().add(tglbtnMarkSuspecious);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setAction(action_2);
		btnReset.setBounds(167, 169, 91, 23);
		frame.getContentPane().add(btnReset);
		
		lblDanger = new JLabel("");
		lblDanger.setBounds(155, 83, 194, 14);
		frame.getContentPane().add(lblDanger);
		
		label = new JLabel("");
		label.setBounds(191, 133, 144, 14);
		frame.getContentPane().add(label);
		
		label_1 = new JLabel("");
		label_1.setBounds(178, 108, 137, 14);
		frame.getContentPane().add(label_1);
	}
	private class PanicNow extends AbstractAction {
		public PanicNow() {
			putValue(NAME, "Panic");
			putValue(SHORT_DESCRIPTION, "Alert Emergency");
		}
		public void actionPerformed(ActionEvent e) {
			updateServer.updateValues(UpdateServer.coordinate, true, updateServer.isDanger(), updateServer.getDangerZone());
			updateServer.run();
		}
	}
	private class MarkDangerZone extends AbstractAction {
		public MarkDangerZone() {
			putValue(NAME, "Mark Danger Zone");
			putValue(SHORT_DESCRIPTION, "Mark this area as a danger zone");
		}
		public void actionPerformed(ActionEvent e) {
			updateServer.updateValues(UpdateServer.coordinate, updateServer.isPanic(), updateServer.isDanger(), UpdateServer.coordinate);
			lblDanger.setText("Danger zone updated!");
		}
	}
	private class Reset extends AbstractAction {
		public Reset() {
			putValue(NAME, "Reset");
			putValue(SHORT_DESCRIPTION, "Reset everything!");
		}
		public void actionPerformed(ActionEvent e) {
			tglbtnPanic.setSelected(false);
			updateServer.updateValues(UpdateServer.coordinate, false, updateServer.isDanger(), updateServer.getDangerZone());
			
		}
	}
}
