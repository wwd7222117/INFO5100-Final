package incentive;

import dao.Special;
import dao.Vehicle;
import dto.DataPersistence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncentiveManager extends JFrame {
    private JButton button1;
    private JPanel panelMain;
    private JTabbedPane tabbedPanel;
    private JPanel Inventory;
    private JPanel Details;
    private JPanel Description;
    private JComboBox startYear;
    private JComboBox endYear;
    private JComboBox startDay;
    private JComboBox startMonth;
    private JComboBox endDay;
    private JComboBox endMonth;
    private JCheckBox cashPaymentCheckBox;
    private JCheckBox checkPaymentCheckBox;
    private JCheckBox loanCheckBox;
    private JCheckBox leaseCheckBox;
    private JRadioButton flatValue;
    private JTextField inputValue;
    private JRadioButton percentValue;
    private JTextField inputPercent;
    private JLabel categoryJLabel;
    private JComboBox categoryComboBox;
    private JTextField textField1;
    private JComboBox yearComboBox;
    private JComboBox makeComboBox;
    private JComboBox modelComboBox4;
    private JComboBox trimsComboBox;
    private JButton clearAllButton;
    private JRadioButton applyRadioButton;
    private JRadioButton manuallyRadioButton;
    private JComboBox priceComboBox;
    private JComboBox comboBox;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField priceTextField;
    private JTextField mileageTextField;
    private JTextField dayTextField;
    private JButton clearCriteriaButton;
    private JComboBox additionalComboBox;
    private JComboBox additionalComboBox1;
    private JCheckBox splitCheckBox;
    private JTable table1;
    private JLabel vinJLabel;
    private JLabel yearJLabel;
    private JLabel makeJLabel;
    private JLabel modelJLabel;
    private JLabel trimJLabel;
    private JLabel searchJLabel;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextArea disclaimerArea;

    Special spl;
    Vehicle veh;

    public IncentiveManager() {
        spl = new Special();
        initComponents();
    }

    private void initComponents(){
        publish();
        addItemsToComboBoxes();
    }

    public void addItemsToComboBoxes(){
        //startMonth.setPreferredSize(80px);
        //add items for start month combo box
        for(int i=1;i<=12;i++){
            startMonth.addItem(i);
            endMonth.addItem(i);
        }

        //add items for start day combo box
        for(int i = 1;i<=31;i++){
            startDay.addItem(i);
            endDay.addItem(i);
            //filter as/month
        }

        for(int i = 2020;i<2050;i++){
            startYear.addItem(i);
            endYear.addItem(i);
        }
    }

    //setting information
    public void setDates() throws ParseException {
        int sMonth = (int) startMonth.getSelectedItem();
        int sDay = (int) startDay.getSelectedItem();
        int sYear = (int) startYear.getSelectedItem();
        String startDate = sDay + "/" + sMonth + "/" + sYear;
        Date sDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);

        int eMonth = (int) endMonth.getSelectedItem();
        int eDay = (int) endDay.getSelectedItem();
        int eYear = (int) endYear.getSelectedItem();
        String endDate = eDay + "/" + eMonth + "/" + eYear;
        Date eDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);

        //System.out.println(date.toString());
        spl.setStartDate(sDate);
        spl.setEndDate(eDate);

        //TODO write logic to validate end date i.e endDate > startDate
    }

    public void setDiscountValue(){
        //TODO one of the two values have to be selected
        if(flatValue.isSelected()){
            System.out.println("#1");
            //TODO if the value entered is not int, throw exception, give pop up
            int value = Integer.parseInt(inputValue.getText());
            spl.setDiscountValue(value);
        }
        else if(percentValue.isSelected()){
            int percent = Integer.parseInt(inputPercent.getText());
            spl.setDiscountPercent(percent);
        }
    }

    public void setPaymentValidity(){
        if(cashPaymentCheckBox.isSelected()){
            spl.setValidOnCashPayment(true);
        }
        if(checkPaymentCheckBox.isSelected()){
            spl.setValidOnCheckPayment(true);
        }
        if(loanCheckBox.isSelected()){
            spl.setValidOnLoan(true);
        }
        if(leaseCheckBox.isSelected()){
            spl.setValidOnLease(true);
        }
    }

    public void setTitleAndDescription(){
        spl.setTitle(titleField.getText());
        spl.setDescription(descriptionArea.getText());
        spl.setDisclaimer(disclaimerArea.getText());
    }

    //TODO get & set dealerID

    public Special publish(){
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //call the setters here
                try {
                    setDates();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                setDiscountValue();
                setPaymentValidity();
                setTitleAndDescription();

                DataPersistence dp = new DataPersistence();
                //add this special to database
                System.out.println("#2 " + spl.getDiscountValue());;
                dp.writeSpecials(spl);

                JOptionPane.showMessageDialog(null,"Incentive Created!");

            }
        });

        return spl;
    }

    public static void main(String[] args) throws ParseException {
        IncentiveManager frame = new IncentiveManager();
        frame.setTitle("Create Incentive");
        frame.setContentPane(frame.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 400));
        frame.pack();
        frame.setVisible(true);
    }
}
