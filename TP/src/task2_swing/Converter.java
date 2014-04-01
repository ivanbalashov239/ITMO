package task2_swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Converter extends JFrame {
    //Data
    private static java.util.List<String> markList = new ArrayList<>();
    private static java.util.List<Double> fuelMileageList = new ArrayList<>();
    private static java.util.List<String> fuelTypeList = new ArrayList<>();

    /**
     * Default constructor
     */
    private Converter() {
        super("Расчет стоимости топлива");
        try {
            parse(new File("resources/data.input"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    /**
     * Entry point
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Converter converter = new Converter();
                converter.createAndShowGUI();
            }
        });
    }

    /**
     * Parse input file
     */
    private void parse(File file) throws FileNotFoundException {
        //Simply read data
        Scanner scnr = new Scanner(file);
        while (scnr.hasNext()) {
            String line = scnr.nextLine();
            String split[] = line.split("\t");//Split by tabs
            markList.add(split[2]);
            try {
                fuelMileageList.add(Double.parseDouble(split[1]));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Bad input data", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
            //Generate lables for fuel type
            switch (split[0]) {
                case "92":
                    fuelTypeList.add("92 бензина");
                    break;
                case "95":
                    fuelTypeList.add("95 бензина");
                    break;
                case "98":
                    fuelTypeList.add("98 бензина");
                    break;
                case "ДТ":
                    fuelTypeList.add("диз. топлива");
                    break;
            }

        }
    }

    /**
     * Creates and show GUI
     */
    private void createAndShowGUI() {
        JPanel mainPane = new JPanel(new GridBagLayout()); //Main panel, all content should be here
        GridBagConstraints c = new GridBagConstraints(); //To add components
        c.insets = new Insets(2,2,2,2); //Cell padding

        //---- ADDING COMPONENTS----
        //Col 1
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.anchor = GridBagConstraints.EAST;
        JLabel markLbl = new JLabel("Марка автомобиля"); //Mark of auto label
        c.gridy = 0;
        mainPane.add(markLbl, c);
        final JLabel fuelTypeLbl = new JLabel("Стоимость " + fuelTypeList.get(0)); //Fuel cost label. Set default type
        c.gridy = 1;
        mainPane.add(fuelTypeLbl, c);
        JLabel mileageLbl = new JLabel("Годовой пробег"); //Annual mileage label
        c.gridy = 2;
        mainPane.add(mileageLbl, c);
        JButton recountBtn = new JButton("Стоимость"); //Recount button
        c.gridy = 3;
        mainPane.add(recountBtn, c);

        //Col 2
        //c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        final JComboBox<String> markCBox = new JComboBox<>(); //Combo box to choose mark
        c.gridy = 0;
        mainPane.add(markCBox, c);
        final JTextField mileageFld = new JTextField(); //Mileage field
        c.gridy = 1;
        mainPane.add(mileageFld, c);
        final JTextField fuelCostFld = new JTextField(); //Fuel cost field
        c.gridy = 2;
        mainPane.add(fuelCostFld, c);
        final JLabel output = new JLabel(); //Result label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridy = 3;
        mainPane.add(output, c);


        //Col 3
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 2;
        JLabel fuelCostLabelAmountLbl = new JLabel("руб./л"); //Fuel cost amount label
        c.gridy = 1;
        mainPane.add(fuelCostLabelAmountLbl, c);
        final JLabel mileageAmountLbl = new JLabel("км"); //Mileage amount label
        c.gridy = 2;
        mainPane.add(mileageAmountLbl, c);

        //Init CBox
        for (String i : markList) {
            markCBox.addItem(i);
        }

        //---- ADDING LISTENERS ----
        markCBox.addActionListener(new ActionListener() { //Choose mark
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fuelTypeLbl.setText("Стоимость " + fuelTypeList.get(markCBox.getSelectedIndex())); //Set type in label
                pack();
            }
        });
        recountBtn.addActionListener(new ActionListener() { //Recount button listener
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try { //Check format
                    double mileage = Double.parseDouble(mileageFld.getText());
                    try {
                        double cost = Double.parseDouble(fuelCostFld.getText());

                        //Check input
                        if (cost > 0 && mileage > 0) {
                            //Calculate ant set output result
                            output.setText(cost * mileage * fuelMileageList.get(markCBox.getSelectedIndex()) + " руб.");
                        } else { //If input is negative
                            JOptionPane.showMessageDialog(null, "Вводимые вами числа должны быть положительны.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            //Set focus on 1st wrong field
                            if (cost <= 0) {
                                fuelCostFld.requestFocus();
                            } else {
                                mileageFld.requestFocus();
                            }
                            output.setText("Negative value");
                        }
                    } catch (NumberFormatException e) {//Bad input format for cost
                        output.setText("NaN");
                        errorFormatMessage();
                        fuelCostFld.requestFocus(); //Set focus on mileage field
                    }
                } catch (NumberFormatException e) {//Bad input format for mileage
                    output.setText("NaN");
                    errorFormatMessage();
                    mileageAmountLbl.requestFocus(); //Set focus on cost field
                }
            }
        });

        //Set frame parameters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPane); //Set main pane by default
        setResizable(false);
        setLocationRelativeTo(null); //Start on middle of the screen
        pack();
        setVisible(true);
    }

    /**
     * Show error format message
     */
    private void errorFormatMessage(){
        JOptionPane.showMessageDialog(null, "Вводимые данные должны быть числами.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
