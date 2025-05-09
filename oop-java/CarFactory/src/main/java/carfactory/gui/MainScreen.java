package carfactory.gui;

import carfactory.configparser.ConfigParser;
import carfactory.exceptions.CarFactoryException;
import carfactory.exceptions.ParserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {
    public MainScreen() throws CarFactoryException{
        super("Car Factory");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel storageLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel bodyStorage = new JLabel("Storage Bodies: 0");
        JLabel engineStorage = new JLabel("Storage Engines: 0");
        JLabel accessoriesStorage = new JLabel("Storage Accessories: 0");
        storageLine.add(bodyStorage);
        storageLine.add(Box.createHorizontalStrut(10));
        storageLine.add(engineStorage);
        storageLine.add(Box.createHorizontalStrut(10));
        storageLine.add(accessoriesStorage);

        JPanel supplierLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel bodySupplier = new JLabel("Supplier Bodies: 0");
        JLabel engineSupplier = new JLabel("Supplier Engines: 0");
        JLabel accessoriesSupplier = new JLabel("Supplier Accessories: 0");
        supplierLine.add(bodySupplier);
        supplierLine.add(Box.createHorizontalStrut(10));
        supplierLine.add(engineSupplier);
        supplierLine.add(Box.createHorizontalStrut(10));
        supplierLine.add(accessoriesSupplier);

        infoPanel.add(storageLine);
        infoPanel.add(supplierLine);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(createSliderPanel("Body"));
        settingsPanel.add(createSliderPanel("Engine"));
        settingsPanel.add(createSliderPanel("Accessories"));
        settingsPanel.add(createSliderPanel("Dealer delay"));

        JButton startButton = new JButton("start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        ConfigParser parser = new ConfigParser();

                    } catch (ParserException e) {
                        throw new RuntimeException(e);
                    }
            }
        });
        startButton.setAlignmentX(CENTER_ALIGNMENT);
        settingsPanel.add(startButton);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(settingsPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createSliderPanel(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(name);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider slider = new JSlider(0, 1000, 500);
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(slider);
        panel.add(Box.createVerticalStrut(15));

        return panel;
    }
}
