import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 09/02/14.
 */
public class Choice {
    private StoryObject sourceObject;
    private StoryObject targetObject;
    private String outputText;
    private OutputGlue outputGlue;
    private List<ChoiceGroup> mostRecentGroups;
    private HashSet<StoryObject> dependsOnObjects;
    private ChoiceGroup parentChoiceGroup;

    public Choice() {
    }

    public void setSourceObject(StoryObject sourceObject) {
        this.sourceObject = sourceObject;
    }

    public StoryObject getSourceObject() {
        return sourceObject;
    }

    public void setTargetObject(StoryObject targetObject) {
        this.targetObject = targetObject;
    }

    public StoryObject getTargetObject() {
        return targetObject;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public String getOutputText(List<Choice> choiceHistory, List<StoryObject> currentObjects) {
        return outputText + ". ";
    }

    public void setOutputGlue(OutputGlue outputGlue) {
        this.outputGlue = outputGlue;
    }

    public OutputGlue getOutputGlue() {
        return outputGlue;
    }

    public void setMostRecentGroups(List<ChoiceGroup> mostRecentGroups) {
        this.mostRecentGroups = mostRecentGroups;
    }

    public List<ChoiceGroup> getMostRecentGroups() {
        return mostRecentGroups;
    }

    public void setDependsOnObjects(HashSet<StoryObject> dependsOnObjects) {
        this.dependsOnObjects = dependsOnObjects;
    }

    public HashSet<StoryObject> getDependsOnObjects() {
        return dependsOnObjects;
    }

    public boolean isApplicable(List<Choice> choiceHistory, List<StoryObject> currentObjects, List<ChoiceGroup> choiceGroupHistory) {
        return (!choiceHistory.contains(this) &&
                parentChoiceGroup.isApplicable(choiceGroupHistory) &&
                verifyDependsOnObjects(currentObjects) &&
                verifyMostRecentGroups(choiceGroupHistory));
    }

    private boolean verifyMostRecentGroups(List<ChoiceGroup> choiceGroupHistory) {
        if (mostRecentGroups != null) {
            if (mostRecentGroups.size() > choiceGroupHistory.size()) {
                return false;
            }
            for (int i = 0; i < mostRecentGroups.size(); i++) {
                if (mostRecentGroups.get(mostRecentGroups.size() - 1 - i) != choiceGroupHistory.get(choiceGroupHistory.size() - 1 - i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verifyDependsOnObjects(List<StoryObject> currentObjects) {
        if (dependsOnObjects != null) {
            for (StoryObject storyObject: dependsOnObjects) {
                if (!currentObjects.contains(storyObject)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getOutputAction() {
        if (targetObject == null) {
            return sourceObject.getDescription();
        } else {
            return sourceObject.getDescription() + " --> " + targetObject.getDescription();
        }
    }

    public ChoiceGroup getParentChoiceGroup() {
        return parentChoiceGroup;
    }

    public void setParentChoiceGroup(ChoiceGroup parentChoiceGroup) {
        this.parentChoiceGroup = parentChoiceGroup;
    }
}
