package gui;
import Details.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Details.tester_details;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class maintenance_modify extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public maintenance_modify(int wo_id) throws Exception {
		setResizable(false);
		setTitle("Maintenance - Work Order ID #" + wo_id);
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		maintenance maint = new maintenance();
		
		JLabel lblNewLabel = new JLabel("Tester: " + maint.fetchMaintInfo(wo_id, "tester"));
		lblNewLabel.setBounds(10, 11, 200, 14);
		contentPanel.add(lblNewLabel);
		{
			JLabel lblPeripheral = new JLabel("Peripheral: " + maint.fetchMaintInfo(wo_id, "peripheral"));
			lblPeripheral.setBounds(215, 11, 209, 14);
			contentPanel.add(lblPeripheral);
		}
		{
			JLabel lblSubmitTime = new JLabel("Submit Time: " + maint.fetchMaintInfo(wo_id, "submitted_time"));
			lblSubmitTime.setBounds(10, 36, 200, 14);
			contentPanel.add(lblSubmitTime);
		}
		
		//Status
		{
			JLabel lblStatus = new JLabel("Status: ");
			lblStatus.setBounds(215, 36, 60, 14);
			contentPanel.add(lblStatus);
		}
		String[] statusOptions = {"Active", "Closed"};
		
		JComboBox statusCB = new JComboBox(statusOptions);
		statusCB.setBounds(261, 32, 82, 22);
		contentPanel.add(statusCB);
		String status = (String) statusCB.getItemAt(statusCB.getSelectedIndex());
		
		//Current Remarks
		{
			JTextArea currentRemarksTextArea = new JTextArea(maint.fetchMaintInfo(wo_id, "description"));
			currentRemarksTextArea.setEditable(false);
			currentRemarksTextArea.setBounds(10, 75, 414, 106);
			contentPanel.add(currentRemarksTextArea);
			currentRemarksTextArea.setLineWrap(true);
			currentRemarksTextArea.setWrapStyleWord(true);
		}
		{
			JLabel lblCurrentRemarks = new JLabel("Current Remarks: ");
			lblCurrentRemarks.setBounds(10, 58, 414, 14);
			contentPanel.add(lblCurrentRemarks);
		}
		
		//Update Remarks
		JTextArea updateDescriptionTextArea = new JTextArea();
		updateDescriptionTextArea.setBounds(10, 200, 414, 117);
		contentPanel.add(updateDescriptionTextArea);
		updateDescriptionTextArea.setLineWrap(true);
		updateDescriptionTextArea.setWrapStyleWord(true);
		
		{
			JLabel lblUpdateDescription = new JLabel("Update Remarks: ");
			lblUpdateDescription.setBounds(10, 186, 414, 14);
			contentPanel.add(lblUpdateDescription);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String description = updateDescriptionTextArea.getText();
							System.out.println(description + "\n" + status);
							if (description.equals("") && status.equals("Active"))
								dispose();
							else {
								maint.modify_description(wo_id, description);
								if (status.equals("Closed"))
									maint.set_status_closed(wo_id);
								dispose();
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				getRootPane().setDefaultButton(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							dispose();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
			}
		}
	}
}
