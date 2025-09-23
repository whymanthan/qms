import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ModernHospitalQueueGUI extends JFrame {
    private QueueManager queueManager;
    private JTable queueTable;
    private DefaultTableModel tableModel;
    private JLabel waitingCountLabel;
    private JLabel avgWaitTimeLabel;
    private JLabel servedTodayLabel;
    private JLabel currentlyServingLabel;
    private JTextField nameField, phoneField, reasonField;
    private JComboBox<String> departmentCombo;
    private JPanel mainPanel;
    private boolean isAdminMode = false;
    
    private static final String[] DEPARTMENTS = {
        "General Medicine", "Cardiology", "Neurology", "Orthopedics", 
        "Pediatrics", "Oncology", "Radiology", "Laboratory", "Pharmacy",
        "Surgery", "Dermatology", "Ophthalmology", "ENT (Ear, Nose, Throat)",
        "Gynecology", "Urology", "Psychiatry", "Physical Therapy", "Billing/Insurance"
    };
    
    public ModernHospitalQueueGUI() {
        queueManager = new QueueManager();
        initializeGUI();
        updateDisplay();
    }
    
    private void initializeGUI() {
        setTitle("Hospital Queue Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Create main panel with gradient background
        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(138, 43, 226), // Purple
                    getWidth(), getHeight(), new Color(30, 144, 255) // Blue
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        
        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Main content area
        JPanel centerPanel = createCenterPanel();
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        // Replace the frame content to avoid stacking multiple panels
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Hospital Queue Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Join our hospital queue and track your position");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        // Admin button
        JButton adminBtn = createStyledButton("Admin Panel", new Color(52, 152, 219), 120, 40);
        adminBtn.addActionListener(e -> toggleAdminMode());
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(adminBtn, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        
        if (isAdminMode) {
            centerPanel.add(createAdminPanel(), BorderLayout.CENTER);
        } else {
            centerPanel.add(createPatientPanel(), BorderLayout.CENTER);
        }
        
        return centerPanel;
    }
    
    private JPanel createPatientPanel() {
        JPanel mainContentPanel = new JPanel(new BorderLayout(20, 20));
        mainContentPanel.setOpaque(false);
        
        // Left panel - Patient registration
        JPanel leftPanel = createRegistrationPanel();
        mainContentPanel.add(leftPanel, BorderLayout.WEST);
        
        // Right panel - Queue display
        JPanel rightPanel = createQueueDisplayPanel();
        mainContentPanel.add(rightPanel, BorderLayout.CENTER);
        
        return mainContentPanel;
    }
    
    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // White card for registration
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        // Title
        JLabel titleLabel = new JLabel("Join Hospital Queue");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(52, 73, 94));
        // text only, no icon
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(nameLabel, gbc);
        gbc.gridy = 1;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(nameField, gbc);
        
        // Phone field
        gbc.gridy = 2;
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(phoneLabel, gbc);
        gbc.gridy = 3;
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(phoneField, gbc);
        
        // Department field
        gbc.gridy = 4;
        JLabel deptLabel = new JLabel("Department/Service");
        deptLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(deptLabel, gbc);
        gbc.gridy = 5;
        departmentCombo = new JComboBox<>(DEPARTMENTS);
        departmentCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        departmentCombo.setPreferredSize(new Dimension(250, 35));
        formPanel.add(departmentCombo, gbc);
        
        // Reason field
        gbc.gridy = 6;
        JLabel reasonLabel = new JLabel("Reason for Visit");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(reasonLabel, gbc);
        gbc.gridy = 7;
        reasonField = new JTextField(20);
        reasonField.setFont(new Font("Arial", Font.PLAIN, 14));
        reasonField.setPreferredSize(new Dimension(250, 35));
        formPanel.add(reasonField, gbc);
        
        // Register button
        gbc.gridy = 8; gbc.anchor = GridBagConstraints.CENTER;
        JButton registerBtn = createGradientButton("Join Hospital Queue", 
            new Color(138, 43, 226), new Color(30, 144, 255), 250, 45);
        registerBtn.addActionListener(e -> registerPatient());
        formPanel.add(registerBtn, gbc);
        
        cardPanel.add(titleLabel, BorderLayout.NORTH);
        cardPanel.add(formPanel, BorderLayout.CENTER);
        
        panel.add(cardPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createQueueDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // White card for queue display
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        // Queue status card
        JPanel statusCard = createQueueStatusCard();
        cardPanel.add(statusCard, BorderLayout.NORTH);
        
        // Current queue list
        JPanel queueListPanel = createQueueListPanel();
        cardPanel.add(queueListPanel, BorderLayout.CENTER);
        
        panel.add(cardPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createQueueStatusCard() {
        JPanel statusCard = new JPanel(new GridLayout(1, 2, 20, 0));
        statusCard.setOpaque(false);
        statusCard.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Left status - People waiting
        JPanel leftStatus = new JPanel(new BorderLayout());
        leftStatus.setBackground(new Color(255, 107, 107));
        leftStatus.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel waitingLabel = new JLabel("People Waiting");
        waitingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        waitingLabel.setForeground(Color.WHITE);
        
        waitingCountLabel = new JLabel("0");
        waitingCountLabel.setFont(new Font("Arial", Font.BOLD, 48));
        waitingCountLabel.setForeground(Color.WHITE);
        waitingCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        leftStatus.add(waitingLabel, BorderLayout.NORTH);
        leftStatus.add(waitingCountLabel, BorderLayout.CENTER);
        
        // Right status - Wait time
        JPanel rightStatus = new JPanel(new BorderLayout());
        rightStatus.setBackground(new Color(255, 159, 67));
        rightStatus.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel timeLabel = new JLabel("Min Wait Time");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setForeground(Color.WHITE);
        
        avgWaitTimeLabel = new JLabel("0");
        avgWaitTimeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        avgWaitTimeLabel.setForeground(Color.WHITE);
        avgWaitTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        rightStatus.add(timeLabel, BorderLayout.NORTH);
        rightStatus.add(avgWaitTimeLabel, BorderLayout.CENTER);
        
        statusCard.add(leftStatus);
        statusCard.add(rightStatus);
        
        return statusCard;
    }
    
    private JPanel createQueueListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Current Queue");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        
        // Queue table
        String[] columnNames = {"#", "Name", "Department", "Phone", "Time", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        queueTable = new JTable(tableModel);
        queueTable.setFont(new Font("Arial", Font.PLAIN, 12));
        queueTable.setRowHeight(40);
        queueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queueTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        queueTable.getTableHeader().setBackground(new Color(52, 73, 94));
        queueTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(queueTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        // White card for admin panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));
        
        // Header
        JLabel titleLabel = new JLabel("Hospital Admin Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        
        JLabel subtitleLabel = new JLabel("Manage the hospital queue");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        // Stats cards
        JPanel statsPanel = createStatsPanel();
        
        // Currently serving panel
        JPanel servingPanel = createCurrentlyServingPanel();
        
        // Queue management
        JPanel queueManagementPanel = createQueueManagementPanel();
        
        // Stack panels vertically under header
        JPanel centerStack = new JPanel();
        centerStack.setOpaque(false);
        centerStack.setLayout(new BoxLayout(centerStack, BoxLayout.Y_AXIS));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        queueManagementPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        servingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerStack.add(statsPanel);
        centerStack.add(Box.createVerticalStrut(15));
        centerStack.add(queueManagementPanel);
        centerStack.add(Box.createVerticalStrut(15));
        centerStack.add(servingPanel);
        
        cardPanel.add(headerPanel, BorderLayout.NORTH);
        cardPanel.add(centerStack, BorderLayout.CENTER);
        
        panel.add(cardPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Total in queue
        JPanel queueCard = new JPanel(new BorderLayout());
        queueCard.setBackground(new Color(52, 152, 219));
        queueCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel queueLabel = new JLabel("Total in Queue");
        queueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        queueLabel.setForeground(Color.WHITE);
        
        waitingCountLabel = new JLabel("0");
        waitingCountLabel.setFont(new Font("Arial", Font.BOLD, 36));
        waitingCountLabel.setForeground(Color.WHITE);
        waitingCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        queueCard.add(queueLabel, BorderLayout.NORTH);
        queueCard.add(waitingCountLabel, BorderLayout.CENTER);
        
        // Average wait time
        JPanel timeCard = new JPanel(new BorderLayout());
        timeCard.setBackground(new Color(46, 204, 113));
        timeCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel timeLabel = new JLabel("Avg Wait (min)");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setForeground(Color.WHITE);
        
        avgWaitTimeLabel = new JLabel("0");
        avgWaitTimeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        avgWaitTimeLabel.setForeground(Color.WHITE);
        avgWaitTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        timeCard.add(timeLabel, BorderLayout.NORTH);
        timeCard.add(avgWaitTimeLabel, BorderLayout.CENTER);
        
        // Served today
        JPanel servedCard = new JPanel(new BorderLayout());
        servedCard.setBackground(new Color(155, 89, 182));
        servedCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel servedLabel = new JLabel("Served Today");
        servedLabel.setFont(new Font("Arial", Font.BOLD, 14));
        servedLabel.setForeground(Color.WHITE);
        
        servedTodayLabel = new JLabel("0");
        servedTodayLabel.setFont(new Font("Arial", Font.BOLD, 36));
        servedTodayLabel.setForeground(Color.WHITE);
        servedTodayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        servedCard.add(servedLabel, BorderLayout.NORTH);
        servedCard.add(servedTodayLabel, BorderLayout.CENTER);
        
        panel.add(queueCard);
        panel.add(timeCard);
        panel.add(servedCard);
        
        return panel;
    }
    
    private JPanel createCurrentlyServingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Currently serving card
        JPanel servingCard = new JPanel(new BorderLayout());
        servingCard.setBackground(new Color(231, 76, 60));
        servingCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Currently Serving");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        currentlyServingLabel = new JLabel("No one is currently being served");
        currentlyServingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        currentlyServingLabel.setForeground(Color.WHITE);
        currentlyServingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Control buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton callNextBtn = createStyledButton("Call Next Patient", Color.WHITE, 150, 35);
        callNextBtn.setForeground(new Color(52, 152, 219));
        callNextBtn.addActionListener(e -> callNextPatient());
        
        JButton completeBtn = createStyledButton("Complete Current", Color.WHITE, 150, 35);
        completeBtn.setForeground(new Color(46, 204, 113));
        completeBtn.addActionListener(e -> completeCurrentPatient());
        
        JButton clearBtn = createStyledButton("Clear Queue", Color.WHITE, 150, 35);
        clearBtn.setForeground(new Color(231, 76, 60));
        clearBtn.addActionListener(e -> clearQueue());
        
        buttonPanel.add(callNextBtn);
        buttonPanel.add(completeBtn);
        buttonPanel.add(clearBtn);
        
        servingCard.add(titleLabel, BorderLayout.NORTH);
        servingCard.add(currentlyServingLabel, BorderLayout.CENTER);
        servingCard.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(servingCard, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createQueueManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JLabel titleLabel = new JLabel("📋 Hospital Queue Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 73, 94));
        
        // Admin queue table
        String[] columnNames = {"#", "Name", "Department", "Phone", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        queueTable = new JTable(tableModel);
        queueTable.setFont(new Font("Arial", Font.PLAIN, 12));
        queueTable.setRowHeight(40);
        queueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queueTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        queueTable.getTableHeader().setBackground(new Color(52, 73, 94));
        queueTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(queueTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Controls under table
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controls.setOpaque(false);
        JButton removeBtn = createStyledButton("Remove Selected", new Color(231, 76, 60), 150, 35);
        removeBtn.addActionListener(e -> removeSelectedPatient());
        controls.add(removeBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        
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
    
    private JButton createGradientButton(String text, Color startColor, Color endColor, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    getWidth(), 0, endColor
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(width, height));
        
        return button;
    }
    
    private void toggleAdminMode() {
        isAdminMode = !isAdminMode;
        initializeGUI();
        updateDisplay();
    }
    
    private void registerPatient() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String department = (String) departmentCombo.getSelectedItem();
        String reason = reasonField.getText().trim();
        
        if (name.isEmpty() || phone.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int queueNumber = queueManager.getNextQueueNumber();
        Patient patient = new Patient(name, phone, department, reason, queueNumber);
        queueManager.addPatient(patient);
        
        // Clear fields
        nameField.setText("");
        phoneField.setText("");
        reasonField.setText("");
        
        updateDisplay();
        
        JOptionPane.showMessageDialog(this, 
            "Patient registered successfully!\nQueue Number: " + queueNumber, 
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
        
        updateDisplay();
        
        JOptionPane.showMessageDialog(this, 
            "CALLING PATIENT\n\n" +
            "Queue #" + nextPatient.getQueueNumber() + "\n" +
            "Name: " + nextPatient.getName() + "\n" +
            "Phone: " + nextPatient.getPhoneNumber() + "\n" +
            "Department: " + nextPatient.getDepartment() + "\n" +
            "Reason: " + nextPatient.getReason(), 
            "Patient Called", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void completeCurrentPatient() {
        queueManager.completeCurrentPatient();
        updateDisplay();
    }
    
    private void clearQueue() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear the entire queue?", 
            "Confirm Clear", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            queueManager.clearQueue();
            updateDisplay();
        }
    }
    
    private void removeSelectedPatient() {
        int row = queueTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int queueNumber = (int) tableModel.getValueAt(row, 0);
        queueManager.removePatient(queueNumber);
        updateDisplay();
    }
    
    private void updateDisplay() {
        // Update labels
        if (waitingCountLabel != null) {
            waitingCountLabel.setText(String.valueOf(queueManager.getWaitingCount()));
        }
        if (avgWaitTimeLabel != null) {
            avgWaitTimeLabel.setText(String.valueOf(queueManager.getAverageWaitTime()));
        }
        if (servedTodayLabel != null) {
            servedTodayLabel.setText(String.valueOf(queueManager.getTotalServedToday()));
        }
        
        // Update currently serving
        Patient serving = queueManager.getCurrentlyServing();
        if (currentlyServingLabel != null) {
            if (serving != null) {
                currentlyServingLabel.setText(serving.getName() + " - " + serving.getDepartment());
            } else {
                currentlyServingLabel.setText("No one is currently being served");
            }
        }
        
        // Update table
        tableModel.setRowCount(0);
        
        List<Patient> waitingQueue = queueManager.getWaitingQueue();
        for (Patient patient : waitingQueue) {
            Object[] row = {
                patient.getQueueNumber(),
                patient.getName(),
                patient.getDepartment(),
                patient.getPhoneNumber(),
                patient.getFormattedTime(),
                isAdminMode ? "Remove" : "⏳ Waiting"
            };
            tableModel.addRow(row);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ModernHospitalQueueGUI().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
