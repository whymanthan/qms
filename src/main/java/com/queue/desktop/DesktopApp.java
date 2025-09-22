package com.queue.desktop;

import com.queue.QueueManagementApplication;
import com.queue.service.QueueService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DesktopApp {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext ctx;
        try {
            ctx = new SpringApplicationBuilder(QueueManagementApplication.class)
                    .headless(false)
                    .web(WebApplicationType.NONE)
                    .run(args);
        } catch (Throwable t) {
            final String msg = t.getClass().getSimpleName() + ": " + (t.getMessage() == null ? "Startup error" : t.getMessage());
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, msg, "Startup Failed", JOptionPane.ERROR_MESSAGE));
            t.printStackTrace();
            return;
        }

        QueueService queueService = ctx.getBean(QueueService.class);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hospital Queue Management - Desktop");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(900, 600);

            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Patient", new PatientPanel(queueService));
            tabs.addTab("Admin", new AdminPanel(queueService));

            frame.setContentPane(tabs);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    ctx.close();
                }
            });
        });
    }
}


