package com.queue.desktop;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import com.queue.model.QueueItem;
import com.queue.service.QueueService;

public class PatientPanel extends JPanel {
    private final JTextField nameField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JComboBox<String> deptCombo = new JComboBox<>(new String[]{
            "Emergency","Cardiology","Neurology","Orthopedics","Pediatrics","Oncology",
            "Radiology","Laboratory","Pharmacy","General Medicine","Surgery","Dermatology",
            "Ophthalmology","ENT","Gynecology","Urology","Psychiatry","Physical Therapy",
            "Billing","Appointment"
    });
    private final JButton joinBtn = new JButton("Join Hospital Queue");
    private final JButton refreshBtn = new JButton("Refresh Status");
    private final JLabel statusLabel = new JLabel("Status: -");

    private final QueueService queueService;

    public PatientPanel(QueueService queueService) {
        this.queueService = queueService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(labeled("Full Name", nameField));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(labeled("Phone Number", phoneField));
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(labeled("Department", deptCombo));
        add(Box.createRigidArea(new Dimension(0, 12)));

        joinBtn.addActionListener(this::joinQueue);
        refreshBtn.addActionListener(this::refreshStatus);

        add(joinBtn);
        add(Box.createRigidArea(new Dimension(0, 8)));
        add(refreshBtn);
        add(Box.createRigidArea(new Dimension(0, 12)));
        add(statusLabel);
    }

    private Component labeled(String label, Component field) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.add(new JLabel(label));
        if (field instanceof JTextField) {
            ((JTextField) field).setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        } else if (field instanceof JComboBox) {
            ((JComboBox<?>) field).setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        }
        row.add(field);
        return row;
    }

    private void joinQueue(ActionEvent e) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String dept = String.valueOf(deptCombo.getSelectedItem());
        if (name.isEmpty() || phone.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }
        try {
            QueueItem item = queueService.addToQueue(name, phone, dept);
            JOptionPane.showMessageDialog(this, "Joined queue! Position #" + item.getPosition());
            nameField.setText("");
            phoneField.setText("");
            refreshStatus(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refreshStatus(ActionEvent e) {
        try {
            int size = queueService.getQueueSize();
            QueueItem servingItem = queueService.getCurrentServing();
            String serving = servingItem == null ? "None" : servingItem.getName() + " (" + servingItem.getService() + ")";
            StringBuilder queueSb = new StringBuilder();
            var list = queueService.getQueue();
            int limit = Math.min(3, list.size());
            for (int i = 0; i < limit; i++) {
                QueueItem it = list.get(i);
                if (i > 0) queueSb.append(", ");
                queueSb.append("#").append(it.getPosition()).append(" ").append(it.getName());
            }
            String html = "<html>" +
                    "Waiting: " + size +
                    " &nbsp;|&nbsp; Serving: " + serving +
                    (queueSb.length() > 0 ? "<br/>Next: " + queueSb : "") +
                    "</html>";
            statusLabel.setText(html);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}


