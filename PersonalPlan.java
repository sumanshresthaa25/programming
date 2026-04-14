public class PersonalPlan extends AIModel {

    private int monthlyQuota;

    // Constructor
    public PersonalPlan(String modelName, double price, int parameterCount, int contextWindow, int monthlyQuota) {

        super(modelName, price, parameterCount, contextWindow);
        this.monthlyQuota = monthlyQuota;
    }

    // Getter
    public int getMonthlyQuota() {
        return monthlyQuota;
    }

    // Purchase additional prompts
    public void purchasePrompts(int purchase) {

        if (purchase > 0) {
            monthlyQuota = monthlyQuota + purchase;
            System.out.println("Purchased " + purchase + " prompts.");
            System.out.println("New quota: " + monthlyQuota);
        } else {
            System.out.println("Invalid prompt amount.");
        }
    }

    // Run prompt
    public void runPrompt(int inputTokens, int outputTokens) {
        int systemTokens = 500;
        int totalTokens = inputTokens + outputTokens + systemTokens;

        if (monthlyQuota <= 0) {
            System.out.println("Quota Exhausted");
            return;
        }

        if (totalTokens > getContextWindow()) {
            System.out.println("Prompt rejected: Context limit exceeded.");
            return;
        }

        monthlyQuota--;

        System.out.println("Prompt processed.");
        System.out.println("Total tokens used: " + totalTokens);
        System.out.println("Remaining quota: " + monthlyQuota);
    }

    // Display plan info
    public String displayPlan() {
        return super.display()
                + "\nPlan Type: PersonalPlan"
                + "\nMonthly Quota: " + monthlyQuota;
    }
}