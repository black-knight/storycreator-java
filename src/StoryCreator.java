/**
 * Created by daniel on 09/02/14.
 */
public class StoryCreator {
    public static void main(String[] args) {
        System.out.println("Initialiserer...");
        StoryBoard.init();

        System.out.println("Klar!");
        System.out.println();

        Story story = new Story();

        while (true) {
            story.printChoice();

            System.out.print("> ");
            String str = System.console().readLine();

            if ("quit".equals(str.toLowerCase())) {
                break;
            }

            if (str.length() != 1) {
                System.out.println("???");
                continue;
            }

            char ch = str.toLowerCase().charAt(0);
            if (ch < 'a' || ch > 'z') {
                System.out.println("???");
                continue;
            }

            Choice choice = story.choose(ch - 'a');

            String outputText = choice.getOutputText(story.choiceHistory, story.currentObjects);
            System.out.println(Util.characters('-', outputText.length() + 4));
            System.out.println("| " + outputText + " |");
            System.out.println(Util.characters('-', outputText.length() + 4));
            System.out.println("");
            System.out.println("");

            Util.outputTextToFile(outputText, "story.txt");
        }
        System.out.println("Farvel!");
    }
}
