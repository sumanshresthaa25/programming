import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PersonalPlan personal = new PersonalPlan(
                "GPT-Basic",
                500.0,
                7,
                64000,
                5
        );

        ProPlan pro = new ProPlan(
                "GPT-Premium",
                800.0,
                14,
                64000,
                5
        );

        System.out.println("Choose Plan:");
        System.out.println("1. Personal Plan");
        System.out.println("2. Pro Plan");

        int choice = sc.nextInt();
        sc.nextLine();

        boolean continuePrompt = true;

        if (choice == 1) {

            System.out.println("\n----- PERSONAL PLAN SELECTED -----");
            System.out.println(personal.displayPlan());

            while (continuePrompt) {

                System.out.println("\nEnter your prompt:");
                String prompt = sc.nextLine();

                System.out.println("Enter input tokens:");
                int inputTokens = sc.nextInt();

                System.out.println("Enter output tokens:");
                int outputTokens = sc.nextInt();
                sc.nextLine();

                System.out.println("\nPrompt: " + prompt);
                System.out.println(personal.runPrompt(inputTokens, outputTokens));

                if (personal.getMonthlyQuota() == 0) {
                    System.out.println("\nQuota finished. Do you want to purchase more prompts? (yes/no)");
                    String buy = sc.nextLine();

                    if (buy.equalsIgnoreCase("yes")) {
                        System.out.println("Enter number of prompts to purchase:");
                        int purchase = sc.nextInt();
                        sc.nextLine();
                        personal.purchasePrompts(purchase);
                    }
                }

                System.out.println("\nDo you want to enter another prompt? (yes/no)");
                String answer = sc.nextLine();

                if (!answer.equalsIgnoreCase("yes")) {
                    continuePrompt = false;
                }
            }

        }

        else if (choice == 2) {

            System.out.println("\n----- PRO PLAN SELECTED -----");
            System.out.println(pro.displayPlan());

            while (continuePrompt) {

                System.out.println("\nEnter your prompt:");
                String prompt = sc.nextLine();

                System.out.println("Enter input tokens:");
                int inputTokens = sc.nextInt();

                System.out.println("Enter output tokens:");
                int outputTokens = sc.nextInt();
                sc.nextLine();

                System.out.println("\nPrompt: " + prompt);
                System.out.println(pro.runPrompt(inputTokens, outputTokens));

                System.out.println("\nDo you want to enter another prompt? (yes/no)");
                String answer = sc.nextLine();

                if (!answer.equalsIgnoreCase("yes")) {
                    continuePrompt = false;
                }
            }

        }

        else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}