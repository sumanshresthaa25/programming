public class ProPlan extends AIModel {

    // Constructor
    public ProPlan(String modelName, double price, int parameterCount, int contextWindow) {

        super(modelName, price, parameterCount, contextWindow);
    }

    // Run prompt
    public void runPrompt(int inputTokens, int outputTokens) {

        int systemTokens = 500;
        int totalTokens = inputTokens + outputTokens + systemTokens;

        if (totalTokens > getContextWindow()) {
            System.out.println("Prompt rejected: Context limit exceeded.");
            return;
        }

        System.out.println("Prompt processed successfully.");
        System.out.println("Total tokens used: " + totalTokens);
        System.out.println("Unlimited prompts available.");
    }

    // Display plan info
    public String displayPlan() {
        return super.display()
                + "\nPlan Type: ProPlan"
                + "\nMonthly Quota: Unlimited";
    }
}