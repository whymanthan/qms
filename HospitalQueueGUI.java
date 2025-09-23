import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HospitalQueueGUI extends JFrame {
    private QueueManager queueManager;
    private JTable queueTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private JLabel waitingCountLabel;
    private JTextField nameField, phoneField, reasonField;
    
    public HospitalQueueGUI() {
        queueManager = new QueueManager();
        initializeGUI();
        updateQueueDisplay();
    }
    
    private void initializeGUI() {
        setTitle("Hospital Queue Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Use default look and feel
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 250));
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center panel with queue display
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel with controls
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("🏥 Hospital Queue Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setOpaque(false);
        
        waitingCountLabel = new JLabel("Patients Waiting: 0");
        waitingCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        waitingCountLabel.setForeground(new Color(46, 204, 113));
        
        statusLabel = new JLabel("System Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(189, 195, 199));
        
        statusPanel.add(waitingCountLabel);
        statusPanel.add(new JLabel(" | "));
        statusPanel.add(statusLabel);
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        return headerPanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Queue table
        String[] columnNames = {"Queue #", "Name", "Phone", "Reason", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        queueTable = new JTable(tableModel);
        queueTable.setFont(new Font("Arial", Font.PLAIN, 12));
        queueTable.setRowHeight(25);
        queueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queueTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        queueTable.getTableHeader().setBackground(new Color(52, 73, 94));
        queueTable.getTableHeader().setForeground(Color.WHITE);
        
        // Set column widths
        queueTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        queueTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        queueTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        queueTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        queueTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        queueTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(queueTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        return centerPanel;
    }
    
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(245, 248, 250));
        
        // Patient registration panel
        JPanel registrationPanel = createRegistrationPanel();
        bottomPanel.add(registrationPanel, BorderLayout.CENTER);
        
        // Control buttons panel
        JPanel controlPanel = createControlPanel();
        bottomPanel.add(controlPanel, BorderLayout.SOUTH);
        
        return bottomPanel;
    }
    
    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94), 2),
            "Patient Registration",
            0, 0, new Font("Arial", Font.BOLD, 14), new Color(52, 73, 94)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(nameField, gbc);
        
        // Phone field
        gbc.gridx = 2;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 3;
        phoneField = new JTextField(15);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(phoneField, gbc);
        
        // Reason field
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Reason:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        reasonField = new JTextField(30);
        reasonField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(reasonField, gbc);
        
        // Register button
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerBtn = createStyledButton("Register Patient", new Color(46, 204, 113));
        registerBtn.addActionListener(e -> registerPatient());
        panel.add(registerBtn, gbc);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(245, 248, 250));
        
        JButton callNextBtn = createStyledButton("📢 Call Next Patient", new Color(52, 152, 219));
        callNextBtn.addActionListener(e -> callNextPatient());
        
        JButton refreshBtn = createStyledButton("🔄 Refresh Queue", new Color(155, 89, 182));
        refreshBtn.addActionListener(e -> updateQueueDisplay());
        
        JButton clearBtn = createStyledButton("🗑️ Clear Completed", new Color(231, 76, 60));
        clearBtn.addActionListener(e -> clearCompletedPatients());
        
        panel.add(callNextBtn);
        panel.add(refreshBtn);
        panel.add(clearBtn);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void registerPatient() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String reason = reasonField.getText().trim();
        
        if (name.isEmpty() || phone.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int queueNumber = queueManager.getNextQueueNumber();
        Patient patient = new Patient(name, phone, "General Medicine", reason, queueNumber);
        queueManager.addPatient(patient);
        
        // Clear fields
        nameField.setText("");
        phoneField.setText("");
        reasonField.setText("");
        
        updateQueueDisplay();
        statusLabel.setText("Patient registered successfully!");
        
        // Show confirmation
        JOptionPane.showMessageDialog(this, 
            "Patient registered!\nQueue Number: " + queueNumber, 
            "Registration Successful", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void callNextPatient() {
        Patient nextPatient = queueManager.callNextPatient();
        
        if (nextPatient == null) {
            JOptionPane.showMessageDialog(this, 
                "No patients in queue!", 
                "Empty Queue", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        updateQueueDisplay();
        statusLabel.setText("Called: " + nextPatient.getName());
        
        // Show call notification
        JOptionPane.showMessageDialog(this, 
            "📢 CALLING PATIENT\n\n" +
            "Queue #" + nextPatient.getQueueNumber() + "\n" +
            "Name: " + nextPatient.getName() + "\n" +
            "Phone: " + nextPatient.getPhoneNumber() + "\n" +
            "Reason: " + nextPatient.getReason(), 
            "Patient Called", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void clearCompletedPatients() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear all completed patients?", 
            "Confirm Clear", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            queueManager.getCompletedPatients().clear();
            updateQueueDisplay();
            statusLabel.setText("Completed patients cleared");
        }
    }
    
    private void updateQueueDisplay() {
        tableModel.setRowCount(0);
        
        List<Patient> waitingQueue = queueManager.getWaitingQueue();
        List<Patient> completedPatients = queueManager.getCompletedPatients();
        
        // Add waiting patients
        for (Patient patient : waitingQueue) {
            Object[] row = {
                patient.getQueueNumber(),
                patient.getName(),
                patient.getPhoneNumber(),
                patient.getReason(),
                patient.getFormattedTime(),
                "⏳ Waiting"
            };
            tableModel.addRow(row);
        }
        
        // Add completed patients
        for (Patient patient : completedPatients) {
            Object[] row = {
                patient.getQueueNumber(),
                patient.getName(),
                patient.getPhoneNumber(),
                patient.getReason(),
                patient.getFormattedTime(),
                "✅ Completed"
            };
            tableModel.addRow(row);
        }
        
        waitingCountLabel.setText("Patients Waiting: " + queueManager.getWaitingCount());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new HospitalQueueGUI().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
