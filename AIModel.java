abstract class AIModel {
    // Attributes
    private final String modelName;
    private final double price;          // NPR per 1 Lakh tokens
    private final int parameterCount;    // in billions
    private final int contextWindow;  // example "64K"

    // Constructor
    public AIModel(String modelName, double price, int parameterCount, int contextWindow) {
        this.modelName = modelName;
        this.price = price;
        this.parameterCount = parameterCount;
        this.contextWindow = contextWindow;
    }

    // getters
    public String getModelName() {
        return modelName;
    }

    public double getPrice() {
        return price;
    }

    public int getParameterCount() {
        return parameterCount;
    }

    public int getContextWindow() {
        return contextWindow;
    }

    // Display method (returns String)
    public String display() {
        return "Model Name: " + modelName
                + "\nPricing (NPR per 1 Lakh tokens): " + price
                + "\nParameter Count (in billions): " + parameterCount
                + "\nContext Window: " + contextWindow;
    }
}