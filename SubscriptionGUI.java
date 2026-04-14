import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class SubscriptionGUI extends JFrame implements ActionListener {

    private static final String SUBSCRIPTIONS_FILE = "subscriptions.txt";
    ArrayList<AIModel> plans = new ArrayList<>();

    // Text fields
    JTextField modelNameField = new JTextField(15);
    JTextField pricingField = new JTextField(15);
    JTextField parametersField = new JTextField(15);
    JTextField contextWindowField = new JTextField(15);
    JTextField promptsQuotaField = new JTextField(15);
    JTextField teamSlotsField = new JTextField(15);
    JTextField promptTextField = new JTextField(15);
    JTextField responseLengthField = new JTextField(15);
    JTextField teamMemberNameField = new JTextField(15);
    JTextField indexNumberField = new JTextField(15);

    // Buttons
    JButton addPersonalPlanButton = new JButton("Add Personal Plan");
    JButton addProPlanButton = new JButton("Add Pro Plan");
    JButton displayAllButton = new JButton("Display All");
    JButton clearButton = new JButton("Clear");
    JButton givePromptButton = new JButton("Give a Prompt");
    JButton addTeamMemberButton = new JButton("Add Team Member");
    JButton removeTeamMemberButton = new JButton("Remove Team Member");
    JButton checkPlanTypeButton = new JButton("Check Plan Type");
    JButton exportToFileButton = new JButton("Export to File");
    JButton loadFromFileButton = new JButton("Load From File");

    public SubscriptionGUI() {
        setTitle("Subscription Manager");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 5, 5));

        // Add fields with labels
        add(new JLabel("Model Name:"));
        add(modelNameField);
        add(new JLabel("Pricing (NPR per 1L tokens):"));
        add(pricingField);
        add(new JLabel("Parameters (billions):"));
        add(parametersField);
        add(new JLabel("Context Window:"));
        add(contextWindowField);
        add(new JLabel("Prompts Quota (Personal):"));
        add(promptsQuotaField);
        add(new JLabel("Team Slots (Pro):"));
        add(teamSlotsField);
        add(new JLabel("Prompt Text (input tokens):"));
        add(promptTextField);
        add(new JLabel("Response Length (output tokens):"));
        add(responseLengthField);
        add(new JLabel("Team Member Name:"));
        add(teamMemberNameField);
        add(new JLabel("Index Number:"));
        add(indexNumberField);

        // Add buttons
        add(addPersonalPlanButton);
        add(addProPlanButton);
        add(displayAllButton);
        add(clearButton);
        add(givePromptButton);
        add(addTeamMemberButton);
        add(removeTeamMemberButton);
        add(checkPlanTypeButton);
        add(exportToFileButton);
        add(loadFromFileButton);

        // Register action listeners
        addPersonalPlanButton.addActionListener(this);
        addProPlanButton.addActionListener(this);
        displayAllButton.addActionListener(this);
        clearButton.addActionListener(this);
        givePromptButton.addActionListener(this);
        addTeamMemberButton.addActionListener(this);
        removeTeamMemberButton.addActionListener(this);
        checkPlanTypeButton.addActionListener(this);
        exportToFileButton.addActionListener(this);
        loadFromFileButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == addPersonalPlanButton) {
            try {
                String name = modelNameField.getText().trim();
                double price = Double.parseDouble(pricingField.getText().trim());
                int params = Integer.parseInt(parametersField.getText().trim());
                int ctx = Integer.parseInt(contextWindowField.getText().trim());
                int quota = Integer.parseInt(promptsQuotaField.getText().trim());
                plans.add(new PersonalPlan(name, price, params, ctx, quota));
                JOptionPane.showMessageDialog(this, "Personal Plan added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == addProPlanButton) {
            try {
                String name = modelNameField.getText().trim();
                double price = Double.parseDouble(pricingField.getText().trim());
                int params = Integer.parseInt(parametersField.getText().trim());
                int ctx = Integer.parseInt(contextWindowField.getText().trim());
                int slots = Integer.parseInt(teamSlotsField.getText().trim());
                plans.add(new ProPlan(name, price, params, ctx, slots));
                JOptionPane.showMessageDialog(this, "Pro Plan added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == displayAllButton) {
            if (plans.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No plans available.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < plans.size(); i++) {
                sb.append("--- Plan ").append(i).append(" ---\n");
                if (plans.get(i) instanceof PersonalPlan) {
                    sb.append(((PersonalPlan) plans.get(i)).displayPlan());
                } else if (plans.get(i) instanceof ProPlan) {
                    sb.append(((ProPlan) plans.get(i)).displayPlan());
                }
                sb.append("\n\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "All Plans", JOptionPane.INFORMATION_MESSAGE);

        } else if (src == clearButton) {
            modelNameField.setText("");
            pricingField.setText("");
            parametersField.setText("");
            contextWindowField.setText("");
            promptsQuotaField.setText("");
            teamSlotsField.setText("");
            promptTextField.setText("");
            responseLengthField.setText("");
            teamMemberNameField.setText("");
            indexNumberField.setText("");

        } else if (src == givePromptButton) {
            try {
                int idx = Integer.parseInt(indexNumberField.getText().trim());
                int inputTokens = Integer.parseInt(promptTextField.getText().trim());
                int outputTokens = Integer.parseInt(responseLengthField.getText().trim());
                if (idx < 0 || idx >= plans.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid plan index.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AIModel plan = plans.get(idx);
                String result;
                if (plan instanceof PersonalPlan) {
                    result = ((PersonalPlan) plan).runPrompt(inputTokens, outputTokens);
                } else if (plan instanceof ProPlan) {
                    result = ((ProPlan) plan).runPrompt(inputTokens, outputTokens);
                } else {
                    result = "Unknown plan type.";
                }
                JOptionPane.showMessageDialog(this, result, "Prompt Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == addTeamMemberButton) {
            try {
                int idx = Integer.parseInt(indexNumberField.getText().trim());
                if (idx < 0 || idx >= plans.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid plan index.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (plans.get(idx) instanceof ProPlan) {
                    String memberName = teamMemberNameField.getText().trim();
                    if (memberName.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Team member name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    boolean added = ((ProPlan) plans.get(idx)).addTeamMember(memberName);
                    if (added) {
                        JOptionPane.showMessageDialog(this, "Team member added: " + memberName);
                    } else {
                        JOptionPane.showMessageDialog(this, "Team slots are full. Cannot add more members.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selected plan is not a Pro Plan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid index: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == removeTeamMemberButton) {
            try {
                int idx = Integer.parseInt(indexNumberField.getText().trim());
                if (idx < 0 || idx >= plans.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid plan index.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (plans.get(idx) instanceof ProPlan) {
                    String memberName = teamMemberNameField.getText().trim();
                    boolean removed = ((ProPlan) plans.get(idx)).removeTeamMember(memberName);
                    if (removed) {
                        JOptionPane.showMessageDialog(this, "Team member removed: " + memberName);
                    } else {
                        JOptionPane.showMessageDialog(this, "Team member not found: " + memberName, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selected plan is not a Pro Plan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid index: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == checkPlanTypeButton) {
            try {
                int idx = Integer.parseInt(indexNumberField.getText().trim());
                if (idx < 0 || idx >= plans.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid plan index.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String type = (plans.get(idx) instanceof PersonalPlan) ? "PersonalPlan" : "ProPlan";
                JOptionPane.showMessageDialog(this, "Plan at index " + idx + " is: " + type);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid index: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == exportToFileButton) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(SUBSCRIPTIONS_FILE))) {
                for (AIModel plan : plans) {
                    if (plan instanceof PersonalPlan) {
                        PersonalPlan p = (PersonalPlan) plan;
                        writer.println("Personal," + p.getModelName() + "," + p.getPrice()
                                + "," + p.getParameterCount() + "," + p.getContextWindow()
                                + "," + p.getMonthlyQuota());
                    } else if (plan instanceof ProPlan) {
                        ProPlan p = (ProPlan) plan;
                        StringBuilder line = new StringBuilder();
                        line.append("Pro,").append(p.getModelName()).append(",").append(p.getPrice())
                                .append(",").append(p.getParameterCount()).append(",").append(p.getContextWindow())
                                .append(",").append(p.getTeamSlots());
                        for (String member : p.getTeamMembers()) {
                            line.append(",").append(member);
                        }
                        writer.println(line);
                    }
                }
                JOptionPane.showMessageDialog(this, "Plans exported to " + SUBSCRIPTIONS_FILE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == loadFromFileButton) {
            try (BufferedReader reader = new BufferedReader(new FileReader(SUBSCRIPTIONS_FILE))) {
                plans.clear();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals("Personal") && parts.length >= 6) {
                        String name = parts[1];
                        double price = Double.parseDouble(parts[2]);
                        int params = Integer.parseInt(parts[3]);
                        int ctx = Integer.parseInt(parts[4]);
                        int quota = Integer.parseInt(parts[5]);
                        plans.add(new PersonalPlan(name, price, params, ctx, quota));
                    } else if (parts[0].equals("Pro") && parts.length >= 6) {
                        String name = parts[1];
                        double price = Double.parseDouble(parts[2]);
                        int params = Integer.parseInt(parts[3]);
                        int ctx = Integer.parseInt(parts[4]);
                        int slots = Integer.parseInt(parts[5]);
                        ProPlan proPlan = new ProPlan(name, price, params, ctx, slots);
                        for (int i = 6; i < parts.length; i++) {
                            proPlan.addTeamMember(parts[i]);
                        }
                        plans.add(proPlan);
                    }
                }
                JOptionPane.showMessageDialog(this, "Loaded " + plans.size() + " plan(s) from subscriptions.txt");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "File not found: " + SUBSCRIPTIONS_FILE, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Load failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new SubscriptionGUI();
    }
}
