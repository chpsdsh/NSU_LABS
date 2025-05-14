package carfactory.gui;

import carfactory.parts.Car;
import carfactory.factory.CarFactory;
import carfactory.factory.Dealer;
import carfactory.parts.Accessory;
import carfactory.parts.Body;
import carfactory.parts.Engine;
import carfactory.partsupplier.PartSupplier;
import carfactory.storage.Storage;
import carfactory.threadpool.ThreadPool;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


public class MainScreen extends JFrame {
    private final JLabel bodyDetailsCountText = new JLabel("PartSupplier Bodies: 0");
    private final JLabel engineDetailsCountText = new JLabel("PartSupplier Engines: 0");
    private final JLabel accessoriesDetailsCountText = new JLabel("PartSupplier Accessories: 0");
    private final JLabel carsCountText = new JLabel("Cars Produced: 0");
    private final JLabel bodyStorageText = new JLabel("Storage Body: 0");
    private final JLabel engineStorageText = new JLabel("Storage Engine: 0");
    private final JLabel accessoriesStorageText = new JLabel("Storage Accessory: 0");
    private final JLabel carStorageText = new JLabel("Storage Cars: 0");
    private final JLabel queueTasksText = new JLabel("Tasks in queue: 0");

    private final PartSupplier<Body> bodySupplier;
    private final PartSupplier<Engine> engineSupplier;
    private final List<PartSupplier<Accessory>> accessorySuppliers;
    private final List<Dealer> dealers;

    public MainScreen(Storage<Body> bodyStorage,
                      Storage<Engine> engineStorage,
                      Storage<Accessory> accessoryStorage,
                      Storage<Car> carStorage,
                      PartSupplier<Body> bodySupplier,
                      PartSupplier<Engine> engineSupplier,
                      List<PartSupplier<Accessory>> accessorySuppliers,
                      List<Dealer> dealers,
                      ThreadPool threadPool,
                      CarFactory carFactory
    ) {
        super("Car Factory");
        this.bodySupplier = bodySupplier;
        this.engineSupplier = engineSupplier;
        this.accessorySuppliers = accessorySuppliers;
        this.dealers = dealers;

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                carFactory.stopFactory();
                dispose();
                System.exit(0);
            }
        });

        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel storageLine = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ChangesListener bodyStorageListener = value -> bodyStorageText.setText("Storage Bodies: " + value);
        bodyStorage.setStorageChangesListener(bodyStorageListener);

        ChangesListener engineStorageListener = value -> engineStorageText.setText("Storage Engines: " + value);
        engineStorage.setStorageChangesListener(engineStorageListener);

        ChangesListener accessoryStorageListener = value -> accessoriesStorageText.setText("Storage Accessories: " + value);
        accessoryStorage.setStorageChangesListener(accessoryStorageListener);

        ChangesListener carStorageListener = value -> carStorageText.setText("Storage Cars: " + value);
        carStorage.setStorageChangesListener(carStorageListener);

        storageLine.add(bodyStorageText);
        storageLine.add(Box.createHorizontalStrut(10));
        storageLine.add(engineStorageText);
        storageLine.add(Box.createHorizontalStrut(10));
        storageLine.add(accessoriesStorageText);
        storageLine.add(Box.createHorizontalStrut(10));
        storageLine.add(carStorageText);

        JPanel supplierLine = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ChangesListener bodyDetailsCountListener = value -> bodyDetailsCountText.setText("PartSupplier Bodies: " + value);
        bodyStorage.setDetailsProducedListener(bodyDetailsCountListener);

        ChangesListener engineDetailsCountListener = value -> engineDetailsCountText.setText("PartSupplier Engines: " + value);
        engineStorage.setDetailsProducedListener(engineDetailsCountListener);

        ChangesListener accessoryDetailsCountListener = value -> accessoriesDetailsCountText.setText("PartSupplier Accessories: " + value);
        accessoryStorage.setDetailsProducedListener(accessoryDetailsCountListener);

        ChangesListener carsCountListener = value -> carsCountText.setText("Cars Produced: " + value);
        carStorage.setDetailsProducedListener(carsCountListener);

        ChangesListener queueTasksListener = value -> queueTasksText.setText("Tasks in queue: " + value);
        threadPool.setTaskQueueListener(queueTasksListener);

        supplierLine.add(bodyDetailsCountText);
        supplierLine.add(Box.createHorizontalStrut(10));
        supplierLine.add(engineDetailsCountText);
        supplierLine.add(Box.createHorizontalStrut(10));
        supplierLine.add(accessoriesDetailsCountText);
        supplierLine.add(Box.createHorizontalStrut(10));
        supplierLine.add(carsCountText);
        supplierLine.add(Box.createHorizontalStrut(10));
        supplierLine.add(queueTasksText);

        infoPanel.add(storageLine);
        infoPanel.add(supplierLine);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.add(createSliderPanel("Body"));
        settingsPanel.add(createSliderPanel("Engine"));
        settingsPanel.add(createSliderPanel("Accessories"));
        settingsPanel.add(createSliderPanel("Dealer"));

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

        JSlider slider = new JSlider(0, 3000);
        slider.setMajorTickSpacing(250);
        slider.setMinorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        switch (name) {
            case "Body" -> slider.addChangeListener(e -> {
                int value = slider.getValue();
                bodySupplier.setDelay(value);
            });
            case "Engine" -> slider.addChangeListener(e -> {
                int value = slider.getValue();
                engineSupplier.setDelay(value);
            });
            case "Dealer" -> slider.addChangeListener(e -> {
                int value = slider.getValue();
                for (Dealer d : dealers) {
                    d.setDelay(value);
                }
            });
            case "Accessories" -> slider.addChangeListener(e -> {
                int value = slider.getValue();
                for (PartSupplier<Accessory> p : accessorySuppliers) {
                    p.setDelay(value);
                }
            });
        }
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(slider);
        panel.add(Box.createVerticalStrut(15));
        return panel;
    }
}
