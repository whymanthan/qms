package com.queue.desktop;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import com.queue.model.QueueItem;
import com.queue.service.QueueService;

public class AdminPanel extends JPanel {
    private final QueueService queueService;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Phone","Dept","Position"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JButton refreshBtn = new JButton("Refresh");
    private final JButton callNextBtn = new JButton("Call Next Patient");
    private final JButton removeBtn = new JButton("Remove Selected");
    private final JButton clearBtn = new JButton("Clear Queue");
    private final JTextField statusField = new JTextField();

    public AdminPanel(QueueService queueService) {
        this.queueService = queueService;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));
        add(Box.createRigidArea(new Dimension(0, 8)));

        refreshBtn.addActionListener(this::refresh);
        callNextBtn.addActionListener(this::callNext);
        removeBtn.addActionListener(this::removeSelected);
        clearBtn.addActionListener(this::clearQueue);

        JPanel buttons = new JPanel();
        buttons.add(refreshBtn);
        buttons.add(callNextBtn);
        buttons.add(removeBtn);
        buttons.add(clearBtn);
        add(buttons);

        statusField.setEditable(false);
        add(new JLabel("Status:"));
        add(statusField);

        refresh(null);
    }

    private void refresh(ActionEvent e) {
        try {
            var list = queueService.getQueue();
            model.setRowCount(0);
            for (QueueItem it : list) {
                model.addRow(new Object[]{it.getId(), it.getName(), it.getPhoneNumber(), it.getService(), it.getPosition()});
            }
            statusField.setText("Waiting: " + queueService.getQueueSize());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void callNext(ActionEvent e) {
        try {
            QueueItem next = queueService.getNext();
            JOptionPane.showMessageDialog(this, next == null ? "Queue empty" : ("Now serving: " + next.getName()));
            refresh(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void removeSelected(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to remove");
            return;
        }
        long id = (long) model.getValueAt(row, 0);
        try {
            boolean ok = queueService.removeFromQueue(id);
            JOptionPane.showMessageDialog(this, ok ? "Removed" : "Not found");
            refresh(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearQueue(ActionEvent e) {
        try {
            queueService.clearQueue();
            JOptionPane.showMessageDialog(this, "Queue cleared");
            refresh(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}


