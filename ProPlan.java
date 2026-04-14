import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProPlan extends AIModel {

    private int teamSlots;
    private ArrayList<String> teamMembers = new ArrayList<>();

    // Constructor
    public ProPlan(String modelName, double price, int parameterCount, int contextWindow, int teamSlots) {

        super(modelName, price, parameterCount, contextWindow);
        this.teamSlots = teamSlots;
    }

    // Getters
    public int getTeamSlots() {
        return teamSlots;
    }

    public List<String> getTeamMembers() {
        return Collections.unmodifiableList(teamMembers);
    }

    // Add a team member, respecting teamSlots capacity
    public boolean addTeamMember(String name) {
        if (teamMembers.size() >= teamSlots) {
            return false;
        }
        teamMembers.add(name);
        return true;
    }

    // Remove a team member
    public boolean removeTeamMember(String name) {
        return teamMembers.remove(name);
    }

    // Run prompt
    public String runPrompt(int inputTokens, int outputTokens) {

        int systemTokens = 500;
        int totalTokens = inputTokens + outputTokens + systemTokens;

        if (totalTokens > getContextWindow()) {
            return "Prompt rejected: Context limit exceeded.";
        }

        return "Prompt processed successfully.\nTotal tokens used: " + totalTokens
                + "\nUnlimited prompts available.";
    }

    // Display plan info
    public String displayPlan() {
        return super.display()
                + "\nPlan Type: ProPlan"
                + "\nMonthly Quota: Unlimited"
                + "\nTeam Slots: " + teamSlots
                + "\nTeam Members: " + teamMembers;
    }
}