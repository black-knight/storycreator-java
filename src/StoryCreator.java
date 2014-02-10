/**
 * Created by daniel on 09/02/14.
 */
public class StoryCreator {
    public static void main(String[] args) {
        System.out.println("Initialiserer...");
        StoryBoard.init();

        System.out.println("Klar! ('afslut' afslutter)");
        System.out.println();

        Story story = new Story();

        while (true) {
            story.printChoice();
            System.out.println("=: Udskriv history");
            System.out.print("> ");

            String str = System.console().readLine();

            if ("afslut".equals(str.toLowerCase())) {
                break;
            }
            if ("=".equals(str)) {
                System.out.println();
                System.out.println(story.outputFinalStory());
                continue;
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

            String outputText = choice.getOutputText(story.choiceHistory);
            System.out.println(Util.characters('-', outputText.length() + 4));
            System.out.println("| " + outputText + " |");
            System.out.println(Util.characters('-', outputText.length() + 4));
            System.out.println("");
            System.out.println("");
        }

        System.out.println();
        System.out.println("Skriver historien til story.txt...");
        Util.outputTextToFile(story.outputFinalStory(), "story.txt");
        System.out.println("Farvel!");
    }
}
