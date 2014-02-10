import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 09/02/14.
 */
public class Story {
    public List<ChoiceGroup> choiceGroupHistory = new ArrayList<ChoiceGroup>();
    public List<Choice> choiceHistory = new ArrayList<Choice>();
    public List<StoryObject> currentObjects = new LinkedList<StoryObject>();

    public Story() {
        choiceGroupHistory.add(StoryBoard.storyChoiceGroups.get("bootstrap"));
    }

    public void printChoice() {
        final int tabSize = 80;
        List<Choice> choiceList = calculateChoice();
        char ch = 'A';
        for (int i = 0; i < Math.max(choiceList.size(), currentObjects.size()); i++) {
            String currentObject = i < currentObjects.size() ? ("| " + currentObjects.get(i).getDescription()) : "";
            String choice = i < choiceList.size() ? (ch + ": " + choiceList.get(i).getOutputAction()) : "";
            System.out.println(choice + Util.characters(' ', tabSize - choice.length()) + currentObject);
            ch++;
        }
    }

    public Choice choose(int choiceNumber) {
        List<Choice> choiceList = calculateChoice();
        Choice choice = choiceList.get(choiceNumber);
        choose(choice);
        return choice;
    }

    public void choose(Choice choice) {
        choiceHistory.add(choice);
        if (!currentObjects.contains(choice.getSourceObject())) {
            currentObjects.add(choice.getSourceObject());
        }
        if (choiceGroupHistory.contains(choice.getParentChoiceGroup())) {
            choiceGroupHistory.remove(choice.getParentChoiceGroup());
        }
        choiceGroupHistory.add(choice.getParentChoiceGroup());
    }

    private List<Choice> calculateChoice() {
        List<Choice> availableChoice = new LinkedList<Choice>();
        for (Choice choice: StoryBoard.storyChoice) {
            if (choice.isApplicable(choiceHistory, currentObjects, choiceGroupHistory)) {
                availableChoice.add(choice);
            }
        }
        return availableChoice;
    }
}
